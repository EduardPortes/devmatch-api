package com.eduardoportes.devmatch_api.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.eduardoportes.devmatch_api.model.User;
import com.eduardoportes.devmatch_api.repository.custom.UserRepositoryCustom;
import com.eduardoportes.devmatch_api.repository.filter.UserFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<User> filter(UserFilter filter, Pageable pageable){

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> user = query.from(User.class);
        Join<Object, Object> profile = user.join("profile", JoinType.LEFT);
    
        List<Predicate> predicates = buildPredicates(filter, builder, user, profile);
        
        query.where(predicates.toArray(new Predicate[0]));
        query.distinct(true);

        List<User> results = executePagedQuery(query, pageable);
        Long total = executeCountQuery(filter, builder);

        return new PageImpl<>(results, pageable, total);
    }

    private List<Predicate> buildPredicates(UserFilter filter, CriteriaBuilder builder, 
                                            Root<User> user, Join<Object, Object> profile) {

        List<Predicate> predicates = new ArrayList<>();

        // User
        if(StringUtils.hasText(filter.getUsername())) {
            predicates.add(
                builder.like(
                    builder.lower(user.get("username")),
                    "%" + filter.getUsername().toLowerCase() + "%"
                )
            );
        }

        if(StringUtils.hasText(filter.getEmail())) {
            predicates.add(
                builder.like(
                    builder.lower(user.get("email")),
                    "%" + filter.getEmail().toLowerCase() + "%"
                )
            );
        }

        // Profile
        if(StringUtils.hasText(filter.getName())){
            predicates.add(
                builder.like(
                    builder.lower(profile.get("name")),
                    "%" + filter.getName().toLowerCase() + "%"
                )
            );
        }

        if (StringUtils.hasText(filter.getBio())) {
            predicates.add(
                builder.like(
                    builder.lower(profile.get("bio")), 
                    "%" + filter.getBio().toLowerCase() + "%"
                )
            );
        }

        if (StringUtils.hasText(filter.getLocation())) {
            predicates.add(
                builder.like(
                    builder.lower(profile.get("location")), 
                    "%" + filter.getLocation().toLowerCase() + "%"
                )
            );
        }

        // Related
        if (filter.getTechnologies() != null && !filter.getTechnologies().isEmpty()) {
            Join<Object, Object> techJoin = profile.join("technologies", JoinType.INNER);
            predicates.add(techJoin.get("name").in(filter.getTechnologies()));
        }
        if (filter.getInterests() != null && !filter.getInterests().isEmpty()) {
            Join<Object, Object> interestJoin = profile.join("interests", JoinType.INNER);
            predicates.add(interestJoin.get("name").in(filter.getInterests()));
        }

        // Age
        if (filter.getMinAge() != null || filter.getMaxAge() != null) {
            LocalDate today = LocalDate.now();
            
            if (filter.getMinAge() != null) {
                LocalDate maxBirthDate = today.minusYears(filter.getMinAge());
                predicates.add(builder.lessThanOrEqualTo(profile.get("birthDate"), maxBirthDate));
            }
            
            if (filter.getMaxAge() != null) {
                LocalDate minBirthDate = today.minusYears(filter.getMaxAge() + 1);
                predicates.add(builder.greaterThan(profile.get("birthDate"), minBirthDate));
            }
        }

        return predicates;
    }

    private List<User> executePagedQuery(CriteriaQuery<User> query, Pageable pageable) {
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        
        return typedQuery.getResultList();
    }

    private Long executeCountQuery(UserFilter filter, CriteriaBuilder builder) {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        Join<Object, Object> countProfile = countRoot.join("profile", JoinType.LEFT);
        
        List<Predicate> countPredicates = buildPredicates(filter, builder, countRoot, countProfile);
        
        countQuery.select(builder.countDistinct(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}

package com.eduardoportes.devmatch_api.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.eduardoportes.devmatch_api.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.eduardoportes.devmatch_api.model.Profile;
import com.eduardoportes.devmatch_api.repository.custom.ProfileRepositoryCustom;
import com.eduardoportes.devmatch_api.repository.filter.ProfileFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

@Repository
public class ProfileRepositoryImpl implements ProfileRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Profile> filter(ProfileFilter filter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Profile> query = builder.createQuery(Profile.class);
        Root<Profile> profile = query.from(Profile.class);
    
        List<Predicate> predicates = buildPredicates(filter, builder, profile);

        query.where(predicates.toArray(new Predicate[0]));
        query.distinct(true);

        List<Profile> results = executePagedQuery(query, pageable);
        Long total = executeCountQuery(filter, builder);

        return new PageImpl<>(results, pageable, total);
    }

    private List<Predicate> buildPredicates(ProfileFilter filter, CriteriaBuilder builder, Root<Profile> profile) {

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getBirthDateBegin() != null) {
            predicates.add(
                builder.greaterThanOrEqualTo(
                    profile.get("birthDate"), filter.getBirthDateBegin())
            );
        }

        if (filter.getBirthDateEnd() != null) {
            predicates.add(
                builder.lessThanOrEqualTo(
                    profile.get("birthDate"), filter.getBirthDateEnd())
            );
        }

        return predicates;
    }

    private List<Profile> executePagedQuery(CriteriaQuery<Profile> query, Pageable pageable) {
        return entityManager.createQuery(query)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();
    }

    private Long executeCountQuery(ProfileFilter filter, CriteriaBuilder builder) {
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Profile> profile = countQuery.from(Profile.class);
        
        countQuery.select(builder.count(profile));
        
        List<Predicate> predicates = buildPredicates(filter, builder, profile);
        countQuery.where(predicates.toArray(new Predicate[0]));
        countQuery.distinct(true);
        
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}

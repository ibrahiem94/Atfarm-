package com.atfarm.challenge.repository;

import com.atfarm.challenge.domain.FieldCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data  repository for the FieldCondition entity.
 */
@Repository
public interface FieldConditionRepository extends JpaRepository<FieldCondition, Long> {

    List<FieldCondition> findAllByOccurrenceAtLessThanEqualAndOccurrenceAtGreaterThanEqual(Instant endDate, Instant startDate);

}

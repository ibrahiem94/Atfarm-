package com.atfarm.challenge.service.impl;

import com.atfarm.challenge.domain.FieldCondition;
import com.atfarm.challenge.repository.FieldConditionRepository;
import com.atfarm.challenge.service.FieldService;
import com.atfarm.challenge.service.dto.FieldConditionDTO;
import com.atfarm.challenge.service.dto.VegetationDTO;
import com.atfarm.challenge.service.mapper.FieldConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;

/**
 * Service Implementation for managing {@link FieldCondition}.
 */
@Service
@Qualifier("FieldServiceImpl")
@Transactional
public class FieldServiceImpl implements FieldService {

	private final Logger log = LoggerFactory.getLogger(FieldServiceImpl.class);

	@Autowired
	private FieldConditionRepository fieldConditionRepository;

	@Autowired
	private FieldConditionMapper fieldConditionMapper;

	/**
	 * Save a fieldCondition.
	 *
	 * @param fieldConditionDTO the entity to save.
	 *
	 */
	@Override
	public void addFieldCondition(FieldConditionDTO fieldConditionDTO) {
		log.debug("Request to save FieldCondition : {}", fieldConditionDTO);
		FieldCondition fieldCondition = fieldConditionMapper.toEntity(fieldConditionDTO);
		fieldCondition = fieldConditionRepository.save(fieldCondition);
		fieldConditionMapper.toDto(fieldCondition);
	}

	/**
	 * Get all the fieldConditions between dates.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public VegetationDTO findStatisticsBetweenDates(Date endDate, Date startDate) {
		log.debug("Request to get all FieldConditions between dates");
		VegetationDTO vegetation = new VegetationDTO();
		DoubleSummaryStatistics statistics = fieldConditionRepository
				.findAllByOccurrenceAtLessThanEqualAndOccurrenceAtGreaterThanEqual(Instant.ofEpochMilli(endDate.getTime()), Instant.ofEpochMilli(startDate.getTime())).stream()
				.mapToDouble(FieldCondition::getVegetation).summaryStatistics();

		vegetation.setCount(statistics.getCount());
		vegetation.setSum(statistics.getSum());
		vegetation.setAvg(statistics.getAverage());
		vegetation.setMin(statistics.getMin());
		vegetation.setMax(statistics.getMax());

		return vegetation;
	}

	/**
	 * Get all the fieldConditions between dates.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	@Override
	public VegetationDTO getStatistics() {
		log.debug("Request to get all FieldConditions between dates");
		VegetationDTO vegetation = new VegetationDTO();
		DoubleSummaryStatistics statistics = fieldConditionRepository.findAll().stream()
				.mapToDouble(FieldCondition::getVegetation).summaryStatistics();

		vegetation.setCount(statistics.getCount());
		vegetation.setSum(statistics.getSum());
		vegetation.setAvg(statistics.getAverage());
		vegetation.setMin(statistics.getMin());
		vegetation.setMax(statistics.getMax());
		return vegetation;
	}

}

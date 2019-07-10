package com.atfarm.challenge.service;

import com.atfarm.challenge.service.dto.FieldConditionDTO;
import com.atfarm.challenge.service.dto.VegetationDTO;

import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Service interface for field statistics.
 */

public interface FieldService {

	Long ONE_MONTH_IN_MILLIS = 30 * 24 * 60 * 60 * 1000l;

	void addFieldCondition(FieldConditionDTO fieldCondition);

	VegetationDTO getStatistics();

	VegetationDTO findStatisticsBetweenDates(Date endDate, Date startDate);

	default boolean isFieldConditionExpired(FieldConditionDTO fieldCondition) {
		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		long epochInMillis = utc.toEpochSecond() * 1000;
		return fieldCondition.getOccurrenceAt().getTime() < (epochInMillis - ONE_MONTH_IN_MILLIS);
	}

}

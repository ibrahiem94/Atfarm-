package com.atfarm.challenge.service.impl;

import com.atfarm.challenge.service.FieldService;
import com.atfarm.challenge.service.dto.FieldConditionDTO;
import com.atfarm.challenge.service.dto.VegetationDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Qualifier("InMemoryFieldServiceImpl")
@Transactional
public class InMemoryFieldServiceImpl implements FieldService {

	private PriorityQueue<FieldConditionDTO> transactions;
	private volatile VegetationDTO vegetation;

	private Lock lock = new ReentrantLock();

	public InMemoryFieldServiceImpl() {
		transactions = new PriorityQueue<>();
		vegetation = new VegetationDTO();
	}

	@Override
	public void addFieldCondition(final FieldConditionDTO fieldCondition) {

		lock.lock();
		try {
			transactions.add(fieldCondition);
			addFieldConditionToStatistics(fieldCondition);
		} finally {
			lock.unlock();
		}
	}

	public VegetationDTO getStatistics() {
		removeExpiredFieldConditions();
		return vegetation;
	}

	private void addFieldConditionToStatistics(final FieldConditionDTO fieldCondition) {
		vegetation.setCount(vegetation.getCount() + 1);
		vegetation.setSum(
				BigDecimal.valueOf(vegetation.getSum() + fieldCondition.getVegetation()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		vegetation.setAvg(BigDecimal.valueOf(vegetation.getSum() / vegetation.getCount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		vegetation.setMin(vegetation.getCount() > 1 ? Math.min(vegetation.getMin(), fieldCondition.getVegetation())
				: fieldCondition.getVegetation());
		vegetation.setMax(vegetation.getCount() > 1 ? Math.max(vegetation.getMax(), fieldCondition.getVegetation())
				: fieldCondition.getVegetation());
	}

	private void removeTransactionFromStatistics(final FieldConditionDTO fieldCondition) {

		if (vegetation.getCount() == 1) {
			vegetation.reset();
		} else {
			vegetation.setCount(vegetation.getCount() - 1);
			vegetation.setSum(vegetation.getSum() - fieldCondition.getVegetation());
			vegetation.setAvg(vegetation.getSum() / vegetation.getCount());
			vegetation.setMin(getMin());
			vegetation.setMax(getMax());
		}
	}

	private double getMin() {
		if (vegetation.getCount() == 0) {
			return 0;
		}
		return transactions.stream().min(Comparator.comparing(FieldConditionDTO::getVegetation)).get().getVegetation();
	}

	private double getMax() {
		if (vegetation.getCount() == 0) {
			return 0;
		}
		return transactions.stream().max(Comparator.comparing(FieldConditionDTO::getVegetation)).get().getVegetation();
	}

	public void removeExpiredFieldConditions() {
		FieldConditionDTO fieldCondition = transactions.peek();
		while (fieldCondition != null && isFieldConditionExpired(fieldCondition)) {
			lock.lock();
			try {
				fieldCondition = transactions.peek();
				if (isFieldConditionExpired(fieldCondition)) {
					removeTransactionFromStatistics(transactions.poll());
				}
			} finally {
				lock.unlock();
			}
			fieldCondition = transactions.peek();
		}
	}

	@Override
	public VegetationDTO findStatisticsBetweenDates(Date endDate, Date startDate) {
		return null;
	}
}

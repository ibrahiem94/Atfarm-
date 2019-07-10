package com.atfarm.challenge.service.dto;

public class VegetationDTO {

	private long count;
	private double sum;
	private double avg;
	private double min;
	private double max;

	public VegetationDTO() {
	}

	/**
	 * @param count
	 * @param sum
	 * @param avg
	 * @param min
	 * @param max
	 */
	public VegetationDTO(long count, double sum, double avg, double min, double max) {
		this.count = count;
		this.sum = sum;
		this.avg = avg;
		this.min = min;
		this.max = max;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the sum
	 */
	public double getSum() {
		return sum;
	}

	/**
	 * @param sum the sum to set
	 */
	public void setSum(double sum) {
		this.sum = sum;
	}

	/**
	 * @return the avg
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * @param avg the avg to set
	 */
	public void setAvg(double avg) {
		this.avg = avg;
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * reset all attributes to 0
	 */
	public void reset() {
		setCount(0);
		setMin(0);
		setMax(0);
		setAvg(0);
		setSum(0);
	}
}

package com.Ashutosh.microservices.limitsservice.bean;

public class LimitsConfiguration {
	private int min;
	private int max;
	
	protected LimitsConfiguration() {
		super();
	}
	public LimitsConfiguration(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	

}

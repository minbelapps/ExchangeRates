package lt.mb.homework.currencies.beans;

public class ExchangeRateBean {
	private String currency;
	private Float value1;
	private Float value2;
	private Float diff;
	private Float diffRatio;
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Float getValue1() {
		return value1;
	}
	public void setValue1(Float value1) {
		this.value1 = value1;
	}
	public Float getValue2() {
		return value2;
	}
	public void setValue2(Float value2) {
		this.value2 = value2;
	}
	public Float getDiff() {
		return diff;
	}
	public void setDiff(Float diff) {
		this.diff = diff;
	}
	public Float getDiffRatio() {
		return diffRatio;
	}
	public void setDiffRatio(Float diffRatio) {
		this.diffRatio = diffRatio;
	}
	
	
	
}

package lt.mb.homework.currencies.beans;

import java.util.Date;
import java.util.List;

public class ExchangeRateResultBean {
	private List<ExchangeRateBean> exchangeRates;
	private Date dateFrom;
	private Date dateTo;
	public List<ExchangeRateBean> getExchangeRates() {
		return exchangeRates;
	}
	public void setExchangeRates(List<ExchangeRateBean> exchangeRates) {
		this.exchangeRates = exchangeRates;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	
}

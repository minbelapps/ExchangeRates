package lt.mb.homework.currencies.services;

import java.util.Date;
import java.util.Map;

import lt.mb.homework.currencies.beans.CurrDescriptionBean;
import lt.mb.homework.currencies.beans.ExchangeRateResultBean;

public interface ExchangeRatesService {
	ExchangeRateResultBean getExchangeRates(Date from, Date to);

	ExchangeRateResultBean getExchangeRates(Date date);

	Map<String, CurrDescriptionBean> getCurrenciesDescriptions();
}

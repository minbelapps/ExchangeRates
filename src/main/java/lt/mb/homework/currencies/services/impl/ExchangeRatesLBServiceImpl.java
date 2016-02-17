package lt.mb.homework.currencies.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lt.mb.homework.currencies.beans.CurrDescriptionBean;
import lt.mb.homework.currencies.beans.ExchangeRateBean;
import lt.mb.homework.currencies.beans.ExchangeRateResultBean;
import lt.mb.homework.currencies.cashe.SimpleCashe;
import lt.mb.homework.currencies.clients.bankoflithuania.BankOfLithuaniaClient;
import lt.mb.homework.currencies.clients.bankoflithuania.CurrencyDescription;
import lt.mb.homework.currencies.clients.bankoflithuania.FxRate;
import lt.mb.homework.currencies.clients.bankoflithuania.Language;
import lt.mb.homework.currencies.services.ExchangeRatesService;
import lt.mb.utils.time.TimeUtils;

public class ExchangeRatesLBServiceImpl implements ExchangeRatesService {

	private static final String DESCRIPTIONS = "DESCRIPTIONS";
	private static final String EXCHANGE_RATES = "EXCHANGE_RATES";
	private static final String DESCRIPTION_LANGUAGE = "en";

	public ExchangeRateResultBean getExchangeRates(Date dateFrom, Date dateTo) {
		ExchangeRateResultBean result = new ExchangeRateResultBean();
		result.setDateFrom(dateFrom);
		result.setDateTo(dateTo);

		List<FxRate> rateListFrom = getExchangeRatesUsingCashe(dateFrom);
		List<FxRate> rateListTo = getExchangeRatesUsingCashe(dateTo);

		List<ExchangeRateBean> transformed = (transform(rateListFrom, rateListTo));
		order(transformed);
		result.setExchangeRates(transformed);
		return result;
	}

	public ExchangeRateResultBean getExchangeRates(Date date) {
		if (date.getTime() > System.currentTimeMillis()) {
			date = new Date();
		}
		Date oneDayBefore = TimeUtils.oneDayBefore(date);

		return getExchangeRates(oneDayBefore, date);
	}

	public Map<String, CurrDescriptionBean> getCurrenciesDescriptions() {
		@SuppressWarnings("unchecked")
		Map<String, CurrDescriptionBean> result = SimpleCashe.get(DESCRIPTIONS, Map.class);
		if (result == null) {
			BankOfLithuaniaClient client = new BankOfLithuaniaClient();
			List<CurrencyDescription> descriptions = client.getCurrenciesDescriptions();
			result = transform(descriptions);
			SimpleCashe.set(DESCRIPTIONS, result);
		}
		return result;
	}

	private Map<String, CurrDescriptionBean> transform(List<CurrencyDescription> currencies) {
		Map<String, CurrDescriptionBean> result = new HashMap<String, CurrDescriptionBean>();
		for (CurrencyDescription description : currencies) {
			CurrDescriptionBean transformedDescription = new CurrDescriptionBean();
			transformedDescription.setCurr(description.getCurrency());
			List<Language> languages = description.getLanguges();
			for (Language lng : languages) {
				if (DESCRIPTION_LANGUAGE.equals(lng.getLng())) {
					transformedDescription.setDescription(lng.getDescription());
					result.put(description.getCurrency(), transformedDescription);
					break;
				}
			}
		}
		return result;
	}

	private List<ExchangeRateBean> transform(List<FxRate> rateListFrom, List<FxRate> rateListTo) {

		List<ExchangeRateBean> joinedList = new ArrayList<ExchangeRateBean>();
		Map<String, FxRate> mapedFrom = new HashMap<String, FxRate>();
		Map<String, FxRate> mapedTo = new HashMap<String, FxRate>();
		for (FxRate rate : rateListFrom) {
			mapedFrom.put(rate.getCcyAmt().getCurr(), rate);
		}
		for (FxRate rate : rateListTo) {
			mapedTo.put(rate.getCcyAmt().getCurr(), rate);
		}

		for (Entry<String, FxRate> entry : mapedFrom.entrySet()) {
			ExchangeRateBean exchangeRate = new ExchangeRateBean();
			float value1 = entry.getValue().getCcyAmt().getAmount();
			FxRate rateTo = mapedTo.get(entry.getKey());
			if (rateTo == null) {
				continue;
			}
			float value2 = rateTo.getCcyAmt().getAmount();
			exchangeRate.setCurrency(entry.getKey());
			exchangeRate.setValue1(value1);
			exchangeRate.setValue2(value2);
			exchangeRate.setDiff(value2 - value1);
			if (value1 != 0) {
				exchangeRate.setDiffRatio(value2 / value1 - 1);
			}
			joinedList.add(exchangeRate);
		}
		return joinedList;
	}

	private List<FxRate> getExchangeRatesUsingCashe(Date date) {
		String dateFormated = TimeUtils.toString(date, TimeUtils.DATE_FORMAT_SHORT);
		String key = EXCHANGE_RATES + dateFormated;

		@SuppressWarnings("unchecked")
		List<FxRate> result = SimpleCashe.get(key, List.class);
		if (result == null) {
			BankOfLithuaniaClient client = new BankOfLithuaniaClient();
			result = client.getExchangeRates(date);
			Date end = null;
			if (date.getTime() > TimeUtils.differentInDays(date, -3).getTime()) {
				if (date.getTime() < TimeUtils.differentInDays(date, -1).getTime()) {
					end = TimeUtils.different(new Date(), Calendar.MINUTE, 10);
				} else {
					end = TimeUtils.different(new Date(), Calendar.MINUTE, 3);
				}
			}
			SimpleCashe.set(key, result, end);
		}
		return result;
	}

	private void order(List<ExchangeRateBean> rates) {
		Comparator<ExchangeRateBean> comparator = new Comparator<ExchangeRateBean>() {
			public int compare(ExchangeRateBean o1, ExchangeRateBean o2) {
				if (o1 == null || o1.getDiffRatio() == null) {
					return -1;
				}
				if (o2 == null || o2.getDiffRatio() == null) {
					return 0;
				}
				return o2.getDiffRatio().compareTo(o1.getDiffRatio());
			}
		};
		Collections.sort(rates, comparator);
	}
}

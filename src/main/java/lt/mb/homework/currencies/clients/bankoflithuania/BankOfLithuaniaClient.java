package lt.mb.homework.currencies.clients.bankoflithuania;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.transform.stream.StreamSource;

import org.springframework.web.client.RestTemplate;

import lt.mb.utils.time.TimeUtils;

public class BankOfLithuaniaClient {
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public List<FxRate> getExchangeRates(Date date) {
		List<FxRate> currentList = null;
		try {
			currentList = Restfull.getExchangeRates(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return currentList;
	}

	public List<CurrencyDescription> getCurrenciesDescriptions() {
		List<CurrencyDescription> descriptions = null;
		try {
			descriptions = Restfull.getDescriptions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return descriptions;
	}

}

class Restfull {

	public static final String URL_EXCHANGE_RATE = "http://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRates?tp=EU&dt={dateFormated}";
	public static final String URL_CURRENCIES = "http://www.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx/getListOfCurrencies";

	static List<FxRate> getExchangeRates(Date date) {
		List<FxRate> rateList = null;
		try {
			String dateFormated = TimeUtils.toString(date, BankOfLithuaniaClient.DATE_FORMAT);

			Map<String, String> map = new HashMap<String, String>();
			map.put("dateFormated", dateFormated);

			rateList = getResultListUsingWrapper(URL_EXCHANGE_RATE, FxRate.class, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rateList;

	}

	static List<CurrencyDescription> getDescriptions() {
		List<CurrencyDescription> rateList = null;
		try {
			rateList = getResultListUsingWrapper(URL_CURRENCIES, CurrencyDescription.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rateList;

	}

	private static <T> List<T> getResultListUsingWrapper(String url, Class<T> clazz, Map<String, String> map)
			throws JAXBException {
		RestTemplate rest = new RestTemplate();
		String xml = null;
		if (map == null) {
			xml = rest.getForObject(url, String.class);
		} else {
			xml = rest.getForObject(url, String.class, map);
		}

		StringReader reader = new StringReader(xml);
		JAXBContext jaxbContext = JAXBContext.newInstance(Wrapper.class, clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		return unmarshalusingWrapper(unmarshaller, clazz, reader);
	}

	private static <T> List<T> unmarshalusingWrapper(Unmarshaller unmarshaller, Class<T> clazz, Reader reader)
			throws JAXBException {

		StreamSource streamSource = new StreamSource(reader);
		@SuppressWarnings("unchecked")
		Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(streamSource, Wrapper.class).getValue();
		List<T> result = wrapper.getItems();
		return result;

	}
}

class Wrapper<T> {

	private List<T> items;

	public Wrapper() {
		items = new ArrayList<T>();
	}

	public Wrapper(List<T> items) {
		this.items = items;
	}

	@XmlAnyElement(lax = true)
	public List<T> getItems() {
		return items;
	}

}

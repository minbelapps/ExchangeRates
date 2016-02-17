package lt.mb.homework.currencies.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lt.mb.homework.currencies.beans.CurrDescriptionBean;
import lt.mb.homework.currencies.beans.ExchangeRateResultBean;
import lt.mb.homework.currencies.services.ExchangeRatesService;
import lt.mb.homework.currencies.services.impl.ExchangeRatesLBServiceImpl;
import lt.mb.utils.time.TimeUtils;

@Controller
public class FirstController {

	/**
	 * @param date
	 * @return index page
	 * @throws ParseException
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(@ModelAttribute("model") ModelMap model,
			@RequestParam(value = "date", required = false) String dateString) throws ParseException {
		Date date = null;
		if (dateString == null) {
			date = new Date();
		} else {
			date = TimeUtils.toDate(dateString, TimeUtils.DATE_FORMAT_SHORT);
		}
		ExchangeRatesService service = new ExchangeRatesLBServiceImpl();
		ExchangeRateResultBean exchangeRates = service.getExchangeRates(date);
		Map<String, CurrDescriptionBean> currenciesDescrptions = service.getCurrenciesDescriptions();

		model.addAttribute("exchangeRates", exchangeRates);
		model.addAttribute("currenciesDescrptions", currenciesDescrptions);
		model.addAttribute("date", TimeUtils.toString(date, TimeUtils.DATE_FORMAT_SHORT));
		model.addAttribute("hardcoded", false);
		return "index";
	}

	/**
	 * 
	 * @param date
	 * @return Redirect to /index page
	 */
	@RequestMapping(value = "/changeDate", method = RequestMethod.POST)
	public String add(@RequestParam("date") String date) {

		return "redirect:index.html";
	}

	/**
	 * 
	 * @return index page
	 * @throws ParseException
	 */
	@RequestMapping(value = "/hardcodedDay", method = RequestMethod.GET)
	public String add(@ModelAttribute("model") ModelMap model) throws ParseException {
		Date date = TimeUtils.toDate("2014-12-31", TimeUtils.DATE_FORMAT_SHORT);
		ExchangeRatesService service = new ExchangeRatesLBServiceImpl();
		ExchangeRateResultBean exchangeRates = service.getExchangeRates(date);
		Map<String, CurrDescriptionBean> currenciesDescrptions = service.getCurrenciesDescriptions();

		model.addAttribute("exchangeRates", exchangeRates);
		model.addAttribute("currenciesDescrptions", currenciesDescrptions);
		model.addAttribute("date", TimeUtils.toString(date, TimeUtils.DATE_FORMAT_SHORT));
		model.addAttribute("hardcoded", true);
		return "index";
	}

}
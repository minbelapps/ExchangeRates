package lt.mb.homework.currencies.clients.bankoflithuania;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyDescription {
	@XmlElement(name = "currency")
	private String currency;

	@XmlElement(name = "description")
	private List<Language> languges;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<Language> getLanguges() {
		return languges;
	}

	public void setLanguges(List<Language> languges) {
		this.languges = languges;
	}

}

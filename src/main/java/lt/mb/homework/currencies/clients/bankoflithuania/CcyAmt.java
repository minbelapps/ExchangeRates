package lt.mb.homework.currencies.clients.bankoflithuania;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;


@XmlAccessorType(XmlAccessType.FIELD)
public class CcyAmt {
	@XmlElement (name = "Ccy", namespace = "http://www.lb.lt/WebServices/FxRates")
	private String curr;
	
	@XmlElement (name = "Amt", namespace = "http://www.lb.lt/WebServices/FxRates")
	private float amount;

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	
}

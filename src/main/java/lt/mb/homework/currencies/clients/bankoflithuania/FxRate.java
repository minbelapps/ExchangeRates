package lt.mb.homework.currencies.clients.bankoflithuania;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FxRate", namespace = "http://www.lb.lt/WebServices/FxRates")
@XmlAccessorType(XmlAccessType.FIELD)
public class FxRate {

	@XmlElement(name = "Dt", namespace = "http://www.lb.lt/WebServices/FxRates")
	private Date date;

	@XmlElement(name = "CcyAmt", namespace = "http://www.lb.lt/WebServices/FxRates")
	private CcyAmt ccyAmt;

	@XmlElement(name = "Tp", namespace = "http://www.lb.lt/WebServices/FxRates")
	private String type;

	public CcyAmt getCcyAmt() {
		return ccyAmt;
	}

	public void setCcyAmt(CcyAmt ccyAmt) {
		this.ccyAmt = ccyAmt;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

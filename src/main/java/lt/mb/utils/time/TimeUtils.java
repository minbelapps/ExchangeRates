package lt.mb.utils.time;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {

	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

	public static Date oneDayBefore(Date date) {
		return differentInDays(date, -1);
	}

	public static Date differentInDays(Date date, int tymeDiff) {
		return different(date, Calendar.DAY_OF_MONTH, tymeDiff);
	}

	public static Date different(Date date, int calendarTimeType, int tymeDiff) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarTimeType, tymeDiff);
		return cal.getTime();
	}

	public static String toStringShort(Date date) {
		return toString(date, DATE_FORMAT_SHORT);
	}

	public static String toString(Date date, String dateFormat) {
		SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);
		return dateFormater.format(date);
	}

	public static Date toDate(String dateString, String dateFormat) throws ParseException {
		SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);
		return dateFormater.parse(dateString);
	}
}

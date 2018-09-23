package meeting.scheduler.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateTimeConversionUtils {

	private DateTimeConversionUtils() {

	}

	/**
	 * Convert format 2018-12-02 17:18:23 into date
	 * 
	 * @param value
	 * @return
	 */
	public static Date formatDateFullDate(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convert format 2018-12-02 into date
	 * 
	 * @param value
	 * @return
	 */
	public static Date formatDateyyyy_mm_dd(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convert 09:10 to 0910
	 * 
	 * @param timeString
	 * @return
	 */
	public static String convertIntoMilataryTime(String timeString) {
		DateFormat dateFormat1 = new SimpleDateFormat("HH:mm"); // HH for hour
																// of the day (0
		Date date = null;
		try {
			date = dateFormat1.parse(timeString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateFormat2 = new SimpleDateFormat("HHmm");
		return dateFormat2.format(date);
	}
	/**
	 * Convert 0910 to 09:10
	 * 
	 * @param timeString
	 * @return
	 */
	public static String convertTwentyFoursHoursTime(String timeString) {
		DateFormat dateFormat1 = new SimpleDateFormat("HHmm"); // HH for hour
																// of the day (0
		Date date = null;
		try {
			date = dateFormat1.parse(timeString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
		return dateFormat2.format(date);
	}
}

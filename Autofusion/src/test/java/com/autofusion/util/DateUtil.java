package com.autofusion.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.autofusion.constants.Constants;
import com.autofusion.constants.KeywordConstant;

public class DateUtil {
	// private Log log = null;

	public DateUtil() {
		// log = Log.getInstance();
	}

	public String getCurrentDate(String format) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);

	}

	public String getCurrentDate() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		return sdf.format(date);
	}

	public String getCurrentDateMillis() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");

		return sdf.format(date);
	}

	public String getDateDifferenceInSec(String format, String current, String previous) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		java.util.Date now;
		java.util.Date date;
		long diff = 0;
		try {

			now = df.parse(current);
			date = df.parse(previous);

			diff = (now.getTime() - date.getTime()) / 1000;

		} catch (ParseException e) {
			// log.debug("unable to get diference in secs for
			// current::"+current+" previous:: "+previous);
			return "0";
		}
		return (diff + "");

	}

	public String getDateDifferenceInSec(String current, String previous) {
		String format = "yyyyMMddHHmmss";
		SimpleDateFormat df = new SimpleDateFormat(format);
		java.util.Date now;
		java.util.Date date;
		long diff = 0;
		try {

			now = df.parse(current);
			date = df.parse(previous);

			diff = (now.getTime() - date.getTime()) / 1000;

		} catch (ParseException e) {
			// log.debug("unable to get diference in secs for
			// current::"+current+" previous:: "+previous);
			return "0";
		}
		return (diff + "");
	}

	public static long getNumberofDaysDiffrence(String inputDate) {

		Pattern dateFrmtPtrn = Pattern
				.compile("[a-z][a-z][a-z] [a-z][a-z][a-z] (0?[1-9]|[12][0-9]|3[01]), ((19|20)\\d\\d)");

		long diffDays = 0;
		inputDate = inputDate.trim().toLowerCase();
		String inputDateWithoutDayName = inputDate.substring(4);
		java.util.regex.Matcher mtch = dateFrmtPtrn.matcher(inputDate);
		if (mtch.matches()) {

			Date d1 = null;
			Date d2 = null;

			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
			try {

				String currentDate = sdf.format(date);
				currentDate = currentDate.toLowerCase();
				date = sdf.parse(inputDateWithoutDayName);
				String givenDate = sdf.format(date);
				givenDate = givenDate.toLowerCase();
				d1 = sdf.parse(currentDate);
				d2 = sdf.parse(givenDate);
				long diff = d2.getTime() - d1.getTime();
				diffDays = diff / (1000 * 60 * 60 * 24);
				// System.out.println(diffDays);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return diffDays;

	}

	public String getDateDifferenceInMilliSec(String current, String previous) {
		String format = "yyyyMMddHHmmssSS";
		SimpleDateFormat df = new SimpleDateFormat(format);
		java.util.Date now;
		java.util.Date date;
		long diff = 0;
		try {

			now = df.parse(current);
			date = df.parse(previous);

			diff = (now.getTime() - date.getTime());

		} catch (ParseException e) {
			// log.debug("unable to get diference in secs for
			// current::"+current+" previous:: "+previous);
			return "0";
		}
		return (diff + "");
	}

	public String convertSecToLongFormat(String sec) {
		long diff = Long.parseLong(sec);
		long day = 0;
		long hour = 0;
		long min = 0;
		long s = 0;
		String format;

		day = diff / (24 * 60 * 60 * 1000);

		hour = (diff / (60 * 60 * 1000) - day * 24);

		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);

		s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

		if (day == 0) {
			if (hour == 0) {
				if (min == 0) {
					format = s + " seconds";
				} else {
					format = min + " minutes " + s + " seconds";
				}
			} else {
				format = hour + " hours " + min + " minutes " + s + " seconds ";
			}
		} else {
			format = day + " days " + hour + " hours " + min + " minutes " + s + " seconds ";
		}

		return format;
	}

	public String getTimeString(long duration) {
		String time = "";
		int hours = (int) duration / (60 * 60);
		int mins = (int) (duration % (60 * 60)) / 60;
		int secs = (int) (duration % (60 * 60)) % 60;
		time = (hours != 0 ? (hours + " hrs ") : "") + (mins != 0 ? (mins + " mins ") : "")
				+ (secs != 0 ? (secs + " secs") : "");
		return time;
	}

	/**
	 * @author Nitish Jaiswal
	 * @date 27 November,16
	 * @description Validate date should be in format DDD DD MMM, YYYY and it
	 *              Should be always future date
	 * @return PASS/FAIL
	 */
	public static String validateFutureDateFormat(String inputDate) {
		Pattern dateFrmtPtrn = Pattern
				.compile("[a-z][a-z][a-z] [a-z][a-z][a-z] (0?[1-9]|[12][0-9]|3[01]), ((19|20)\\d\\d)");
		long diffDays = 0;
		inputDate = inputDate.trim().toLowerCase();
		String inputDateWithoutDayName = inputDate.substring(4);
		Matcher mtch = dateFrmtPtrn.matcher(inputDate);
		if (mtch.matches()) {

			Date d1 = null;
			Date d2 = null;

			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
			try {

				String currentDate = sdf.format(date);
				currentDate = currentDate.toLowerCase();
				date = sdf.parse(inputDateWithoutDayName);
				String givenDate = sdf.format(date);
				givenDate = givenDate.toLowerCase();
				d1 = sdf.parse(currentDate);
				d2 = sdf.parse(givenDate);
				long diff = d2.getTime() - d1.getTime();
				diffDays = diff / (1000 * 60 * 60 * 24);
				if (diffDays >= 1) {
					return Constants.FAIL + ": Date is future date with diffrence of - '" + diffDays + "' Day.'";
				} else {
					return Constants.FAIL + ": Date is not future date with diffrence of - '" + diffDays + "' Day.'";
				}
			} catch (Throwable e) {
				e.printStackTrace();
				return Constants.FAIL + ": Error while validating future date - " + KeywordConstant.ELEMENT_LOCATOR
						+ " : " + e.getMessage();
			}
		}
		return Constants.FAIL + ": Format for Date displayed on the UI is not the same as was earlier - '";
	}

	// public String getSecAdd(String current, String previous){
	// String format = "yyyyMMddHHmmss";
	// SimpleDateFormat df = new SimpleDateFormat (format);
	// java.util.Date now;
	// java.util.Date date;
	// long add = 0;
	// try {
	// now = df.parse (current);
	// date = df.parse (previous);
	// add = now.getTime () + date.getTime ();
	//
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return (add+"");
	// }
	//
	// public String getSecAdd(String format, String current, String previous){
	// SimpleDateFormat df = new SimpleDateFormat (format);
	// java.util.Date now;
	// java.util.Date date;
	// long add = 0;
	// try {
	// now = df.parse (current);
	// date = df.parse (previous);
	// add = now.getTime () + date.getTime ();
	//
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return (String)(add+"");
	// }
}

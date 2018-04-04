package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	
	public static Integer getDayOfWeek(Date date) throws ParseException{		
		return getParam(date, Calendar.DAY_OF_WEEK);
	}
	
	public static Integer getDayOfYear(Date date) throws ParseException{		
		return getParam(date, Calendar.DAY_OF_YEAR);
	}
		
	public static Integer getDayOfMonth(Date date) throws ParseException{		
		return getParam(date, Calendar.DAY_OF_MONTH);
	}
	
	public static Integer getYear(Date date) throws ParseException{		
		return getParam(date, Calendar.YEAR);
	}
	
	public static Integer getMonth(Date date) throws ParseException{		
		return getParam(date, Calendar.MONTH);
	}
	
	public static Date getFirstDateOfMonth(Integer year, Integer month, String datePattern) throws ParseException{
				
		return getBorderDayOfMonth("first", year, month,datePattern);
	}
	
	public static Date getLastDateOfMonth(Integer year, Integer month, String datePattern) throws ParseException{
				
		
		return getBorderDayOfMonth("last", year, month,datePattern);
				
	}
		
	public static boolean isGreather(Date initDate, Date endDate){
		
		Calendar c = Calendar.getInstance();
		c.setTime(initDate);		
		
		Calendar ce = Calendar.getInstance();
		ce.setTime(endDate);	
		
		return c.compareTo(ce) > 0 ;
	}
	
	public static boolean isLess(Date initDate, Date endDate){
		Calendar c = Calendar.getInstance();
		c.setTime(initDate);		
		
		Calendar ce = Calendar.getInstance();
		ce.setTime(endDate);	
		
		return c.compareTo(ce) < 0 ;
	}
	
	public static boolean isSame(Date initDate, Date endDate){
		Calendar c = Calendar.getInstance();
		c.setTime(initDate);		
		
		Calendar ce = Calendar.getInstance();
		ce.setTime(endDate);	
		
		return c.compareTo(ce) == 0 ;
	}
	
	
	public static String toString_Date(Date date, String pattern){
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern );		
		return sdf.format(date);		
	}
	
	public static Date toDate_String(String date, String pattern  ) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);		 
		return sdf.parse(date);
	}

	public static boolean isSameMonth(Date dateMonth, Integer month) throws ParseException{
		
		//return isSameParam(Calendar.MONTH, dateMonth, month);
		return month == getMonth(dateMonth);
	}
	
	public static boolean isSameYear(Date dateYear, Integer year) throws ParseException{
		
		
		return year.toString().equals(getYear(dateYear).toString());
	}
	
	public static boolean isSameDay(Date dateDay, Integer day) throws ParseException{
		
		//return isSameParam(Calendar.DAY_OF_MONTH, dateDay, day);
		return day == getDayOfMonth(dateDay);
	}
	
	public static Date sumDays(Date date, Integer days ) throws ParseException{
		
		return sumDate(date, days, Calendar.DAY_OF_MONTH);
	}

	public static Date sumMonths(Date date, Integer months ) throws ParseException{
		
		return sumDate(date, months, Calendar.MONTH);
	}
	
	public static Date sumYears(Date date, Integer years ) throws ParseException{
		
		return sumDate(date, years, Calendar.YEAR);
	}
		
	//Metodos privados auxiliares en la conversion de informacion
	private static Date getBorderDayOfMonth(String position, Integer year, Integer month, String datePattern) throws ParseException{
				
		
		if( month < 1 || month > 12)			
			throw new ParseException("El mes ingresado no es valido", 0);
				
		
		Calendar c = Calendar.getInstance();		
		
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
			
		c.set(Calendar.DAY_OF_MONTH,1);
		
		
		if(position.equals("first")){
			
			return c.getTime();
		}		
		
		int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH); 
		
		c.clear(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH,lastDayOfMonth);		
		
		return c.getTime();
	}
	
	/*
	 * Codigo duplicado
	private static boolean isSameParam(Integer sourceType, Date dateMonth, Integer source) throws ParseException{
		
		Calendar c = Calendar.getInstance();
		c.setTime(dateMonth);		
		Integer compareValue = null;
		
		switch (sourceType) {
		case Calendar.DAY_OF_MONTH:
			compareValue = c.get(Calendar.DAY_OF_MONTH);
			break;
		
		case Calendar.MONTH:
			compareValue = c.get(Calendar.MONTH) + 1;
			break;

		case Calendar.YEAR:
			compareValue = c.get(Calendar.YEAR);
			break;
		default:	
			throw new ParseException("El valor de sourceType no coincide con los valoresPermitidos",0);
		}
		
		
		return  source == compareValue; 
	}*/

	private static Date sumDate(Date sourceDate, Integer sumValue , Integer sourceType) throws ParseException{
		
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDate);		
		Integer sumParam = null;
		
		switch (sourceType) {
		case Calendar.DAY_OF_MONTH:
			sumParam = Calendar.DAY_OF_MONTH;
			break;
		
		case Calendar.MONTH:
			sumParam = Calendar.MONTH;
			break;

		case Calendar.YEAR:
			sumParam = Calendar.YEAR;
			break;
		default:	
			throw new ParseException("El valor de sourceType no coincide con los valoresPermitidos",0);
		}
		
		c.add(sumParam, sumValue);
		
		
		return  c.getTime(); 

	}

	private static Integer getParam(Date sourceDate, Integer sourceType) throws ParseException{
		
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDate);		
		Integer returnParam = null;
		
		switch (sourceType) {
		case Calendar.DAY_OF_MONTH:
			returnParam = c.get(Calendar.DAY_OF_MONTH);
			break;
		
		case Calendar.MONTH:
			returnParam = c.get(Calendar.MONTH) + 1;
			break;

		case Calendar.YEAR:
			returnParam = c.get(Calendar.YEAR);
			break;
			
		case Calendar.DAY_OF_WEEK:
			returnParam = c.get(Calendar.DAY_OF_WEEK) - 1;
			break;	
			
		case Calendar.DAY_OF_YEAR:
			returnParam = c.get(Calendar.DAY_OF_YEAR);
			break;	
		default:	
			throw new ParseException("El valor de sourceType no coincide con los valoresPermitidos",0);
		}	
	
		return returnParam;
	}
	
	
	public static Date formatDate(Date date , String pattern) throws ParseException {
		
		System.out.println("date -> " + date);
		System.out.println("pattern -> " + pattern);
		
		return toDate_String(toString_Date(date, pattern), pattern) ;
		 
		
	}
	
	public static void main(String [] args) throws ParseException{
		
		Date date = new Date();
		
		System.out.println("formatDate -> " + DateUtils.formatDate(date, "yyyy/MM/dd 00:00:00") );
		
		System.out.println("toDate_String -> " + DateUtils.toDate_String("21/03/1981", "dd/MM/yyyy") );
		System.out.println("toString_Date** -> " + DateUtils.toString_Date(date, "yyyy/MM/dd") );
		
		//OK System.out.println("getDayOfMonth -> " + DateUtils.getDayOfMonth(date));
		//OK System.out.println("getDayOfWeek -> " + DateUtils.getDayOfWeek(date));
		//OK System.out.println("getYear -> " + DateUtils.getYear(date));		
		//? System.out.println("getDayOfYear -> " + DateUtils.getDayOfYear(date));		
		//OK System.out.println("getMonth -> " + DateUtils.getMonth(date));		
		//OK System.out.println("getFirstDateOfMonth -> " + DateUtils.getFirstDateOfMonth(2014, 6, "dd/MM/yyyy"));
		//OK System.out.println("getLastDateOfMonth -> " + DateUtils.getLastDateOfMonth(2014, 6, "dd/MM/yyyy"));

		//OK System.out.println("isSameDay (22)-> " + DateUtils.isSameDay(date, 22));
		//OK System.out.println("isSameMonth(6) -> " + DateUtils.isSameMonth(date, 6));
		//OK System.out.println("isSameYear (2014)-> " + DateUtils.isSameYear(date, 2014));
		
		//OK System.out.println("sumDays(-3) -> " + DateUtils.sumDays(date, -3));
		//OK System.out.println("sumMonts(-3) -> " + DateUtils.sumMonths(date, -3));
		
		//OK System.out.println("sumYears(-3) -> " + DateUtils.sumYears(date, -3));
		
		
		/*String pattern = "dd/MM/yyyy";
		Date initDate = DateUtils.toDate_String("23/06/2014", pattern);
		System.out.println("initDate -> " + DateUtils.toString_Date(initDate, pattern));
		
		Date endDate = DateUtils.toDate_String("24/06/2014", "dd/MM/yyyy");
		System.out.println("endDate -> " + DateUtils.toString_Date(endDate, pattern));
		
		System.out.println("initDate > endDate -> " + DateUtils.isGreather(initDate, endDate));
		System.out.println("initDate < endDate -> " + DateUtils.isLess(initDate, endDate));
		System.out.println("initDate == endDate -> " + DateUtils.isLess(initDate, endDate));

		/*
		
		*/
		
		
		
		
		
	};
}

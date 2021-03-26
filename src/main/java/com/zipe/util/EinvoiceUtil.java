package com.zipe.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EinvoiceUtil {

	/**
	 * 取得日期當月與前月或後月的字串陣列
	 *
	 * @param date
	 * @param isBefore
	 * @return
	 */
	public static String[] getTwoMonth(Date date, boolean isBefore) {
		String[] dates = null;
		Calendar c = Calendar.getInstance();
		if(date!=null)
			c.setTime(date);

		int _month = c.get(Calendar.MONTH);
		if(isBefore) {
			String nowMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month-1);
			String beforeMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			dates = new String[]{beforeMonth, nowMonth};
		} else {
			String nowMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month+1);
			String afterMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			dates = new String[]{nowMonth, afterMonth};
		}
		return dates;
	}

	/**
	 * 取得傳入日期的發票月份(0102、0304...1112)字串陣列
	 *
	 * @param date
	 * @return
	 */
	public static String[] getInvMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getInvMonth(c);
	}

	/**
	 * 取得系統日期的發票月份(0102、0304...1112)字串陣列
	 *
	 * @return
	 */
	public static String[] getInvMonthSysdate() {
		Calendar c = Calendar.getInstance();
		return getInvMonth(c);
	}

	/** 取得傳入日期的前一期發票月份(0102、0304...1112)字串陣列
	 * @param date
	 * @return
	 */
	public static String[] getLastInvMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month-2);
		return getInvMonth(c);
	}

	/**
	 * 排出發票日期
	 *
	 * @param c
	 * @return
	 */
	private static String[] getInvMonth(Calendar c) {
		String[] dates = null;
		if(c.get(Calendar.MONTH)%2==1) {
			//若傳入日期為雙月
			String oddMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH), 2);
			String evenMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			dates = new String[]{oddMonth, evenMonth};
		} else if(c.get(Calendar.MONTH)%2==0) {
			//若傳入日期為單月
			String oddMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			String evenMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+2, 2);
			dates = new String[]{oddMonth, evenMonth};
		}
		return dates;
	}

	/**
	 * 傳入發票號碼起迄算出發票捲數
	 *
	 * @param noFrom
	 * @param noTo
	 * @return
	 */
	public static BigDecimal getInvoiceRolls(String noFrom, String noTo) {
		BigDecimal BASE_SHEET = new BigDecimal("1");	//基底張數
		BigDecimal BASE_ROLL = new BigDecimal("50");	//每捲發票基底張數
		//發票起頭
		BigDecimal begin = new BigDecimal(noFrom);
		//發票結尾
		BigDecimal end = new BigDecimal(noTo);
		//區間張數
		BigDecimal sheets = end.subtract(begin).add(BASE_SHEET);
		//有小數無條件進位表示剩於發票自成一捲
		BigDecimal rolls = sheets.divide(BASE_ROLL, 0, BigDecimal.ROUND_CEILING);
		return rolls;
	}

	/**
	 * 取得人工編號檢查碼
	 *
	 * @param barcode
	 * @return
	 */
	public static String getInvoiceCheckNo(String barcode) {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		List<Integer> numList = new ArrayList<Integer>();

		//店號六碼相加mod 10
		String store = barcode.substring(0, 6);
		BigDecimal chk1 = new BigDecimal("0");
		for(int i=0; i<store.length(); i++)
			chk1 = chk1.add(new BigDecimal(store.substring(i, i+1)));
		numList.add(chk1.intValue()%10);

		//發票年月起值6碼相加mod 10
		String dateFrom = barcode.substring(6, 12);
		BigDecimal chk2 = new BigDecimal("0");
		for(int i=0; i<dateFrom.length(); i++)
			chk2 = chk2.add(new BigDecimal(dateFrom.substring(i, i+1)));
		numList.add(chk2.intValue()%10);

		//發票年月迄值6碼相加mod 10
		String dateTo = barcode.substring(12, 18);
		BigDecimal chk3 = new BigDecimal("0");
		for(int i=0; i<dateTo.length(); i++)
			chk3 = chk3.add(new BigDecimal(dateTo.substring(i, i+1)));
		numList.add(chk3.intValue()%10);

		//字軌字母取號
		int num4 = letters.indexOf(barcode.substring(18,19))+1;
		numList.add(num4);
		int num5 = letters.indexOf(barcode.substring(19,20))+1;
		numList.add(num5);

		//發票起值八碼相加mod 10
		String noFrom = barcode.substring(20,28);
		BigDecimal chk4 = new BigDecimal("0");
		for(int i=0; i<noFrom.length(); i++)
			chk4 = chk4.add(new BigDecimal(noFrom.substring(i, i+1)));
		numList.add(chk4.intValue()%10);

		//發票迄值八碼相加mod 10
		String noTo = barcode.substring(28,36);
		BigDecimal chk5 = new BigDecimal("0");
		for(int i=0; i<noTo.length(); i++)
			chk5 = chk5.add(new BigDecimal(noTo.substring(i, i+1)));
		numList.add(chk5.intValue()%10);

		//POS機號兩碼
		int num8 = Integer.parseInt(barcode.substring(36,37));
		numList.add(num8);
		int num9 = Integer.parseInt(barcode.substring(37,38));
		numList.add(num9);

		//總和mod 100
		int count = 0;
		for(Integer num : numList) {
			count += num;
		}
		int chkNo = count%100;
		return barcode+CommonStringUtil.addZero(chkNo, 2);
	}

	/**
	 * 傳入日期取得日期最近的雙月月底剩餘天數
	 *
	 * @param date
	 * @return
	 */
	public static int getEndOfEvenMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		int days = 0;
		if(month%2==0) {
			//傳入日期為單月
			//系統日
			int begin = c.get(Calendar.DATE);
			//當月份天數
			int monthLastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			//當月份剩餘天數
			int oddMonthDays = monthLastDay - begin +1;
			//跳入下一個雙月
			c.set(Calendar.MONTH, month+1);
			//雙月份天數
			int evenMonthDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			//兩剩餘天數相加
			days = oddMonthDays + evenMonthDays;
		} else if(month%2==1) {
			//傳入日期為雙月
			//系統日
			int begin = c.get(Calendar.DATE);
			//當月份天數
			int monthLastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			//剩餘天數
			days = monthLastDay - begin +1;
		}
		return days;
	}

	public static int getBeginOfOddMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		int days = 0;
		//系統日
		int begin = c.get(Calendar.DATE)-1;
		if(month%2==0) {
			//傳入日期為單月
			days = begin;
		} else if(month%2==1) {
			//傳入日期為雙月
			//跳入上一個單月
			c.set(Calendar.MONTH, month-1);
			//單月份天數
			int oddMonthDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			//單雙月兩月已過天數相加
			days = oddMonthDays + begin;
		}
		return days;
	}

	public static String[] getThreeMonth(Date date, boolean isBefore) {
		String[] dates = null;
		Calendar c = Calendar.getInstance();
		if(date!=null)
			c.setTime(date);

		int _month = c.get(Calendar.MONTH);
		if(isBefore) {
			String nowMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month-1);
			String beforeMonth1 = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month-2);
			String beforeMonth2 = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month-3);
			String beforeMonth3 = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month-4);
			String beforeMonth4 = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			dates = new String[]{beforeMonth4,beforeMonth3,beforeMonth2,beforeMonth1, nowMonth};
		} else {
			String nowMonth = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month+1);
			String afterMonth1 = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month+2);
			String afterMonth2 = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			c.set(Calendar.MONTH, _month+3);
			String afterMonth3 = CommonStringUtil.addZero(c.get(Calendar.MONTH)+1, 2);
			dates = new String[]{nowMonth, afterMonth1,afterMonth2,afterMonth3};
		}
		return dates;
	}

}

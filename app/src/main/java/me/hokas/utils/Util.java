package me.hokas.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Util {

	public final static String DATE_YMD = "yyyy-MM-dd";
	public final static String DATE_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static boolean existSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	public static int dp2px(Context context, float dpValue) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		final float scale = dm.density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static float px2dp(Context context, float pxValue) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		final float scale = dm.density;
		return (pxValue - 0.5f)/scale ; 
	}

	public static int px2sp(Context context, float pxValue) { 
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
		return (int) (pxValue / fontScale + 0.5f); 
	} 

	public static int sp2px(Context context, float spValue) { 
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
		return (int) (spValue * fontScale + 0.5f); 
	} 

	public static String dataFormat(String format, String parse, String datestr){
		if(datestr == null || "".equals(datestr)){
			return "";
		}
		String str = "";
		try {
			Date date = dateParse(parse, datestr);
			str = getDatedes(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str != null ? str : "";
	}

	@SuppressLint("SimpleDateFormat")
	public static String dateFormat(String format, String parse, String datestr){
		if(datestr == null || "".equals(datestr)){
			return "";
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			SimpleDateFormat pa = new SimpleDateFormat(parse);
			Date date = pa.parse(datestr);
			datestr = df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datestr;
	}

	@SuppressLint("SimpleDateFormat")
	public static Date dateParse(String parse, String datestr){
		if(datestr == null || "".equals(datestr)){
			return null;
		}
		Date date = null;
		try {
			SimpleDateFormat pa = new SimpleDateFormat(parse);
			date = pa.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	@SuppressLint("SimpleDateFormat")
	public static String dateFormat(String format, Date date){
		if(date == null){
			return "";
		}
		String datestr = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			datestr = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datestr;
	}

	public static Map<String, Object> jsonStringToMap(String rsContent, LinkedHashMap<String, Object> map) {
		try{
			if(rsContent != null && !"".equals(rsContent)){
				JSONObject jsonObject = new JSONObject(rsContent);
				for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();){
					String key = (String) iter.next();
					Object value = jsonObject.get(key);
					if(value instanceof String){
						map.put(key, String.valueOf(value));
					}else if(value instanceof JSONObject){
						String jsonstr = String.valueOf(value);
						map.put(key, jsonStringToMap(jsonstr, new LinkedHashMap<String, Object>()));
					}else if(value instanceof JSONArray){
						JSONArray arry = new JSONArray(String.valueOf(value));
						List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
						if(arry != null && arry.length() > 0){
							for (int i = 0; i < arry.length(); i++){
								JSONObject json = arry.getJSONObject(i);
								rsList.add(jsonStringToMap(String.valueOf(json), new LinkedHashMap<String, Object>()));
								map.put(key, rsList);
							}
						}else{
							map.put(key, rsList);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

//	public static Drawable getFaceDefault(Context context, int index){
//		Drawable drawable = null;
//		switch (index) {
//		//case 0:
//		case 1:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_1);
//			break;
//		}
//		case 2:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_2);
//			break;
//		}
//		case 3:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_3);
//			break;
//		}
//		case 4:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_4);
//			break;
//		}
//		case 5:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_5);
//			break;
//		}
//		case 6:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_6);
//			break;
//		}
//		case 7:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_7);
//			break;
//		}
//		case 8:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_8);
//			break;
//		}
//		case 9:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_9);
//			break;
//		}
//		case 10:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_10);
//			break;
//		}
//		case 11:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_11);
//			break;
//		}
//		case 12:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_12);
//			break;
//		}
//		case 13:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_13);
//			break;
//		}
//		case 14:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_14);
//			break;
//		}
//		case 15:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_15);
//			break;
//		}
//		case 16:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_16);
//			break;
//		}
//		case 17:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_17);
//			break;
//		}
//		default:{
//			drawable = context.getResources().getDrawable(R.drawable.portrait_1);
//			break;
//		}
//		}
//		return drawable;
//	}

	public static String getDatedes(long srctime){
		if(srctime == 0){
			return null;
		}
		long time = new Date().getTime() - srctime;
		String str = "";
		int day = (int) (time/1000/60/60/24) ;
		if(day > 0){
			str = dateFormat(Util.DATE_YMD, new Date(srctime));
		}else{
			int hour = (int) (time/1000/60/60);
			if(hour > 0){
				str = hour + "小时前";
			}else{
				int minute = (int) (time/1000/60);
				if(minute > 0){
					str = minute + "分钟前";
				}else{
					str = (int) time/1000 + "秒前";
				}
			}
		}
		return str;
	}

//	public static List<ImagePiece> split(Bitmap bitmap, int xPiece, int yPiece) {    
//		List<ImagePiece> pieces = new ArrayList<ImagePiece>(xPiece * yPiece);    
//		int width = bitmap.getWidth();    
//		int height = bitmap.getHeight();    
//		int pieceWidth = width / xPiece;    
//		int pieceHeight = height / yPiece;    
//		for (int i = 0; i < yPiece; i++) {    
//			for (int j = 0; j < xPiece; j++) {    
//				ImagePiece piece = new ImagePiece();    
//				piece.index = j + i * xPiece;    
//				int xValue = j * pieceWidth;    
//				int yValue = i * pieceHeight;    
//				piece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,    
//						pieceWidth, pieceHeight);    
//				pieces.add(piece);    
//			}    
//		}    
//		return pieces;    
//	}    
	
	public static String getMD5(String str){
		MessageDigest messageDigest = null;
		StringBuffer md5StrBuff = null;
		try {
			md5StrBuff = new StringBuffer();
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			for(byte b : messageDigest.digest())
				md5StrBuff.append(String.format("%02x", b&0xff) ); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return md5StrBuff.toString();
	}

	/**
	 * 将时间戳转为代表"距现在多久之前"的字符串
	 * @param timeStr   时间戳
	 * @return
	 */
	public static String getStandardDate(String timeStr) {

		StringBuffer sb = new StringBuffer();

		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - (t*1000);
		long mill = (long) Math.ceil(time /1000);//秒前

		long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

		long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

		long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

		if (day - 1 > 0) {
			sb.append(day + "天");
		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟");
			} else {
				sb.append(mill + "秒");
			}
		} else {
			sb.append("刚刚");
		}
		if (!sb.toString().equals("刚刚")) {
			sb.append("前");
		}
		return sb.toString();
	}
}

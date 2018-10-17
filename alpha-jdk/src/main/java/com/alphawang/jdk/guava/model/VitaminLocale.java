package com.alphawang.jdk.guava.model;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Coupang.parternallove
 * Date: 2014. 4. 9.
 * Time: 오후 4:06
 * To change this template use File | Settings | File Templates.
 */
public enum VitaminLocale {
	ko_KR(Locale.KOREA, "한국어", "Korean", Locale.KOREAN),
	en_US(Locale.US, "영어", "English", Locale.ENGLISH),
	zh_CN(Locale.SIMPLIFIED_CHINESE, "중국어(간체)", "Chinese(Simplified)", Locale.SIMPLIFIED_CHINESE),
	zh_TW(Locale.TRADITIONAL_CHINESE, "중국어(번체)", "Chinese(Traditional)", Locale.TRADITIONAL_CHINESE);

	private Locale locale;
	private String koreaTitle;
	private String englishTitle;
	private Locale googleMapCode;

	private VitaminLocale(Locale locale, String koreaTitle, String englishTitle, Locale googleMapCode) {
		this.locale = locale;
		this.koreaTitle = koreaTitle;
		this.englishTitle = englishTitle;
		this.googleMapCode = googleMapCode;
	}

	public static VitaminLocale localeValueOf(String name) {
		return VitaminLocale.valueOf(name);
	}

	public Locale getLocale() {
		return locale;
	}

	public String getKoreaTitle() {
		return koreaTitle;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public Locale getGoogleMapCode() {
		return googleMapCode;
	}
}

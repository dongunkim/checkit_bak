package com.sktelecom.checkit.core.common;

public class Constants {

	/**
	 * EndUser 사용기기 구분
	 *
	 */
	public enum USER_AGENT {
		EngineWebKit("webkit"),
		
		DeviceIphone("iphone"),
		DeviceIpod("ipod"),
		DeviceIpad("ipad"),
		DeviceMacPpc("macintosh"), // Used for disambiguation
		
		DeviceAndroid("android"),
		DeviceGoogletTV("googletv"),
		
		DeviceWinPhone7("windows phone os 7"),
		DeviceWinPhone8("windows phone os 8"),
		DeviceWinPhone10("windows phone os 10"),
		DeviceWinMob("windows ce"),
		DeviceWindows("windows"),
		DeviceIeMob("iemobile"),
		DevicePpc("ppc"), // Stands for PocketPC
		EnginePie("wm5 pie"), // An Old Windows Mobile
		
		DeviceBB("blackberry"),
		DeviceBB10("bb10"),
		VndRIM("vnd.rim"), // Detectable when BB devices emulate IE or Firefox
		DeviceBBStorm("blackberry95"), // Storm1 and 2
		DeviceBBBold("blackberry97"), // non-touch
		DeviceBBBoldTouch("blackberry99"), // touchscreen
		DeviceBBTour("blackberry96"), // Tour
		DeviceBBCurve("blackberry89"), // Curve2
		DeviceBBCurveTouch("blackberry 938"), // Curve Touch 9380
		DeviceBBTorch("blackberry 98"), // Torch
		DeviceBBPlaybook("playbook"), // PlayBook tablet
		
		DeviceSymbian("symbian"),
		DeviceS60("series60"),
		DeviceS70("series70"),
		DeviceS80("series80"),
		DeviceS90("series90"),
		
		DevicePalm("plam"),
		DeviceWebOS("webos"), // For Palm devices
		DeviceWebOStv("web0s"), // For LG TVs
		DeviceWebOShp("hpwos"), // For HP's line of WebOS devices
		
		DeviceNuvifone("nuvifone"), // Garmin Nuvifone
		DeviceBada("bada"), // Samsung's Bada OS
		DeviceTizen("tizen"), // tizen OS
		DeviceMeego("meego"), // Meego OS
		DeviceSailfish("sailfish"), //Sailfish OS
		DeviceUbuntu("ubuntu"), // Ubuntu Mobile OS
		DeviceKindle("kindle"), // Amazon Kindle
		EngineSilk("silk-accelerated"), // Amazon's accelerated Silk browser for Kindle Fire
		
		EngineBlazer("blazer"), // Old Palm
		EngineXiino("xiino"), // Another Old Palm
		
		// mobile-specific content
		Vndwap("vnd.wap"),
		Wml("wml"),
		
		// Random devices and mobile browsers
		DeviceTablet("tablet"), // Generic term for slate and tablet devices
		DeviceBrew("brew"),
		DeviceDanger("danger"),
		DeviceHiptop("hiptop"),
		DevicePlaystation("playstation"),
		DevicePlaystationVita("vita"),
		DeviceNintendo("nitro"),
		DeviceNintendoDS("nintendo"),
		DeviceWii("wii"),
		DeviceXbox("xbox"),
		DeviceArchos("archos"),
		
		EngineFireFox("firefox"), // For FireFox OS
		EngineOpera("opera"),
		EngineOpr("opr"),
		EngineChrome("chrome"),
		EngineCrios("crios"),
		EngineSafari("safari"),
		EngineMsie("msie"),
		EngineTrident("trident"),
		EngineNetfront("netfront"), // Common embedded OS browser
		EngineUpBrowser("up.browser"), // Common on some phones
		EngineOpenWeb("openweb"), // Transcoding by OpenWave server
		DeviceMidp("midp"), // a mobile java technology
		Uplink("up.link"),
		EngineTelecaQ("teleca q"), // a modern feature phone browser
		DevicePda("pda"),
		Mini("mini"),
		Mobile("mobile"),
		Mobi("mobi"),
		
		// Smart TV
		SmartTV1("smart-tv"), // Samsung Tizen smartTVs
		SmartTV2("smarttv"), // LG WebOS smartTVs
		
		// Use Maemo, Tablet and Linux to test for Nokia's Internet Tablets
		Maemo("maemo"),
		Linux("linux"),
		Qtembedded("qt embedded"), // for Sony Mylo
		Mylocom2("com2"), // for Sony Mylo
		
		// In some UserAgents, the only clue is the manufacturer.
		ManuSonyEricsson("sonyericsson"),
		Manuericsson("ericsson"),
		ManuSamsung1("sec-sgh"),
		ManuSony("sony"),
		ManuHtc("htc"),
		
		// the only clue is the operator
		SvcDocomo("docomo"),
		SvcKddi("kddi"),
		SvcVodafone("vodafone"),
		
		// disambiguation
		DisUpdate("update");
		
		private String name;
		
		USER_AGENT(String name) {
			this.name = name;
		}
		
		public String get() {
			return this.name;
		}
	}
}

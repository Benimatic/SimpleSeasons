package com.benimatic.simpleseasons;

/**
 * Is it appropriate to have everything static here? Is there ever a case where there will be multiple seasons at once?
 */
public class CurrentSeason {
	
	private static EnumSeason season = EnumSeason.SPRING;
	
	public static EnumSeason getSeason() {
		return season;
	}
	
	public static void setSeason(EnumSeason newSeason) {
		CurrentSeason.season = newSeason;
	}

	public static float getModifiedTemperature(float temperature) {
		
		//System.out.println("MODIFYING TEMPERATURE! old = " + temperature);
		
		switch (getSeason()) {
		case SPRING:
			return temperature;
		case SUMMER:
			return temperature + 0.5F;
		case FALL:
			return temperature;
		case WINTER:
			return temperature - 0.5F;
		default:
			return temperature;
		}

		
	}
}

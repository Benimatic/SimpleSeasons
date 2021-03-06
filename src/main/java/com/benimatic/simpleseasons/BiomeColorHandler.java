package com.benimatic.simpleseasons;

import java.awt.Color;

import net.minecraftforge.event.terraingen.BiomeEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BiomeColorHandler {
	
	public int springGrassColor = 0x00FF00;
	public float springGrassMultiplier = 0.0F;
	public int springFoliageColor = 0x00FF00;
	public float springFoliageMultiplier = 0.0F;
	public int summerGrassColor = 0x00FF00;
	public float summerGrassMultiplier = 0.3F;
	public int summerFoliageColor = 0x00FF00;
	public float summerFoliageMultiplier = 0.3F;
	public int fallGrassColor = 0xFF0000;
	public float fallGrassMultiplier = 0.4F;
	public int fallFoliageColor = 0xFF0000;
	public float fallFoliageMultiplier = 0.6F;
	public int winterGrassColor = 0x777766;
	public float winterGrassMultiplier = 0.9F;
	public int winterFoliageColor = 0x999988;
	public float winterFoliageMultiplier = 0.9F;
	

	@SubscribeEvent
	public void onGrassColor(BiomeEvent.GetGrassColor event) {

		switch (CurrentSeason.getSeason()) {
		case SPRING :
			event.newColor = applyColor(event.newColor, springGrassColor, springGrassMultiplier);
			break;
		case SUMMER :
			event.newColor = applyColor(event.newColor, summerGrassColor, summerGrassMultiplier);
			break;
		case FALL :
			event.newColor = applyColor(event.newColor, fallGrassColor, fallGrassMultiplier);
			break;
		case WINTER :
			event.newColor = applyColor(event.newColor, winterGrassColor, winterGrassMultiplier);
			break;
		default:
			// no change
			break;
		}
	}

	@SubscribeEvent
	public void onFoliageColor(BiomeEvent.GetFoliageColor event) {
		switch (CurrentSeason.getSeason()) {
		case SPRING :
			event.newColor = applyColor(event.newColor, springFoliageColor, springFoliageMultiplier);
			break;
		case SUMMER :
			event.newColor = applyColor(event.newColor, summerFoliageColor, summerFoliageMultiplier);
			break;
		case FALL :
			event.newColor = applyColor(event.newColor, fallFoliageColor, fallFoliageMultiplier);
			break;
		case WINTER :
			event.newColor = applyColor(event.newColor, winterFoliageColor, winterFoliageMultiplier);
			break;
		default:
			// no change
			break;
		}
	}
	
	
	/**
	 * 
	 * Apply the a seasonal color to the base color.
	 * 
	 * 
	 * @param baseColor 	The original color
	 * @param seasonColor	The seasonal tint to apply
	 * @param multiplier	The degree (from 0.0 to 1.0) in which we apply the seasonal color
	 * @return
	 */
	private int applyColor(int baseColor, int seasonColor, float multiplier) {
		if (multiplier > 0F) {
			Color base = new Color(baseColor);
			Color season = new Color(seasonColor);
			
			int red = (int) (base.getRed() * (1F - multiplier) + season.getRed() * multiplier);
			int green = (int) (base.getGreen() * (1F - multiplier) + season.getGreen() * multiplier);
			int blue = (int) (base.getBlue() * (1F - multiplier) + season.getBlue() * multiplier);

			Color combine = new Color(red, green, blue); 


			return combine.getRGB();
		} else {
			return baseColor;
		}
	}

	
}

package com.benimatic.simpleseasons;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Currently listens for the client tick in order to reload the renderers when seasons change
 * 
 * 
 * @author benma_000
 *
 */
public class ClientTickHandler {
	
	private static boolean shouldRenderReload = false;


	public static void triggerRenderReload() {
		ClientTickHandler.shouldRenderReload = true;
	}
	
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && ClientTickHandler.shouldRenderReload) {
			Minecraft.getMinecraft().renderGlobal.loadRenderers();
			ClientTickHandler.shouldRenderReload = false;
		}
	}

}

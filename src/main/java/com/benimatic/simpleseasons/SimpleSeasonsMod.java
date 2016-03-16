package com.benimatic.simpleseasons;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = SimpleSeasonsMod.MODID, name = SimpleSeasonsMod.NAME, version = SimpleSeasonsMod.VERSION)
public class SimpleSeasonsMod
{
    public static final String MODID = "simpleseasons";
    public static final String NAME = "Simple Seasons";
    public static final String VERSION = "0.1";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    

    @EventHandler
	public void load(FMLInitializationEvent evt) {

    	// register the event listener    	
		MinecraftForge.EVENT_BUS.register(new BiomeColorHandler());
		
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		
    }
	
    @EventHandler
	public void startServer(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandSeason());
	}
}

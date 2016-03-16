package com.benimatic.simpleseasons;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class CommandSeason extends CommandBase {

	
	@Override
	public String getCommandName() {
		return "season";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
        return "commands.season.usage";
	}


	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] commandArray) {
		
		if (commandArray.length == 0) {
			// just say what season it is
			 func_152373_a(p_71515_1_, this, "Current season is %s", new Object[] {CurrentSeason.getSeason()});
		} else if (commandArray.length == 1) {
			for (EnumSeason season : EnumSeason.values()) {
				if (season.name().equalsIgnoreCase(commandArray[0])) {
					CurrentSeason.setSeason(season);
					func_152373_a(p_71515_1_, this, "Season set to %s", new Object[] {season});
					// reload renderers
					// TODO: we actually need to send packets to all clients causing them to do this
					ClientTickHandler.triggerRenderReload();
				}
			}
		} else {
			// use error
		}
		
	}


    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] commandArray)
    {
    	if (commandArray.length == 1) {
    		return getListOfStringsMatchingLastWord(commandArray, new String[] {"spring", "summer", "winter", "fall"});
    	} else {
    		return null;
    	}
    	
    }
}
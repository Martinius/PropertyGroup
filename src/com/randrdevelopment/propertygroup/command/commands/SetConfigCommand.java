package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.PropertyGroup;

public class SetConfigCommand extends BaseCommand{	
	FileConfiguration propertyConfig;
	
	public SetConfigCommand(PropertyGroup plugin) {
        super(plugin);
        name = "Set";
        description = "Sets Configuration Options.";
        usage = "/property set <groupname> <option> <value>";
        minArgs = 3;
        maxArgs = 3;
        identifiers.add("property set");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	propertyConfig = plugin.getPropertyConfig();
    	String propertyGroup = args[0].toLowerCase();
    	String setOption = args[1].toLowerCase();
    	String setValue = args[2].toLowerCase();
    	
    	sender.sendMessage(plugin.getTag()+"Arg 0: " + propertyGroup);
    	sender.sendMessage(plugin.getTag()+"Arg 1: " + setOption);
    	sender.sendMessage(plugin.getTag()+"Arg 2: " + setValue);
    	
    	if (args[1] == "hi"){
    		sender.sendMessage("hi test 1");
    	}
    	if (args[1].toLowerCase() == "hi"){
    		sender.sendMessage("hi test 2");
    	}
    	
    	if (setOption == "createregion"){
    		sender.sendMessage(plugin.getTag()+"made it here...");
    		if (setValue == "true"){
    			propertyConfig.set(propertyGroup+".createregion", true);
    			sender.sendMessage(plugin.getTag() + "Create Region Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue == "false"){
    			propertyConfig.set(propertyGroup+".createregion", false);
    			sender.sendMessage(plugin.getTag() + "Create Region Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for createregion option.  Valid options are True and False");
    		}
    	} else if (setOption == "propertyspacing"){
    		try {
    			int spacing = Integer.parseInt(setValue);
    			if (spacing >= 0){
    				propertyConfig.set(propertyGroup+".propertyspacing", spacing);
    				sender.sendMessage(plugin.getTag() + "Spacing set to " + spacing);
    				plugin.savePropertyConfig();
    			} else {
    				sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property spacing cannot be a negative number.");
    			}
    		} catch (Exception e) {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property spacing must be a number");
    		}
    	} else {
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + setOption + " is not a valid option");
    	}
    }
}

package com.randrdevelopment.propertygroup.regions;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionTools {
	private WorldGuardPlugin wgp;

	private RegionTools () {
		wgp = PropertyGroup.getWorldGuard();
		if (wgp == null) {
			return;
		}
	}
	
	private boolean createRegion(String regionName, World world, int x1, int x2, int y1, int y2, int z1, int z2, int Priority, String playerName){
		RegionManager m = wgp.getRegionManager(world);
		if (m == null)
			return false;
		
		BlockVector min = new BlockVector(x1, y1, z1);
        BlockVector max = new BlockVector(x2, y2, z2);
		
        //com.sk89q.worldguard.protection.flags.DefaultFlag.GREET_MESSAGE
        ProtectedRegion region = new ProtectedCuboidRegion(regionName, min, max);

        region.setFlag(DefaultFlag.GREET_MESSAGE, "Welcome to the Property");
        region.setFlag(DefaultFlag.FAREWELL_MESSAGE, "Goodbye...");
        region.setFlag(DefaultFlag.CHEST_ACCESS, State.DENY);
        region.setFlag(DefaultFlag.MOB_DAMAGE, State.DENY);
        
        if (playerName != null){
        	DefaultDomain owners = new DefaultDomain();
        	owners.addPlayer(playerName);
        	region.setOwners(owners);
        }
        
        RegionManager regionManager = wgp.getRegionManager(world);
        regionManager.addRegion(region);
        
		try {
			regionManager.save();
			return true;
		} catch (ProtectionDatabaseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean createProtectedRegion(String regionName, String worldName, int x1, int x2, int y1, int y2, int z1, int z2, int Priority, String playerName){
		RegionTools rt = new RegionTools();
		
		World world = Bukkit.getWorld(worldName);
		
		if (rt.createRegion(regionName, world, x1, x2, y1, y2, z1, z2, Priority, playerName))
			return true;
		
		return false;
	}
}

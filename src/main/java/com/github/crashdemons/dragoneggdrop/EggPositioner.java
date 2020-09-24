/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.crashdemons.dragoneggdrop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.DragonBattle;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class EggPositioner {
    
    public static boolean isValidLand(Block block){
        if(block == null) return false;
        Location landLoc = block.getLocation();
        if(landLoc.getBlockY()>250) return false;
        
        if(block.getType().isAir()) return false;
        return block.getType().isSolid();
    }
    
    public static Location findBedrockPillar(Location start, World world){
        if(Math.abs(start.getX())>150) return null;//don't allow egg-on-pillar if you're nowhere near it.
        if(Math.abs(start.getZ())>150) return null;
        
        DragonBattle battle = world.getEnderDragonBattle();
        if(battle==null) return null;
        return battle.getEndPortalLocation();
    }
    
    public static Location findEggPillarSpot(Location start, World world){
        Location pillar = findBedrockPillar(start,world);
        if(pillar==null) return null;
        return pillar.add(0, 5, 0);
    }
    
    public static Location findLandSpot(Location start, World world){
        for(int x=0;x<16;x++){
            for(int z=0;z<16;z++){
                Location offset = start.add(x,0,z);
                Block block = world.getHighestBlockAt(offset);
                if(isValidLand(block)){
                    return offset.add(0,1,0);
                }
            }
        }
        return null;
    }
    
    public static Location findBestLocation(Location start){
        World world = start.getWorld();
        if(world==null) throw new IllegalArgumentException("Location has no set world.");
        
        Location foundLoc = findEggPillarSpot(start,world);
        if(foundLoc == null){
            DragonEggDrop.debugWarn("Exit portal could not be located near the dragon");
            foundLoc = findLandSpot(start,world);
        }
        if(foundLoc==null){
            DragonEggDrop.debugWarn("No land under the dragon could be found.");
        }else foundLoc.setY(255);//this isn't necessary but it looks really funny/impressive falling from the sky, and prevents overwriting an existing egg (they stack!)
        
        return foundLoc;
    }
    
    
    public static boolean placeEggNear(Location start){
        Location placement = findBestLocation(start);
        if(placement==null) return false;
        World world = start.getWorld();
        if(world==null) return false;
        Block block = world.getBlockAt(placement);
        block.setType(Material.DRAGON_EGG);
        return true;
    }
}

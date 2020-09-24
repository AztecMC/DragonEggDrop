/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.crashdemons.dragoneggdrop;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class DragonDeathListener implements Listener {
    
    
    @EventHandler(ignoreCancelled=true)
    public void onEntityDeath(EntityDeathEvent evt){
        if(DragonEggDrop.plugin==null) return;
        if(evt.getEntity().getType()==EntityType.ENDER_DRAGON)
            onDragonKilled(evt);
    }
    
    public void onDragonKilled(EntityDeathEvent evt){
        LivingEntity killed = evt.getEntity();
        Player killer = killed.getKiller();
        boolean pk = (killer!=null);
        
        if(!pk && DragonEggDrop.plugin.getConfig().getBoolean("pkonly")){
            DragonEggDrop.debug("Dragon killed, but with no killer + pkonly.");
            return;
        }
        
        
        
        World world = killed.getWorld();
        Location loc = killed.getLocation();
        
        DragonBattle battle = world.getEnderDragonBattle();
        boolean isFirstDragon = false;
        if(battle!=null){
            if(!battle.hasBeenPreviouslyKilled()) isFirstDragon = true;
        }
        
        
        
        
        boolean dropSuccess = Stats.calculateDropSuccess(killer);
        
        DragonEggDrop.debug("Dragon killed, rolling egg: "+dropSuccess+" firstdragon: "+isFirstDragon);
        
        
        if(pk){
            if(isFirstDragon){
                killer.sendMessage("You killed the first dragon.");
                return;
            }
            
            if(dropSuccess){
                if(EggPositioner.placeEggNear(loc)){
                    DragonEggDrop.debug("egg placed");
                    killer.sendMessage("You killed the dragon and it dropped an egg.");
                }else{
                    killer.sendMessage("You killed the dragon but the egg fell into the void..."); 
                }
            }
            else killer.sendMessage("You killed the dragon but it didn't drop an egg.");
        }else{
            if(EggPositioner.placeEggNear(loc)) DragonEggDrop.debug("egg placed");
        }
        
    }
    
}

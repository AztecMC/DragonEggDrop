/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.crashdemons.dragoneggdrop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class DragonEggDrop extends JavaPlugin {
    public static DragonEggDrop plugin = null;
    public static DragonDeathListener listener = new DragonDeathListener();
    
    public static void debugWarn(String s){
        if(DragonEggDrop.plugin!=null) DragonEggDrop.plugin.getLogger().warning(s);
    }
    public static void debug(String s){
        if(DragonEggDrop.plugin!=null) DragonEggDrop.plugin.getLogger().info(s);
    }
    
    
    
    @Override
    public void onLoad(){
        plugin = this;
    }
    
    @Override
    public void onEnable(){
        saveDefaultConfig();
        reloadConfig();
        Bukkit.getServer().getPluginManager().registerEvents(listener , plugin);
    }
    
    
    
}

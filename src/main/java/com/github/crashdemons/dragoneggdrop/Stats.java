/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.crashdemons.dragoneggdrop;

import java.util.Random;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class Stats {
    private static final Random prng = new Random();
    
    public static double getLuck(LivingEntity lentity){
        if(lentity==null) return 0;
        double luck = 0;//0 = no effect
        AttributeInstance attrib = lentity.getAttribute(Attribute.GENERIC_LUCK);//default is 0
        if(attrib!=null){
            luck = attrib.getValue();
        }
        return luck;
    }
    public static double getLooting(Player player){
        if(player==null) return 0; 
        ItemStack weapon = player.getInventory().getItemInMainHand();
        if(weapon==null) return 0;
        return weapon.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
    }
    
    public static double modifyDroprate(double droprate, double looting, double luck, double lootingrate, double luckrate){
        return droprate*(1+looting*lootingrate)*(1+luck*luckrate);
    }
    
    public static boolean calculateDropSuccess(Player killer){
        //stats--------------------
        Double dropchance = prng.nextDouble();
        double droprate = DragonEggDrop.plugin.getConfig().getDouble("eggrate");
        double lootingrate = DragonEggDrop.plugin.getConfig().getDouble("lootingrate");
        double luckrate = DragonEggDrop.plugin.getConfig().getDouble("luckrate");
        
        double looting = Stats.getLooting(killer);
        double luck = Stats.getLuck(killer);
        
        double finalDroprate = Stats.modifyDroprate(droprate, looting, luck, lootingrate, luckrate);
        
        boolean dropSuccess = dropchance < finalDroprate;
        return dropSuccess;
    }
    
    
}

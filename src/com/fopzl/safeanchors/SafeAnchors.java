package com.fopzl.safeanchors;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.World.Environment;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.inventory.ItemStack;

public class SafeAnchors extends JavaPlugin implements Listener {
    public void onEnable(){
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = event.getClickedBlock();
            if(b.getWorld().getEnvironment() == Environment.NETHER) return; // ignore in nether
            if(b.getType() != Material.RESPAWN_ANCHOR) return; // ignore if not anchor
            
            RespawnAnchor ra = (RespawnAnchor)b.getBlockData();
            if(ra.getCharges() == 0) return; // ignore if 0 charges
            ItemStack itemInHand = event.getItem();
            if(ra.getCharges() == 4 || itemInHand == null || itemInHand.getType() != Material.GLOWSTONE) { // cancel if fully-charged or not being charged
                event.setCancelled(true);
            }
        }
    }
}
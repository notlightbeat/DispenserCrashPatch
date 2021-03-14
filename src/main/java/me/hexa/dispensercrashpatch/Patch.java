package me.hexa.dispensercrashpatch;

import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Patch extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Listener() {
            final int maxHeight = getConfig().getInt("upper-protection-height");
            final int minHeight = getConfig().getInt("lower-protection-height");
            @EventHandler
            private void onDispense(BlockDispenseEvent evt) {
                if (getConfig().getBoolean("enabled")) {
                    if (evt.getBlock().getLocation().getY() >= maxHeight || evt.getBlock().getLocation().getY() <= minHeight) {
                        getLogger().warning("Dispenser crash attempt on location " + evt.getBlock().getLocation());
                        evt.setCancelled(true);
                    }
                }
            }
        }, this);
        getCommand("dispensercrash").setExecutor((sender, command, label, args) -> {
            if (sender.isOp() && args[0].equals("reload")) {
                reloadConfig();
                sender.sendMessage("§b[DispenserCrashPatch] §9Configuration reloaded");
                return true;
            }
            return false;
        });
        getLogger().info(Color.AQUA + "Dispenser Crash Patch Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info(Color.AQUA + "Dispenser Crash Patch Disabled");
    }
    
}
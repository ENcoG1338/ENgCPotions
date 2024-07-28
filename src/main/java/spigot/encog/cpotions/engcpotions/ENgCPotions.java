package spigot.encog.cpotions.engcpotions;

import org.bukkit.plugin.java.JavaPlugin;

public final class ENgCPotions extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("§a>> §fПлагин §aENgCPotions §fвключён!\n§a>> §fВерсия плагина: §av1.1\n§a>> §fby §aENcoG");
        saveDefaultConfig();

        getCommand("engcpotions").setExecutor(new ENgCommand(this));
        this.getCommand("engcpotions").setTabCompleter(new ENgTabComplete(this));
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        System.out.println("§c>> §fПлагин §cENgCPotions §fотключён!\n§c>> §fВерсия плагина: §cv1.1\n§c>> §fby §cENcoG");
        // Plugin shutdown logic
    }


}
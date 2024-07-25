package spigot.encog.cpotions.engcpotions;

import org.bukkit.plugin.java.JavaPlugin;

public final class ENgCPotions extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("§aвключён. §fby ENcoG");
        saveDefaultConfig();

        getCommand("engcpotions").setExecutor(new ENgCommand(this));
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        getLogger().info("§aотключён. §fby ENcoG");
        // Plugin shutdown logic
    }


}

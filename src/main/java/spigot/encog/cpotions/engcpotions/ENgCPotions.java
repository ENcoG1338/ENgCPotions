package spigot.encog.cpotions.engcpotions;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ENgCPotions extends JavaPlugin {

    private FileConfiguration messages;

    @Override
    public void onEnable() {
        System.out.println("§a>> §fПлагин §aENgCPotions §fвключён!\n§a>> §fВерсия плагина: §av1.2\n§a>> §fby §aENcoG");
        saveDefaultConfig();
        saveDefaultMessages();
        loadMessages();

        getCommand("engcpotions").setExecutor(new ENgCommand(this));
        this.getCommand("engcpotions").setTabCompleter(new ENgTabComplete(this));
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        System.out.println("§c>> §fПлагин §cENgCPotions §fотключён!\n§c>> §fВерсия плагина: §cv1.2\n§c>> §fby §cENcoG");
        // Plugin shutdown logic
    }

    public void loadMessages() {
        String locale = getConfig().getString("locale", "en");
        File messagesFile = new File(getDataFolder(), "messages_" + locale + ".yml");
        if (!messagesFile.exists()) {
            saveResource("messages_" + locale + ".yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        translateColors(messages);
    }

    public void saveDefaultMessages() {
        if (!new File(getDataFolder(), "messages_ru.yml").exists()) {
            saveResource("messages_ru.yml", false);
        }
        if (!new File(getDataFolder(), "messages_en.yml").exists()) {
            saveResource("messages_en.yml", false);
        }
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    private void translateColors(FileConfiguration config) {
        for (String key : config.getKeys(true)) {
            if (config.isString(key)) {
                config.set(key, ENgColorUtil.translateHexColorCodes(config.getString(key)));
            }
        }
    }

}
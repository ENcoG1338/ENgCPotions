package spigot.encog.cpotions.engcpotions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ENgTabComplete implements TabCompleter {

    private final ENgCPotions plugin;

    public ENgTabComplete(ENgCPotions plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("engcpotions")) {
            if (args.length == 1) {
                return Arrays.asList("give", "reload", "help");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
                List<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerNames.add(player.getName());
                }
                return playerNames;
            } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
                FileConfiguration config = plugin.getConfig();
                return new ArrayList<>(config.getConfigurationSection("potions").getKeys(false));
            } else if (args.length == 4 && args[0].equalsIgnoreCase("give")) {
                return Arrays.asList("normal", "explosive", "mist");
            } else if (args.length == 5 && args[0].equalsIgnoreCase("give")) {
                return Arrays.asList("количество");
            }
        }
        return null;
    }

}
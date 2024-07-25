package spigot.encog.cpotions.engcpotions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ENgCommand implements CommandExecutor {

    private final ENgCPotions plugin;

    public ENgCommand(ENgCPotions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("engcpotions")) {
            if (args.length < 4) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.usage")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.player_not_found")));
                return true;
            }

            String potionName = args[2];
            int amount;
            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.invalid_amount")));
                return true;
            }

            FileConfiguration config = plugin.getConfig();
            if (!config.contains("potions." + potionName)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.potion_not_found")));
                return true;
            }

            ItemStack potion = new ItemStack(Material.POTION, amount);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("potions." + potionName + ".name")));
            List<String> lore = new ArrayList<>();
            for (String line : config.getStringList("potions." + potionName + ".description")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            meta.setLore(lore);

            for (String effect : config.getStringList("potions." + potionName + ".effects")) {
                String[] parts = effect.split(":");
                PotionEffectType type = PotionEffectType.getByName(parts[0]);
                int duration = Integer.parseInt(parts[1]);
                int amplifier = Integer.parseInt(parts[2]);
                meta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
            }

            if (config.contains("potions." + potionName + ".color")) {
                String colorString = config.getString("potions." + potionName + ".color");
                Color color = Color.fromRGB(Integer.parseInt(colorString, 16));
                meta.setColor(color);
            }

            potion.setItemMeta(meta);
            target.getInventory().addItem(potion);

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.give_success")
                    .replace("{amount}", String.valueOf(amount))
                    .replace("{potion}", potionName)
                    .replace("{player}", target.getName())));
            return true;
        }
        return false;
    }

}
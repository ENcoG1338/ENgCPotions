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
            FileConfiguration messages = plugin.getMessages();

            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("engcpotions.reload")) {
                    sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.no_permission")));
                    return true;
                }
                plugin.reloadConfig();
                plugin.loadMessages();
                sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.reload_success")));
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                if (!sender.hasPermission("engcpotions.help")) {
                    sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.no_permission")));
                    return true;
                }
                List<String> helpMessages = messages.getStringList("messages.help");
                for (String message : helpMessages) {
                    sender.sendMessage(ENgColorUtil.translateHexColorCodes(message));
                }
                return true;
            }

            if (!sender.hasPermission("engcpotions.give")) {
                sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.no_permission")));
                return true;
            }

            if (args.length < 5) {
                sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.usage")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.player_not_found")));
                return true;
            }

            String potionName = args[2];
            String potionType = args[3];
            int amount;
            try {
                amount = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.invalid_amount")));
                return true;
            }

            FileConfiguration config = plugin.getConfig();
            if (!config.contains("potions." + potionName)) {
                sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.potion_not_found")));
                return true;
            }

            Material potionMaterial;
            switch (potionType.toLowerCase()) {
                case "explosive":
                    potionMaterial = Material.SPLASH_POTION;
                    break;
                case "mist":
                    potionMaterial = Material.LINGERING_POTION;
                    break;
                default:
                    potionMaterial = Material.POTION;
                    break;
            }

            ItemStack potion = new ItemStack(potionMaterial, amount);
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName(ENgColorUtil.translateHexColorCodes(config.getString("potions." + potionName + ".name")));
            List<String> lore = new ArrayList<>();
            for (String line : config.getStringList("potions." + potionName + ".description")) {
                lore.add(ENgColorUtil.translateHexColorCodes(line));
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

            sender.sendMessage(ENgColorUtil.translateHexColorCodes(messages.getString("messages.give_success")
                    .replace("{amount}", String.valueOf(amount))
                    .replace("{potion}", potionName)
                    .replace("{player}", target.getName())));
            return true;
        }
        return false;
    }

}
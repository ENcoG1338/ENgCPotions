package spigot.encog.cpotions.engcpotions;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ENgColorUtil {

    public static String translateHexColorCodes(String message) {
        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String color = matcher.group(1);
            message = message.replace("&#" + color, net.md_5.bungee.api.ChatColor.of("#" + color).toString());
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
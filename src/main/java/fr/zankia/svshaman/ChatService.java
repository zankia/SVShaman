package fr.zankia.svshaman;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class ChatService {
    public static final String NO_PERMISSION = ChatColor.RED + "Erreur : Vous n'avez pas la permission pour cette commande.";
    public static final String RELOADED = "Configurations rechargées avec succès";

    private static String prefix;

    private ChatService() {}

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(prefix + message);
    }

    public static void init(JavaPlugin plugin) {
        prefix = ChatColor.translateAlternateColorCodes(
                '&',
                Objects.requireNonNull(plugin.getConfig().getString("prefix"))
        );
    }
}

package fr.zankia.svshaman;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SVSCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public SVSCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (label.toLowerCase()) {
            case "svs":
            case "svshaman":
                return svshamanCommand(sender, args);

            default:
                return false;
        }
    }

    private boolean svshamanCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("svshaman.admin")) {
            ChatService.sendMessage(sender, ChatService.NO_PERMISSION);
            return false;
        }
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            return false;
        }

        plugin.reloadConfig();
        ChatService.init(plugin);
        ChatService.sendMessage(sender, ChatService.RELOADED);
        return true;
    }
}

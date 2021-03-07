package fr.zankia.svshaman;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SVShaman extends JavaPlugin {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        ChatService.init(this);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new SVSListener(this), this);

        CommandExecutor executor = new SVSCommand(this);
        Objects.requireNonNull(getServer().getPluginCommand("svshaman")).setExecutor(executor);
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }
}

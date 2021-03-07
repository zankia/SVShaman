package fr.zankia.svshaman;

import fr.zankia.svshaman.entity.SVEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public class SVSListener implements Listener {

    private final JavaPlugin plugin;
    private final Logger log;

    public SVSListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.log = plugin.getSLF4JLogger();
    }

    private ConfigurationSection getConfig() {
        return plugin.getConfig().getConfigurationSection("effects");
    }

    @EventHandler
    public void onBurn(EntityCombustByBlockEvent event) {
        if (!EntityType.DROPPED_ITEM.name().equals(event.getEntityType().name())) {
            return;
        }
        String itemType = ((Item) event.getEntity()).getItemStack().getType().name();
        if (!getConfig().contains(itemType)) {
            return;
        }
        List<Map<?, ?>> effectsToApply = getConfig().getMapList(itemType);
        Entity entity = event.getEntity();
        Location location = entity.getLocation();

        log.info("{} burnt in {}({}, {}, {})", entity.getName(), entity.getWorld().getName(), location.getBlockX(),
                location.getBlockY(), location.getBlockZ());

        effectsToApply.forEach(toApply -> toApply.forEach((name, properties) -> {
            SVEffect effect = new SVEffect((Map<String, Integer>) properties);
            log.debug("Playing {}({}, {}, {})", name, effect.getAmplifier(), effect.getDuration(), effect.getTimeout());
            entity.getWorld().getNearbyPlayers(location, 4, 2).forEach(player ->
            {
                PotionEffectType potionEffectType = PotionEffectType.getByName((String) name);
                if (potionEffectType == null) {
                    log.error("Could not find effect {}", name);
                    return;
                }

                Bukkit.getScheduler().runTaskLater(plugin, () ->
                    player.addPotionEffect(new PotionEffect(
                            potionEffectType,
                            effect.getDuration(),
                            effect.getAmplifier())
                    ), effect.getTimeout()
                );
            });
        }));
    }
}

package es.networkersmc.lobby.player;

import es.networkersmc.dendera.module.Module;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import javax.inject.Inject;

public class PlayerController implements Module, Listener {

    private final Location joinLocation;

    @Inject
    public PlayerController(World world) {
        this.joinLocation = new Location(world, 0, 0, 0, 0, 0);
    }

    @EventHandler
    public void onPlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(joinLocation);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        event.setCancelled(true);
    }
}

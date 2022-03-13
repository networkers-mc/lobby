package es.networkersmc.lobby.world;

import es.networkersmc.dendera.module.Module;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import javax.inject.Inject;

public class WorldController implements Module, Listener {

    @Inject private Server server;

    @Override
    public void onStart() {
        server.setIgnorePlayerData(true);

        World world = server.getWorlds().get(0);
        world.setAutoSave(false);
        world.setGameRuleValue("doDaylightCycle", "false");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}

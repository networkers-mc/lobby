package es.networkersmc.lobby;

import com.google.inject.Provides;
import es.networkersmc.dendera.inject.Manifest;
import es.networkersmc.lobby.player.PlayerController;
import es.networkersmc.lobby.world.WorldController;
import org.bukkit.Server;
import org.bukkit.World;

public class LobbyManifest extends Manifest {

    @Override
    protected void configure() {
        bindModule(PlayerController.class);
        bindModule(WorldController.class);
    }

    @Provides
    public World provideWorld(Server server) {
        return server.getWorlds().get(0);
    }
}

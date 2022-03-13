package es.networkersmc.lobby;

import com.google.inject.Provides;
import es.networkersmc.dendera.inject.Manifest;
import es.networkersmc.lobby.player.PlayerController;
import org.bukkit.Server;
import org.bukkit.World;

public class LobbyManifest extends Manifest {

    @Override
    protected void configure() {
        bindModule(PlayerController.class);
    }

    @Provides
    public World provideWorld(Server server) {
        return server.getWorlds().get(0);
    }
}

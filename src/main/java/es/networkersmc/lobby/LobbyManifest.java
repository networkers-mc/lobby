package es.networkersmc.lobby;

import es.networkersmc.dendera.inject.Manifest;
import es.networkersmc.lobby.player.PlayerController;

public class LobbyManifest extends Manifest {

    @Override
    protected void configure() {
        bindModule(PlayerController.class);
    }
}

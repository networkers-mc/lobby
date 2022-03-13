package es.networkersmc.lobby;

import es.networkersmc.dendera.DenderaInjector;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        DenderaInjector.getInstance().registerModule(new LobbyManifest());
    }
}

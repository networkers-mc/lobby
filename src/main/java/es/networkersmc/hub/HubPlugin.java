package es.networkersmc.hub;

import es.networkersmc.dendera.DenderaInjector;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        DenderaInjector.getInstance().registerModule(new HubManifest());
    }
}

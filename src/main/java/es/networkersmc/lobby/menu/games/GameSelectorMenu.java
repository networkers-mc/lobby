package es.networkersmc.lobby.menu.games;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import es.networkersmc.dendera.bukkit.event.gui.GUICloseEvent;
import es.networkersmc.dendera.bukkit.gui.GUI;
import es.networkersmc.dendera.bukkit.gui.GUIService;
import es.networkersmc.dendera.bukkit.gui.button.Button;
import es.networkersmc.dendera.bukkit.gui.widget.BackButtonProvider;
import es.networkersmc.dendera.bukkit.language.PlayerLanguageService;
import es.networkersmc.dendera.concurrent.MinecraftSchedulerService;
import es.networkersmc.dendera.docs.User;
import es.networkersmc.dendera.event.EventService;
import es.networkersmc.dendera.language.LanguageService;
import es.networkersmc.dendera.module.Module;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

public class GameSelectorMenu implements Module {

    private final Map<GUI, OpenMenu> openMenus = Maps.newConcurrentMap();

    @Inject private GUIService guiService;
    @Inject private BackButtonProvider backButtonProvider;

    @Inject private EventService eventService;
    @Inject private MinecraftSchedulerService schedulerService;

    @Inject private LanguageService languageService;
    @Inject private PlayerLanguageService playerLanguageService;

    @Override
    public void onStart() {
        eventService.registerListener(GUICloseEvent.class, event -> this.openMenus.remove(event.getClosedGUI()));
        schedulerService.runTaskTimer(this::updateNextOpeningButtons, 1, ChronoUnit.SECONDS, 1, ChronoUnit.SECONDS);
    }

    public void open(Player player, User user) {
        GUI gui = guiService.createBasicChestGUI(player, user, 4, languageService.getTranslation("menu.games.title", user));
        Set<NextOpeningButton> nextOpeningButtons = Sets.newHashSet();

        // Button definitions
        Button skyWars = Button.builder(Material.BOW).build();

        // Button placement
        gui.setButton(2, skyWars);
        backButtonProvider.place(gui, player, user);

        // Next opening buttons
        nextOpeningButtons.add(new NextOpeningButton(
                skyWars,
                new GregorianCalendar(2023, Calendar.FEBRUARY, 23, 13, 0, 0).toInstant(),
                user,
                playerLanguageService
        ));

        this.openMenus.put(gui, new OpenMenu(gui, nextOpeningButtons));
        guiService.open(player, gui);
    }

    private void updateNextOpeningButtons() {
        for (OpenMenu openMenu : this.openMenus.values()) {
            for (NextOpeningButton nextOpeningButton : openMenu.nextOpeningButtons()) {
                nextOpeningButton.update();
            }
        }
    }

    private record OpenMenu(GUI gui, Set<NextOpeningButton> nextOpeningButtons) {
    }
}

package es.networkersmc.lobby.menu.games;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import es.networkersmc.dendera.bukkit.event.gui.GUICloseEvent;
import es.networkersmc.dendera.bukkit.gui.GUI;
import es.networkersmc.dendera.bukkit.gui.GUIService;
import es.networkersmc.dendera.bukkit.gui.button.Button;
import es.networkersmc.dendera.bukkit.language.PlayerLanguageService;
import es.networkersmc.dendera.concurrent.MinecraftSchedulerService;
import es.networkersmc.dendera.docs.User;
import es.networkersmc.dendera.event.EventService;
import es.networkersmc.dendera.module.Module;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

public class GameSelectorMenu implements Module {

    private final Map<GUI, OpenMenu> openMenus = Maps.newConcurrentMap();

    @Inject private GUIService guiService;
    @Inject private EventService eventService;
    @Inject private MinecraftSchedulerService schedulerService;

    @Inject private PlayerLanguageService languageService;

    @Override
    public void onStart() {
        eventService.registerListener(GUICloseEvent.class, event -> this.openMenus.remove(event.getClosedGUI()));
        schedulerService.runTaskTimer(this::updateNextOpeningButtons, 1, ChronoUnit.SECONDS, 1, ChronoUnit.SECONDS);
    }

    public void open(Player player, User user) {
        GUI gui = guiService.createBasicChestGUI(player, user, 4, languageService.getTranslation("menu.games.title", user));

        Button skyWars = Button.builder(Material.BOW).build();
        Button eggWars = Button.builder(Material.DRAGON_EGG).build();
        this.setNextOpeningButton(eggWars, gui, user, new GregorianCalendar(2023, Calendar.FEBRUARY, 23).toInstant());
        // more games...

        gui.setButton(2, skyWars);
        gui.setButton(3, eggWars);

        guiService.open(player, gui);
    }

    private void setNextOpeningButton(Button button, GUI gui, User user, Instant instant) {
        this.openMenus.computeIfAbsent(gui, g -> new OpenMenu(gui, Sets.newHashSet()))
                .nextOpeningButtons()
                .add(new NextOpeningButton(button, instant, user, languageService));
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

package es.networkersmc.lobby.menu.games;

import es.networkersmc.dendera.bukkit.gui.button.Button;
import es.networkersmc.dendera.bukkit.gui.button.DynamicButton;
import es.networkersmc.dendera.bukkit.language.PlayerLanguageService;
import es.networkersmc.dendera.docs.User;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

public class NextOpeningButton {

    private final PlayerLanguageService languageService;

    private final Instant openingTime;
    private final String name;
    private final User user;

    private final DynamicButton dynamicButton;

    public NextOpeningButton(Button button, Instant openingTime, User user, PlayerLanguageService languageService) {
        this.languageService = languageService;

        this.openingTime = openingTime;
        this.name = button.getName();
        this.user = user;

        this.dynamicButton = new DynamicButton(button, b -> b.setName(this.formatName()));
        this.dynamicButton.update();

        button.setClickAction(clickData -> {
            if (Instant.now().isAfter(openingTime)) {
                button.getClickAction().onClick(clickData);
                return;
            }

            Player player = clickData.player();
            languageService.sendMessage(player, user, "");
            player.closeInventory();
        });
    }

    public void update() {
        dynamicButton.update();
    }

    private String formatName() {
        Instant now = Instant.now();

        if (now.isAfter(openingTime)) {
            return name + "- §e§l" + languageService.getTranslation("commons.new", this.user);
        }

        return name + " §f- §e" + DurationFormatUtils.formatDuration(Duration.between(now, openingTime).toMillis(), "d:HH:mm:ss");
    }
}

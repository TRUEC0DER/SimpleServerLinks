package me.truec0der.simpleserverlinks.listener;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.simpleserverlinks.interfaces.service.link.LinkService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLinksSendEvent;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LinkListener implements Listener {
    LinkService linkService;

    @EventHandler(priority = EventPriority.HIGHEST)
    private void addLinksToPlayer(PlayerLinksSendEvent event) {
        linkService.handleLinks(event.getPlayer(), event.getLinks());
    }
}

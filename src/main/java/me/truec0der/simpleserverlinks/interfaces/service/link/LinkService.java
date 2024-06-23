package me.truec0der.simpleserverlinks.interfaces.service.link;

import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public interface LinkService {
    void handleLinks(Player player, ServerLinks serverLinks);

    Map<?, ?> getPlayerLinks(String playerName);

    boolean hasPlayerLinks(String playerName);

    void sendDefaultLinks(List<Map<?, ?>> map, ServerLinks serverLinks);

    void sendTypeLinks(List<Map<?, ?>> map, ServerLinks serverLinks);

    void sendPlayerLinks(Map<?, ?> map, ServerLinks serverLinks);
}

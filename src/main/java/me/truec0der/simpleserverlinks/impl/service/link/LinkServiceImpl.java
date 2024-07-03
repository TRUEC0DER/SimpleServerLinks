package me.truec0der.simpleserverlinks.impl.service.link;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.simpleserverlinks.config.configs.MainConfig;
import me.truec0der.simpleserverlinks.interfaces.service.link.LinkService;
import me.truec0der.simpleserverlinks.util.MessageUtil;
import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LinkServiceImpl implements LinkService {
    MainConfig mainConfig;

    @Override
    public void handleLinks(Player player, ServerLinks serverLinks) {
        if (!mainConfig.isAllowThirdParty()) serverLinks.getLinks().forEach(serverLinks::removeLink);

        if (mainConfig.isSendTypeLinksFirst() && mainConfig.isStatusLinksType())
            sendTypeLinks(mainConfig.getLinksType(), serverLinks);

        if (mainConfig.isStatusLinksNickname() && hasPlayerLinks(player.getName())) {
            sendPlayerLinks(getPlayerLinks(player.getName()), serverLinks);
            sendTypeLinksIfNeeded(serverLinks);
            return;
        }

        if (mainConfig.isStatusLinksDefault()) {
            sendDefaultLinks(mainConfig.getLinksDefault(), serverLinks);
            sendTypeLinksIfNeeded(serverLinks);
        }
    }

    public Map<?, ?> getPlayerLinks(String playerName) {
        return mainConfig.getLinksNickname().stream().filter(nicknameLink -> nicknameLink.containsKey(playerName)).findFirst().orElse(null);
    }

    public boolean hasPlayerLinks(String playerName) {
        return getPlayerLinks(playerName) != null;
    }

    public void sendDefaultLinks(List<Map<?, ?>> list, ServerLinks serverLinks) {
        list.forEach(link -> {
            Map<?, ?> linkMap = link;
            String name = (String) linkMap.get("name");
            String url = (String) linkMap.get("url");
            try {
                serverLinks.addLink(MessageUtil.serialize(name), new URI(url));
            } catch (URISyntaxException ignored) {
            }
        });
    }

    public void sendTypeLinks(List<Map<?, ?>> list, ServerLinks serverLinks) {
        list.forEach(link -> {
            Map<?, ?> linkMap = link;
            String type = (String) linkMap.get("type");
            String url = (String) linkMap.get("url");
            try {
                serverLinks.addLink(ServerLinks.Type.valueOf(type), new URI(url));
            } catch (URISyntaxException ignored) {
            }
        });
    }

    public void sendPlayerLinks(Map<?, ?> map, ServerLinks serverLinks) {
        map.forEach((key, value) -> {
            List<?> linkList = (ArrayList<?>) value;
            linkList.forEach(link -> {
                Map<?, ?> linkMap = (HashMap<?, ?>) link;
                String name = (String) linkMap.get("name");
                String url = (String) linkMap.get("url");
                try {
                    serverLinks.addLink(MessageUtil.serialize(name), new URI(url));
                } catch (URISyntaxException ignored) {
                }
            });
        });
    }

    private void sendTypeLinksIfNeeded(ServerLinks serverLinks) {
        if (!mainConfig.isSendTypeLinksFirst() && mainConfig.isStatusLinksType()) {
            sendTypeLinks(mainConfig.getLinksType(), serverLinks);
        }
    }
}

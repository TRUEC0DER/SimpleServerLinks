package me.truec0der.simpleserverlinks.config.configs;

import lombok.Getter;
import me.truec0der.simpleserverlinks.config.ConfigHolder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.Map;

@Getter
public class MainConfig extends ConfigHolder {
    private boolean allowThirdParty;
    private boolean sendTypeLinksFirst;

    private boolean statusLinksDefault;
    private boolean statusLinksType;
    private boolean statusLinksNickname;

    private List<Map<?, ?>> linksDefault;
    private List<Map<?, ?>> linksType;
    private List<Map<?, ?>> linksNickname;

    public MainConfig(Plugin plugin, File filePath, String file) {
        super(plugin, filePath, file);
        loadAndSave();
        init();
    }

    @Override
    public void init() {
        YamlConfiguration config = this.getConfig();

        allowThirdParty = config.getBoolean("allow-third-party");
        sendTypeLinksFirst = config.getBoolean("send-type-links-first");

        statusLinksDefault = config.getBoolean("status.links.default");
        statusLinksType = config.getBoolean("status.links.type");
        statusLinksNickname = config.getBoolean("status.links.nickname");

        linksDefault = config.getMapList("links.default");
        linksType = config.getMapList("links.type");
        linksNickname = config.getMapList("links.nickname");
    }
}

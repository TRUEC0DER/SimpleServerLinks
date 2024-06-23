package me.truec0der.simpleserverlinks.config.configs;

import lombok.Getter;
import me.truec0der.simpleserverlinks.config.ConfigHolder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.Map;

@Getter
public class LangConfig extends ConfigHolder {
    private String notPerms;
    private String needCorrectArgs;
    private String onlyPlayer;
    private String reloadInfo;

    public LangConfig(Plugin plugin, File filePath, String file) {
        super(plugin, filePath, file);
        loadAndSave();
        init();
    }

    @Override
    public void init() {
        YamlConfiguration config = this.getConfig();

        notPerms = config.getString("not-perms");
        needCorrectArgs = config.getString("need-correct-args");
        onlyPlayer = config.getString("only-player");

        reloadInfo = config.getString("reload.info");
    }
}

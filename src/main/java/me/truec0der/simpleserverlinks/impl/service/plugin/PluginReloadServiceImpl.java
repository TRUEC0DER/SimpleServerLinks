package me.truec0der.simpleserverlinks.impl.service.plugin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.truec0der.simpleserverlinks.config.configs.LangConfig;
import me.truec0der.simpleserverlinks.config.configs.MainConfig;
import me.truec0der.simpleserverlinks.interfaces.service.plugin.PluginReloadService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PluginReloadServiceImpl implements PluginReloadService {
    MainConfig mainConfig;
    LangConfig langConfig;

    @Override
    public void reload() {
        mainConfig.reload();
        langConfig.reload();
    }
}

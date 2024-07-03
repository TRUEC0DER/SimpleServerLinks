package me.truec0der.simpleserverlinks;

import lombok.NonNull;
import me.truec0der.simpleserverlinks.command.CommandHandler;
import me.truec0der.simpleserverlinks.config.configs.LangConfig;
import me.truec0der.simpleserverlinks.config.configs.MainConfig;
import me.truec0der.simpleserverlinks.impl.service.link.LinkServiceImpl;
import me.truec0der.simpleserverlinks.impl.service.plugin.PluginReloadServiceImpl;
import me.truec0der.simpleserverlinks.interfaces.service.link.LinkService;
import me.truec0der.simpleserverlinks.interfaces.service.plugin.PluginReloadService;
import me.truec0der.simpleserverlinks.listener.LinkListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SimpleServerLinksPlugin extends JavaPlugin implements Listener {
    BukkitAudiences adventure;
    MainConfig mainConfig;
    LangConfig langConfig;
    PluginReloadService pluginReloadService;
    LinkService linkService;

    @Override
    public void onEnable() {
        initAdventure();
        initConfig();
        initService();
        initListener();
        initCommand();
        initMetrics();

        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }

    public @NonNull BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }

        return adventure;
    }

    private void initAdventure() {
        adventure = BukkitAudiences.create(this);
    }

    private void initConfig() {
        mainConfig = new MainConfig(this, new File(this.getDataFolder().getPath()), "config.yml");
        langConfig = new LangConfig(this, new File(this.getDataFolder().getPath()), "lang.yml");
    }

    private void initService() {
        linkService = new LinkServiceImpl(mainConfig);
        pluginReloadService = new PluginReloadServiceImpl(mainConfig, langConfig);
    }

    private void initListener() {
        getServer().getPluginManager().registerEvents(new LinkListener(linkService), this);
    }

    private void initCommand() {
        getServer().getPluginCommand("simpleserverlinks").setExecutor(new CommandHandler(adventure(), pluginReloadService, mainConfig, langConfig));
        getCommand("simpleserverlinks").setTabCompleter(new CommandHandler(adventure(), pluginReloadService, mainConfig, langConfig));
    }

    private void initMetrics() {
        int pluginId = 22366;
        Metrics metrics = new Metrics(this, pluginId);
    }
}

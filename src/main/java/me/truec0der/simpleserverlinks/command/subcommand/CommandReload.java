package me.truec0der.simpleserverlinks.command.subcommand;

import me.truec0der.simpleserverlinks.command.ICommand;
import me.truec0der.simpleserverlinks.config.configs.LangConfig;
import me.truec0der.simpleserverlinks.interfaces.service.plugin.PluginReloadService;
import me.truec0der.simpleserverlinks.util.MessageUtil;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;

public class CommandReload implements ICommand {
    private final LangConfig langConfig;
    private final PluginReloadService pluginReloadService;

    public CommandReload(LangConfig langConfig, PluginReloadService pluginReloadService) {
        this.langConfig = langConfig;
        this.pluginReloadService = pluginReloadService;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getRegex() {
        return "";
    }

    @Override
    public String[] getCompleteArgs() {
        return new String[]{""};
    }

    @Override
    public String getPermission() {
        return "simpleserverlinks.commands.reload";
    }

    @Override
    public boolean isConsoleCan() {
        return true;
    }

    public boolean execute(CommandSender sender, Audience audience, String[] args) {
        pluginReloadService.reload();
        audience.sendMessage(MessageUtil.create(langConfig.getReloadInfo()));
        return true;
    }
}

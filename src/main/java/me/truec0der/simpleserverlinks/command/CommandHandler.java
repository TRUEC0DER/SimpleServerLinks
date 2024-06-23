package me.truec0der.simpleserverlinks.command;

import me.truec0der.simpleserverlinks.command.subcommand.CommandReload;
import me.truec0der.simpleserverlinks.config.configs.LangConfig;
import me.truec0der.simpleserverlinks.config.configs.MainConfig;
import me.truec0der.simpleserverlinks.interfaces.service.plugin.PluginReloadService;
import me.truec0der.simpleserverlinks.util.TextFormatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private final PluginReloadService pluginReloadService;
    private final MainConfig mainConfig;
    private final LangConfig langConfig;
    private final CommandManager commandManager;

    public CommandHandler(PluginReloadService pluginReloadService, MainConfig mainConfig, LangConfig langConfig) {
        this.pluginReloadService = pluginReloadService;

        this.mainConfig = mainConfig;
        this.langConfig = langConfig;

        this.commandManager = new CommandManager();

        initCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String[] slicedArgs = args.length > 0 ? Arrays.copyOfRange(args, 1, args.length) : new String[0];

        Optional<ICommand> emptyCommand = commandManager.getCommands().stream().filter(cmd -> cmd.getName().isEmpty()).findFirst();

        if (args.length == 0) {
            if (!emptyCommand.isPresent()) {
                sender.sendMessage(TextFormatUtil.format(langConfig.getNeedCorrectArgs()));
                return true;
            }

            if (!(sender instanceof Player) && !emptyCommand.get().isConsoleCan()) {
                sender.sendMessage(TextFormatUtil.format(langConfig.getOnlyPlayer()));
                return true;
            }

            if (!sender.hasPermission(emptyCommand.get().getPermission())) {
                sender.sendMessage(TextFormatUtil.format(langConfig.getNotPerms()));
                return true;
            }
            return emptyCommand.map(cmd -> executeCommand(cmd, sender, slicedArgs)).get();
        }

        List<ICommand> foundCommands = commandManager.findCommandsByName(args[0]);

        if (!foundCommands.isEmpty()) {
            for (ICommand commandObject : foundCommands) {
                if (Pattern.matches(commandObject.getRegex(), String.join(" ", slicedArgs))) {
                    if (!sender.hasPermission(commandObject.getPermission())) {
                        sender.sendMessage(TextFormatUtil.format(langConfig.getNotPerms()));
                        return true;
                    }
                    return executeCommand(commandObject, sender, slicedArgs);
                }
            }
            sender.sendMessage(TextFormatUtil.format(langConfig.getNeedCorrectArgs()));
            return true;
        }

        sender.sendMessage(TextFormatUtil.format(langConfig.getNeedCorrectArgs()));
        return true;
    }

    private boolean executeCommand(ICommand command, CommandSender sender, String[] args) {
        return command.execute(sender, args);
    }

    private void initCommands() {
        commandManager.addCommand(new CommandReload(langConfig, pluginReloadService));;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return commandManager.getCommandNamesForSender(sender);
        }

        String commandName = args[0];
        List<ICommand> foundCommands = commandManager.findCommandsByName(commandName);

        return foundCommands.stream()
                .filter(cmd -> sender.hasPermission(cmd.getPermission()) && !cmd.getName().isEmpty())
                .flatMap(cmd -> getCompletionList(args, cmd).stream())
                .collect(Collectors.toList());
    }

    private List<String> getCompletionList(String[] args, ICommand command) {
        int argIndex = args.length - 2;
        if (argIndex >= 0 && argIndex < command.getCompleteArgs().length) {
            return Arrays.asList(command.getCompleteArgs()[argIndex].split("\\|"));
        } else {
            return Collections.emptyList();
        }
    }
}
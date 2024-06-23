package me.truec0der.simpleserverlinks.command;

import org.bukkit.command.CommandSender;

public interface ICommand {
    String getName();

    String getRegex();

    String[] getCompleteArgs();

    String getPermission();

    boolean isConsoleCan();

    boolean execute(CommandSender sender, String[] args);
}

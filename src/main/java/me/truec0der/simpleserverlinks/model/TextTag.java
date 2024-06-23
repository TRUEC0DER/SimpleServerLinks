package me.truec0der.simpleserverlinks.model;

import lombok.Getter;

@Getter
public enum TextTag {
    BOLD("l", "<bold>", "<b>"),
    ITALIC("o", "<italic>", "<i>"),
    UNDERLINED("n", "<underlined>", "<u>"),
    STRIKETHROUGH("m", "<strikethrough>", "<st>"),
    OBFUSCATED("k", "<obfuscated>", "<obf>"),
    RESET("r", "<reset>", "<r>"),
    BLACK("0", "<black>"),
    DARK_BLUE("1", "<dark_blue>"),
    DARK_GREEN("2", "<dark_green>"),
    DARK_AQUA("3", "<dark_aqua>"),
    DARK_RED("4", "<dark_red>"),
    DARK_PURPLE("5", "<dark_purple>"),
    GOLD("6", "<gold>"),
    GRAY("7", "<gray>"),
    DARK_GRAY("8", "<dark_gray>"),
    BLUE("9", "<blue>"),
    GREEN("a", "<green>"),
    AQUA("b", "<aqua>"),
    RED("c", "<red>"),
    LIGHT_PURPLE("d", "<light_purple>"),
    YELLOW("e", "<yellow>"),
    WHITE("f", "<white>");

    private final String replace;
    private final String[] tag;

    TextTag(String replace, String... tag) {
        this.replace = replace;
        this.tag = tag;
    }
}

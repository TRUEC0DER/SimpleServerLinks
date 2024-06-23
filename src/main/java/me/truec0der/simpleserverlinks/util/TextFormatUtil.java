package me.truec0der.simpleserverlinks.util;

import lombok.experimental.UtilityClass;
import me.truec0der.simpleserverlinks.model.TextTag;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

@UtilityClass
public class TextFormatUtil {
    public String formatColor(String text) {
        final Pattern hexPattern = Pattern.compile("<#" + "([A-Fa-f0-9]{6})" + ">");
        Matcher matcher = hexPattern.matcher(text);
        StringBuilder builder = new StringBuilder(text.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            matcher.appendReplacement(builder, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(builder).toString();
    }

    public String formatTag(String text, char colorSymbol) {
        for (TextTag tag : TextTag.values()) {
            for (String t : tag.getTag()) {
                String appendText = colorSymbol + tag.getReplace();
                String formattedText = ChatColor.translateAlternateColorCodes(colorSymbol, appendText);
                text = text.replace(t, formattedText);
            }
        }

        return text;
    }

    public String format(String text) {
        String formatTag = formatTag(text, '&');
        return formatColor(formatTag);
    }
}

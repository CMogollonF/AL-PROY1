package encription.Coloring;

import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class Formatter {
 
    /**
     * Formats a text to use the determined style from the Names json.
     * @param text Text to format
     * @param style Corresponding style to use (bold and color)
     * @return The corresponding String to print for the format to become effective
     */
    public static String format(String text, TextStyle style){
        AttributedStyle s = AttributedStyle.DEFAULT;

        if(style.color != null) s = s.foreground(style.color.getJlineColor());
        if(style.bold) s = s.bold();

        return new AttributedStringBuilder()
            .append(text, s)
            .toAnsi();
        
    }

}

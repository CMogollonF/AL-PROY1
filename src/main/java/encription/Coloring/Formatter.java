package encription.Coloring;

import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class Formatter {
 
    public static String format(String text, TextStyle style){
        AttributedStyle s = AttributedStyle.DEFAULT;

        if(style.color != null) s = s.foreground(style.color.getJlineColor());
        if(style.bold) s = s.bold();

        return new AttributedStringBuilder()
            .append(text, s)
            .toAnsi();
        
    }

}

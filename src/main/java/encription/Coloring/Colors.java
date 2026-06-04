package encription.Coloring;

import org.jline.utils.AttributedStyle;

/**
 * Auxiliar method to convert enum colors into jLine colors
 */
public enum Colors {
    BLACK (AttributedStyle.BLACK),
    RED(AttributedStyle.RED),
    GREEN(AttributedStyle.GREEN),
    YELLOW(AttributedStyle.YELLOW),
    BLUE(AttributedStyle.BLUE),
    MAGENTA(AttributedStyle.MAGENTA),
    CYAN(AttributedStyle.CYAN),
    WHITE(AttributedStyle.WHITE);

    private final int jlineColor;

    /**
     * Creates the correlation between the color and its jline equivalent
     * @param jlineColor The numerical equivalent of the color in jLine
     */
    Colors(int jlineColor){
        this.jlineColor = jlineColor;
    }

    /**
     * @return The color for jline to use
     */
    public int getJlineColor(){
        return jlineColor;
    }
}


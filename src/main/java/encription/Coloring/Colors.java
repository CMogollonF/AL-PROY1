package encription.Coloring;

import org.jline.utils.AttributedStyle;

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

    Colors(int jlineColor){
        this.jlineColor = jlineColor;
    }

    public int getJlineColor(){
        return jlineColor;
    }
}


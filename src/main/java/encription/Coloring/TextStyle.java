package encription.Coloring;

/**
 * A representation of the Style, with colors and whether the text should be bold.
 */
public class TextStyle {
    
    public Colors color;
    public boolean bold;
    
    public TextStyle(Colors color, boolean bold){
        this.color = color;
        this.bold = bold;
    }
}

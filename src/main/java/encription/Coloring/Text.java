package encription.Coloring;

/**
 * A representation of the json format for a message.
 */
public class Text {
    protected String name;
    protected String string;
    protected Attributes attributes;

    public class Attributes {
        protected String color;
        protected boolean bold;
    }
}

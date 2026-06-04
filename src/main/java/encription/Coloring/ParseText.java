package encription.Coloring;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class ParseText {
    /**
     * Fetchs a string with its corresponding format from the json.
     * @param textName The name of the string, as defined in the json
     * @return The formatted string for use in the terminal
     */
    public static String getText(String textName){

        Text text = fetchText(textName);

        TextStyle style = getTextStyle(text);

        return Formatter.format(text.string, style);
    
    } 

    /**
     * Figures out the style of the text and converts it into a TextStyle
     * @param text the Text element to get the style from
     * @return The Style to use for the string
     */
    private static TextStyle getTextStyle(Text text){

        Colors color = null;
        if (text.attributes.color != null){
            try{
                color = Colors.valueOf(text.attributes.color.toUpperCase());
            } catch (IllegalArgumentException e) {}
        }

        return new TextStyle(
            color,
            text.attributes.bold
        );
    }

    /**
     * Finds the text in the json and returns a Text element that represents the string along with its style
     * @param textName The name of the string, as defined in the json
     * @return A Text element composed of the message, along with its style
     */
    private static Text fetchText(String textName){

        InputStream is = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("Names.json");


        
        InputStreamReader reader = new InputStreamReader(is);
            
        Gson gson = new Gson();
        Text[] text = gson.fromJson(
            reader,
            Text[].class
        );

        Text textScope = null;
        for(int i = 0; i < text.length; i++){
            if (text[i].name.equals(textName)){
                textScope = text[i];
                break;
            }
        }
        if (textScope == null) return null;

        return textScope;

    }
}

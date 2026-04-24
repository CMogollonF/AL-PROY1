package encription.Coloring;

import java.io.InputStream;
import java.io.InputStreamReader;
import org.jline.terminal.Terminal;
import com.google.gson.Gson;

public class ParseText {
    public static String getText(Terminal terminal,String textName){

        Text text = fetchText(textName);

        TextStyle style = getTextStyle(text);

        return Formatter.format(text.string, style);
    
    } 

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

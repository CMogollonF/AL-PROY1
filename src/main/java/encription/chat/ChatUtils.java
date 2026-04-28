package encription.chat;
import encription.Coloring.ParseText;

public class ChatUtils {
    private static Writer terminalWriter;

    private static void startWriter() {
        if(terminalWriter != null) return;

        terminalWriter = new Writer();
        terminalWriter.start();
        
    }

    public static void killWriter(){
        terminalWriter.kill();
    }

    public static boolean isDead(){
        return terminalWriter.isDead();
    }

    protected static void removeMessage(int size){

        for(int i = 0; i < size + 7; i++){
            ChatUtils.print("\b");
        }
    }

    protected static void printCurrentMessage(String Message){

        ChatUtils.print(String.format(ParseText.getText("ChatBlueprint"), ParseText.getText("UserDefault"), Message));
    }

    protected static String read() {
        ChatUtils.startWriter();

        return terminalWriter.read();
    }

    public static String readLine(){
        StringBuilder message = new StringBuilder();

        while(true){
            String letter = read();
            if (letter == null) continue;
            // print("received string: " + (int) letter.charAt(0) + " - " + letter + "\n");
            if ((int) letter.charAt(0) == 13) {
                break;
            }

            if ((int) letter.charAt(0) == 8) {
                if (message.length() == 0) continue;
                message.deleteCharAt(message.length() - 1);
                continue;
            }

            message.append(letter);
        }

        return message.toString();
    }

    public static void print(String message){
        ChatUtils.startWriter();

        terminalWriter.print(message);
    }

    public static void println(String message){

        ChatUtils.print(message + "\n");
    }

    public static void printFromJson(String name){
        terminalWriter.print(ParseText.getText(name));
    }
}

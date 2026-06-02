package encription.chat;
import encription.Coloring.ParseText;

public class ChatUtils {
    private static Writer terminalWriter;

    /**
     * Creates an instance of the writer if one wasn't already running.
     * This method makes sure we create the writer only once.
     * 
     */
    private static void startWriter() {
        if(terminalWriter != null) return;

        terminalWriter = new Writer();
        terminalWriter.start();
        
    }

    /**
     * Kills the writer
     */
    public static void killWriter(){
        terminalWriter.kill();
    }

    /**
     * @return Whether the instance was killed
     */
    public static boolean isDead(){
        return terminalWriter.isDead();
    }

    /**
     * Removes the message if remote wrote something.
     * @param size The lenght of the message to remove
     */
    protected static void removeMessage(int size){

        for(int i = 0; i < size; i++){
            ChatUtils.print("\b");
        }
    }

    /**
     * Prints the blueprint along with any stored message
     * @param Message The message saved that needs to be restored.
     */
    protected static void printCurrentMessage(String Message){

        ChatUtils.print(String.format(ParseText.getText("ChatBlueprint"), ParseText.getText("UserDefault"), Message));
    }

    /**
     * Returns whathever the writer had ready to read. Ensures a non-blocking behaviour at all times.
     * @return The message sent from terminal. Null if no message was sent.
     */
    protected static String read() {
        ChatUtils.startWriter();

        return terminalWriter.read();
    }

    /**
     * Reads an entire line of code. Note that this method will block the reader execution until a newline character is sent.
     * @return The message sent up until a newline character was read.
     */
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

    /**
     * Prints a message.
     * @param message The message to print.
     */
    public static void print(String message){
        ChatUtils.startWriter();

        terminalWriter.print(message);
    }

    /**
     * Prints a message, followed by a newline character.
     * @param message The message to print
     */
    public static void println(String message){

        ChatUtils.print(message + "\n");
    }

    /**
     * Calls the Parser to retrieve a string from Names.json
     * @param name The name associated to the string
     */
    public static void printFromJson(String name){
        terminalWriter.print(ParseText.getText(name));
    }
}

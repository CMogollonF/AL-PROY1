package encription.chat;
import java.io.IOException;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class ChatUtils {
    private static Terminal terminal;

    protected static void removeMessage(int size){
        for(int i = 0; i < size + 7; i++){
            System.out.print("\b");
        }
    }

    protected static void printCurrentMessage(String Message){
        System.out.print(String.format("[you]: %s", Message));
    }

    protected static int read() throws IOException{
        return terminal.reader().read();
    }

    protected static int readCharNonBlocking() throws IOException{
        if(terminal.reader().ready()) {
            return ChatUtils.read();
        }
        return 0;
    }

    public static void print(String message){
        terminal.writer().print(message);
        terminal.writer().flush();
    }

    public static void println(String message){
        ChatUtils.print(message + "\n");
    }

    public static void setNonblockingTerminal() throws IOException{
        terminal = createTerminal();

        terminal.enterRawMode();
    }

    public static void closeTerminal() throws IOException{
        terminal.close();
    }

    public static Terminal createTerminal() throws IOException{
        terminal = TerminalBuilder.builder()
        .system(true)
        .jna(true)
        .build();

        return terminal;
    }
}

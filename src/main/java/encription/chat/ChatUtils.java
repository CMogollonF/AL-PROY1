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

    protected static void print(String message){
        terminal.writer().print(message);
        terminal.writer().flush();
    }

    protected static void println(String message){
        ChatUtils.print(message + "\n");
    }

    protected static void setNonblockingTerminal() throws IOException{
        terminal = TerminalBuilder.builder()
        .system(true)
        .jna(true)
        .build();

        terminal.enterRawMode();
    }

    protected static void exitNonBlocking() throws IOException{
        terminal.close();
    }
}

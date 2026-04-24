package encription.chat;
import java.io.IOException;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import encription.Coloring.ParseText;

public class ChatUtils {
    private static Terminal terminal = null;
    private static boolean closed = false;

    protected static void removeMessage(int size){
        for(int i = 0; i < size + 7; i++){
            System.out.print("\b");
        }
    }

    protected static void printCurrentMessage(Terminal terminal, String Message){
        System.out.print(String.format(ParseText.getText(terminal, "ChatBlueprint"), ParseText.getText(terminal, "UserDefault"), Message));
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

    public static void printFromJson(Terminal terminal, String name){
        ChatUtils.println(ParseText.getText(terminal, name));
    }

    public static void setNonblockingTerminal() throws IOException{
        if (terminal == null) terminal = getTerminal();

        terminal.enterRawMode();
    }

    public static void closeTerminal() throws IOException{
        terminal.close();
        closed = true;
    }

    public static Terminal getTerminal() throws IOException{
        if (terminal == null | closed) {
            terminal = TerminalBuilder.builder()
                .system(true)
                .jna(true)
                .build();

            closed = false;
        }

        return terminal;
    }
}

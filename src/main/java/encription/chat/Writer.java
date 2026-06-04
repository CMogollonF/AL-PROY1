package encription.chat;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * Since we are using a non-blocking terminal, we can't use the normal methods for reading and writing from the terminal.
 * Writer creates the necessary methods to handle read/write operations, similar to the normal terminal, while allowing non-blocking
 * character by character reading that allows us to restore what the user wrote without needing to wait for them to press enter.
 */
public class Writer extends Thread{
    private Terminal terminal;
    private boolean killed;
    private BlockingQueue<String> queue;
    private StringBuilder savedMessage;

    /**
     * Sets the terminal to non-blocking mode
     */
    public Writer(){
        this.queue = new LinkedBlockingQueue<>();
        this.savedMessage = new StringBuilder();

        try {
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .jna(true)
                    .build();
        } catch (IOException e) { this.kill(); }

        this.killed = false;
    }

    /**
     * Processes the two main operations for the terminal.
     * For reading, the Writer saves the read data and prints to the terminal for feedback. The read characters are saved in a string.
     * For writing, the Writer checks if there is a message that needs to be displayed. If there is, display it
     * This is done while ensuring a non-blocking behavior.
     */
    @Override
    public void run(){
        terminal.enterRawMode();
        // terminal.writer().write("starting reader...");

        while(!killed){
            try{

                while (!queue.isEmpty()) {
                    String msg = queue.poll();

                    if(msg.charAt(0) == '\b') {
                        terminal.writer().write("\b \b");
                    } else {

                        terminal.writer().write(msg);
                    }

                    terminal.writer().flush();
                }

                int letter = terminal.reader().read(50);
                
                if (letter == -2) {
                    Thread.sleep(10);
                    continue;
                }

                savedMessage.append((char) letter);
                // print("savedMessage: " + savedMessage.toString() + "\n");

                Thread.sleep(10);
            }catch(Exception e) {this.kill();}
        }

        try {
            terminal.close();
        } catch (IOException e) {}
    }

    /**
     * Reads from the queue. Ensures a non-blocking behavior.
     * @return The message stored from the terminal, null if no message was stored.
     */
    public String read(){
        if (savedMessage.toString().isEmpty()) return null;
        String msg = savedMessage.toString();
        print(msg);
        savedMessage.delete(0, savedMessage.length());
        return msg;
    }

    /**
     * Adds a message to the queue of messages to print.
     * @param msg The message to print
     */
    public void print(String msg){
        queue.add(msg);
    }

    /**
     * Disables non-blocking mode and stops the Writer
     */
    public void kill(){
        this.killed = true;
    }

    /**
     * @return wheter the writer was killed
     */
    public boolean isDead(){
        return killed;
    }
}

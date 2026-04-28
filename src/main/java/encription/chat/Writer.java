package encription.chat;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Writer extends Thread{
    private Terminal terminal;
    private boolean killed;
    private BlockingQueue<String> queue;
    private StringBuilder savedMessage;

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

    @Override
    public void run(){
        terminal.enterRawMode();
        terminal.writer().write("starting reader...");

        while(!killed){
            try{

                while (!queue.isEmpty()) {
                    String msg = queue.poll();

                    if(msg == "\b") {
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

    public String read(){
        if (savedMessage.toString().isEmpty()) return null;
        String msg = savedMessage.toString();
        print(msg);
        savedMessage.delete(0, savedMessage.length());
        return msg;
    }

    public void print(String msg){
        queue.add(msg);
    }

    public void kill(){
        this.killed = true;
    }

    public boolean isDead(){
        return killed;
    }
}

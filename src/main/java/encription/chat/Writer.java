package encription.chat;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Writer extends Thread{
    private Terminal terminal;
    private boolean killed;
    private BlockingQueue<String> queue;
    private StringBuilder savedMessage;
    private int letters;
    private boolean printJob;

    public Writer(){
        this.queue = new LinkedBlockingQueue<>();
        this.savedMessage = new StringBuilder();
        this.letters = 0;
        this.printJob = false;

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
        // terminal.writer().write("starting reader...");

        while(!killed){
            try{

                while (!queue.isEmpty()) {
                    String msg = queue.poll();

                    if (msg.charAt(0) == '\n') {
                        this.letters = 0;
                        terminal.writer().write(msg);
                    } else if(msg.charAt(0) == '\b' && (this.letters > 0 || this.printJob)) {
                        if (!this.printJob) this.letters--;
                        terminal.writer().write("\b \b");
                    } else if (msg.charAt(0) != '\b') {
                        if (!this.printJob) {
                            this.letters++;
                        }
                        terminal.writer().write(msg);
                    }

                    terminal.writer().flush();
                }
                this.printJob = false;

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
        queue.add(msg);
        savedMessage.delete(0, savedMessage.length());
        return msg;
    }

    public void print(String msg){
        this.printJob = true;
        queue.add(msg);
    }

    public void kill(){
        this.killed = true;
    }

    public boolean isDead(){
        return killed;
    }
}

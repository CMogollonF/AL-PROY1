package encription.chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Connection {

    public Connection(String ipAddress) throws InterruptedException{
        Socket socket = null;
        if ("SERVER".equals(ipAddress.toUpperCase())){
            ServerSocket server;
            try{
                server = new ServerSocket(5000);
            } catch (IOException e) {
                System.err.println("Server socket unavailable.");
                return;
            }

            try{
                socket = server.accept();
                server.close();
            } catch (IOException e){
                System.err.println("\nRemote unavailable.");
            }
        }
        else {
            int connectionAttemps = 0;

            System.out.print(String.format("Trying to connect to remote (%d)...", connectionAttemps));
            
            while(socket == null){
                try {
                    socket = new Socket(ipAddress, 5000);
                } catch (IOException e){
                    connectionAttemps++;
                    System.out.print(String.format("\b\b\b\b\b%d)...", connectionAttemps));
                    if(connectionAttemps >= 15){
                        System.err.println("\nRemote failed to respond in time. Terminating...");
                        return;
                    }
                    Thread.sleep(1000);
                }
            }
        }

        
        

        
        System.out.println("\nRemote connected. Starting session...");
        
        try{
            
            PrintWriter remoteWriter = new PrintWriter(
                socket.getOutputStream(), true
            );
            
            StringBuilder message = new StringBuilder();
            Listener listener = new Listener(socket, message);
            
            listener.start();
            
            ChatUtils.setNonblockingTerminal();

            while (!listener.isTerminated()){
                ChatUtils.printCurrentMessage("");
                while(!listener.isTerminated()){
                    int letter = ChatUtils.readCharNonBlocking();
                    if (letter == 0) continue;
                    if (letter == 13) break;

                    if (letter == 8) {
                        ChatUtils.print("\b \b");
                        message.deleteCharAt(message.length() - 1);
                        continue;
                    }

                    message.append((char) letter);
                    ChatUtils.print(String.valueOf((char) letter));
                }
                ChatUtils.println("");
                if (message.isEmpty()) break;

                remoteWriter.println(message.toString());
                message.delete(0, message.length());    
                while(ChatUtils.readCharNonBlocking() != 0);
                // System.out.println(String.format("Sent message (%s) to remote.", message));
            }
            ChatUtils.closeTerminal();
            listener.terminate();

            
        } catch (IOException e){
            System.err.println(String.format("Session terminated forcefully (%s).", e.getMessage()));
        }

        try{
            socket.close();
    
        } catch(IOException e){
            
        }
        
    }

    private class Listener extends Thread {

        private Socket socket;
        private StringBuilder message;
        private boolean terminated = false;

        public Listener(Socket socket, StringBuilder message){
            this.socket = socket;
            this.message = message;
        }

        @Override
        public void run() {
            try{
                BufferedReader remoteMessage = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

                String msg;
                while (!isTerminated()) {
                    msg = remoteMessage.readLine();
                    if(msg == null) break;
                    // if (msg.isBlank()) continue;
                    
                    ChatUtils.removeMessage(message.length());
                    ChatUtils.println(String.format("[Remote]: %s", msg));
                    ChatUtils.printCurrentMessage(message.toString());


                }
                socket.close();
            } catch (IOException e){
                System.out.println("Connection terminated.");
                this.terminate();
                return;
            }
            System.out.println("Remote closed connection. Press enter to exit.");
            this.terminate();
        }

        public void terminate(){
            this.terminated = true;
        }

        public boolean isTerminated(){
            return this.terminated;
        }
    }
}

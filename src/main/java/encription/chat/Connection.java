package encription.chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.jline.terminal.Terminal;

import encription.Coloring.ParseText;
import encription.Matrix.Encription;

public class Connection {

    public static void Connect(String ipAddress) throws InterruptedException, IOException{
        Socket socket = null;
        Terminal terminal = ChatUtils.getTerminal();

        if ("SERVER".equals(ipAddress.toUpperCase())){
            ServerSocket server;
            try{
                server = new ServerSocket(5000);
            } catch (IOException e) {
                System.err.println(ParseText.getText(terminal, "SocketUnavailable"));
                return;
            }

            try{
                ChatUtils.println(ParseText.getText(terminal, "ConnectingServer"));
                socket = server.accept();
                server.close();
            } catch (IOException e){
                System.err.println("\n" + ParseText.getText(terminal, "RemoteUnavailable"));
            }
        }
        else {
            int connectionAttemps = 0;

            System.out.print(String.format(ParseText.getText(terminal, "ConnectingClient"), connectionAttemps));
            
            while(socket == null){
                try {
                    socket = new Socket(ipAddress, 5000);
                } catch (IOException e){
                    connectionAttemps++;
                    System.out.print(String.format("\b\b\b\b\b%d)...", connectionAttemps));
                    if(connectionAttemps >= 15){
                        System.err.println(ParseText.getText(terminal, "FailedConnection"));
                        return;
                    }
                    Thread.sleep(1000);
                }
            }
        }

        
        

        
        ChatUtils.println(ParseText.getText(terminal, "RemoteConnected"));
        
        try{
            
            PrintWriter remoteWriter = new PrintWriter(
                socket.getOutputStream(), true
            );
            
            StringBuilder message = new StringBuilder();
            Listener listener = new Listener(socket, message);
            
            listener.start();
            
            ChatUtils.setNonblockingTerminal();

            while (!listener.isTerminated()){
                ChatUtils.printCurrentMessage(terminal , message.toString());
                while(!listener.isTerminated()){
                    int letter = ChatUtils.readCharNonBlocking();
                    if (letter == 0) continue;
                    if (letter == 13) break;

                    if (letter == 8) {
                        if (message.length() == 0) continue;
                        ChatUtils.print("\b \b");
                        message.deleteCharAt(message.length() - 1);
                        continue;
                    }

                    message.append((char) letter);
                    ChatUtils.print(String.valueOf((char) letter));
                }
                ChatUtils.println("");
                if (message.isEmpty()) {
                    ChatUtils.println(ParseText.getText(terminal, "ConnectionTerminated"));
                    break;
                };

                String encripted = Encription.encriptMessage(message.toString());
                remoteWriter.println(encripted);
                message.delete(0, message.length());    
                while(ChatUtils.readCharNonBlocking() != 0);
                // System.out.println(String.format("Sent message (%s) to remote.", message));
            }
            ChatUtils.closeTerminal();
            listener.terminate();

            
        } catch (IOException e){
            
        }

        try{
            socket.close();
    
        } catch(IOException e){
            
        }
        
    }

    private static class Listener extends Thread {

        private Socket socket;
        private StringBuilder message;
        private boolean terminated = false;
        Terminal terminal;

        public Listener(Socket socket, StringBuilder message) throws IOException{
            this.socket = socket;
            this.message = message;
            this.terminal = ChatUtils.getTerminal();
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
                    
                    String decrypted = Encription.decryptMessage(msg);

                    ChatUtils.removeMessage(message.length());
                    ChatUtils.println(String.format(ParseText.getText(terminal, "ChatBlueprint"),ParseText.getText(terminal, "RemoteDefault"), decrypted));
                    ChatUtils.printCurrentMessage(terminal, message.toString());


                }
                socket.close();
            } catch (IOException e){
                ChatUtils.println(ParseText.getText(terminal, "ConnectionTerminated"));
                this.terminate();
                return;
            }
            ChatUtils.println(ParseText.getText(terminal, "RemoteClosed")); 
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

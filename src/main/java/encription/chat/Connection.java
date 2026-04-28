package encription.chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import encription.Coloring.ParseText;
import encription.Matrix.Encription;

public class Connection {

    public static void Connect(String ipAddress) throws InterruptedException, IOException{
        Socket socket = null;

        if ("SERVER".equals(ipAddress.toUpperCase())){
            ServerSocket server;
            try{
                server = new ServerSocket(5000);
            } catch (IOException e) {
                ChatUtils.println(ParseText.getText("SocketUnavailable"));
                return;
            }

            try{
                ChatUtils.println(ParseText.getText("ConnectingServer"));
                socket = server.accept();
                server.close();
            } catch (IOException e){
                ChatUtils.println("\n" + ParseText.getText("RemoteUnavailable"));
            }
        }
        else {
            int connectionAttemps = 0;

            ChatUtils.print(String.format(ParseText.getText("ConnectingClient"), connectionAttemps));
            
            while(socket == null){
                try {
                    socket = new Socket(ipAddress, 5000);
                } catch (IOException e){
                    connectionAttemps++;
                    ChatUtils.print(String.format("\b\b\b\b\b%d)...", connectionAttemps));
                    if(connectionAttemps >= 15){
                        ChatUtils.println(ParseText.getText("FailedConnection"));
                        return;
                    }
                    Thread.sleep(1000);
                }
            }
        }

        
        

        
        ChatUtils.println(ParseText.getText("RemoteConnected"));
        
        try{
            
            PrintWriter remoteWriter = new PrintWriter(
                socket.getOutputStream(), true
            );
            
            StringBuilder message = new StringBuilder();
            Listener listener = new Listener(socket, message);
            
            listener.start();

                while(!listener.isTerminated()){
                    ChatUtils.printCurrentMessage(message.toString());
                    while(!listener.isTerminated()){
                        String letter = ChatUtils.read();
                        if (letter == null) continue;
                        // print("received string: " + (int) letter.charAt(0) + " - " + letter + "\n");
                        if ((int) letter.charAt(0) == 13) break;

                        if ((int) letter.charAt(0) == 8) {
                            if (message.length() == 0) continue;
                            message.deleteCharAt(message.length() - 1);
                            continue;
                        }

                        message.append(letter);
                    }

                    if (message.isEmpty()) {
                        ChatUtils.println(ParseText.getText("ConnectionTerminated"));
                        listener.terminate();
                    } else {
                        String encripted = Encription.encriptMessage(message.toString());
                        remoteWriter.println(encripted);
                        message.delete(0, message.length());    
                        ChatUtils.println("");
                    }
                }
            
            
            
        } catch (IOException e){
            
        }
        
        ChatUtils.killWriter();
        try{
            socket.close();
    
        } catch(IOException e){
            
        }
        
    }

    private static class Listener extends Thread {

        private Socket socket;
        private StringBuilder message;
        private boolean terminated = false;

        public Listener(Socket socket, StringBuilder message) throws IOException{
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
                    
                    String decrypted = Encription.decryptMessage(msg);

                    ChatUtils.removeMessage(message.length());
                    ChatUtils.println(String.format(ParseText.getText("ChatBlueprint"),ParseText.getText("RemoteDefault"), decrypted));
                    ChatUtils.printCurrentMessage(message.toString());


                }
                socket.close();
            } catch (IOException e){
                ChatUtils.println(ParseText.getText("ConnectionTerminated"));
                this.terminate();
                return;
            }
            ChatUtils.println(ParseText.getText("RemoteClosed")); 
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

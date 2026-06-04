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

    /**
     * Handles the main read/write interaction within the server and the client. Also creates and handles the terminal for
     * writing and reading operations using the Writer. Note that only one machine MUST take the role of the server; this method
     * won't work if both machines try to take the same role.
     * 
     * We refer to the machine making the connection as Remote, and the machine taking the role of the server as Server. 
     * 
     * @param ipAddress the ip address of the remote connection (or SERVER to take the role of the server)
     * @throws InterruptedException If the terminal requests to stop execution
     * @throws IOException If Names.json doesn't exist
     */
    public static void Connect(String ipAddress) throws InterruptedException, IOException{
        Socket socket = null;

        // The system chose to take the role of the server
        if ("SERVER".equals(ipAddress.toUpperCase())){
            ServerSocket server;
            try{
                //Start listening on socket 5000
                server = new ServerSocket(5000);
            } catch (IOException e) {
                //Socket was occupied
                ChatUtils.println(ParseText.getText("SocketUnavailable"));
                return;
            }

            try{
                //Wait until remote connects
                ChatUtils.println(ParseText.getText("ConnectingServer"));
                socket = server.accept();
                server.close();
            } catch (IOException e){
                ChatUtils.println("\n" + ParseText.getText("RemoteUnavailable"));
            }
        }
        //The system chose to take the role of remote
        else {
            //We have a total of 15 attempts to establish a connection before timeout
            Integer connectionAttemps = 0;

            ChatUtils.print(String.format(ParseText.getText("ConnectingClient"), connectionAttemps));
            
            while(socket == null){
                try {
                    socket = new Socket(ipAddress, 5000);
                } catch (IOException e){
                    ChatUtils.removeMessage(4 + connectionAttemps.toString().length());
                    connectionAttemps++;
                    ChatUtils.print(String.format(ParseText.getText("ChangingText"), connectionAttemps));
                    if(connectionAttemps >= 15){
                        ChatUtils.println(ParseText.getText("FailedConnection"));
                        Thread.sleep(500);
                        System.exit(0);
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

    /**
     * Auxiliar class that listen for messages for the remote
     */
    private static class Listener extends Thread {

        private Socket socket;
        private StringBuilder message;
        private boolean terminated = false;

        public Listener(Socket socket, StringBuilder message) throws IOException{
            this.socket = socket;
            this.message = message;
        }

        /**
         * Checks wheter remote has sent a message and prints it using the Writer class
         */
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

                    ChatUtils.removeMessage(message.length() + 7);
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

        /**
         * Kills the listener.
         */
        public void terminate(){
            this.terminated = true;
        }

        /**
         * 
         * @return Whether the Listener has been killed
         */
        public boolean isTerminated(){
            return this.terminated;
        }
    }
}

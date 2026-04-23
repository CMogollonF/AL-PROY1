import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Connection {
    private ServerSocket server;
    private Socket client;

    public Connection(String ipAddress) throws InterruptedException{
        try{
            this.server = new ServerSocket(5000);
        } catch (IOException e) {
            System.err.println("Server socket unavailable.");
            return;
        }

        int connectionAttemps = 0;

        System.out.print(String.format("Trying to connect to remote (%d)...", connectionAttemps));
        
        client = null;
        while(client == null){
            try {
                this.client = new Socket(ipAddress, 5000);
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

        Socket remote = null;
        try{
            remote = server.accept();
        } catch (IOException e){
            System.err.println("\nRemote unavailable. Terminating...");
            return;
        }

        
        Listener listener = new Listener(remote);
        
        listener.start();

        
        System.out.println("\nRemote connected. Starting session...");

        try{
            PrintWriter remoteWriter = new PrintWriter(
                client.getOutputStream()
            );

            String message;
            BufferedReader keyboard = new BufferedReader(
                new InputStreamReader(System.in)
            );
            while (!listener.isTerminated() && (message = keyboard.readLine()) != null){
                remoteWriter.println(message);
                System.out.println("Sent message to remote.");
            }

            
        } catch (IOException e){
            System.err.println(String.format("Session terminated forcefully (%s).", e.getMessage()));
        }

        try{
            listener.terminate();
            client.close();
            server.close();
            remote.close();
    
        } catch(IOException e){
            
        }
        
    }

    private class Listener extends Thread {

        private Socket socket;
        private boolean terminated = false;

        public Listener(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                BufferedReader remoteMessage = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

                String msg;
                while (!terminated) {
                    while((msg = remoteMessage.readLine()) != null){
                        System.out.println(String.format("[Remote]: %s", msg));
                    }
                }
            } catch (IOException e){
                System.out.println("Connection terminated.");
                this.terminate();
            }
            System.out.println("Finished reading.");
        }

        public void terminate(){
            this.terminated = true;
        }

        public boolean isTerminated(){
            return this.terminated;
        }
    }
}

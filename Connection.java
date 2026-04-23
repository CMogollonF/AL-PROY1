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
                if(connectionAttemps >= 10){
                    System.err.println("Remote failed to respond in time. Terminating...");
                    return;
                }
                System.out.print(String.format("\b\b\b\b\b%d)...", connectionAttemps));
                Thread.sleep(1000);
            }
        }

        Socket remote = null;
        try{
            remote = server.accept();
        } catch (IOException e){
            System.err.println("Remote unavailable. Terminating...");
            return;
        }

        
        Listener listener = new Listener(remote);
        
        listener.start();

        
        System.out.println("Remote connected. Starting session...");

        try{
            PrintWriter remoteWriter = new PrintWriter(
                client.getOutputStream()
            );

            String message;
            BufferedReader keyboard = new BufferedReader(
                new InputStreamReader(System.in)
            );
            while ((message = keyboard.readLine()) != null){
                remoteWriter.println(message);
            }

            client.close();
            server.close();
            remote.close();
        } catch (IOException e){
            System.err.println("Session terminated forcefully.");
        }
        
    }

    private class Listener extends Thread {

        private Socket socket;

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
            while((msg = remoteMessage.readLine()) != null){
                System.out.println(String.format("[Remote]: %s", msg));
            }
        } catch (IOException e){
            System.out.println("Connection terminated.");
        }
        }
    }
}

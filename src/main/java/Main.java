import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.jline.terminal.Terminal;

import encription.Coloring.ParseText;
import encription.chat.ChatUtils;
import encription.chat.Connection;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        BufferedReader tec = new BufferedReader(
            new InputStreamReader(System.in)
        );

        final DatagramSocket ds = new DatagramSocket();
        ds.connect(InetAddress.getByName("8.8.8.8"), 12345);
        String localAddress = ds.getLocalAddress().getHostAddress();
        ds.close();
        
        Terminal terminal = ChatUtils.getTerminal();
        System.out.print(String.format(ParseText.getText(terminal, "start"), localAddress));
        String ipAdress = tec.readLine();

        //start connection
        new Connection(ipAdress);
    }
}

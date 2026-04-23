import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TestConnection {
    public static void main(String[] args) throws IOException, InterruptedException{
        BufferedReader tec = new BufferedReader(
            new InputStreamReader(System.in)
        );

        final DatagramSocket ds = new DatagramSocket();
        ds.connect(InetAddress.getByName("8.8.8.8"), 12345);
        String localAddress = ds.getLocalAddress().getHostAddress();
        ds.close();
        

        System.out.print(String.format("Ingrese la ip remota (su ip es %s): ", localAddress));
        String ipAdress = tec.readLine();
        tec.close();

        //start connection
        new Connection(ipAdress);
    }
}

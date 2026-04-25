import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.jline.terminal.Terminal;

import encription.Coloring.ParseText;
import encription.Matrix.CreateMatrix;
import encription.Matrix.Encription;
import encription.chat.ChatUtils;
import encription.chat.Connection;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        String ipAdress;
        try (BufferedReader tec = new BufferedReader(
                new InputStreamReader(System.in)
        )) {
            Terminal terminal = ChatUtils.getTerminal();

            ChatUtils.print(ParseText.getText(terminal, "AskForPin"));
            String pin = tec.readLine();
            
            try {
                Integer.parseInt(pin);
            } catch (NumberFormatException e) {
                ChatUtils.print(ParseText.getText(terminal, "NotANumber"));
                System.exit(0);
            }
            
            double[][] matrix = CreateMatrix.createMatrix(pin);

            if (matrix == null){
                ChatUtils.print(ParseText.getText(terminal, "MalformedPin"));
                System.exit(0);
            }
            
            Encription.setMatrix(matrix);
            
            final DatagramSocket ds = new DatagramSocket();
            ds.connect(InetAddress.getByName("8.8.8.8"), 12345);
            String localAddress = ds.getLocalAddress().getHostAddress();
            ds.close();
            
            System.out.print(String.format(ParseText.getText(terminal, "start"), localAddress));
            ipAdress = tec.readLine();
            if (!(ipAdress.toUpperCase().equals("SERVER") || ipAdress.toUpperCase().equals("LOCALHOST")) && !ipAdress.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
                ChatUtils.print(ParseText.getText(terminal, "MalformedIP"));
                System.exit(0);
            }
        }
        //start connection
        Connection.Connect(ipAdress);
    }
}

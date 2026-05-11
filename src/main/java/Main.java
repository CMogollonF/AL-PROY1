import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import encription.Coloring.ParseText;
import encription.Matrix.CreateMatrix;
import encription.Matrix.Encription;
import encription.Matrix.GridOperations;
import encription.chat.ChatUtils;
import encription.chat.Connection;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        String ipAdress;

        ChatUtils.print(ParseText.getText( "AskForPin"));
        String pin = ChatUtils.readLine();
        ChatUtils.println("");
        
        try {
            int rawPin = Integer.parseInt(pin);
            if (rawPin < 0){
                ChatUtils.println(ParseText.getText( "NegativePin"));
                Thread.sleep(100);
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            ChatUtils.println(ParseText.getText( "NotANumber"));
            Thread.sleep(100);
            System.exit(0);
        }
        
        double[][] matrix = CreateMatrix.createMatrix(pin);

        if (matrix == null){
            ChatUtils.println(ParseText.getText( "MalformedPin"));
            Thread.sleep(100);
            System.exit(0);
        }

        if (GridOperations.calculateDet(matrix) == 0) {
            ChatUtils.println(ParseText.getText( "DeterminantZero"));
            Thread.sleep(100);
            System.exit(0);
        }
        
        Encription.setMatrix(matrix);
        
        final DatagramSocket ds = new DatagramSocket();
        ds.connect(InetAddress.getByName("8.8.8.8"), 12345);
        String localAddress = ds.getLocalAddress().getHostAddress();
        ds.close();
        
        ChatUtils.print(String.format(ParseText.getText( "start"), localAddress));
        ipAdress = ChatUtils.readLine();
        ChatUtils.println("");

        if (!(ipAdress.toUpperCase().equals("SERVER") || ipAdress.toUpperCase().equals("LOCALHOST")) && !ipAdress.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            ChatUtils.println(ParseText.getText( "MalformedIP"));
            System.exit(0);
        }
        //start connection
        Connection.Connect(ipAdress);
    }
}

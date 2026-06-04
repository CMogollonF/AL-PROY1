import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import encription.Coloring.ParseText;
import encription.Matrix.CreateMatrix;
import encription.Matrix.Encription;
import encription.Matrix.GridOperations;
import encription.chat.ChatUtils;
import encription.chat.Connection;
import encription.chat.TestMode;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        String ipAdress;

        //Ask for encription pin
        ChatUtils.print(ParseText.getText( "AskForPin"));
        String pin = ChatUtils.readLine();
        ChatUtils.println("");
        
        //We make sure the pin is an actual number
        try {
            long rawPin = Long.parseLong(pin);
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
        
        //We make a matrix out of the pin and check if the matrix failed to create
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
        
        //We get the machine's local ip
        final DatagramSocket ds = new DatagramSocket();
        ds.connect(InetAddress.getByName("8.8.8.8"), 12345);
        String localAddress = ds.getLocalAddress().getHostAddress();
        ds.close();
        
        //Ask the user for remote ip (or to take the role of the server)
        ChatUtils.println(String.format(ParseText.getText( "start"), localAddress));
        ChatUtils.print(String.format(ParseText.getText("ChatBlueprint"), ParseText.getText("UserDefault"), ""));
        ipAdress = ChatUtils.readLine();
        ChatUtils.println("");

        if(ipAdress.toUpperCase().equals("TEST")){
            ChatUtils.printFromJson("test");
            TestMode.startTestMode();
        }
        //Make sure we actually got an ip address (or that we chose to me the server/ connect to the same pc)
        if (!(ipAdress.toUpperCase().equals("SERVER") || ipAdress.toUpperCase().equals("LOCALHOST")) && !ipAdress.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            ChatUtils.println(ParseText.getText( "MalformedIP"));
            Thread.sleep(500);
            System.exit(0);
        }
        //start connection
        Connection.Connect(ipAdress);
    }
}

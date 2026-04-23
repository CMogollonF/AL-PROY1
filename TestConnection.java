import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestConnection {
    public static void main(String[] args) throws IOException, InterruptedException{
        BufferedReader tec = new BufferedReader(
            new InputStreamReader(System.in)
        );

        System.out.print("Ingrese la ip remota: ");
        String ipAdress = tec.readLine();
        tec.close();

        //start connection
        new Connection(ipAdress);
    }
}

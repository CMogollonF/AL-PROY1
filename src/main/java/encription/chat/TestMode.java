package encription.chat;

import encription.Matrix.CreateMatrix;
import encription.Matrix.Encription;

public class TestMode {
    public static void startTestMode() throws InterruptedException{
        String message;
        boolean enc = true;

        while(true){
            ChatUtils.printFromJson(enc? "encMode" : "decMode");
            message = ChatUtils.readLine();
            ChatUtils.println("");

            //We received a command
            if (message.startsWith("/")){
                String commandName;
                if (message.contains(" ")){
                    commandName = message
                        .substring(0, message.indexOf(' '))
                        .strip()
                        .replace("/", "");
                    message = message
                        .substring(message.indexOf(' '))
                        .strip();

                } else {
                    commandName = message.replace("/", "");
                    message = "";
                }

                switch (commandName) {
                    case "switch":
                        enc = !enc;
                        break;
                    case "change":
                        double[][] matrix = CreateMatrix.createMatrix(message);
                        Encription.setMatrix(matrix);
                        break;
                    case "exit":
                        ChatUtils.printFromJson("exiting");
                        Thread.sleep(500);
                        System.exit(0);
                    default:
                        ChatUtils.printFromJson("unknownCommand");
                        break;
                }

            } else {
                ChatUtils.printFromJson("testAnswer");
                if (enc){
                    ChatUtils.println(Encription.encriptMessage(message));
                } else {
                    ChatUtils.println(Encription.decryptMessage(message));
                }
            }
        }
    }
}
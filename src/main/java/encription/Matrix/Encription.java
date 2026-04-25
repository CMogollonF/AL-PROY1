package encription.Matrix;


public class Encription {
    public static double[][] encriptionMatrix;

    public static void setMatrix(double[][] matrix){
        encriptionMatrix = matrix;
    }

    public static String encriptMessage(String message){
        if( GridOperations.calculateDet(encriptionMatrix) == 0 ) System.out.println("WARNING: String cannot be decoded (determinant = 0)");

        StringBuilder encriptedMessage = new StringBuilder();
        for(int i = 0; i < message.length(); i += encriptionMatrix.length) {
            double[][] mockGrid = new double[encriptionMatrix.length][1];

            for(int j = 0; j < encriptionMatrix.length; j++)
                mockGrid[j][0] = (j + i < message.length())? Relations.FetchCode(message.charAt(i + j)) : 28;

            int[][] newGrid = Relations.normalize(GridOperations.multiplyGrids(encriptionMatrix, mockGrid));
            for(int j = 0; j < mockGrid.length; j++) encriptedMessage.append(Relations.Decode(newGrid[j][0]));
        }

        return encriptedMessage.toString();
    }

    public static String decryptMessage(String message){
        if( GridOperations.calculateDet(encriptionMatrix) == 0 ) return "Message can't be decrypted (determinant = 0)";

        encriptionMatrix = GridOperations.findInverse(encriptionMatrix);

        StringBuilder encriptedMessage = new StringBuilder();
         for(int i = 0; i < message.length(); i += encriptionMatrix.length) {
            double[][] mockGrid = new double[encriptionMatrix.length][1];

            for(int j = 0; j < encriptionMatrix.length; j++)
                mockGrid[j][0] = (j + i < message.length())? Relations.FetchCode(message.charAt(i + j)) : 0;

            int[][] newGrid = Relations.normalize(GridOperations.multiplyGrids(encriptionMatrix, mockGrid));
            for(int j = 0; j < mockGrid.length; j++) encriptedMessage.append(Relations.Decode(newGrid[j][0]));
        }

        return encriptedMessage.toString();
    }
}

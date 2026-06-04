package encription.Matrix;

import encription.chat.ChatUtils;


public class Encription {
    private static double[][] encriptionMatrix;

    /**
     * Sets the encryption matrix to be used for encryption/decryption operations.
     * 
     * @param matrix the encryption matrix
     */
    public static void setMatrix(double[][] matrix){
        encriptionMatrix = matrix;
    }

    /**
     * Encrypts a message using matrix-based encryption.
     * Returns empty string if matrix has determinant 0 (non-invertible).
     * 
     * @param message the plaintext message to encrypt
     * @return the encrypted message, or empty string if message cannot be decrypted.
     */
    public static String encriptMessage(String message){
        if( GridOperations.calculateDet(encriptionMatrix) == 0 ) {
            ChatUtils.println("WARNING: String cannot be decoded (determinant = 0)");
            return "";
        }

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

    /**
     * Decrypts a message using matrix-based decryption.
     * Returns error message if matrix has determinant 0 (cannot be inverted).
     * 
     * @param message the encrypted message to decrypt
     * @return the decrypted message, or error message if message cannot be decrypted.
     */
    public static String decryptMessage(String message){
        if( GridOperations.calculateDet(encriptionMatrix) == 0 ) return "Message can't be decrypted (determinant = 0)";

        double[][] inverse = GridOperations.findInverse(encriptionMatrix);

        StringBuilder encriptedMessage = new StringBuilder();
         for(int i = 0; i < message.length(); i += inverse.length) {
            double[][] mockGrid = new double[inverse.length][1];

            for(int j = 0; j < inverse.length; j++)
                mockGrid[j][0] = (j + i < message.length())? Relations.FetchCode(message.charAt(i + j)) : 0;

            int[][] newGrid = Relations.normalize(GridOperations.multiplyGrids(inverse, mockGrid));
            for(int j = 0; j < mockGrid.length; j++) encriptedMessage.append(Relations.Decode(newGrid[j][0]));
        }

        return encriptedMessage.toString();
    }
}

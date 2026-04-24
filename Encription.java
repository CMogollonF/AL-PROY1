

public class Encription {
    public static String encriptMessage(double[][] grid, String message){
        if( GridOperations.calculateDet(grid) == 0 ) System.out.println("WARNING: String cannot be decoded (determinant = 0)");

        StringBuilder encriptedMessage = new StringBuilder();
        for(int i = 0; i < message.length(); i += grid.length) {
            double[][] mockGrid = new double[grid.length][1];

            for(int j = 0; j < grid.length; j++)
                mockGrid[j][0] = (j + i < message.length())? Relations.FetchCode(message.charAt(i + j)) : 28;

            int[][] newGrid = Relations.normalize(GridOperations.multiplyGrids(grid, mockGrid));
            for(int j = 0; j < mockGrid.length; j++) encriptedMessage.append(Relations.Decode(newGrid[j][0]));
        }

        return encriptedMessage.toString();
    }

    public static String decryptMessage(double[][] grid, String message){
        if( GridOperations.calculateDet(grid) == 0 ) return "Message can't be decrypted (determinant = 0)";

        grid = GridOperations.findInverse(grid);

        StringBuilder encriptedMessage = new StringBuilder();
         for(int i = 0; i < message.length(); i += grid.length) {
            double[][] mockGrid = new double[grid.length][1];

            for(int j = 0; j < grid.length; j++)
                mockGrid[j][0] = (j + i < message.length())? Relations.FetchCode(message.charAt(i + j)) : 0;

            int[][] newGrid = Relations.normalize(GridOperations.multiplyGrids(grid, mockGrid));
            for(int j = 0; j < mockGrid.length; j++) encriptedMessage.append(Relations.Decode(newGrid[j][0]));
        }

        return encriptedMessage.toString();
    }
}

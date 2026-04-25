package encription.Matrix;

public class CreateMatrix {
    public static double[][] createMatrix(String pin){
        double RawSize = Math.sqrt(pin.length());
        if(RawSize % 1 != 0) return null;

        int size = (int) Math.sqrt(pin.length());
        double[][] matrix = new double[size][size];

        int index = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                matrix[i][j] = (double) Integer.parseInt(pin.substring(index,index + 1));
                index++;
            }
        }

        return matrix;
        
    }
}

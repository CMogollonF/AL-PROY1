package encription.Matrix;

public class CreateMatrix {
    /**
     * Creates a square matrix from a PIN string.
     * The PIN length must be a perfect square (4, 9, 16, 25, etc.).
     * Each digit of the PIN becomes an element in the matrix, read left-to-right, top-to-bottom.
     * 
     * @param pin the PIN string (must contain only digits and have perfect square length)
     * @return a square matrix populated with PIN digits, or null if PIN length is not a perfect square
     */
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

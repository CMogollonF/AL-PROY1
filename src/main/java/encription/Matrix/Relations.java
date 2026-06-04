package encription.Matrix;


public class Relations {
    /**
     * Converts a letter into the corresponding number
     * @param letter Letter to decode
     * @return the corresponding number, or -1 if the letter is not part of the list
     */
    public static int FetchCode(char letter){
        if(letter >= 'a' && letter <= 'z') return letter - 'a';
        else if (letter >= 'A' && letter <= 'Z') return letter - 'A';
        else if (letter == '.') return 26;
        else if (letter == ',') return 27;
        else if (letter == ' ') return 28;
        else return -1;
    }   

    /**
     * Converts a code into the corresponding letter
     * @param code The number to decode
     * @return the corresponding letter or space if the code is not valid
     */
    public static char Decode(int code){
        if(code <= 25) return (char)(code + 'A');
        else if(code == 26) return '.';
        else if(code == 27) return ',';
        else return ' ';
    }

    /**
     * Makes sure the value is between 0 and 29.
     * @param value Value to normalize
     * @return Number between 0 and 29
     */
    public static int normalize(double value){
        int newVal = (int) value % 29;
        if (newVal < 0) return 29 + newVal;
        else return newVal;
    }

    /**
     * Makes sure the grid is normalized. That is, that all values are between 0 and 29.
     * @param grid Grid to normalize.
     * @return The normalized grid, with all values between 0 and 29
     */
    public static int[][] normalize(double[][] grid){
        int[][] newGrid = new int[grid.length][grid[0].length];
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                newGrid[i][j] = normalize(grid[i][j]);
            }
        }

        return newGrid;
    }

}

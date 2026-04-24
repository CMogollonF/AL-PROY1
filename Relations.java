

public class Relations {
    public static int FetchCode(char letter){
        if(letter >= 'a' && letter <= 'z') return letter - 'a';
        else if (letter >= 'A' && letter <= 'Z') return letter - 'A';
        else if (letter == '.') return 26;
        else if (letter == ',') return 27;
        else if (letter == ' ') return 28;
        else return -1;
    }   

    public static char Decode(int code){
        if(code < 25) return (char)(code + 'A');
        else if(code == 26) return '.';
        else if(code == 27) return ',';
        else return ' ';
    }

    public static int normalize(double value){
        int newVal = (int) value % 29;
        if (newVal < 0) return 29 + newVal;
        else return newVal;
    }

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

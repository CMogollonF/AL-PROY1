package encription.Matrix;
public final class GridOperations {

    public static int calculateDet(int[][] grid){
        int det = 0;
        for(int i = 0; i < grid.length; i++){
            det += grid[i][0] * calculateCofactor(grid, i, 0);
        }
        return det;
    } 

    private static int calculateCofactor(int[][] grid, int row, int column){
        int[][] subGrid = calculateSubGrid(grid, row, column);

        if(subGrid.length == 2) return (int)Math.pow(-1, row + column) * (subGrid[0][0]*subGrid[1][1]  - subGrid[0][1]*subGrid[1][0]);
        if(subGrid.length == 1) return subGrid[0][0];


        return (int)Math.pow(-1, row + column) * calculateDet(subGrid); 
    }

    private static int[][] calculateSubGrid(int[][] grid, int row, int column){
        int[][] subGrid = new int[grid.length - 1][grid.length - 1];

        int offsetX = 0;
        for(int i = 0; i < subGrid.length; i++){
            if(i == row){
                offsetX = 1;
            }

            int offsetY = 0;
            for(int j = 0; j < subGrid.length; j++){
                
                if(j == column){
                    offsetY = 1;
                }
                subGrid[i][j] = grid[i + offsetX][j + offsetY];
            }
        }

        return subGrid;
    }

    public static int[][] transposeGrid(int[][] grid){
        int[][] newGrid = new int[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[j][i] = grid[i][j];
            }
        }

        return newGrid;
    }

    public static int[][] findCofactors(int[][] grid){
        int[][] newGrid = new int[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[i][j] = calculateCofactor(grid, i, j);
            }
        }

        return newGrid;
    }

    public static int[][] multiplyGrids(int[][] firstGrid, int[][] secondGrid){
        if(firstGrid[0].length != secondGrid.length) return null;
        int[][] newGrid = new int[firstGrid.length][secondGrid[0].length]; 

        for (int i = 0; i < firstGrid.length; i++) {
            for (int j = 0; j < secondGrid[0].length; j++) {
                int newFactor = 0;
                for(int k = 0; k < secondGrid.length; k++){
                    newFactor += firstGrid[i][k]*secondGrid[k][j];
                }
                newGrid[i][j] = newFactor;
            }
        }

        return newGrid;
    }

    public static int[][] multiplyGrids(int imm, int[][] grid){
          int[][] newGrid = new int[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[i][j] = imm * grid[i][j];
            }
        }

        return newGrid;
    }
}

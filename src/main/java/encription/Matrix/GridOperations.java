package encription.Matrix;
public final class GridOperations {
    public static double calculateDet(double[][] grid){
        double det = 0;
        for(int i = 0; i < grid.length; i++){
            det += grid[i][0] * calculateCofactor(grid, i, 0);
        }
        return det;
    } 

    private static double calculateCofactor(double[][] grid, int row, int column){
        double[][] subGrid = calculateSubGrid(grid, row, column);

        if(subGrid.length == 2) return (int)Math.pow(-1, row + column) * (subGrid[0][0]*subGrid[1][1]  - subGrid[0][1]*subGrid[1][0]);
        if(subGrid.length == 1) return  Math.pow(-1, row + column) * subGrid[0][0];


        return Math.pow(-1, row + column) * calculateDet(subGrid); 
    }

    private static double[][] calculateSubGrid(double[][] grid, int row, int column){
        double[][] subGrid = new double[grid.length - 1][grid.length - 1];

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

    public static double[][] transposeGrid(double[][] grid){
        double[][] newGrid = new double[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[j][i] = grid[i][j];
            }
        }

        return newGrid;
    }

    public static double[][] findCofactors(double[][] grid){
        double[][] newGrid = new double[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[i][j] = calculateCofactor(grid, i, j);
            }
        }

        return newGrid;
    }

    public static double[][] multiplyGrids(double[][] firstGrid, double[][] secondGrid){
        if(firstGrid[0].length != secondGrid.length) return null;
        double[][] newGrid = new double[firstGrid.length][secondGrid[0].length]; 

        for (int i = 0; i < firstGrid.length; i++) {
            for (int j = 0; j < secondGrid[0].length; j++) {
                double newFactor = 0;
                for(int k = 0; k < secondGrid.length; k++){
                    newFactor += firstGrid[i][k]*secondGrid[k][j];
                }
                newGrid[i][j] = newFactor;
            }
        }

        return newGrid;
    }

    public static double[][] multiplyGrids(double imm, double[][] grid){
          double[][] newGrid = new double[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[i][j] = imm * grid[i][j];
            }
        }

        return newGrid;
    }


    public static double[][] findInverse(double[][] grid) {
        double det = calculateDet(grid);
        if(det == 0) return null;

        return multiplyGrids(1/det, transposeGrid(findCofactors(grid)));
    }
}

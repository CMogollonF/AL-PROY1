package encription.Matrix;

import java.math.BigInteger;

public final class GridOperations {
    /**
     * Calculates the determinant of a matrix using cofactor expansion along the first column.
     * Result is normalized mod 29.
     * 
     * @param grid the matrix
     * @return the determinant mod 29
     */
    public static double calculateDet(double[][] grid){
        double det = 0;
        for(int i = 0; i < grid.length; i++){
            det += grid[i][0] * calculateCofactor(grid, i, 0);
        }
        return det % 29;
    } 

    /**
     * Calculates the cofactor of an element at position (row, column).
     * For 2x2 matrices, uses direct formula; for 1x1, returns the element;
     * for larger matrices, recursively calculates determinant of subgrid.
     * 
     * @param grid the matrix
     * @param row the row index
     * @param column the column index
     * @return the cofactor value
     */
    private static double calculateCofactor(double[][] grid, int row, int column){
        double[][] subGrid = calculateSubGrid(grid, row, column);

        if(subGrid.length == 2) return (int)Math.pow(-1, row + column) * (subGrid[0][0]*subGrid[1][1]  - subGrid[0][1]*subGrid[1][0]);
        if(subGrid.length == 1) return  Math.pow(-1, row + column) * subGrid[0][0];


        return Math.pow(-1, row + column) * calculateDet(subGrid); 
    }

    /**
     * Creates a subgrid by removing the specified row and column from the original matrix.
     * Used in cofactor expansion calculations.
     * 
     * @param grid the original matrix
     * @param row the row to remove
     * @param column the column to remove
     * @return the subgrid without the specified row and column
     */
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

    /**
     * Transposes a square matrix by swapping rows and columns.
     * 
     * @param grid the matrix to transpose
     * @return the transposed matrix
     */
    public static double[][] transposeGrid(double[][] grid){
        double[][] newGrid = new double[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[j][i] = grid[i][j];
            }
        }

        return newGrid;
    }

    /**
     * Calculates the cofactor matrix (all cofactors) and normalizes values modulo 29.
     * 
     * @param grid the original matrix
     * @return the cofactor matrix with all values mod 29
     */
    public static double[][] findCofactors(double[][] grid){
        double[][] newGrid = new double[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[i][j] = (calculateCofactor(grid, i, j) + 29) % 29;
            }
        }

        return newGrid;
    }

    /**
     * Multiplies two matrices using standard matrix multiplication algorithm.
     * Returns null if dimensions are incompatible.
     * 
     * @param firstGrid the left matrix
     * @param secondGrid the right matrix
     * @return the product matrix, or null if incompatible dimensions
     */
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

    /**
     * Multiplies a matrix by a scalar value.
     * 
     * @param imm the scalar multiplier
     * @param grid the matrix to scale
     * @return the scaled matrix
     */
    public static double[][] multiplyGrids(double imm, double[][] grid){
          double[][] newGrid = new double[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                newGrid[i][j] = imm * grid[i][j];
            }
        }

        return newGrid;
    }

    /**
     * Calculates the inverse matrix using the adjugate method and modular arithmetic (mod 29).
     * Returns null if matrix is singular (determinant = 0).
     * 
     * @param grid the matrix to invert
     * @return the inverted matrix, or null if singular
     */
    public static double[][] findInverse(double[][] grid) {
        BigInteger det = BigInteger.valueOf((int) calculateDet(grid));
        if(det.equals(0)) return null;
        BigInteger result = det.modPow(BigInteger.valueOf(27), BigInteger.valueOf(29));

        return multiplyGrids(result.intValue(), transposeGrid(findCofactors(grid)));
    }
}

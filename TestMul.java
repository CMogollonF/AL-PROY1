import java.util.Arrays;

public class TestMul {
    public static void main(String[] args) {
        double[][] firstGrid = {
            {
                -1, 2, -4
            },
            {
                3, 0, 7
            }
        };

        double[][] secondGrid = {
            {
                2, -5
            },
            {
                5, 2
            },
            {
                8, -3
            }
        };

        System.out.println(Arrays.toString(GridOperations.multiplyGrids(firstGrid, secondGrid)));
    }
}

public class TestDet {
    public static void main(String[] args) {
        int[][] testGrid = {
            {
                3, -1, 2
            },
            {
                4, 0, -2
            },
            {
                5, 1, -3
            }
        };

        System.out.println(GridOperations.calculateDet(testGrid));
    }
}

public class TestDet {
    public static void main(String[] args) {
        double[][] testGrid = {
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

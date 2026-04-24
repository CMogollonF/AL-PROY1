public class TestInverse {
   public static void main(String[] args) {
        double[][] normal = {
        {
            1, 2, 1
        },
        {
            3, 2, 1
        },
        {
            5, 0, 3
        }
    };

    GridOperations.findInverse(normal);
   }
}

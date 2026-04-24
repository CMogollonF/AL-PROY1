public class TestEncription {
    public static void main(String[] args) {
        double[][] mockGrid = {
            {
                1, 5
            },
            {
                1, 3
            }
        };

        String message = "complex, .Test";

        String encripted = Encription.encriptMessage(mockGrid, message);
        System.out.println(String.format("encripted message: %s", encripted));
        String decrypted = Encription.decryptMessage(mockGrid, encripted);
        System.out.println(String.format("decrypted message: %s", decrypted));

    }
}

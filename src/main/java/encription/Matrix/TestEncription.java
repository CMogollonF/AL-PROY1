package encription.Matrix;

public class TestEncription {
    public static void main(String[] args) {
        Encription.setMatrix(CreateMatrix.createMatrix("2513"));

        String encripted = Encription.encriptMessage("af");

        System.out.println(encripted);

        String decrypted = Encription.decryptMessage(encripted);

        System.out.println(decrypted);
    }
}

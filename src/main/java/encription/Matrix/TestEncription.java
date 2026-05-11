package encription.Matrix;

public class TestEncription {
    public static void main(String[] args) {
        Encription.setMatrix(CreateMatrix.createMatrix("542243772"));

        String encripted = Encription.encriptMessage("mi nombre es carlos mogollon");

        System.out.println(encripted);

        String decrypted = Encription.decryptMessage(encripted);

        System.out.println(decrypted);
    }
}

package utils;



import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class MaHoa {

    private static final String SALT = "asjrlkmcoewj@tjle;oxqskjhdjksjf1jurVn";

    // Hàm băm SHA-512 với salt
    public static String toSHA512(String input) {
        try {
            String saltedInput = input + SALT;

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(saltedInput.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package ultil;

public class ValidationUtils {
    public static boolean isValidEmail (String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
        return email.matches(emailRegex);
    }

    public static String validatePassword(String password) {
        if (password == null) return "Mật khẩu không được để trống";
        if (password.trim().isEmpty()) return "Mật khẩu không được để trống";

        // Kiểm tra độ dài tối thiểu
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }

        // Kiểm tra độ dài tối đa để tránh DOS attacks
        if (password.length() > 128) {
            return "Mật khẩu không được vượt quá 128 ký tự";
        }

        // Kiểm tra có chứa ít nhất một chữ thường
        if (!password.matches(".*[a-z].*")) {
            return "Mật khẩu phải chứa ít nhất một chữ cái thường";
        }

        // Kiểm tra có chứa ít nhất một chữ hoa
        if (!password.matches(".*[A-Z].*")) {
            return "Mật khẩu phải chứa ít nhất một chữ cái hoa";
        }

        // Kiểm tra có chứa ít nhất một số
        if (!password.matches(".*[0-9].*")) {
            return "Mật khẩu phải chứa ít nhất một chữ số";
        }

        // Kiểm tra có chứa ít nhất một ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return "Mật khẩu phải chứa ít nhất một ký tự đặc biệt (!@#$%^&*...)";
        }

        // Kiểm tra không chứa khoảng trắng
        if (password.contains(" ")) {
            return "Mật khẩu không được chứa khoảng trắng";
        }

        // Kiểm tra không chứa chuỗi lặp lại
        if (hasRepeatingSequence(password)) {
            return "Mật khẩu không được chứa chuỗi ký tự lặp lại liên tiếp";
        }

        // Kiểm tra không phải là mật khẩu phổ biến
        if (isCommonPassword(password)) {
            return "Mật khẩu này quá phổ biến, vui lòng chọn mật khẩu khác";
        }

        return null; // Mật khẩu hợp lệ
    }

    // Hàm phụ trợ kiểm tra chuỗi lặp lại
    private static boolean hasRepeatingSequence(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            char current = password.charAt(i);
            if (password.charAt(i + 1) == current && password.charAt(i + 2) == current) {
                return true; // Có 3 ký tự giống nhau liên tiếp
            }
        }
        return false;
    }

    // Hàm phụ trợ kiểm tra mật khẩu phổ biến
    private static boolean isCommonPassword(String password) {
        String[] commonPasswords = {
                "password", "123456", "123456789", "12345678", "12345",
                "1234567", "admin", "password123", "qwerty", "abc123",
                "letmein", "monkey", "1234567890", "dragon", "111111",
                "baseball", "iloveyou", "trustno1", "sunshine", "master"
        };

        String lowerPassword = password.toLowerCase();
        for (String common : commonPasswords) {
            if (lowerPassword.equals(common)) {
                return true;
            }
        }
        return false;
    }

}

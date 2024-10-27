package helper;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class Tool {
    public static String randomID() {
        Random random = new Random();
        long value = random.nextLong() % 1000000L; // Số ngẫu nhiên từ 0 đến 999999
        return String.format("%06d", Math.abs(value));
    }

    public static String createPassword() {
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";
        final String ALL_CHARACTERS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;
        final int length = 12;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        // Đảm bảo có ít nhất 1 ký tự thuộc mỗi loại bắt buộc
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Thêm các ký tự ngẫu nhiên còn lại từ tập hợp tất cả các ký tự
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // Trộn các ký tự để đảm bảo mật khẩu không theo thứ tự cố định
        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars);

        // Chuyển đổi lại thành chuỗi và trả về kết quả
        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }
    
    public static Boolean isEmpty(String input) {
        if (input == null) {
            return true;
        }
        return input.equals("");
    }
    
    public static boolean isPhoneNumber(String phone) {
        String pattern = "^(0|\\+84)[1-9]\\d{8}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(phone);
        return matcher.matches();
    }

    public static Boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public static boolean isNumber(String num) {
        boolean result = true;
        if (num == null) return false;
        try {
            long k = Long.parseLong(num);
            if(k < 0) {
                result = false;
            }
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
}

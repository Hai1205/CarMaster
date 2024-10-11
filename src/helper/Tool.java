package helper;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class Tool {
    public static String randomID() {
        Random random = new Random();
        long value = random.nextLong() % 1000000L; // Số ngẫu nhiên từ 0 đến 999999
        return String.format("%06d", Math.abs(value));
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

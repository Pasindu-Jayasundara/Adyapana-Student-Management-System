package model;

import java.util.regex.Pattern;

public class EmailFormatValidation {
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(gmail\\.com|yahoo\\.com|hotmail\\.com)$";
    
    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
    
}

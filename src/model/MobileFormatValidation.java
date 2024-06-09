package model;

import java.util.regex.Pattern;

public class MobileFormatValidation {
    
    private static final String MOBILE_NUMBER_REGEX = "^(07|\\+947)([1245678]\\d{7})$";

    public static boolean isValidMobile(String mobile) {
        return Pattern.compile(MOBILE_NUMBER_REGEX).matcher(mobile).matches();
    }
    
}

package com.demo.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isEmailValid(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class CheckValidate {
    
    public static final String NAME_CHECK = "^([a-z]{1,})\\\\s+([a-z]{1,})$";
    public static final String PHONENUMBER_CHECK = "^[0-9]{10}$";
    public static final String EMAIL_CHECK = "^(.+)@(.+)$";
    
    public static boolean validateString(String text, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
    
}

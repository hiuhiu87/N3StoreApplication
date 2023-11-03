/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class XDate {
    public static final String pattern = "dd-MM-YYYY";

    public static SimpleDateFormat format = new SimpleDateFormat();
    
    public static Date parse(String text, String pattern) throws RuntimeException{
        try{
            format.applyPattern(pattern);
            return format.parse(text);
        }
        catch(ParseException e){
            throw new RuntimeException(e);
        }
    }
    
    public static Date parse(String dateString) throws RuntimeException{
        return XDate.parse(dateString, pattern);
    }
    
    public static String parseToString(Date date) throws RuntimeException{
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String res = formatter.format(date);
        return res;
    }
}

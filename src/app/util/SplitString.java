/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

/**
 *
 * @author Admin
 */
public class SplitString {

    public static String[] splitString(String text) {
        String[] arrayString = text.split(" ");
        return arrayString;
    }
    
    public static String[] splitName(String fullname) {
        String name = "";
        String middleName = "";
        String lastName = "";
        String[] nameSplit = SplitString.splitString(fullname);
        if (nameSplit.length >= 2) {
            lastName = nameSplit[0];
            for (int i = 1; i <= nameSplit.length - 2; i++) {
                if (i == nameSplit.length - 2) {
                    middleName += (nameSplit[i]);
                } else {
                    middleName += (nameSplit[i] + " ");
                }
            }
            name = nameSplit[nameSplit.length - 1];
        } else {
            lastName = nameSplit[0];
            name = nameSplit[nameSplit.length - 1];
            middleName = "";
        }
        String[] result = {name, middleName, lastName};
        return result;
    }

}

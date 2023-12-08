/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Admin
 */
public class FormatMoney {

    public static String formatMoney(BigDecimal number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        return decimalFormat.format(number);
    }

    public static void main(String[] args) {
        String m = FormatMoney.formatMoney(new BigDecimal(11500000));
        System.out.println(m);
    }
    
}

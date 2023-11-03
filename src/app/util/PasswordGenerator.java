/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.util.Random;

/**
 *
 * @author Admin
 */
public class PasswordGenerator {

        private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        public static String generatePassword() {
            Random random = new Random();
            String password = "";
            for (int i = 0; i < 6; i++) {
                password += CHARS.charAt(random.nextInt(CHARS.length()));
            }
            return password;
        }

    }

package com.example.demo.common;

public class ValidationFunctions {
    public static boolean isInteger(String strNum, int min, int max) {
        if (strNum == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(strNum);
            if(number < min || number > max){
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

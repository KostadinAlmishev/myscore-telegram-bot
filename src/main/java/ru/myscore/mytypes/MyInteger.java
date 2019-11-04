package ru.myscore.mytypes;

public class MyInteger {
    public static int parseInt(String str) {
        try {
            int res = Integer.parseInt(str.trim());
            return res;
        } catch (Exception e) {
            return 0;
        }
    }
}

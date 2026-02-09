package com.ramennsama.springboot.system.utils;

public class OtpUtil {

    public static String generateOtp() {
        return String.valueOf(
                (int) (Math.random() * 900000) + 100000
        );
    }
}

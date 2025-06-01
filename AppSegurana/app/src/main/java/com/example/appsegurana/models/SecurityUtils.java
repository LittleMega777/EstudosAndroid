package com.example.appsegurana.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SecurityUtils {
    public static String sha256(String base) {
        try {
            MessageDigest digest_in_sha265 = MessageDigest.getInstance("SHA-256");

            byte[] inputBytes = base.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes = digest_in_sha265.digest(inputBytes);

            StringBuilder hexString = new StringBuilder();

            for (byte bytes : hashBytes) {
                hexString.append(String.format("%02x", bytes));
            }
            return hexString.toString();

        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}

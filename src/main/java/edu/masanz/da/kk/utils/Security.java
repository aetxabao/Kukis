package edu.masanz.da.kk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Security {

    /**
     * Generates a random id.
     * @return the generated id.
     * E.g. bffa917acfb30f8e6addd23402e98b78f3aef485
     */
    public static String generateRandomId() {
        return hash(generateSalt());
    }

    /**
     * Generates a random salt.
     * @return the generated salt
     * E.g. FsgVTWPkpxxXWiuksg2LsLgvW6M5dOih
     */
    public static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[24];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes the given word using a SHA1 hash algorithm.
     * @param word the word to hash E.g. 123456
     * @return the hashed word
     * E.g. 7c4a8d09ca3762af61e59520943dc26494f8941b
     */
    public static String hash(String word) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(word.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
//        String id = generateRandomId();
//        System.out.println(id);

//        String salt = generateSalt();
//        System.out.println(salt);
//        String word = "123456";
//        String hashedWord = hash(word);
//        System.out.println(hashedWord);

//        for (int i = 0; i < 5; i++) {
//            System.out.println(generateRandomId());
//        }
    }

}

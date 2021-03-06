package de.mki.jchess.commons;

import java.util.Random;

/**
 * Creates a random String of [a-z0-9]. Length can be specified.
 * Created by Igor on 11.11.2015.
 */
public class RandomStringService {

    /**
     * Don't allow creation of an instance.
     */
    private RandomStringService() {
    }

    /**
     * Returns a {@link String} containing the lower cased english alphabet and the digits from 0 to 9
     * @return Returns a {@link String} with the length of 20
     */
    public static String getRandomString() {
        return getRandomString(20);
    }

    /**
     * Returns a {@link String} containing the lower cased english alphabet and the digits from 0 to 9
     * @param length The desired length of the {@link String}.
     * @return Returns a {@link String} with the specified length
     */
    public static String getRandomString(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}

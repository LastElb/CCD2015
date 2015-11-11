package de.mki.jchess.server.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Igor on 11.11.2015.
 */
@Service
public class RandomStringService {

    /**
     * Returns a {@link String} containing the lower cased english alphabet and the digits from 0 to 9
     * @return String Returns a {@link String} with the length of 20
     */
    public String getRandomString() {
        return getRandomString(20);
    }

    /**
     * Returns a {@link String} containing the lower cased english alphabet and the digits from 0 to 9
     * @param length The desired length of the {@link String}.
     * @return @return String Returns a {@link String} with the specified length
     */
    public String getRandomString(int length) {
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

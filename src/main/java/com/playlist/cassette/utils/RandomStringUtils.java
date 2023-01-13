package com.playlist.cassette.utils;

import java.util.Random;

public abstract class RandomStringUtils {

    private static final Random random = new Random();
    public static String getRandomString(int length) {
        StringBuffer newWord = new StringBuffer();
        for(int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            switch(choice) {
                case 0:
                    newWord.append((char)((int)random.nextInt(25)+97));
                    break;
                case 1:
                    newWord.append((char)((int)random.nextInt(25)+65));
                    break;
                case 2:
                    newWord.append((char)((int)random.nextInt(10)+48));
                    break;
                default:
                    break;
            }
        }

        return newWord.toString();
    }
}

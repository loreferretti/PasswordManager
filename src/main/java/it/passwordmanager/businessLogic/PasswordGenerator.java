package it.passwordmanager.businessLogic;

import java.util.Random;

public class PasswordGenerator {

    public String generate(int length) {
        String alphabet = getAlphabet();
        String password = "";
        Random rand = new Random();
        for(int i = 0; i < length; i++) {
            password += alphabet.charAt(rand.nextInt(alphabet.length()));
        }
        return password;
    }

    private String getAlphabet() {
        String alphabet = "";
        for (char c = 'A'; c <= 'Z'; c++)
            alphabet += c;

        for (char c = 'a'; c <= 'z'; c++)
            alphabet += c;

        for (int i = 0; i < 10; i++)
            alphabet += i;

        return alphabet;
    }
}

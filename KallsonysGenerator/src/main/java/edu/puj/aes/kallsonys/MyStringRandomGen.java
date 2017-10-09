package edu.puj.aes.kallsonys;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * From http://www.java2novice.com/java-collections-and-util/random/string/
 *
 * @author Michael Felipe Rondón Acosta
 */
public class MyStringRandomGen {

    private static final String CHAR_LIST
            = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 10;

    /**
     * This method generates random string
     *
     * @return
     */
    public String generateRandomString() {

        StringBuilder randStr = new StringBuilder();
        Random randomLenghtGenerator = new Random();
        int nextInt = randomLenghtGenerator.nextInt(15 + 1);
        for (int i = 0; i < nextInt; i++) {
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    /**
     * This method generates random numbers
     *
     * @return int
     */
    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    public String getSentence(int wordsQuantity) {
        if (wordsQuantity <= 1) {
            return this.generateRandomString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.generateRandomString());
        for (int i = 1; i < wordsQuantity; i++) {
            stringBuilder.append(' ');
            stringBuilder.append(this.generateRandomString());
        }
        return stringBuilder.toString();
    }
    
    public List<String> getRamdomListStrings(){
        Random random = new Random();
        List<String> strings =  new LinkedList<>();
        int nextInt = random.nextInt(10);
        for (int i = 0; i < nextInt; i++) {
            strings.add(generateRandomString());
        }
        return strings;
    }
}

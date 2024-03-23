package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BullsAndCows {

    private final char[] secretCodeArray;

    public BullsAndCows(int codeLength, int secretCodeSymbolsCount) {
        String secretCode = generateSecretCode(codeLength, secretCodeSymbolsCount);
        this.secretCodeArray = secretCodeToArray(secretCode);
    }

    private String generateSecretCode(int codeLength, int secretCodeSymbolsCount) {
        StringBuilder secretCodeBuilder = new StringBuilder();
        Random random = new Random();
        String secretCodeSeed = "0123456789abcdefghijklmnopqrstuvwxyz";
        List<Character> potentialCode = new ArrayList<>();
        potentialCode.add(secretCodeSeed.charAt(random.nextInt(secretCodeSymbolsCount)));
        for (int i = 1; i < codeLength; i++) {
            boolean duplicate = true;
            do {
                char temp = secretCodeSeed.charAt(random.nextInt(secretCodeSymbolsCount));
                if (!potentialCode.contains(temp)) {
                    duplicate = false;
                } else {
                    continue;
                }
                potentialCode.add(temp);
            } while (duplicate);
        }
        for (char c : potentialCode) {
            secretCodeBuilder.append(c);
        }

        StringBuilder usedCharSet = new StringBuilder("0-");
        if (secretCodeSymbolsCount > 10) {
            usedCharSet.append("9, a-");
        }
        usedCharSet.append(secretCodeSeed.charAt(secretCodeSymbolsCount - 1));
        String codeAsterisk = "*".repeat(codeLength);
        System.out.printf("The secret is prepared: %s (%s).%n", codeAsterisk, usedCharSet);

        return secretCodeBuilder.toString();
    }

    private char[] secretCodeToArray(String secretCode) {
        return secretCode.toCharArray();
    }

    public char[] getSecretCodeArray() {
        return secretCodeArray;
    }
}

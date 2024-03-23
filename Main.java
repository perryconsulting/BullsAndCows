package bullscows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int secretCodeLength = getSecretCodeLength(scanner);
        int secretCodeSymbolsCount = getSecretCodeSymbolsCount(scanner, secretCodeLength);
        BullsAndCows newGame = new BullsAndCows(secretCodeLength, secretCodeSymbolsCount);
        System.out.println("Okay, let's start a game!");
        mainLoop(newGame, scanner);
        System.exit(0);
    }

    private static int getSecretCodeSymbolsCount(Scanner scanner, int secretCodeLength) {
        System.out.println("Input the number of possible symbols in the code:");
        String input = scanner.nextLine();
        int symbolsCount = Integer.parseInt(input);
        if (symbolsCount < secretCodeLength) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n", secretCodeLength, symbolsCount);
            System.exit(1);
        }
        if (symbolsCount > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(1);
        }
        return Integer.parseInt(input);
    }

    private static int getSecretCodeLength(Scanner scanner) {
        System.out.println("Input the length of the secret code:");
        if (!scanner.hasNextInt()) {
            String errorInput = scanner.nextLine();
            System.out.printf("Error: \"%s\" isn't a valid number.%n", errorInput);
            System.exit(1);
        }
        String input = scanner.nextLine();
        int codeLength = Integer.parseInt(input);
        if (codeLength > 36) {
            System.out.println("Error: can't generate a secret code with a length over 36 because there aren't enough unique characters.");
            System.exit(1);
        }
        if (codeLength < 1) {
            System.out.println("Error: can't generate a secret code with a length less than 1.");
            System.exit(1);
        }
        return codeLength;
    }

    private static void mainLoop(BullsAndCows newGame, Scanner scanner) {
        boolean gameOver;
        int turnCounter = 1;
        do {
            System.out.printf("Turn %d:%n", turnCounter);
            char[] playerGuess = getPlayerGuess(scanner);
            int[] playerGrade = calculateGrade(newGame, playerGuess);
            gameOver = reportPlayerGrade(playerGrade);
            turnCounter++;
        } while (!gameOver);

    }

    private static boolean reportPlayerGrade(int[] playerGrade) {
        // playerGrade[0] = # of Bulls
        // playerGrade[1] = # of Cows
        int totalDigits = playerGrade[2];
        if (playerGrade[0] == totalDigits) {
            System.out.printf("Grade: %d bull(s).%nCongratulations! You guessed the secret code.%n", playerGrade[0]);
            return true;
        } else if (playerGrade[1] > 0 && playerGrade[0] == 0) {
            System.out.printf("Grade: %d cow(s).%n", playerGrade[1]);
        } else if (playerGrade[0] > 0 || playerGrade[1] > 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s).%n", playerGrade[0], playerGrade[1]);
        } else {
            System.out.printf("Grade: None.%n");
        }
        return false;
    }

    private static int[] calculateGrade(BullsAndCows newGame, char[] playerGuess) {
        List<Character> secretCodeList = new ArrayList<>();
        for (char c : newGame.getSecretCodeArray()) {
            secretCodeList.add(c);
        }
        List<Character> playerGuessList = new ArrayList<>();
        for (char c : playerGuess) {
            playerGuessList.add(c);
        }

        int cowCounter = 0;
        int bullCounter = 0;

        for (int i = 0; i < secretCodeList.size(); i++) {
            if (playerGuessList.get(i) == secretCodeList.get(i)) {
                bullCounter++;
                Collections.replaceAll(playerGuessList, playerGuessList.get(i), '-'); // Remove duplicate guesses from consideration
            }
        }

        for (int i = 0; i < secretCodeList.size(); i++) {
            for (int j = 0; j < secretCodeList.size(); j++) {
                if (playerGuessList.get(i) == secretCodeList.get(j)) {
                    cowCounter++;
                }
            }
        }

        int[] playerGrade = new int[3];
        playerGrade[0] = bullCounter;
        playerGrade[1] = cowCounter;
        playerGrade[2] = secretCodeList.size();
        return playerGrade;
    }

    private static char[] getPlayerGuess(Scanner scanner) {
        String guess = scanner.nextLine();
        return guess.toCharArray();
    }
}

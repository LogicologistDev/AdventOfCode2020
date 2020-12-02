package me.logicologist.adventofcode2020.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayTwo {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day2/DayTwo.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }
        partOne(inputs);
        partTwo(inputs);
    }

    public static void partOne(List<String> inputs) {
        int validPasswords = 0;
        for (String input : inputs) {
            String[] splitInput = input.split(" ");
            int lowBound = Integer.parseInt(splitInput[0].split("-")[0]);
            int highBound = Integer.parseInt(splitInput[0].split("-")[1]);
            String requiredCharacter = splitInput[1].replace(":", "");
            int bound = 0;
            for (String character : splitInput[2].split("")) if (character.equals(requiredCharacter)) bound++;
            if (bound >= lowBound && bound <= highBound) validPasswords++;
        }
        System.out.println(validPasswords);
    }

    public static void partTwo(List<String> inputs) {
        int validPasswords = 0;
        for (String input : inputs) {
            String[] splitInput = input.split(" ");
            int lowIndex = Integer.parseInt(splitInput[0].split("-")[0]);
            int highIndex = Integer.parseInt(splitInput[0].split("-")[1]);
            String requiredCharacter = splitInput[1].replace(":", "");
            if (splitInput[2].split("")[lowIndex - 1].equals(requiredCharacter) != splitInput[2].split("")[highIndex - 1].equals(requiredCharacter)) validPasswords++;
        }
        System.out.println(validPasswords);
    }
}

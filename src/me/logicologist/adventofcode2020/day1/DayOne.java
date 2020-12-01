package me.logicologist.adventofcode2020.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DayOne {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day1/DayOne.txt");
        Scanner scanner = new Scanner(file);
        List<Integer> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            input.add(Integer.parseInt(inputLine));
        }
        Collections.sort(input);
        firstPart(input);
        secondPart(input);
    }

    public static void firstPart(List<Integer> input) {
        int firstNumber = -1;
        int secondNumber = -1;

        for (int x : input) {
            firstNumber = x;
            secondNumber = input.stream().filter(x1 -> 2020 - x == x1).findFirst().orElse(-1);
            if (secondNumber != -1) break;
        }

        System.out.println(firstNumber * secondNumber);
    }

    public static void secondPart(List<Integer> input) {
        int firstNumber = -1;
        int secondNumber = -1;
        int thirdNumber = -1;

        for (int x : input) {
            firstNumber = x;
            List<Integer> possibleResults = input.stream().filter(x1 -> 2020 - x >= x1).collect(Collectors.toList());
            for (int x1 : possibleResults) {
                secondNumber = x1;
                thirdNumber = possibleResults.stream().filter(x2 -> 2020 - x - x1 == x2).findFirst().orElse(-1);
                if (thirdNumber != -1) break;
            }
            if (thirdNumber != -1) break;
        }

        System.out.println(firstNumber * secondNumber * thirdNumber);
    }
}

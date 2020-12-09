package me.logicologist.adventofcode2020.day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DayNine {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day9/DayNine.txt");
        Scanner scanner = new Scanner(file);
        List<Long> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(Long.parseLong(scanner.nextLine()));
        }

        partTwo(inputs, partOne(inputs));
    }

    public static long partOne(List<Long> inputs) {
        long wrongNumber = -1;

        List<Long> selection = new ArrayList<>();
        for (long i : inputs) {
            if (selection.size() < 25) {
                selection.add(i);
                continue;
            }
            boolean containsNumbers = selection.stream().anyMatch(x -> selection.contains(i - x));
            if (containsNumbers) {
                selection.remove(0);
                selection.add(i);
                continue;
            }
            wrongNumber = i;
            break;
        }
        System.out.println(wrongNumber);
        return wrongNumber;
    }

    public static void partTwo(List<Long> inputs, long wrongNumber) {
        int startAt = 0;
        while (startAt < inputs.size()) {
            int combined = 0;
            int testAt = startAt;
            List<Long> results = new ArrayList<>();
            while (combined < wrongNumber) {
                combined += inputs.get(testAt);
                results.add(inputs.get(testAt));
                testAt++;
            }
            if (combined == wrongNumber) {
                System.out.println(Collections.max(results) + Collections.min(results));
                return;
            }
            startAt++;
        }
    }
}

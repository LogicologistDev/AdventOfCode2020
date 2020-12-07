package me.logicologist.adventofcode2020.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayFive {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day5/DayFive.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        partTwo(partOne(inputs));
    }

    public static List<Integer> partOne(List<String> inputs) {
        List<Integer> ids = new ArrayList<>();

        int highestId = 0;
        for (String s : inputs) {
            int lowBoundRow = 0;
            int lowBoundColumn = 0;
            int changeBy = 128;
            boolean rowCalculate = true;
            String[] instructions = s.split("");
            for (String instruction : instructions) {
                if (changeBy == 1 && rowCalculate) {
                    rowCalculate = false;
                    changeBy = 8;
                }
                changeBy /= 2;
                if (rowCalculate) {
                    if (instruction.equals("B")) {
                        lowBoundRow += changeBy;
                    }
                    continue;
                }
                if (instruction.equals("R")) {
                    lowBoundColumn += changeBy;
                }
            }
            ids.add(lowBoundRow * 8 + lowBoundColumn);
            highestId = Math.max(highestId, lowBoundRow * 8 + lowBoundColumn);
        }
        System.out.println(highestId);
        return ids;
    }

    public static void partTwo(List<Integer> ids) {
        int seat = 0;
        while (true) {
            seat++;
            if (ids.contains(seat)) continue;
            if (ids.contains(seat + 1) && ids.contains(seat - 1)) break;
        }
        System.out.println(seat);
    }
}

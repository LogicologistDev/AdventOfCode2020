package me.logicologist.adventofcode2020.day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayEight {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day8/DayEight.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        partOne(inputs);
        partTwo(inputs);
    }

    public static void partOne(List<String> inputs) {
        int accum = 0;
        int index = 0;

        List<Integer> usedIndexes = new ArrayList<>();

        while (true) {
            usedIndexes.add(index);
            if (inputs.get(index).contains("nop")) {
                index++;
                continue;
            }
            if (inputs.get(index).contains("jmp")) {
                index += Integer.parseInt(inputs.get(index).split(" ")[1]);
                if (usedIndexes.contains(index)) {
                    break;
                }
                continue;
            }
            accum += Integer.parseInt(inputs.get(index).split(" ")[1]);
            index++;
            if (index == inputs.size()) {
                System.out.println("perfect!");
                break;
            }
        }

        System.out.println(accum);
    }

    public static void partTwo(List<String> inputs) {
        for (int i = 0; i < inputs.size(); i++) {

            if (!inputs.get(i).contains("nop") && !inputs.get(i).contains("jmp")) continue;

            List<String> newInstruct = new ArrayList<>(inputs);
            newInstruct.set(i, (inputs.get(i).contains("nop") ? "jmp " : "nop ") + inputs.get(i).split(" ")[1]);

            if (causesLoop(newInstruct) == -1) continue;
            System.out.println(causesLoop(newInstruct));
            return;
        }
    }

    public static int causesLoop(List<String> inputs) {
        int acc = 0;
        int index = 0;
        List<Integer> usedIndexes = new ArrayList<>();

        while (true) {
            if (usedIndexes.contains(index)) {
                return -1;
            }
            usedIndexes.add(index);
            if (inputs.get(index).contains("nop")) {
                index++;
                continue;
            }
            if (inputs.get(index).contains("jmp")) {
                index += Integer.parseInt(inputs.get(index).split(" ")[1]);
                if (index == inputs.size()) return acc;
                continue;
            }
            acc += Integer.parseInt(inputs.get(index).split(" ")[1]);
            index++;
            if (index == inputs.size()) return acc;
        }
    }
}

package me.logicologist.adventofcode2020.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DayFifteen {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day15/DayFifteen.txt");
        Scanner scanner = new Scanner(file);
        List<Integer> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            for (String s : scanner.next().split(",")) inputs.add(Integer.parseInt(s));
        }

        System.out.println("Calculating part 1...");
        getGameOutput(inputs, 2020);
        System.out.println("Calculating part 2...");
        getGameOutput(inputs, 30000000);
    }

    public static void getGameOutput(List<Integer> inputs, int turn) {
        HashMap<Integer, Integer> recentlySpoken = new HashMap<>();
        HashMap<Integer, Integer> count = new HashMap<>();
        int lastNumber = 0;
        int lastLastNumber;

        for (int i = 1; i <= turn; i++) {
            lastLastNumber = lastNumber;
            if (i <= inputs.size()) {
                count.put(inputs.get(i - 1), 1);
                lastNumber = inputs.get(i - 1);
                recentlySpoken.put(lastLastNumber, i - 1);
                continue;
            }
            int finalLastNumber = lastNumber;
            if (count.get(finalLastNumber) == 1) {
                count.put(0, count.get(0) + 1);
                lastNumber = 0;
                recentlySpoken.put(lastLastNumber, i - 1);
                continue;
            }
            int newNumber = (i - 1) - recentlySpoken.get(lastNumber);
            count.putIfAbsent(newNumber, 0);
            count.put(newNumber, count.get(newNumber) + 1);
            lastNumber = newNumber;
            recentlySpoken.put(lastLastNumber, i - 1);
        }
        System.out.println(lastNumber);
    }
}

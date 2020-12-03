package me.logicologist.adventofcode2020.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayThree {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day3/DayThree.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        long i = getTreesX(1, 1, inputs);
        long i2 = getTreesX(3, 1, inputs);
        long i3 = getTreesX(5, 1, inputs);
        long i4 = getTreesX(7, 1, inputs);
        long i5 = getTreesX(1, 2, inputs);

        System.out.println(i2);
        System.out.println(i * i2 * i3 * i4 * i5);
    }

    public static long getTreesX(int slopeX, int slopeY, List<String> inputs) {
        int y = slopeY - 1;
        int x = -slopeX;
        int trees = 0;
        for (String input : inputs) {
            y++;
            if (y != slopeY) continue;
            y = 0;
            x += slopeX;
            if (x >= input.length()) x = x % input.length();
            if (input.split("")[x].equals("#")) trees++;
        }
        return trees;
    }
}

package me.logicologist.adventofcode2020.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayTen {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day10/DayTen.txt");
        Scanner scanner = new Scanner(file);
        List<Integer> inputs = new ArrayList<>();
        inputs.add(0);
        while (scanner.hasNextLine()) {
            inputs.add(Integer.parseInt(scanner.nextLine()));
        }
        Collections.sort(inputs);

        partOne(inputs);
        partTwo(inputs);
    }

    public static void partOne(List<Integer> inputs) {
        int oneDiff = 0;
        int threeDiff = 1;
        for (int i = 1; i < inputs.size(); i++) {
            int count = inputs.get(i) - inputs.get(i - 1);
            if (count == 1) oneDiff++;
            if (count == 3) threeDiff++;
        }
        System.out.println(oneDiff * threeDiff);
    }

    public static void partTwo(List<Integer> inputs) {
        HashMap<Integer, List<Integer>> choices = new HashMap<>();
        for (int i = 0; i < inputs.size(); i++) {
            int number = inputs.get(i);
            choices.put(number, new ArrayList<>());
            if (inputs.contains(number + 1)) choices.get(number).add(number + 1);
            if (inputs.contains(number + 2)) choices.get(number).add(number + 2);
            if (inputs.contains(number + 3)) choices.get(number).add(number + 3);
            if (choices.get(number).size() == 0) choices.get(number).add(number);
        }
        HashMap<Integer, Long> containedEndings = new HashMap<>();
        containedEndings.put(Collections.max(new ArrayList<>(choices.keySet())), 1L);
        List<Integer> keys = new ArrayList<>(choices.keySet());
        Collections.sort(keys);
        Collections.reverse(keys);
        for (int endContainer : keys) {
            long endsContained = 0;
            List<Integer> container = choices.get(endContainer);
            for (int containerElement : container) {
                endsContained += containedEndings.get(containerElement);
            }
            containedEndings.put(endContainer, endsContained);
        }
        System.out.println(containedEndings.get(0));
    }
}

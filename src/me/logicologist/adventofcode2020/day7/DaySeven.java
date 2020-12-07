package me.logicologist.adventofcode2020.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DaySeven {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day7/DaySeven.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        HashMap<String, List<String>> simpleRules = new HashMap<>();
        HashMap<String, List<String>> complexRules = new HashMap<>();

        for (String input : inputs) {
            String[] containers = input.split(" contain ");
            simpleRules.put(containers[0], new ArrayList<>(Arrays.asList(containers[1].replaceAll("\\d ", "").replace("bag,", "bags,").replace("bag ", "bags ").replace("bag.", "bags.").replace("bag.", "bags.").replace(".", "").split(", "))));
            complexRules.put(containers[0], new ArrayList<>(Arrays.asList(containers[1].replace("bag,", "bags,").replace("bag ", "bags ").replace("bag.", "bags.").replace("bag.", "bags.").replace(".", "").split(", "))));
        }

        partOne(simpleRules);
        partTwo(complexRules);
    }

    public static void partOne(HashMap<String, List<String>> input) {
        int count = 0;

        for (String rule : input.keySet()) {
            List<String> has = input.get(rule);
            while (has.stream().distinct().count() != 0 && !has.contains("shiny gold bags")) {
                has = searchSimple(input, has);
            }
            if (has.contains("shiny gold bags")) count++;
        }

        System.out.println(count);
    }

    public static void partTwo(HashMap<String, List<String>> input) {
        int bagsCount = 0;

        List<String> has = searchComplex(input, new ArrayList<>(Arrays.asList("shiny gold bags")));
        bagsCount += has.size();
        while (has.stream().distinct().count() != 0) {
            has = searchComplex(input, has);
            bagsCount += has.size();
        }

        System.out.println(bagsCount);
    }

    public static List<String> searchSimple(HashMap<String, List<String>> rules, List<String> bags) {
        List<String> contains = new ArrayList<>();
        for (String bag : bags) {
            if (bag.equals("no other bags")) continue;
            contains.addAll(rules.get(bag));
        }
        return contains;
    }

    public static List<String> searchComplex(HashMap<String, List<String>> complexRules, List<String> bags) {
        List<String> contains = new ArrayList<>();
        for (String bag : bags) {
            List<String> container = complexRules.get(bag.replaceAll("\\d ", ""));
            for (String contain : container) {
                if (contain.equals("no other bags")) continue;
                for (int i2 = 0; i2 < Integer.parseInt(contain.split(" ")[0]); i2++) {
                    contains.add(contain.replaceAll("\\d ", ""));
                }
            }
        }
        return contains;
    }
}

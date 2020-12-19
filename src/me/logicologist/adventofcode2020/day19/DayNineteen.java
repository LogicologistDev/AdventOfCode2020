package me.logicologist.adventofcode2020.day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayNineteen {

    public static final HashMap<Integer, String> rules = new HashMap<>();
    public static final HashMap<Integer, String> regexes = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day19/DayNineteen.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        List<String> messages = new ArrayList<>();
        boolean isMessage = false;
        for (String input : inputs) {
            if (input.isEmpty()) {
                isMessage = true;
                continue;
            }
            if (isMessage) {
                messages.add(input);
                continue;
            }
            rules.put(Integer.parseInt(input.split(": ")[0]), input.split(": ")[1]);
        }

        String partOneRegex = "^" + getRegex(0, false) + "$";
        regexes.clear();
        String partTwoRegex = "^" + getRegex(0, true) + "$";

        int partOneCorrect = 0;
        int partTwoCorrect = 0;
        for (String message : messages) {
            if (message.matches(partOneRegex)) partOneCorrect++;
            if (message.matches(partTwoRegex)) partTwoCorrect++;
        }
        System.out.println(partOneCorrect);
        System.out.println(partTwoCorrect);
    }

    public static String getRegex(int rule, boolean override) {
        if (regexes.containsKey(rule)) return regexes.get(rule);
        if (override) {
            switch (rule) {
                case 8:
                    regexes.put(rule, "(" + getRegex(42, true) + "+)");
                    return regexes.get(rule);
                case 11:
                    StringBuilder sb = new StringBuilder("(");
                    for (int i = 1; i < 5; i++) {
                        if (i > 1) sb.append('|');
                        sb.append('(');
                        for (int i2 = 0; i2 < i; i2++) sb.append(getRegex(42, true));
                        for (int i2 = 0; i2 < i; i2++) sb.append(getRegex(31, true));
                        sb.append(')');
                    }
                    sb.append(')');
                    regexes.put(rule, sb.toString());
                    return sb.toString();
            }
        }
        String ruleSet = rules.get(rule);
        if (ruleSet.contains("\"")) return ruleSet.replace("\"", "");
        StringBuilder sb = new StringBuilder("(");
        String[] rulePart = ruleSet.split(" ");
        for (String part : rulePart) {
            if (Character.isDigit(part.charAt(0))) {
                sb.append(getRegex(Integer.parseInt(part), override));
                continue;
            }
            if (part.equals("|")) sb.append('|');
        }
        sb.append(")");
        regexes.put(rule, sb.toString());
        return sb.toString();
    }
}
package me.logicologist.adventofcode2020.day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayEighteen {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day18/DayEighteen.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        calculateInputs(inputs, false);
        calculateInputs(inputs, true);
    }

    public static void calculateInputs(List<String> inputs, boolean order) {
        long added = 0L;
        for (String equation : inputs) added += calculateEquation(equation, order);
        System.out.println(added);
    }

    public static long calculateEquation(String s, boolean order) {
        Pattern pattern = Pattern.compile("(\\((\\+|\\*| |\\d)+\\))");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            s = s.replace(matcher.group(), String.valueOf(calculateEquation(matcher.group().replace("(", "").replace(")", ""), order)));
            matcher = pattern.matcher(s);
        }
        Pattern eqPattern = Pattern.compile("(\\d+ [+*] \\d+)");
        if (order) eqPattern = Pattern.compile("(\\d+ \\+ \\d+)");
        Matcher eqMatcher = eqPattern.matcher(s);
        while (eqMatcher.find()) {
            s = s.replaceFirst(Pattern.quote(eqMatcher.group()), calculateBasic(eqMatcher.group()) + "");
            eqMatcher = eqPattern.matcher(s);
        }
        if (order) {
            eqPattern = Pattern.compile("(\\d+ \\* \\d+)");
            eqMatcher = eqPattern.matcher(s);
            while (eqMatcher.find()) {
                s = s.replaceFirst(Pattern.quote(eqMatcher.group()), calculateBasic(eqMatcher.group()) + "");
                eqMatcher = eqPattern.matcher(s);
            }
        }
        return Long.parseLong(s);
    }

    public static long calculateBasic(String s) {
        Long firstNumber = Long.parseLong(s.split(" ")[0]);
        String operator = s.split(" ")[1];
        Long secondNumber = Long.parseLong(s.split(" ")[2]);
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "*":
                return firstNumber * secondNumber;
            default:
                throw new UnsupportedOperationException();
        }
    }
}

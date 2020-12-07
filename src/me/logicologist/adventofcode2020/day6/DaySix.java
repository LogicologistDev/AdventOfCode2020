package me.logicologist.adventofcode2020.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class DaySix {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day6/DaySix.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        partOne(inputs);
        partTwo(inputs);
    }

    public static void partOne(List<String> inputs) {
        int questionsAnswered = 0;
        List<String> questions = new ArrayList<>();
        for (String input : inputs) {
            if (input.isEmpty()) {
                questions = questions.stream().distinct().collect(Collectors.toList());
                questionsAnswered += questions.size();
                questions.clear();
                continue;
            }
            questions.addAll(Arrays.asList(input.split("")));
        }

        System.out.println(questionsAnswered);
    }

    public static void partTwo(List<String> inputs) {
        List<String> questionList = new ArrayList<>();

        int questionsAnswered = 0;
        int questionsCount = 0;

        for (String input : inputs) {
            if (input.isEmpty()) {
                Map<String, Long> counts = questionList.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
                int finalQuestionsCount = questionsCount;
                questionsAnswered += counts.keySet().stream().filter(x -> counts.get(x) == finalQuestionsCount).count();
                questionList.clear();
                questionsCount = 0;
                continue;
            }
            List<String> q = Arrays.asList(input.split(""));
            questionList.addAll(q);
            questionsCount++;
        }
        System.out.println(questionsAnswered);
    }
}

package me.logicologist.adventofcode2020.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayFour {


    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src/me/logicologist/adventofcode2020/day4/DayFour.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        partTwo(partOne(inputs));
    }

    public static List<Passport> partOne(List<String> inputs) {
        int validChecks = 0;
        int validPassports = 0;

        List<Passport> passports = new ArrayList<>();
        HashMap<String, String> ids = new HashMap<>();

        for (String input : inputs) {
            if (input.isEmpty()) {
                if (validChecks >= 7) {
                    validPassports++;
                    passports.add(new Passport(ids.get("byr"), ids.get("iyr"), ids.get("eyr"), ids.get("hgt"), ids.get("hcl"), ids.get("ecl"), ids.get("pid")));
                }
                ids.clear();
                validChecks = 0;
                continue;
            }
            for (String s : input.split(" ")) {
                if (s.startsWith("cid")) continue;
                ids.put(s.split(":")[0], s.split(":")[1]);
                validChecks++;
            }
        }

        System.out.println(validPassports);
        return passports;
    }

    public static void partTwo(List<Passport> passports) {
        int count = (int) passports.stream().filter(Passport::isValid).count();
        System.out.println(count);
    }
}

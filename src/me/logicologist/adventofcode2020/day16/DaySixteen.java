package me.logicologist.adventofcode2020.day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DaySixteen {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day16/DaySixteen.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        HashMap<Integer, List<Integer>> nearbyTicketDetails = new HashMap<>();
        HashMap<String, List<String>> criterion = new HashMap<>();
        List<Integer> personalTicketDetails = new ArrayList<>();

        int stage = 0;
        int nearbyTicketId = 0;
        for (String input : inputs) {
            if (input.isEmpty()) continue;
            if (input.equals("your ticket:") || input.equals("nearby tickets:")) {
                stage++;
                continue;
            }

            switch (stage) {
                case 0:
                    criterion.put(input.split(": ")[0], Arrays.asList(input.split(": ")[1].split(" or ")));
                    continue;
                case 1:
                case 2:
                    nearbyTicketId++;
                    List<Integer> details = new ArrayList<>();
                    for (String detail : input.split(",")) details.add(Integer.parseInt(detail));
                    if (stage == 1) personalTicketDetails.addAll(details);
                    nearbyTicketDetails.put(nearbyTicketId, new ArrayList<>(details));
            }
        }

        partTwo(partOne(nearbyTicketDetails, criterion, personalTicketDetails), criterion, personalTicketDetails);
    }

    public static HashMap<Integer, List<Integer>> partOne(HashMap<Integer, List<Integer>> nearbyTicketDetails, HashMap<String, List<String>> criterion, List<Integer> personalTicketDetails) {

        List<Integer> foreignDetails = new ArrayList<>();
        HashMap<Integer, List<Integer>> correctFields = new HashMap<>();

        for (List<Integer> details : nearbyTicketDetails.values()) {
            boolean discardTicket = false;
            for (Integer detail : details) {
                boolean matchesDetail = false;
                for (List<String> criteriaBounds : criterion.values()) {
                    for (String criteria : criteriaBounds) {
                        int lowBound = Integer.parseInt(criteria.split("-")[0]);
                        int highBound = Integer.parseInt(criteria.split("-")[1]);
                        if (detail >= lowBound && detail <= highBound) matchesDetail = true;
                        if (matchesDetail) break;
                    }
                }
                if (!matchesDetail) {
                    foreignDetails.add(detail);
                    discardTicket = true;
                }
            }
            if (discardTicket) continue;
            int detailNumber = -1;
            for (Integer detail : details) {
                detailNumber++;
                correctFields.putIfAbsent(detailNumber, new ArrayList<>());
                correctFields.get(detailNumber).add(detail);
            }
        }

        int detailNumber = -1;
        for (Integer detail : personalTicketDetails) {
            detailNumber++;
            correctFields.putIfAbsent(detailNumber, new ArrayList<>());
            correctFields.get(detailNumber).add(detail);
        }

        int errorRate = 0;

        for (Integer foreignDetail : foreignDetails) errorRate += foreignDetail;
        System.out.println(errorRate);
        return correctFields;
    }

    public static void partTwo(HashMap<Integer, List<Integer>> correctFields, HashMap<String, List<String>> criterion, List<Integer> personalTicketDetails) {
        HashMap<Integer, List<String>> foundFields = new HashMap<>();

        for (Map.Entry<Integer, List<Integer>> correctFieldList : correctFields.entrySet()) {
            for (Map.Entry<String, List<String>> criteriaBounds : criterion.entrySet()) {
                boolean fieldFound = true;
                int lowBound1 = Integer.parseInt(criteriaBounds.getValue().get(0).split("-")[0]);
                int highBound1 = Integer.parseInt(criteriaBounds.getValue().get(0).split("-")[1]);
                int lowBound2 = Integer.parseInt(criteriaBounds.getValue().get(1).split("-")[0]);
                int highBound2 = Integer.parseInt(criteriaBounds.getValue().get(1).split("-")[1]);
                for (Integer field : correctFieldList.getValue()) {
                    if ((field >= lowBound1 && field <= highBound1) || (field >= lowBound2 && field <= highBound2))
                        continue;
                    fieldFound = false;
                    break;
                }
                if (fieldFound) {
                    foundFields.putIfAbsent(correctFieldList.getKey(), new ArrayList<>());
                    foundFields.get(correctFieldList.getKey()).add(criteriaBounds.getKey());
                }
            }
        }

        HashMap<String, Integer> confirmedFields = new HashMap<>();

        while (confirmedFields.size() < foundFields.size()) {
            for (Map.Entry<Integer, List<String>> foundField : foundFields.entrySet()) {
                if (foundField.getValue().size() != 1) continue;
                String confirmedField = foundField.getValue().get(0);
                confirmedFields.put(confirmedField, foundField.getKey());
                for (List<String> fields : foundFields.values()) fields.remove(confirmedField);
            }
        }

        long result = 1;

        for (Map.Entry<String, Integer> confirmedField : confirmedFields.entrySet()) {
            if (!confirmedField.getKey().contains("departure")) continue;
            result *= personalTicketDetails.get(confirmedField.getValue());
        }

        System.out.println(result);
    }
}

package me.logicologist.adventofcode2020.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DayEleven {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day11/DayEleven.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }

        List<Seat> config = new ArrayList<>();
        int row = 0;
        for (String input : inputs) {
            int column = 0;
            for (String slot : input.split("")) {
                config.add(new Seat(row, column, slot));
                column++;
            }
            row++;
        }

        partOne(config);
        partTwo(config);
    }

    public static void partOne(List<Seat> config) {
        List<Seat> lastConfig = new ArrayList<>(config);
        List<Seat> newConfig = new ArrayList<>();
        HashMap<String, Seat> plane = new HashMap<>();
        while (true) {
            plane.clear();
            for (Seat s : lastConfig) {
                plane.put(s.getLocation(), s);
            }
            AtomicInteger calculated = new AtomicInteger();
            for (Seat s : lastConfig) {
                Seat seat = s.clone();
                newConfig.add(seat);
                int x = Integer.parseInt(seat.getLocation().split(",")[0]);
                int y = Integer.parseInt(seat.getLocation().split(",")[1]);
                List<Integer> possibleX = new ArrayList<>(Arrays.asList(x - 1, x, x + 1));
                List<Integer> possibleY = new ArrayList<>(Arrays.asList(y - 1, y, y + 1));
                List<Seat> affectedSeats = new ArrayList<>();
                for (int selectedX : possibleX) {
                    for (int selectedY : possibleY) {
                        String coords = selectedX + "," + selectedY;
                        if (coords.equals(seat.getLocation())) continue;
                        if (plane.containsKey(coords)) affectedSeats.add(plane.get(coords));
                    }
                }
                long takenSeats = affectedSeats.stream().filter(seat2 -> seat2.getState().equals("#")).count();
                if (seat.getState().equals("#") && takenSeats >= 4) {
                    seat.setState("L");
                } else if (seat.getState().equals("L") && takenSeats == 0) {
                    seat.setState("#");
                }
                calculated.getAndIncrement();
            }
            boolean changed = false;
            for (int i = 0; i < lastConfig.size(); i++) {
                if (!lastConfig.get(i).getState().equals(newConfig.get(i).getState())) {
                    changed = true;
                    break;
                }
            }
            if (!changed) break;
            lastConfig.clear();
            lastConfig.addAll(newConfig);
            newConfig.clear();
        }
        System.out.println(lastConfig.stream().filter(x -> x.getState().equals("#")).count());
    }

    public static void partTwo(List<Seat> config) {
        List<Seat> lastConfig = new ArrayList<>(config);
        List<Seat> newConfig = new ArrayList<>();
        HashMap<String, Seat> plane = new HashMap<>();
        while (true) {
            plane.clear();
            for (Seat s : lastConfig) {
                plane.put(s.getLocation(), s);
            }
            AtomicInteger calculated = new AtomicInteger();
            for (Seat s : lastConfig) {
                Seat seat = s.clone();
                newConfig.add(seat);
                int x = Integer.parseInt(seat.getLocation().split(",")[0]);
                int y = Integer.parseInt(seat.getLocation().split(",")[1]);
                List<Seat> affectedSeats = new ArrayList<>(Arrays.asList(
                        search(plane, x, y, -1, -1),
                        search(plane, x, y, -1, 0),
                        search(plane, x, y, -1, 1),
                        search(plane, x, y, 0, -1),
                        search(plane, x, y, 0, 1),
                        search(plane, x, y, 1, -1),
                        search(plane, x, y, 1, 0),
                        search(plane, x, y, 1, 1)
                ));
                affectedSeats = affectedSeats.stream().filter(Objects::nonNull).collect(Collectors.toList());
                long takenSeats = affectedSeats.stream().filter(seat2 -> seat2.getState().equals("#")).count();
                if (seat.getState().equals("#") && takenSeats >= 5) {
                    seat.setState("L");
                } else if (seat.getState().equals("L") && takenSeats == 0) {
                    seat.setState("#");
                }
                calculated.getAndIncrement();
            }
            boolean changed = false;
            for (int i = 0; i < lastConfig.size(); i++) {
                if (!lastConfig.get(i).getState().equals(newConfig.get(i).getState())) {
                    changed = true;
                    break;
                }
            }
            if (!changed) break;
            lastConfig.clear();
            lastConfig.addAll(newConfig);
            newConfig.clear();
        }
        System.out.println(lastConfig.stream().filter(x -> x.getState().equals("#")).count());
    }

    public static Seat search(HashMap<String, Seat> plane, int x, int y, int xIncrement, int yIncrement) {
        while (true) {
            x += xIncrement;
            y += yIncrement;
            if (!plane.containsKey(x + "," + y)) return null;
            if (!plane.get(x + "," + y).getState().equals(".")) return plane.get(x + "," + y);
        }
    }
}

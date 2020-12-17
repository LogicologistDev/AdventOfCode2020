package me.logicologist.adventofcode2020.day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DaySeventeen {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/me/logicologist/adventofcode2020/day17/DaySeventeen.txt");
        Scanner scanner = new Scanner(file);
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }
        HashMap<String, Tesseract> pocketUniverse = new HashMap<>();
        for (int x = 0; x < inputs.size(); x++) {
            for (int y = 0; y < inputs.size(); y++) {
                pocketUniverse.put(x + "," + y + ",0,0", new Tesseract(x, y, 0, 0, inputs.get(x).split("")[y].equals("#")));
            }
        }
        calculatePhases(new HashMap<>(pocketUniverse), false);
        calculatePhases(new HashMap<>(pocketUniverse), true);
    }

    public static void calculatePhases(HashMap<String, Tesseract> pocketUniverse, boolean hasUniverses) {
        HashMap<String, Tesseract> previousUniverses;
        for (int phase = 0; phase < 6; phase++) {
            previousUniverses = cloneUniverse(pocketUniverse);
            loadNeighborTesseracts(previousUniverses, hasUniverses);
            pocketUniverse.clear();
            for (Map.Entry<String, Tesseract> entry : previousUniverses.entrySet()) {
                Tesseract newTesseract = entry.getValue().clone();
                toggleTesseract(newTesseract, getNeighborTesseracts(entry.getValue(), previousUniverses));
                pocketUniverse.put(entry.getKey(), newTesseract);
            }
        }
        System.out.println(pocketUniverse.values().stream().filter(Tesseract::getActive).count());
    }

    public static HashMap<String, Tesseract> cloneUniverse(HashMap<String, Tesseract> pocketDimension) {
        HashMap<String, Tesseract> newDimension = new HashMap<>();
        for (Map.Entry<String, Tesseract> entry : pocketDimension.entrySet())
            newDimension.put(entry.getKey(), entry.getValue().clone());
        return newDimension;
    }

    public static void loadNeighborTesseracts(HashMap<String, Tesseract> pocketDimension, boolean hasUniverse) {
        List<Tesseract> ogTesseracts = new ArrayList<>(pocketDimension.values());
        for (int w = -1; w <= 1; w++) {
            if (!hasUniverse && w != 0) continue;
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0 && w == 0) continue;
                        for (Tesseract tesseract : ogTesseracts) {
                            String location = (tesseract.x() + x) + "," + (tesseract.y() + y) + "," + (tesseract.z() + z) + "," + (tesseract.w() + w);
                            if (pocketDimension.containsKey(location)) continue;
                            pocketDimension.put(location, new Tesseract(tesseract.x() + x, tesseract.y() + y, tesseract.z() + z, tesseract.w() + w, false));
                        }
                    }
                }
            }
        }
    }

    public static List<Tesseract> getNeighborTesseracts(Tesseract tesseract, HashMap<String, Tesseract> pocketDimension) {
        List<Tesseract> neighbors = new ArrayList<>();
        for (int w = -1; w <= 1; w++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0 && w == 0) continue;
                        String location = (tesseract.x() + x) + "," + (tesseract.y() + y) + "," + (tesseract.z() + z) + "," + (tesseract.w() + w);
                        if (!pocketDimension.containsKey(location)) continue;
                        neighbors.add(pocketDimension.get(location));
                    }
                }
            }
        }
        return neighbors;
    }

    public static void toggleTesseract(Tesseract tesseract, List<Tesseract> neighbors) {
        long active = neighbors.stream().filter(Tesseract::getActive).count();
        if (tesseract.getActive()) {
            if (active != 2 && active != 3) tesseract.setActive(false);
            return;
        }
        if (active == 3) tesseract.setActive(true);
    }

}
import java.util.*;
import java.io.*;

public class Route2 {
    public static void main(String[] args) {
        String line;
        String directions = "";
        HashMap<String, String[]> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            directions = br.readLine();
            while ((line = br.readLine()) != null) {
                processLine(line, map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> startingPoints = getStartingPoints(map);
        int steps = 0;
        boolean allEndsWithZ;
        do {
            allEndsWithZ = true;
            for (int i = 0; i < startingPoints.size(); i++) {
                if (!startingPoints.get(i).endsWith("Z")) {
                    allEndsWithZ = false;
                    String[] nextNodes = map.get(startingPoints.get(i));
                    if (nextNodes == null) {
                        System.out.println("No path found from " + startingPoints.get(i));
                        return;
                    }
                    char direction = directions.charAt(steps % directions.length());
                    startingPoints.set(i, direction == 'L' ? nextNodes[0] : nextNodes[1]);
                }
            }
            if (!allEndsWithZ)
                steps++;
        } while (!allEndsWithZ);

        System.out.println("Total steps to reach all ends: " + steps);
    }

    public static List<String> getStartingPoints(HashMap<String, String[]> map) {
        List<String> startingPoints = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.endsWith("A")) {
                startingPoints.add(key);
            }
        }
        return startingPoints;
    }

    public static void processLine(String line, HashMap<String, String[]> map) {
        int startIndex = line.indexOf("(");
        int endIndex = line.indexOf(")");

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            String subString = line.substring(startIndex + 1, endIndex);
            String[] leftRight = subString.split(",");
            if (leftRight.length == 2) {
                String key = line.substring(0, line.indexOf("=")).trim();
                leftRight[0] = leftRight[0].trim();
                leftRight[1] = leftRight[1].trim();
                map.put(key, leftRight);
            }
        }
    }
}

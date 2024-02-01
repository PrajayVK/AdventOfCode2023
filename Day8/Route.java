import java.util.*;
import java.io.*;

public class Route {
    public static void main(String[] args) {
        String line;
        String directions = "";
        HashMap<String, String[]> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            if ((directions = br.readLine()) == null) {
                System.out.println("No directions found in the input file.");
                return;
            }
            while ((line = br.readLine()) != null) {
                processLine(line, map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String current = "AAA";
        int directionIndex = 0;
        int steps = 0;
        while (!current.equals("ZZZ")) {
            String[] nextNodes = map.get(current);
            if (nextNodes == null) {
                System.out.println("No path found from " + current);
                return;
            }

            char direction = directions.charAt(directionIndex);
            current = direction == 'L' ? nextNodes[0] : nextNodes[1];
            directionIndex = (directionIndex + 1) % directions.length();
            steps++;
        }

        System.out.println("Reached ZZZ in " + steps + " steps");
        int result = part2Solution(map, directions);
        System.out.println("Part 2 answer:" + result);
    }

    public static int part2Solution(HashMap<String, String[]> map, String directions) {
        List<String> currentNodes = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.endsWith("A")) {
                currentNodes.add(key);
            }
        }

        int steps = 0;
        boolean allNodesEndWithZ;
        do {
            allNodesEndWithZ = true;
            List<String> nextNodes = new ArrayList<>();
            char direction = directions.charAt(steps % directions.length());

            for (String node : currentNodes) {
                String[] next = map.get(node);
                if (next == null)
                    continue; // Skip if no mapping found

                String nextNode = direction == 'L' ? next[0] : next[1];
                nextNodes.add(nextNode);
                if (!nextNode.endsWith("Z")) {
                    allNodesEndWithZ = false;
                }
            }

            currentNodes = nextNodes;
            steps++;
        } while (!allNodesEndWithZ);

        return steps;
    }

    public static boolean checkEndsWithZ(String[] arr) {
        int count = 0;
        for (String str : arr) {
            if (str.endsWith("Z")) {
                count++;
            }
        }
        return count == arr.length;
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

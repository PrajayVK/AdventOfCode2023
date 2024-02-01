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
        long result = part2Solution(map, directions);
        System.out.println("Part 2 answer:" + result);
    }

    public static long part2Solution(HashMap<String, String[]> map, String directions) {
        List<String> currentNodes = new ArrayList<>();
        int steps;
        List<Integer> allSteps = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.endsWith("A")) {
                currentNodes.add(key);
            }
        }
        for (String str : currentNodes) {
            steps = 0;
            int directionIndex = 0;
            String curr = str;
            while (!curr.endsWith("Z")) {
                String[] nextNodes = map.get(curr);
                if (nextNodes == null) {
                    System.out.println("No path found from " + curr);

                }
                char direction = directions.charAt(directionIndex);
                curr = direction == 'L' ? nextNodes[0] : nextNodes[1];
                directionIndex = (directionIndex + 1) % directions.length();
                steps++;
            }
            allSteps.add(steps);
        }

        return lcmOfList(allSteps);
    }

    private static long lcm(long a, int b) {
        return a * (b / gcd(a, b));
    }

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static long lcmOfList(List<Integer> numbers) {
        long result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }
        return result;
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

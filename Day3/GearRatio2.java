import java.util.*;
import java.io.*;

public class GearRatio2 {

    public static void main(String[] args) {
        String filePath = "input.txt";
        List<char[]> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line.toCharArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        char[][] graph = list.toArray(new char[list.size()][]);
        solvePuzzle(graph);
    }

    private static void solvePuzzle(char[][] graph) {
        long partSum = sumPartNumbers(graph);
        System.out.println("Part 1 - Sum of Part Numbers: " + partSum);

        long gearRatioSum = sumGearRatios(graph);
        System.out.println("Part 2 - Sum of Gear Ratios: " + gearRatioSum);
    }

    private static long sumPartNumbers(char[][] graph) {
        Set<String> processed = new HashSet<>();
        long sum = 0;

        for (int row = 0; row < graph.length; row++) {
            for (int col = 0; col < graph[row].length; col++) {
                if (!Character.isDigit(graph[row][col]) && graph[row][col] != '.') {
                    sum += sumAdjacentNumbers(graph, row, col, processed);
                }
            }
        }

        return sum;
    }

    private static long sumGearRatios(char[][] graph) {
        int[][] directions = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };
        long gearRatioSum = 0;

        for (int row = 0; row < graph.length; row++) {
            for (int col = 0; col < graph[row].length; col++) {
                if (graph[row][col] == '*') {
                    List<Integer> adjacentNumbers = new ArrayList<>();

                    for (int[] dir : directions) {
                        int adjRow = row + dir[0];
                        int adjCol = col + dir[1];

                        if (adjRow >= 0 && adjRow < graph.length && adjCol >= 0 && adjCol < graph[adjRow].length) {
                            if (Character.isDigit(graph[adjRow][adjCol])) {
                                int number = getFullNumber(graph, adjRow, adjCol);
                                if (!adjacentNumbers.contains(number)) {
                                    adjacentNumbers.add(number);
                                }
                            }
                        }
                    }

                    if (adjacentNumbers.size() == 2) {
                        gearRatioSum += (long) adjacentNumbers.get(0) * adjacentNumbers.get(1);
                    }
                }
            }
        }

        return gearRatioSum;
    }

    private static long sumAdjacentNumbers(char[][] graph, int row, int col, Set<String> processed) {
        int[][] directions = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };
        long sum = 0;

        for (int[] dir : directions) {
            int adjRow = row + dir[0];
            int adjCol = col + dir[1];

            if (adjRow >= 0 && adjRow < graph.length && adjCol >= 0 && adjCol < graph[adjRow].length) {
                if (Character.isDigit(graph[adjRow][adjCol]) && !processed.contains(adjRow + "," + adjCol)) {
                    int number = getFullNumber(graph, adjRow, adjCol);
                    sum += number;
                    processed.add(adjRow + "," + adjCol);
                }
            }
        }

        return sum;
    }

    private static int getFullNumber(char[][] graph, int row, int col) {
        StringBuilder numberBuilder = new StringBuilder();
        numberBuilder.append(graph[row][col]);
        int left = col - 1;
        while (left >= 0 && Character.isDigit(graph[row][left])) {
            numberBuilder.insert(0, graph[row][left]);
            left--;
        }

        int right = col + 1;
        while (right < graph[row].length && Character.isDigit(graph[row][right])) {
            numberBuilder.append(graph[row][right]);
            right++;
        }

        return Integer.parseInt(numberBuilder.toString());
    }
}

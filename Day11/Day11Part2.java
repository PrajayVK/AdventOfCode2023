import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;

public class Day11Part2 {
    private static final long MILLION = 1000000L;

    public static void main(String[] args) {
        String fileName = "input.txt";
        List<List<Character>> grid = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char ch : line.toCharArray()) {
                    row.add(ch);
                }
                grid.add(row);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> rowsWithNoGal = getRows(grid);
        List<Integer> colsWithNoGal = getCols(grid);
        System.out.println("Number of rows with no galleons: " + rowsWithNoGal);
        System.out.println("Columns with no galleons: " + colsWithNoGal);

        expandGridPartTwo(grid, rowsWithNoGal, colsWithNoGal); // use the new expand method
        Map<Point, Integer> galaxyMap = identifyGalaxies(grid);

        long[][] distanceGraph = buildGraphPartTwo(galaxyMap, grid); // use long for larger distances

        floydWarshall(distanceGraph);

        long sum = sumShortestPaths(distanceGraph); // use long for sum
        System.out.println("Total sum of shortest paths: " + sum);
    }

    public static List<Integer> getCols(List<List<Character>> grid) {
        List<Integer> colsWithNoGal = new ArrayList<>();
        int numOfCols = grid.get(0).size();

        for (int i = 0; i < numOfCols; i++) {
            boolean flag = false;
            for (List<Character> row : grid) {
                if (row.get(i) == '#') {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                colsWithNoGal.add(i);
            }
        }
        return colsWithNoGal;
    }

    public static List<Integer> getRows(List<List<Character>> grid) {
        List<Integer> rowsWithNoGal = new ArrayList<>();

        for (int i = 0; i < grid.size(); i++) {
            if (!grid.get(i).contains('#')) {
                rowsWithNoGal.add(i);
            }
        }
        return rowsWithNoGal;
    }

    private static Map<Point, Integer> identifyGalaxies(List<List<Character>> grid) {
        Map<Point, Integer> galaxyMap = new HashMap<>();
        int id = 0;
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == '#') {
                    galaxyMap.put(new Point(i, j), id++);
                }
            }
        }
        return galaxyMap;
    }

    // ... existing methods for getCols, getRows, identifyGalaxies ...

    public static void expandGridPartTwo(List<List<Character>> grid, List<Integer> rowsWithNoGal,
            List<Integer> colsWithNoGal) {
        for (int i = rowsWithNoGal.size() - 1; i >= 0; i--) {
            int row = rowsWithNoGal.get(i);
            List<Character> newRow = new ArrayList<>();
            for (int j = 0; j < grid.get(row).size(); j++) {
                newRow.add('M');
            }
            grid.add(row + 1, newRow);
        }

        for (int i = colsWithNoGal.size() - 1; i >= 0; i--) {
            int col = colsWithNoGal.get(i);
            for (List<Character> row : grid) {
                row.add(col + 1, 'M');
            }
        }
    }

    private static long[][] buildGraphPartTwo(Map<Point, Integer> galaxyMap, List<List<Character>> grid) {
        int n = galaxyMap.size();
        long[][] graph = new long[n][n];
        for (int i = 0; i < n; i++) {
            java.util.Arrays.fill(graph[i], Long.MAX_VALUE / 2);
            graph[i][i] = 0;
        }

        for (Point p1 : galaxyMap.keySet()) {
            for (Point p2 : galaxyMap.keySet()) {
                if (!p1.equals(p2)) {
                    long dist = calculateDistancePartTwo(p1, p2, grid);
                    graph[galaxyMap.get(p1)][galaxyMap.get(p2)] = dist;
                }
            }
        }
        return graph;
    }

    private static long calculateDistancePartTwo(Point p1, Point p2, List<List<Character>> grid) {
        long dist = 0;
        // Check if in the same row or column first
        if (p1.x == p2.x) { // same column
            for (int i = Math.min(p1.y, p2.y); i <= Math.max(p1.y, p2.y); i++) {
                dist += (grid.get(p1.x).get(i) == 'M') ? MILLION : 1;
            }
        } else if (p1.y == p2.y) { // same row
            for (int i = Math.min(p1.x, p2.x); i <= Math.max(p1.x, p2.x); i++) {
                dist += (grid.get(i).get(p1.y) == 'M') ? MILLION : 1;
            }
        } else {
            // If not in the same row or column, use Manhattan distance
            dist = (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y)) * MILLION;
        }
        return dist;
    }

    private static void floydWarshall(long[][] graph) {
        int n = graph.length;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (graph[i][k] + graph[k][j] < graph[i][j]) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                    }
                }
            }
        }
    }

    private static long sumShortestPaths(long[][] graph) {
        long sum = 0;
        int n = graph.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += graph[i][j];
            }
        }
        return sum;
    }
}

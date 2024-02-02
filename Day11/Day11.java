import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;

public class Day11 {
    public static void main(String[] args) {
        String fileName = "testInput.txt";
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
        expandGrid(grid, rowsWithNoGal, colsWithNoGal);
        Map<Point, Integer> galaxyMap = identifyGalaxies(grid);

        int[][] distanceGraph = buildGraph(galaxyMap, grid);

        floydWarshall(distanceGraph);

        int sum = sumShortestPaths(distanceGraph);
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

    public static void expandGrid(List<List<Character>> grid, List<Integer> rowsWithNoGal,
            List<Integer> colsWithNoGal) {
        for (int i = rowsWithNoGal.size() - 1; i >= 0; i--) {
            int row = rowsWithNoGal.get(i);
            grid.add(row + 1, new ArrayList<>(grid.get(row)));
        }

        for (int i = colsWithNoGal.size() - 1; i >= 0; i--) {
            int col = colsWithNoGal.get(i);
            for (List<Character> row : grid) {
                row.add(col + 1, row.get(col));
            }
        }
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

    private static int[][] buildGraph(Map<Point, Integer> galaxyMap, List<List<Character>> grid) {
        int n = galaxyMap.size();
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            java.util.Arrays.fill(graph[i], Integer.MAX_VALUE / 2);
            graph[i][i] = 0;
        }

        for (Point p1 : galaxyMap.keySet()) {
            for (Point p2 : galaxyMap.keySet()) {
                if (!p1.equals(p2)) {
                    int dist = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
                    graph[galaxyMap.get(p1)][galaxyMap.get(p2)] = dist;
                }
            }
        }
        return graph;
    }

    private static void floydWarshall(int[][] graph) {
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

    private static int sumShortestPaths(int[][] graph) {
        int sum = 0;
        int n = graph.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += graph[i][j];
            }
        }
        return sum;
    }

}

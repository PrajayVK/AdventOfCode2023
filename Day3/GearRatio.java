import java.util.*;
import java.io.*;

public class GearRatio {

    public static List<int[]> coOrd = new ArrayList<int[]>();
    public static List<int[]> numCoOrd = new ArrayList<>();
    public static List<int[]> starCoOrd = new ArrayList<>();
    public static Set<String> processedForPart2 = new HashSet<>();

    public static void main(String[] args) {
        String line;
        String filePath = "input.txt";
        List<char[]> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                list.add(line.toCharArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        char[][] graph = new char[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            graph[i] = list.get(i);
        }
        getResult(graph);
        result(graph);
        int result = finalSum(graph);
        System.out.println(result);

    }

    public static void getResult(char[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                char ch = graph[i][j];
                if (!Character.isDigit(ch) && ch != '.') {
                    coOrd.add(new int[] { i, j });
                }
                if (ch == '*') {
                    starCoOrd.add(new int[] { i, j });
                }

            }
        }
    }

    public static void result(char[][] graph) {
        int[][] direction = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 }, { -1, 1 }, { 1, -1 }, { -1, -1 }, { 1, 1 } };
        for (int[] i : coOrd) {
            for (int[] j : direction) {
                int newRow = i[0] + j[0];
                int newCol = i[1] + j[1];
                if (newRow >= 0 && newRow < graph.length && newCol >= 0 && newCol < graph[newRow].length) {
                    if (Character.isDigit(graph[newRow][newCol])) {
                        numCoOrd.add(new int[] { newRow, newCol });
                    }
                }
            }
        }
        int resultPart2 = 0;
        for (int[] i : starCoOrd) {
            int count = 0;
            int[] res = new int[2];
            for (int[] j : direction) {
                int newRow = i[0] + j[0];
                int newCol = i[1] + j[1];
                if (newRow >= 0 && newRow < graph.length && newCol >= 0 && newCol < graph[newRow].length) {
                    if (Character.isDigit(graph[newRow][newCol]) && count < 2) {
                        res[count] = getFullNumber(graph, newRow, newCol);
                        count++;
                    }
                }
            }
            resultPart2 = resultPart2 + res[0] * res[1];
        }
        System.out.println("Result Part 2: " + resultPart2);
    }

    public static int getFullNumber(char[][] graph, int newRow, int newCol) {
        int sum = 0;
        String pos = newRow + "," + newCol;
        if (!processedForPart2.contains(pos)) {
            StringBuilder numberBuilder = new StringBuilder();

            int left = newCol - 1;
            while (left >= 0 && Character.isDigit(graph[newRow][left])) {
                numberBuilder.insert(0, graph[newRow][left]);
                processedForPart2.add(newRow + "," + left);
                left--;
            }

            numberBuilder.append(graph[newRow][newCol]);
            processedForPart2.add(pos);

            int right = newCol + 1;
            while (right < graph[newRow].length && Character.isDigit(graph[newRow][right])) {
                numberBuilder.append(graph[newRow][right]);
                processedForPart2.add(newRow + "," + right);
                right++;
            }

            if (numberBuilder.length() > 0) {
                return Integer.parseInt(numberBuilder.toString());
            }
        }
        return 0;
    }

    public static int finalSum(char[][] graph) {
        int sum = 0;
        Set<String> processed = new HashSet<>();

        for (int[] coord : numCoOrd) {
            String pos = coord[0] + "," + coord[1];
            if (!processed.contains(pos)) {
                StringBuilder numberBuilder = new StringBuilder();

                int left = coord[1] - 1;
                while (left >= 0 && Character.isDigit(graph[coord[0]][left])) {
                    numberBuilder.insert(0, graph[coord[0]][left]);
                    processed.add(coord[0] + "," + left);
                    left--;
                }

                numberBuilder.append(graph[coord[0]][coord[1]]);
                processed.add(pos);

                int right = coord[1] + 1;
                while (right < graph[coord[0]].length && Character.isDigit(graph[coord[0]][right])) {
                    numberBuilder.append(graph[coord[0]][right]);
                    processed.add(coord[0] + "," + right);
                    right++;
                }

                if (numberBuilder.length() > 0) {
                    sum += Integer.parseInt(numberBuilder.toString());
                }
            }
        }
        return sum;
    }

}

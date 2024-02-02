import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day11Part2 {
    private static final long SCALE = 1000000L;

    public static void main(String[] args) {
        String fileName = "input.txt";
        List<String> grid = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                grid.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<Integer> emptyRows = new ArrayList<>();
        for (int r = 0; r < grid.size(); r++) {
            if (grid.get(r).chars().allMatch(ch -> ch == '.'))
                emptyRows.add(r);
        }

        List<Integer> emptyCols = new ArrayList<>();
        for (int c = 0; c < grid.get(0).length(); c++) {
            int finalC = c;
            if (grid.stream().allMatch(row -> row.charAt(finalC) == '.'))
                emptyCols.add(c);
        }

        List<Point> points = new ArrayList<>();
        for (int r = 0; r < grid.size(); r++) {
            for (int c = 0; c < grid.get(r).length(); c++) {
                if (grid.get(r).charAt(c) == '#')
                    points.add(new Point(r, c));
            }
        }

        long total = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < i; j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);

                for (int r = Math.min(p1.x, p2.x); r < Math.max(p1.x, p2.x); r++)
                    total += emptyRows.contains(r) ? SCALE : 1;

                for (int c = Math.min(p1.y, p2.y); c < Math.max(p1.y, p2.y); c++)
                    total += emptyCols.contains(c) ? SCALE : 1;
            }
        }

        System.out.println(total);
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

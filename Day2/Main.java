package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "Games.txt";
        StringBuilder inputBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                inputBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String input = inputBuilder.toString();
        Day02 day02 = new Day02(input);

        System.out.println("Part 1 Result: " + day02.part1());
        System.out.println("Part 2 Result: " + day02.part2());
    }
}

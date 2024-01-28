import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Card {
    static HashMap<Integer, Integer> cardCount = new HashMap<>();

    public static void main(String args[]) {
        String line;
        int sum = 0;
        int part2 = 0;
        String filePath = "input.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                sum = sum + (int) Math.pow(2, (processInput(line) - 1));
                part2 = part2(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Answer:" + sum);
        for (int i : cardCount.values()) {
            System.out.println(i);
        }
    }

    public static int processInput(String line) {
        int val = 0;
        line = line.replaceFirst("Card ", "");
        int colonIndex = line.indexOf(":");
        line = line.substring(colonIndex + 1);
        String[] cards = line.split("\\|");
        String[] winning = cards[0].trim().split("\\s+");
        String[] have = cards[1].trim().split("\\s+");
        HashSet<Integer> winningCards = new HashSet<>();
        for (String str : winning)
            winningCards.add(Integer.parseInt(str));
        for (String card : have) {
            if (!card.isEmpty()) {
                int temp = Integer.parseInt(card.trim());
                if (winningCards.contains(temp)) {
                    val++;
                }
            }
        }
        return val;
    }

    public static int part2(String line) {
        line = line.replaceFirst("Card ", "");
        int key = Integer.parseInt(line.substring(0, line.indexOf(":")).trim());

        int val = processInput(line);
        for (int i = key; i <= val; ++i) {
            cardCount.put(i, cardCount.getOrDefault(i, 0) + 1);
        }
        return 0;
    }

}
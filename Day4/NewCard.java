import java.io.*;
import java.util.*;

public class NewCard {
    public static void main(String[] args) throws IOException {
        String path = "input.txt";
        List<Pair<Set<Integer>, Set<Integer>>> cards = processCardInput(path);
        int totalScratchcards = countWinningCards(cards);
        System.out.println(totalScratchcards);
    }

    private static List<Pair<Set<Integer>, Set<Integer>>> processCardInput(String filePath) throws IOException {
        List<Pair<Set<Integer>, Set<Integer>>> cards = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            Set<Integer> winningNumbers = new HashSet<>();
            Set<Integer> playerNumbers = new HashSet<>();
            for (String num : parts[0].split(":")[1].trim().split("\\s+")) {
                winningNumbers.add(Integer.parseInt(num));
            }
            for (String num : parts[1].trim().split("\\s+")) {
                playerNumbers.add(Integer.parseInt(num));
            }
            cards.add(new Pair<>(winningNumbers, playerNumbers));
        }
        reader.close();
        return cards;
    }

    private static int countWinningCards(List<Pair<Set<Integer>, Set<Integer>>> cards) {
        int totalCards = cards.size();
        int[] cardCopies = new int[totalCards];
        Arrays.fill(cardCopies, 1);

        for (int i = 0; i < totalCards; i++) {
            Set<Integer> matches = new HashSet<>(cards.get(i).first);
            matches.retainAll(cards.get(i).second);
            for (int j = i + 1; j < Math.min(i + 1 + matches.size(), totalCards); j++) {
                cardCopies[j] += cardCopies[i];
            }
        }
        return Arrays.stream(cardCopies).sum();
    }

    static class Pair<T, U> {
        public final T first;
        public final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }
}

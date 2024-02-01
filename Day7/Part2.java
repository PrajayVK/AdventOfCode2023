import java.util.*;
import java.io.*;

public class Part2 {
    public static HashMap<String, Integer> map = new HashMap<>();
    public static List<String> fiveKind = new ArrayList<>();
    public static List<String> fourKind = new ArrayList<>();
    public static List<String> threeKind = new ArrayList<>();
    public static List<String> fullHouse = new ArrayList<>();
    public static List<String> twoPair = new ArrayList<>();
    public static List<String> onePair = new ArrayList<>();
    public static List<String> highCard = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                if (map.containsKey(split[0])) {
                    System.out.println("repeated value:" + split[0]);
                }
                map.put(split[0], Integer.parseInt(split[1]));
                categoriseKey(split[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        calculateWinnings();

    }

    public static void categoriseKey(String key) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : key.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        Integer jokers = freqMap.getOrDefault('J', 0); // Use getOrDefault to avoid NullPointerException

        if (freqMap.containsValue(5)) {
            fiveKind.add(key);
        } else if (freqMap.containsValue(4) || (freqMap.getOrDefault('J', 0) + freqMap.getOrDefault('A', 0) == 5)) {
            fourKind.add(key);
        } else if (freqMap.containsValue(3)) {
            if (freqMap.size() == 2 && jokers >= 1) {
                fullHouse.add(key);
            } else if (jokers >= 1) {
                fourKind.add(key);
            } else {
                threeKind.add(key);
            }
        } else if (freqMap.size() == 3) {
            if (jokers >= 1) {
                fullHouse.add(key);
            } else {
                twoPair.add(key);
            }
        } else if (freqMap.size() == 4) {
            if (jokers == 1 && freqMap.containsValue(2)) {
                threeKind.add(key);
            } else {
                onePair.add(key);
            }
        } else {
            if (jokers > 0) {
                onePair.add(key);
            } else {
                highCard.add(key);
            }
        }
    }

    private static Comparator<String> handComparator = new Comparator<String>() {
        @Override
        public int compare(String hand1, String hand2) {
            String order = "AKQJT98765432";
            for (int i = 0; i < 5; i++) {
                char c1 = hand1.charAt(i), c2 = hand2.charAt(i);
                if (c1 == 'J')
                    c1 = '1';
                if (c2 == 'J')
                    c2 = '1';
                int compare = order.indexOf(c1) - order.indexOf(c2);
                if (compare != 0) {
                    return compare;
                }
            }
            return 0;
        }
    };

    private static void calculateWinnings() {
        Collections.sort(fiveKind, handComparator);
        Collections.sort(fourKind, handComparator);
        Collections.sort(fullHouse, handComparator);
        Collections.sort(threeKind, handComparator);
        Collections.sort(twoPair, handComparator);
        Collections.sort(onePair, handComparator);
        Collections.sort(highCard, handComparator);

        List<String> allHands = new ArrayList<>();
        allHands.addAll(fiveKind);
        allHands.addAll(fourKind);
        allHands.addAll(fullHouse);
        allHands.addAll(threeKind);
        allHands.addAll(twoPair);
        allHands.addAll(onePair);
        allHands.addAll(highCard);

        int totalWinnings = 0;
        int rank = allHands.size();
        for (String hand : allHands) {
            totalWinnings += map.get(hand) * rank;
            rank--;
        }

        System.out.println("Total Winnings: " + totalWinnings);
    }

}
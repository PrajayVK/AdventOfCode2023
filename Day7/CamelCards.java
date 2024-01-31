import java.util.*;
import java.io.*;

public class CamelCards {
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
        if (freqMap.containsValue(5)) {
            fiveKind.add(key);
        } else if (freqMap.containsValue(4)) {
            fourKind.add(key);
        } else if (freqMap.containsValue(3)) {
            if (freqMap.size() == 2) {
                fullHouse.add(key);
            } else {
                threeKind.add(key);
            }
        } else if (freqMap.size() == 3) {
            twoPair.add(key);
        } else if (freqMap.size() == 4) {
            onePair.add(key);
        } else {
            highCard.add(key);
        }
    }

    private static Comparator<String> handComparator = new Comparator<String>() {
        @Override
        public int compare(String hand1, String hand2) {
            String order = "AKQJT98765432";
            for (int i = 0; i < 5; i++) {
                int compare = order.indexOf(hand1.charAt(i)) - order.indexOf(hand2.charAt(i));
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
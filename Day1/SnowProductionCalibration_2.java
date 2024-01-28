// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.*;

// public class SnowProductionCalibration_2 { 

//     public static void main(String[] args) {
//         String filePath = "calibration2.txt";
//         int sum = 0;

//         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//             String line;
//             while ((line = br.readLine()) != null) {
//                 int value = getCalibrationValue(line);
//                 if (value != -1) {
//                     sum += value;
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         System.out.println("Total sum of calibration values: " + sum);
//     }

//     private static int getCalibrationValue(String line) {
//         StringBuilder sb = new StringBuilder();
//         Map<String, Integer> numberWords = new HashMap<>();
//         boolean firstFlag = false;
//         int firstDigit;
//         numberWords.put("one", 1);
//         numberWords.put("two", 2);
//         numberWords.put("three", 3);
//         numberWords.put("four", 4);
//         numberWords.put("five", 5);
//         numberWords.put("six", 6);
//         numberWords.put("seven", 7);
//         numberWords.put("eight", 8);
//         numberWords.put("nine", 9);
//         for(char ch: line.toCharArray()){
//             if(Character.isDigit(ch) && !firstFlag){
//                 firstDigit = ch - '0';
//                 firstFlag = true;
//             }
//             else if(!Character.isLetterOrDigit(ch)){
//                 sb.append(ch);
//                 if(numberWords.containsKey(sb.toString()) && !firstFlag){
//                     firstDigit = numberWords.get(sb.toString());
//                     firstFlag=true;
//                 }
//             }
//         }
//         return 0;
//     }
// }
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SnowProductionCalibration_2 {

    public static void main(String[] args) {
        String filePath = "calibration2.txt";
        int sum = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                int value = getCalibrationValue(line);
                if (value != -1) {
                    sum += value;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total sum of calibration values: " + sum);
    }

    public static int getCalibrationValue(String line) {

        StringBuilder number = new StringBuilder();
        StringBuilder numbersParts = new StringBuilder();
        for (Character c : line.toCharArray()) {
            numbersParts.append(c);
            numbersParts = new StringBuilder(replaceWordDigits(numbersParts.toString()));

            char ch = numbersParts.charAt(numbersParts.length() - 1);
            if (Character.isDigit(ch)) {
                number.append(ch);
                break;
            }
        }

        numbersParts.setLength(0);
        for (int i = line.length() - 1; i >= 0; i--) {
            numbersParts.insert(0, line.charAt(i));
            numbersParts = new StringBuilder(replaceWordDigits(numbersParts.toString()));

            char ch = numbersParts.charAt(0);
            if (Character.isDigit(ch)) {
                number.append(ch);
                break;
            }
        }
        return Integer.parseInt(number.isEmpty() ? "0" : number.toString());
    }

    private static String replaceWordDigits(String s) {
        return s.replaceAll("one", "1")
                .replaceAll("two", "2")
                .replaceAll("three", "3")
                .replaceAll("four", "4")
                .replaceAll("five", "5")
                .replaceAll("six", "6")
                .replaceAll("seven", "7")
                .replaceAll("eight", "8")
                .replaceAll("nine", "9");
    }
}

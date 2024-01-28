import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SnowProductionCalibration {

    public static void main(String[] args) {
        String filePath = "calibration.txt";
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

    private static int getCalibrationValue(String line) {
        int firstDigit = -1;
        int lastDigit = -1;

        for (char ch : line.toCharArray()) {
            if (Character.isDigit(ch)) {
                if (firstDigit == -1) {
                    firstDigit = Character.getNumericValue(ch);
                }
                lastDigit = Character.getNumericValue(ch);
            }
        }

        if (firstDigit != -1 && lastDigit != -1) {
            return firstDigit * 10 + lastDigit;
        }
        return -1;
    }
}

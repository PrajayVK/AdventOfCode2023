import java.util.*;
import java.io.*;

public class Day6 {
    public static void main(String[] args) throws IOException {
        String filePath = "input.txt";
        String time;
        String distance;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            time = br.readLine();
            distance = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int result = processInput(time, distance);
        System.out.println(result);
    }

    public static int processInput(String t, String d) {
        t = t.substring(t.indexOf(":") + 1).trim();
        d = d.substring(d.indexOf(":") + 1).trim();
        String[] time = t.split("\\s+");
        String[] distance = d.split("\\s+");
        List<Integer> timeArray = new ArrayList<>();
        List<Integer> distArray = new ArrayList<>();
        String timePart2 = "";
        String distancePart2 = "";
        for (int i = 0; i < time.length; i++) {
            timePart2 = timePart2 + time[i];
            distancePart2 = distancePart2 + distance[i];
            timeArray.add(Integer.parseInt(time[i]));
            distArray.add(Integer.parseInt(distance[i]));
        }
        long part2result = getCount(Long.parseLong(timePart2), Long.parseLong(distancePart2));
        System.out.println("Part 2 result is " + part2result);
        int result = 1;
        for (int i = 0; i < timeArray.size(); i++) {
            result = result * getCount(timeArray.get(i), distArray.get(i));
        }
        return result;
    }

    public static int getCount(int t, int d) {
        int count = 0;
        for (int i = 0; i < t; i++) {
            if (i * (t - i) > d) {
                count++;
            }
        }
        return count;
    }

    public static long getCount(long t, long d) {
        long count = 0;
        for (int i = 0; i < t; i++) {
            if (i * (t - i) > d) {
                count++;
            }
        }
        return count;
    }
}
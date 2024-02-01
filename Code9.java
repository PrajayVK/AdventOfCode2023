import java.util.*;
import java.io.*;
public class Main
{
    public static int intpart2sum =0;
    public static void main(String[] args) {
        String line;
        String filePath = "input.txt";
        long sum = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            while((line = br.readLine())!=null){
                sum += processInput(line);
            }
        }catch(IOException e){
            e.printStackTrace();
            return;
        }
        System.out.println("Part 1 answer is:"+ sum);
        System.out.println("Part 2 answer is: "+ intpart2sum);
    }
    public static int processInput(String line){
        
        List<List<Integer>> input = new ArrayList<>();
        String[] nums= null;
        if(line.length()>0){
            nums = line.split(" ");
        }
         
        List<Integer> temp = new ArrayList<>();
        for(String s: nums){
            if(s.length()>0){
                int num = Integer.parseInt(s);
                temp.add(num);
            }
            
        }
        input.add(temp);
        while (!checkZeros(input.get(input.size()-1))){
            List<Integer> temp2 = new ArrayList<>();
            int size = input.get(input.size()-1).size();
            for(int i=0;i<size-1;i++){
                
                int diff = input.get(input.size()-1).get(i+1) - input.get(input.size()-1).get(i);
                temp2.add(diff);
            }
            input.add(temp2);
        }
        int finals = getFinal(input);
        getFinalPart2(input);
        return finals;
    }
    public static int getFinal(List<List<Integer>> input){
        int size = input.size();
        input.get(size-1).add(0);
        for(int i=size-2;i>=0;i--){
            List<Integer> temp = input.get(i);
            temp.add(input.get(i).get(input.get(i).size()-1) +input.get(i+1).get(input.get(i+1).size()-1));
        }
       // check(input);
        
        return input.get(0).get(input.get(0).size()-1);
    }
    public static void getFinalPart2(List<List<Integer>> input){
        int size = input.size();
        input.get(size-1).add(0,0);
        for(int i=size-2;i>=0;i--){
            List<Integer> temp = input.get(i);
            temp.add(0,input.get(i).get(0) - input.get(i+1).get(0));
        }
       // check(input);
        intpart2sum = intpart2sum+input.get(0).get(0);
    }
    public static boolean checkZeros(List<Integer> arr){
        for(int i=0;i< arr.size();i++){
            if(arr.get(i)!=0){
                return false;
            }
        }
        return true;
    }
    public static void check(List<List<Integer>> arr){
        for(List<Integer> ar : arr){
            for(int i: ar){
                System.out.print(i+" ");
            }
            System.out.println("");
        }
    }

}

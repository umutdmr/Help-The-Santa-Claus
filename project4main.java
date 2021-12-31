import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileNotFoundException;
public class project4main {


    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));
        int s = 0;
        int t;
        int mediumIndex = 1;
        int totalGifts = 0;
        HashMap<Integer, String> bagsAndTrains = new HashMap<>();
        HashMap<Integer, Integer> capacities = new HashMap<>();
        
        int noOfGT = in.nextInt();
        for(int i = 0; i < noOfGT; i++)
        {
            int capacity = in.nextInt();
            bagsAndTrains.put(mediumIndex, "GT");
            capacities.put(mediumIndex, capacity);
            mediumIndex++;
        }
        
        int noOfRT = in.nextInt();
        for(int j = 0; j < noOfRT; j++)
        {
            int capacity = in.nextInt();
            bagsAndTrains.put(mediumIndex, "RT");
            capacities.put(mediumIndex, capacity);
            mediumIndex++;
        }

        int noOfGR = in.nextInt();
        for(int k = 0; k < noOfGR; k++)
        {
            int capacity = in.nextInt();
            bagsAndTrains.put(mediumIndex, "GR");
            capacities.put(mediumIndex, capacity);
            mediumIndex++;
        }
        
        int noOfRR = in.nextInt();
        for(int l = 0; l < noOfRR; l++)
        {
            int capacity = in.nextInt();
            bagsAndTrains.put(mediumIndex, "RR");
            capacities.put(mediumIndex, capacity);
            mediumIndex++;
        }

        int noOfBags = in.nextInt();
        for(int m = 0; m < noOfBags; m++)
        {
            String type = in.next();
            int capacity = in.nextInt();
            totalGifts += capacity;
            if(type.startsWith("a"))
            {
                bagsAndTrains.put(mediumIndex, type);
                capacities.put(mediumIndex, capacity);
                mediumIndex++;
            }
            else
            {
                if(bagsAndTrains.containsValue(type))
                {
                    for(Integer integer: bagsAndTrains.keySet())
                    {
                        
                        if(bagsAndTrains.get(integer).equals(type))
                        {
                            
                            capacities.replace(integer, capacities.get(integer), capacities.get(integer) + capacity);
                            
                        }
                    }              
                }
                else
                {
                    bagsAndTrains.put(mediumIndex, type);
                    capacities.put(mediumIndex, capacity);
                    mediumIndex++;
                }
            }
            
        }
        t = mediumIndex;
        System.out.println(totalGifts);
        
    }
}

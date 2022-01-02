import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileNotFoundException;
import static java.lang.Math.min;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class withFordFulkerson {
	
    static final int INF = Integer.MAX_VALUE / 2;
 
    public static class Edge {
        public int from, to;
        public Edge residual;
        public int flow;
        public final int capacity;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            
        }

        public int remainingCapacity() {
            return capacity - flow;
        }

        public void augment(int bottleNeck) {
            flow += bottleNeck;
            residual.flow -= bottleNeck;
        }

    }

    
    int n, s, t;

    int maxFlow;
    List<Edge>[] graph;

    
    private int visitedToken = 1;
    private int[] visited;

    public withFordFulkerson(int n, int s, int t) {
        this.n = n;
        this.s = s;
        this.t = t;
        initializeGraph();
       
        visited = new int[n];
    }

    @SuppressWarnings("unchecked")
    private void initializeGraph() {
        graph = new List[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<Edge>();
    }

    public void addEdge(int from, int to, int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity < 0");
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);
        e1.residual = e2;
        e2.residual = e1;
        graph[from].add(e1);
        graph[to].add(e2);
    }

    public void visit(int i) {
        visited[i] = visitedToken;
    }

    public boolean visited(int i) {
        return visited[i] == visitedToken;
    }

    public void markAllNodesAsUnvisited() {
        visitedToken++;
    }

    public int getMaxFlow() {
        for (int f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
            markAllNodesAsUnvisited();
            maxFlow += f;
        }
        return maxFlow;
    }

    private int dfs(int node, int flow) {
        
        if (node == t)
            return flow;

        List<Edge> edges = graph[node];
        visit(node);

        for (Edge edge : edges) {
            int rcap = edge.remainingCapacity();
            if (rcap > 0 && !visited(edge.to)) {
                int bottleNeck = dfs(edge.to, min(flow, rcap));

                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));
        int s = 0;
        int t;
        int mediumIndex = 1;
        int totalGifts = 0;
        int totalVehicles = 0;
        HashMap<Integer, String> bagsAndTrains = new HashMap<>();
        HashMap<Integer, Integer> capacities = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> possibilities = new HashMap<>();
        HashMap<String, String[]> allPossibilities = new HashMap<>();
        allPossibilities.put("a", new String[] {"RT", "RR", "GR", "GT"});
        allPossibilities.put("b", new String[] {"GR", "GT"});
        allPossibilities.put("c", new String[] {"RT", "RR"});
        allPossibilities.put("d", new String[] {"RT", "GT"});
        allPossibilities.put("e", new String[] {"RR", "GR"});
        allPossibilities.put("ab", new String[] {"GR", "GT"});
        allPossibilities.put("ac", new String[] {"RT", "RR"});
        allPossibilities.put("ad", new String[] {"RT", "GT"});
        allPossibilities.put("ae", new String[] {"RR", "GR"});
        allPossibilities.put("bd", new String[] {"GT"});
        allPossibilities.put("be", new String[] {"GR"});
        allPossibilities.put("cd", new String[] {"RT"});
        allPossibilities.put("ce", new String[] {"RR"});
        allPossibilities.put("abd", new String[] {"GT"});
        allPossibilities.put("abe", new String[] {"GR"});
        allPossibilities.put("acd", new String[] {"RT"});
        allPossibilities.put("ace", new String[] {"RR"});

        
        int noOfGT = in.nextInt();
        totalVehicles += noOfGT;
        for(int i = 0; i < noOfGT; i++)
        {
            int capacity = in.nextInt();
            bagsAndTrains.put(mediumIndex, "GT");
            capacities.put(mediumIndex, capacity);
            mediumIndex++;
        }
        
        int noOfRT = in.nextInt();
        totalVehicles += noOfRT;
        for(int j = 0; j < noOfRT; j++)
        {
            int capacity = in.nextInt();
            bagsAndTrains.put(mediumIndex, "RT");
            capacities.put(mediumIndex, capacity);
            mediumIndex++;
        }

        int noOfGR = in.nextInt();
        totalVehicles += noOfGR;
        for(int k = 0; k < noOfGR; k++)
        {
            int capacity = in.nextInt();
            bagsAndTrains.put(mediumIndex, "GR");
            capacities.put(mediumIndex, capacity);
            mediumIndex++;
        }
        
        int noOfRR = in.nextInt();
        totalVehicles += noOfRR;
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
                possibilities.put(mediumIndex, new ArrayList<Integer>());
                ArrayList<String> pbLst = new ArrayList<String>();
            	Collections.addAll(pbLst, allPossibilities.get(type));
            	for(int d = 1; d <= totalVehicles; d++)
            	{
            		if(pbLst.contains(bagsAndTrains.get(d)))
            		{
            			possibilities.get(mediumIndex).add(d);
            		}
            	}
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
                    possibilities.put(mediumIndex, new ArrayList<Integer>());
                    ArrayList<String> pbLst = new ArrayList<String>();
                	Collections.addAll(pbLst, allPossibilities.get(type));
                	for(int d = 1; d <= totalVehicles; d++)
                	{
                		if(pbLst.contains(bagsAndTrains.get(d)))
                		{
                			possibilities.get(mediumIndex).add(d);
                		}
                	}
                    mediumIndex++;
                }
            }
            
        }
        t = mediumIndex;
        
        withFordFulkerson solve = new withFordFulkerson(bagsAndTrains.size() + 2, s, t);
        int i = 1;
        for(; i <= totalVehicles; i++)
        {
        	solve.addEdge(i, t, capacities.get(i));
        }
        for(; i <= bagsAndTrains.size(); ++i)
        {
        	solve.addEdge(s, i, capacities.get(i));
        	if(bagsAndTrains.get(i).startsWith("a"))
        	{
        		for(int n: possibilities.get(i))
        		{
        			solve.addEdge(i, n, 1);
        		}
        	}
        	else
        	{
        		for(int n: possibilities.get(i))
        		{
        			solve.addEdge(i, n, capacities.get(i));
        		}
        	}
        }
        int notDistibuted = totalGifts - solve.getMaxFlow();
        out.print(notDistibuted);
    }
}

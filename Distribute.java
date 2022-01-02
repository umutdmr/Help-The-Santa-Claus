import static java.lang.Math.min;
import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

public class Distribute {
    
    //defining graph's edges
    public static class Edge{

        public int from;
        public int to;
        public Edge residualEdge;
        public int flow;
        public int capacity;

        public Edge(int from, int to, int capacity){
            
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public int remainingCapacity(){
            
            return capacity - flow;
        }

        public void augmentEdge(int bottleNeck){
            
            flow += bottleNeck;
            residualEdge.flow -= bottleNeck;
        }
    }

    static final int INFINITY = Integer.MAX_VALUE / 2;
    int[] levelArray; //for dinic's algorithm
    int number;
    int source;
    int sink;
    int maxDistribution; //maximum flow
    List<Edge>[] graph; //adjacency list representation of graphs

    //constructor
    @SuppressWarnings("unchecked")
    public Distribute(int number, int source, int sink)
    {

        this.number = number;
        this.source = source;
        this.sink = sink;
        levelArray = new int[number];
        graph = new List[number];

        for(int i = 0; i < number ; i++)
        {

            graph[i] = new ArrayList<Edge>();
        }
    }

    public void addEdge(int from, int to, int capacity){

        Edge edge = new Edge(from, to, capacity);
        Edge residual = new Edge(to, from, 0);
        edge.residualEdge = residual;
        residual.residualEdge = edge;
        graph[from].add(edge);
        graph[to].add(residual); 
    }

    //leveling the graph using bfs
    public boolean isLeveled(){

        Arrays.fill(levelArray, -1);
        levelArray[source] = 0;
        Deque<Integer> queue = new ArrayDeque<>(number);
        queue.offer(source);

        while(!queue.isEmpty()){

            int vertex = queue.poll();

            for(Edge e: graph[vertex]){
                int cap = e.remainingCapacity();

                if(cap > 0 && levelArray[e.to] == -1){

                    levelArray[e.to] = levelArray[vertex] + 1;
                    queue.offer(e.to);
                }
            }
        }

        return levelArray[sink] != -1;
    }

    //to send flows to the sink
    public int sendFlow(int from, int[] next, int flowAmount){

        if(from == sink){
            return flowAmount;
        }

        int noOfEdges = graph[from].size();

        for(; next[from] < noOfEdges; next[from]++){

            Edge e= graph[from].get(next[from]);
            int cap = e.remainingCapacity();

            if(cap > 0 && levelArray[e.to] == levelArray[from] + 1){

                int bottleNeck = sendFlow(e.to, next, min(flowAmount, cap));

                if(bottleNeck > 0){

                    e.augmentEdge(bottleNeck);
                    return bottleNeck;
                }
            }
        }

        return 0;
    }

    //maximum flow 
    public int getMaxDistribution(){

        int[] next = new int[number];

        while(isLeveled()){

            Arrays.fill(next, 0);

            for(int flow = sendFlow(source, next, INFINITY); flow != 0; flow = sendFlow(source, next, INFINITY)){

                maxDistribution += flow;
            }
        }

        return maxDistribution;
    }
}

import static java.lang.Math.min;
import java.util.List;
import java.util.ArrayList;

public class FordFulkersonDfsSolverAdjacencyList {

    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    static final long INF = Long.MAX_VALUE / 2;

    public static class Edge {
        public int from, to;
        public Edge residual;
        public long flow;
        public final long capacity;

        public Edge(int from, int to, long capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            
        }

        public long remainingCapacity() {
            return capacity - flow;
        }

        public void augment(long bottleNeck) {
            flow += bottleNeck;
            residual.flow -= bottleNeck;
        }

    }

    // Inputs: n = number of nodes, s = source, t = sink
    int n, s, t;

    long maxFlow;
    List<Edge>[] graph;

    // 'visited' and 'visitedToken' are variables used for graph sub-routines to
    // track whether a node has been visited or not. In particular, node 'i' was
    // recently visited if visited[i] == visitedToken is true. This is handy
    // because to mark all nodes as unvisited simply increment the visitedToken.
    private int visitedToken = 1;
    private int[] visited;

    // Indicates whether the network flow algorithm has ran. We should not need to
    // run the solver multiple times, because it always yields the same result.
    

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge} method
     * to add edges to
     * the graph.
     *
     * @param n - The number of nodes in the graph including source and sink nodes.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public FordFulkersonDfsSolverAdjacencyList(int n, int s, int t) {
        this.n = n;
        this.s = s;
        this.t = t;
        initializeGraph();
       
        visited = new int[n];
    }

    // Construct an empty graph with n nodes including the source and sink nodes.
    private void initializeGraph() {
        graph = new List[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<Edge>();
    }

    /**
     * Adds a directed edge (and residual edge) to the flow graph.
     *
     * @param from     - The index of the node the directed edge starts at.
     * @param to       - The index of the node the directed edge ends at.
     * @param capacity - The capacity of the edge.
     */
    public void addEdge(int from, int to, long capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity < 0");
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);
        e1.residual = e2;
        e2.residual = e1;
        graph[from].add(e1);
        graph[to].add(e2);
    }

    // Marks node 'i' as visited.
    public void visit(int i) {
        visited[i] = visitedToken;
    }

    // Returns whether or not node 'i' has been visited.
    public boolean visited(int i) {
        return visited[i] == visitedToken;
    }

    // Resets all nodes as unvisited. This is especially useful to do
    // between iterations of finding augmenting paths, O(1)
    public void markAllNodesAsUnvisited() {
        visitedToken++;
    }

    // Returns the maximum flow from the source to the sink.
    public long getMaxFlow() {
        for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
            markAllNodesAsUnvisited();
            maxFlow += f;
        }
        return maxFlow;
    }

    private long dfs(int node, long flow) {
        // At sink node, return augmented path flow.
        if (node == t)
            return flow;

        List<Edge> edges = graph[node];
        visit(node);

        for (Edge edge : edges) {
            long rcap = edge.remainingCapacity();
            if (rcap > 0 && !visited(edge.to)) {
                long bottleNeck = dfs(edge.to, min(flow, rcap));

                // Augment flow with bottle neck value
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {

        int n = 12;
        int s = n - 2;
        int t = n - 1;

        FordFulkersonDfsSolverAdjacencyList solver;
        solver = new FordFulkersonDfsSolverAdjacencyList(n, s, t);

        solver.addEdge(s, 1, 2);
        solver.addEdge(s, 2, 1);
        solver.addEdge(s, 0, 7);

        solver.addEdge(0, 3, 2);
        solver.addEdge(0, 4, 4);

        solver.addEdge(1, 4, 5);
        solver.addEdge(1, 5, 6);

        solver.addEdge(2, 3, 4);
        solver.addEdge(2, 7, 8);

        solver.addEdge(3, 6, 7);
        solver.addEdge(3, 7, 1);

        solver.addEdge(4, 5, 8);
        solver.addEdge(4, 8, 3);

        solver.addEdge(5, 8, 3);

        solver.addEdge(6, t, 1);
        solver.addEdge(7, t, 3);
        solver.addEdge(8, t, 4);
        System.out.print("The max flow is: ");
        System.out.println(solver.getMaxFlow());

    }

}
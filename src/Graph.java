import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by curtis on 2/15/16.
 */
public abstract class Graph {
    private int numVertices;
    private int[] visited, predecessors;

    private Vertex[] vertices;

    public Graph(int N) {
        numVertices = N;

        predecessors = new int[N];
        visited = new int[N];

        vertices = new Vertex[N];
        for(int i = 0; i < N; i++) {
            vertices[i] = new Vertex(i);
        }
    }

    public abstract void connect(int src, int dest, int weight);

    public void reset() {
        for (int i = 0; i < numVertices; i++) {
            vertices[i].setDiscovered(false);
        }
    }

    public boolean isConnected() {
        predecessors[0] = -1;
        return uniqueVisitedNodes(0) == numVertices;
    }

    public String predecessorsToString() {
        String predecessorsString = "";

        for(int i = 0; i < predecessors.length; i++) {
            predecessorsString += String.format(
                "%s%s",
                predecessors[i],
                (i == predecessors.length - 1? "" : " ")
            );
        }

        return predecessorsString;
    }

    public String visitedNodesToString() {
        String vistedNodesString = "";

        for(int i = 0; i < visited.length; i++) {
            vistedNodesString += String.format(
                "%s%s",
                visited[i],
                (i == visited.length - 1? "" : " ")
            );
        }

        return vistedNodesString;
    }

    public List<Edge> getKruskalMST(SortMode sortMode) {
        ArrayList<Edge> mst = new ArrayList<>();

        LinkedList<Edge> edgeStack = new LinkedList<>(getSortedEdges(sortMode));


        int[] parents = new int[numVertices],
              ranks   = new int[numVertices];

        for(int i = 0; i < numVertices; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }

        int includedCount = 0;

        while (edgeStack.size() > 0 && includedCount < numVertices) {
            Edge shortestEdge = edgeStack.removeFirst();

            int u = find(shortestEdge.getSource(), parents),
                v = find(shortestEdge.getDestination(), parents);

            if (u != v) {
                mst.add(shortestEdge);
                includedCount++;
                union(u, v, parents, ranks);
            }
        }

        return mst;
    }

    public List<Edge> getPrimMST() {
        List<Edge> primMst = new ArrayList<>();

        PriorityQueue pq = new PriorityQueue(numVertices);
        boolean[] reached = new boolean[numVertices];

        reached[0] = true;
        for(int v : getAdjacentNodes(0)) {
            pq.insert(new Edge(v, 0, getEdgeWeight(0, v)));
        }

        pq.heapify();

        while(!pq.isEmpty()) {
            Edge min = pq.delMinEdge();

            int u = min.getSource();
            reached[u] = true;

            primMst.add(new Edge(min.getDestination(), min.getSource(), min.getWeight()));

            for(int v : getAdjacentNodes(u)) {
                if(reached[v]) {
                    continue;
                }

                int w = getEdgeWeight(u, v);
                if(pq.containsNode(v)) {
                    pq.update(v, u, w);
                } else {
                    pq.insert(new Edge(v, u, w));
                }
            }

            pq.heapify();
        }

        return primMst;
    }

    private List<Edge> getSortedEdges(SortMode sortMode) {
        SortStrategy sorter = null;

        switch (sortMode) {
            case INSERTION_SORT: sorter = new InsertionSortStrategy(); break;
            case COUNT_SORT: sorter = new CountSortStrategy(numVertices); break;
            case QUICKSORT: sorter = new QuicksortStrategy(); break;
        }

        return sorter.sort(getEdges());
    }

    private int uniqueVisitedNodes(int source) {
        int count = 0;

        vertices[source].setDiscovered(true);
        visited[source] = source;
        count += 1;

        int[] edges = getAdjacentNodes(source);
        Arrays.sort(edges);
        for(int node : edges) {
            if (!vertices[node].isDiscovered()) {
                predecessors[node] = source;
                count += uniqueVisitedNodes(node);
            }
        }

        return count;
    }

    private int find(int v, int[] parents) {
        if (v != parents[v]) {
            parents[v] = find(parents[v], parents);
        }

        return parents[v];
    }

    private void union(int u, int v, int[] parents, int[] ranks) {
        int i = find(u, parents),
            j = find(v, parents);

        if (ranks[i] > ranks[j]) {
            parents[j] = i;
        } else {
            parents[i] = j;
            if (ranks[i] == ranks[j]) {
                ranks[j] += 1;
            }
        }
    }

    protected abstract int getEdgeWeight(int src, int dest);

    protected abstract int getNumberOfEdges();

    protected abstract int[] getAdjacentNodes(int source);

    protected abstract List<Edge> getEdges();
}

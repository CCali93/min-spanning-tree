import java.util.*;

/**
 * Created by Curtis on 2/17/2016.
 */
public class AdjacencyListGraph extends Graph {
    private class VertexPair {
        public int vertexId, weight;

        public VertexPair(int vid, int w) {
            vertexId = vid;
            weight = w;
        }
    }

    private ArrayList<ArrayList<VertexPair>> adjList;

    public AdjacencyListGraph(int N) {
        super(N);

        adjList = new ArrayList<>(N);

        for(int i = 0; i < N; i++) {
            adjList.add(new ArrayList<>(N));
        }
    }

    @Override
    public void connect(int src, int dest, int weight) {
        boolean containsConnection = false;
        ArrayList<VertexPair> adjacencies = adjList.get(src);

        for(VertexPair vp : adjacencies) {
            if (vp.vertexId == dest) {
                containsConnection = true;
                break;
            }
        }

        if(!containsConnection)
            adjacencies.add(new VertexPair(dest, weight));
    }

    @Override
    public void reset() {
        super.reset();

        for (int i = 0; i < adjList.size(); i++) {
            adjList.get(i).clear();
        }
    }

    @Override
    protected int getEdgeWeight(int src, int dest) {
        ArrayList<VertexPair> adjacent = adjList.get(src);

        for(VertexPair vp : adjacent) {
            if (vp.vertexId == dest) {
                return vp.weight;
            }
        }

        return 0;
    }

    @Override
    protected int getNumberOfEdges() {
        int numEdges = 0;

        for (ArrayList<VertexPair> adjacencies : adjList) {
            numEdges += adjacencies.size();
        }

        return numEdges;
    }

    @Override
    protected int[] getAdjacentNodes(int source) {
        ArrayList<VertexPair> adjPairs = adjList.get(source);
        int[] adjacentEdges = new int[adjPairs.size()];

        for (int i = 0; i < adjPairs.size(); i++) {
            adjacentEdges[i] = adjPairs.get(i).vertexId;
        }

        return adjacentEdges;
    }

    @Override
    protected List<Edge> getEdges() {
        Set<Integer> edgeHashSet = new HashSet<>();
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < adjList.size(); i++) {
            ArrayList<VertexPair> adjacencies = adjList.get(i);

            for(VertexPair vp : adjacencies) {
                Edge newEdge = new Edge(i, vp.vertexId, vp.weight);

                if(!edgeHashSet.contains(newEdge.hashCode())) {
                    edges.add(newEdge);
                    edgeHashSet.add(newEdge.hashCode());
                }
            }
        }

        return edges;
    }

    public String toString() {
        String adjListStr = "";

        for(int i = 0; i < adjList.size(); i++) {
            adjListStr += i + "-> ";
            int adjListSize = adjList.get(i).size();

            for (int j = 0; j < adjListSize; j++) {
                VertexPair vp = adjList.get(i).get(j);
                adjListStr += String.format(
                    "%d(%d)%s",
                    vp.vertexId,
                    vp.weight, j == adjListSize - 1 ? "" : " "
                );
            }

            adjListStr += "\n";
        }

        return adjListStr;
    }
}

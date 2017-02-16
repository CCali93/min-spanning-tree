import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Curtis on 2/17/2016.
 */
public class MatrixGraph extends Graph {
    private int[][] edgeMatrix;

    public MatrixGraph(int N) {
        super(N);

        edgeMatrix = new int[N][N];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                edgeMatrix[i][j] = 0;
            }
        }

        reset();
    }

    @Override
    public void connect(int src, int dest, int weight) {
        if(edgeMatrix[src][dest] == 0)
            edgeMatrix[src][dest] = weight;
    }

    @Override
    public void reset() {
        super.reset();

        for (int i = 0; i < edgeMatrix.length; i++) {
            for(int j = 0; j < edgeMatrix[i].length; j++) {
                edgeMatrix[i][j] = 0;
            }
        }
    }

    @Override
    protected int getEdgeWeight(int src, int dest) {
        return edgeMatrix[src][dest];
    }

    @Override
    protected int getNumberOfEdges() {
        int numEdges = 0;

        for(int i = 0; i < edgeMatrix.length; i++) {
            for(int j = 0; j < edgeMatrix[i].length; j++) {
                if (edgeMatrix[i][j] > 0) {
                    numEdges++;
                }
            }
        }

        return numEdges;
    }

    @Override
    protected int[] getAdjacentNodes(int source) {
        int numVisitable = 0;
        int[] edges = edgeMatrix[source];

        for(int i = 0; i < edges.length; i++) {
            if (edges[i] > 0) numVisitable++;
        }

        int[] adjacent = new int[numVisitable];
        int appendIdx = 0;

        for(int i = 0; i < edges.length; i++) {
            if (edges[i] > 0) {
                adjacent[appendIdx++] = i;
            }
        }

        return adjacent;
    }

    @Override
    protected List<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < edgeMatrix.length; i++) {
            int[] adjacencies = edgeMatrix[i];

            for(int j = i; j < adjacencies.length; j++) {
                if (adjacencies[j] > 0) {
                    edges.add(new Edge(i, j, adjacencies[j]));
                }
            }
        }

        return edges;
    }

    public String toString() {
        String matrix = "";

        for(int i = 0; i < edgeMatrix.length; i++) {
            for(int j = 0; j < edgeMatrix[i].length; j++) {
                matrix += (edgeMatrix[i][j] + (j == edgeMatrix[i].length - 1 ? "" : "   "));
            }

            matrix += i == edgeMatrix.length - 1 ? "\n" : "\n\n";
        }

        return matrix;
    }
}

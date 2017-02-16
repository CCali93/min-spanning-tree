import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MST {
    public static void main(String[] args) {
	    // write your code here
        if (args.length < 1) {
            errorMessage("Input file not found");
        } else {
            Scanner scanner = null;

            try {
                scanner = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                errorMessage("Input file not found");
            }

            int N = 0, seed = 0;
            double p = 0;

            try {
                N = scanner.nextInt();

                if (N < 2) {
                    errorMessage("n must be greater than 1");
                }
            } catch(InputMismatchException e) {
                errorMessage("n and seed must be integers");
            }

            try {
                seed = scanner.nextInt();
            } catch(InputMismatchException e) {
                errorMessage("n and seed must be integers");
            }

            try {
                p = scanner.nextDouble();

                if (p < 0.0 || p > 1.0) {
                    errorMessage("p must be between 0 and 1");
                }
            } catch(InputMismatchException e) {
                errorMessage("p must be a real number");
            }

            System.out.printf("\nTEST: n=%d, seed=%d, p=%.1f\n", N, seed, p);

            long startTime = System.currentTimeMillis();

            Graph matrixGraph = new MatrixGraph(N),
                adjListGraph = new AdjacencyListGraph(N);

            Random edgeRand = new Random(seed),
                    weightRand = new Random(2 * seed);

            int range = N;

            while (!matrixGraph.isConnected()) {
                matrixGraph.reset();
                adjListGraph.reset();

                for (int i = 0; i < N; i++) {
                    for (int j = i + 1; j < N; j++) {
                        double r = edgeRand.nextDouble();

                        if (r <= p) {
                            int weight = 1 + weightRand.nextInt(range);

                            matrixGraph.connect(i, j, weight);
                            matrixGraph.connect(j, i, weight);

                            adjListGraph.connect(i, j, weight);
                            adjListGraph.connect(j, i, weight);
                        }
                    }
                }
            }

            System.out.printf(
                "Time to generate the graph: %d milliseconds\n\n",
                System.currentTimeMillis() - startTime
            );

            if (N < 10) {
                System.out.println("The graph as an adjacency matrix:\n");
                System.out.println(matrixGraph);

                System.out.println("The graph as an adjacency list:");
                System.out.println(adjListGraph);

                System.out.println("Depth-First Search:");

                System.out.println("Vertices:");
                System.out.println(matrixGraph.visitedNodesToString());

                System.out.println("Predecessors:");
                System.out.println(matrixGraph.predecessorsToString());
            }

            runKruskal(matrixGraph, "MATRIX", N);
            System.out.println("\n");
            runKruskal(adjListGraph, "LIST", N);
            System.out.println("\n");

            runPrim(matrixGraph, "MATRIX", N);
            System.out.println("\n");
            runPrim(adjListGraph, "LIST", N);
            System.out.println("\n");
        }
    }

    private static void errorMessage(String msg) {
        System.out.println(msg);
        System.exit(1);
    }

    private static void runKruskal(Graph g, String graphType, int N) {
        int i = 0;

        for (SortMode mode: SortMode.values()) {
            System.out.println("===================================");
            System.out.printf("KRUSKAL WITH %s USING %s\n", graphType, mode.getName());

            long startTime = System.currentTimeMillis();
            List<Edge> mst = g.getKruskalMST(mode);
            long endTime = System.currentTimeMillis();

            int weightSum = 0;

            for (Edge e : mst) {
                weightSum += e.getWeight();

                if (N < 10) {
                    System.out.println(e);
                }
            }


            System.out.printf("\nTotal weight of MST using Kruskal: %d\n", weightSum);
            System.out.printf(
                "Runtime: %d milliseconds%s",
                endTime - startTime,
                i == SortMode.values().length - 1 ? "" : "\n\n"
            );

            i++;
        }
    }

    private static void runPrim(Graph g, String graphType, int N) {
        System.out.println("===================================");
        System.out.printf("PRIM WITH ADJACENCY %s\n", graphType);

        long startTime = System.currentTimeMillis();
        List<Edge> mst = g.getPrimMST();
        long endTime = System.currentTimeMillis();

        int weightSum = 0;

        for (Edge e : mst) {
            weightSum += e.getWeight();

            if (N < 10) {
                System.out.println(e);
            }
        }


        System.out.printf("\nTotal weight of MST using Prim: %d\n", weightSum);
        System.out.printf("Runtime: %d milliseconds", endTime - startTime);
    }
}

import java.util.Comparator;

/**
 * Created by curtis on 3/7/16.
 */
public class Edge implements Comparable<Edge> {
    private int source, destination, weight;

    public Edge(int src, int dest, int w) {
        source = src;
        destination = dest;
        weight = w;
    }

    public int getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }

    public int getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Edge)) {
            return false;
        }

        Edge o = (Edge) other;

        return this.hashCode() == o.hashCode();
    }

    public int hashCode() {
        return source >= destination ?
            source * source + destination : source + destination * destination;
    }

    @Override
    public int compareTo(Edge o) {
        if (weight == o.weight) {
            return source - o.source;
        }

        return weight - o.weight;
    }

    public void setDestination(int newDest) {
        destination = newDest;
    }

    public void setSource(int newSource) {
        source = newSource;
    }

    public void setWeight(int newWeight) {
        weight = newWeight;
    }

    public String toString() {
        return String.format("%d %d weight = %d", source, destination, weight);
    }
}

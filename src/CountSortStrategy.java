import java.util.ArrayList;
import java.util.List;

/**
 * Created by Curtis on 3/7/2016.
 */
public class CountSortStrategy implements SortStrategy {
    private int maxWeight;

    public CountSortStrategy(int max) {
        maxWeight = max;
    }

    @Override
    public List<Edge> sort(List<Edge> edges) {
        if (edges.size() < 1) {
            return edges;
        }

        List<Edge> output = new ArrayList<>(edges.size());
        for(int i = 0; i < edges.size(); i++) {
            output.add(null);
        }

        int[] count = new int[maxWeight + 1];
        for (Edge e : edges) {
            count[key(e)] += 1;
        }

        int total = 0;
        for(int i = 0; i < maxWeight + 1; i++) {
            int oldCount = count[i];
            count[i] = total;
            total += oldCount;
        }

        for (Edge e : edges) {
            output.set(count[key(e)], e);
            count[key(e)] += 1;
        }

        return output;
    }

    private int key(Edge e) {
        return e.getWeight();
    }
}

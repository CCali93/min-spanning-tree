import java.util.ArrayList;
import java.util.List;

/**
 * Created by Curtis on 3/7/2016.
 */
public class QuicksortStrategy implements SortStrategy {
    @Override
    public List<Edge> sort(List<Edge> edges) {
        if (edges.size() <= 1) {
            return edges;
        }

        Edge pivot = edges.get(0);

        List<Edge> lesserWeights = new ArrayList<>(),
            equalWeights = new ArrayList<>(),
            greaterWeights = new ArrayList<>();

        for (Edge e : edges) {
            if (e.compareTo(pivot) < 0) {
                lesserWeights.add(e);
            } else if (e.compareTo(pivot) == 0) {
                equalWeights.add(e);
            } else {
                greaterWeights.add(e);
            }
        }

        return concat(sort(lesserWeights), equalWeights, sort(greaterWeights));
    }

    private List<Edge> concat(List<Edge> a, List<Edge> b, List<Edge> c) {
        List<Edge> result = new ArrayList<>();

        for (Edge e : a) {
            result.add(e);
        }

        for (Edge e : b) {
            result.add(e);
        }

        for (Edge e : c) {
            result.add(e);
        }

        return result;
    }
}

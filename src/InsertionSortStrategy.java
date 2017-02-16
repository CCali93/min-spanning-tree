import java.util.List;

/**
 * Created by Curtis on 3/7/2016.
 */
public class InsertionSortStrategy implements SortStrategy {
    @Override
    public List<Edge> sort(List<Edge> edges) {
        for(int i = 1; i < edges.size(); i++) {
            for(int j = i; j > 0 && edges.get(j - 1).compareTo(edges.get(j)) > 0; j--) {
                Edge tmp = edges.get(j);
                edges.set(j, edges.get(j - 1));
                edges.set(j - 1, tmp);
            }
        }

        return edges;
    }
}

/**
 * Created by curtis on 3/7/16.
 */
public enum SortMode {
    INSERTION_SORT("INSERTION SORT"),
    COUNT_SORT("COUNT SORT"),
    QUICKSORT("QUICKSORT");

    private String name;
    private SortMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

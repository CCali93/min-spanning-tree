/**
 * Created by curtis on 2/15/16.
 */
public class Vertex {
    private boolean discovered;
    private int id;

    public Vertex(int id) {
        this.discovered = false;
        this.id = id;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public int getId() {
        return this.id;
    }

    public void setDiscovered(boolean isDiscovered) {
        this.discovered = isDiscovered;
    }
}

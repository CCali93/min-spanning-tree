/**
 * Created by Curtis on 5/10/2016.
 */
public class PriorityQueue {
    private Edge[] heap;
    private int[] qp;
    private int size, capacity;

    public PriorityQueue(int N) {
        heap = new Edge[N+1];
        qp = new int[N];
        size = 0;
        capacity = N;

        for(int i = 1; i < N + 1; i++) {
            qp[i - 1] = -1;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsNode(int u) {
        return qp[u] != -1;
    }

    public Edge delMinEdge() {
        Edge min = heap[1];

        swap(1, size--);

        sink(1);
        heap[size + 1] = null;

        qp[min.getSource()] = -1;

        return min;
    }

    public void heapify() {
        heapify(1);
    }

    public void insert(Edge e) {
        if(size < capacity) {
            heap[++size] = e;
            sink(size);
        }
    }

    public void update(int u, int v, int w) {
        int location = qp[u];
        Edge edge = heap[location];

        if(w < edge.getWeight()) {
            edge.setWeight(w);
            edge.setDestination(v);

            swim(location);
        }
    }

    private boolean less(int a, int b) {
        return heap[a].getWeight() < heap[b].getWeight();
    }

    private int getVertexIndex(int u) {
        int index = -1;

        for(int i = 1; i <= size; i++) {
            if (heap[i].getSource() == u) {
                index = i;
                break;
            }
        }

        return -1;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return 2 * i;
    }

    private int rightChild(int i) {
        return (2 * i) + 1;
    }

    private void heapify(int i) {
        int l = leftChild(i),
            r = rightChild(i),
            smallest = i;

        if(l <= size && less(l, i)) {
            smallest = l;
        }

        if (r <= size && less(r, i)) {
            smallest = r;
        }

        if (smallest != i) {
            swap(i, smallest);

            heapify(smallest);
        }
    }

    private void sink(int k) {
        while(leftChild(k) <= size) {
            int j = leftChild(k);

            if(j < size && less(j, j+1)) j++;

            if(!less(k, j)) break;

            swap(k, j);

            k = j;
        }
    }

    private void swim(int k) {
        while (k > 1 && less(parent(k), k)) {
            swap(k, k/2);
            k = parent(k);
        }
    }

    private void swap(int a, int b) {
        Edge tmp = heap[a];

        heap[a] = heap[b];
        heap[b] = tmp;

        qp[heap[b].getSource()] = b;
        qp[heap[a].getSource()] = a;
    }
}

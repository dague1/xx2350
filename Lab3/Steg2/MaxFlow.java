import java.sql.SQLOutput;
import java.util.*;

public class MaxFlow {
    Kattio io;
    int v, s, t, e, totFlow;
    Queue<Integer> queue;
    LinkedList<String> edgesToPrint;
    LinkedList<Integer> neighbours;
    ArrayList<LinkedList> edges;
    int f[][];
    int cf[][];
    int prev[];
    boolean visited[];
    int currentNode;


    public boolean bfs() {
        visited = new boolean[v+1];     // +1 eftersom visited[0] inte används.
        queue = new LinkedList<Integer>();
        prev = new int[v+1];

        queue.add(s);
        visited[s] = true;      // visited is false by default.

        //System.out.println("bfs before while");
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            neighbours = edges.get(currentNode);
            for (int n : neighbours) {
                if (!visited[n] && cf[currentNode][n] > 0) {
                    prev[n] = currentNode;
                    if (n == t) return true;
                    else {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }
        }
        return false;
    }


    public void edmondsKarp() {
        totFlow = 0;
        f = new int[v+1][v+1];
        while (bfs()) {
            int minFlow = Integer.MAX_VALUE;
            int tr = t;
            while (tr != s) {
                if (minFlow > cf[prev[tr]][tr]) minFlow = cf[prev[tr]][tr];
                tr = prev[tr];
            }
            tr = t;
            while (tr != s) {
                f[prev[tr]][tr] += minFlow;
                f[tr][prev[tr]] -= minFlow;
                cf[prev[tr]][tr] -= minFlow;
                cf[tr][prev[tr]] += minFlow;
                tr = prev[tr];
            }
            totFlow += minFlow;
        }
    }


    public void readFlowGraph() {
        v = io.getInt();
        s = io.getInt();
        t = io.getInt();
        e = io.getInt();
        cf = new int[v+1][v+1];
        edges = new ArrayList<>(v+1);

        for (int i = 0; i < v+1; i++) {
            edges.add(new LinkedList<>());
        }

        for (int i = 0; i < e; ++i) {
            int l = io.getInt();
            int r = io.getInt();
            int c = io.getInt();
            if (cf[l][r] == 0 && cf[r][l] == 0) {
                edges.get(l).add(r);
                edges.get(r).add(l);
            }
            cf[l][r] = c;
        }
        //System.out.println("readFlowGraph done");
    }


    public void writeMaxFlowSolution() {
        edgesToPrint = new LinkedList<>();

        edmondsKarp();
        //System.out.println("eds-karp done");
        io.println(v);
        io.println(s + " " + t + " " + totFlow);

        e = 0;
        for (int i = 1; i < edges.size(); i++) {
            neighbours = edges.get(i);
            for (int n : neighbours) {
                if (f[i][n] > 0) {
                    e++;
                    edgesToPrint.add(i + " " + n + " " + f[i][n]);
                }
            }
        }
        io.println(e);
        for (String s : edgesToPrint) io.println(s);
        io.flush();
    }


    MaxFlow() {
        io = new Kattio(System.in, System.out);
        readFlowGraph();
        writeMaxFlowSolution();
        //io.close();
    }


    public static void main(String args[]) {
        new MaxFlow();
    }

}


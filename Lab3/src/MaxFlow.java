import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.io.BufferedReader;
import java.io.StringReader;

public class MaxFlow {
    Kattio io;
    int v, s, t, e, totFlow;
    Queue<Integer> queue;
    LinkedList<String> edgesToPrint;
    LinkedList<Integer> neighbours;
    ArrayList<LinkedList> edges;
    HashMap<String, Integer> cf;
    HashMap<String, Integer> f;
    int prev[];
    boolean visited[];
    int currentNode;


    public boolean bfs() {
        visited = new boolean[v+1];     // +1 eftersom visited[0] inte används.
        queue = new LinkedList<Integer>();
        prev = new int[v+1];        // previous node av nån node

        queue.add(s);
        visited[s] = true;      // visited is false by default.

        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            neighbours = edges.get(currentNode);
            for (int n : neighbours) {
                if (!visited[n] && cf.get(currentNode + " " + n) > 0) {     // Om vi har hittat nån node som är inte besökt och har cf
                    prev[n] = currentNode;      // Ett sätt att spara stigen
                    if (n == t) return true;        // Om vi har nått till sinken
                    else {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }
        }
        return false;       // Ingen stig till sinken har hittats
    }


    public void edmondsKarp() {
        totFlow = 0;

        while (bfs()) {
            int minFlow = Integer.MAX_VALUE;
            int tr = t;
            // Hittar bottleneck i stigen vi har hittat på bfs
            while (tr != s) {
                if (minFlow > cf.get(prev[tr] + " " + tr)) minFlow = cf.get(prev[tr] + " " + tr);
                tr = prev[tr];      // Vi traversar från sinken till källan på stigen
            }
            tr = t;
            // Uppdaterar f och cf på kanterna i stigen vi har hittat på bfs
            while (tr != s) {
                f.put(prev[tr] + " " + tr, f.get(prev[tr] + " " + tr)+minFlow);
                f.put(tr + " " + prev[tr], f.get(tr + " " + prev[tr])-minFlow);
                cf.put(prev[tr] + " " + tr, cf.get(prev[tr] + " " + tr)-minFlow);
                cf.put(tr + " " + prev[tr], cf.get(tr + " " + prev[tr])+minFlow);
                tr = prev[tr];
            }
            totFlow += minFlow;
        }
    }


    public void readFlowGraph(String flowGraph) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(flowGraph));
        // Nyckeln är sträng: node l + mellanslag + node r. Värden är int cf och f på gällande kanten.
        cf = new HashMap<String, Integer>();
        f = new HashMap<String, Integer>();

        String line = br.readLine();
        v = Integer.parseInt(line);
        line = br.readLine();
        String[] st = line.split(" ");      // Andra raden är s t
        s = Integer.parseInt(st[0]);
        t = Integer.parseInt(st[1]);
        e = Integer.parseInt(br.readLine());

        edges = new ArrayList<>(v+1);

        for (int i = 0; i < v+1; i++) {
            edges.add(new LinkedList<>());
        }

        line = br.readLine();
        while (line != null) {      // Läser hela raderna av input sträng flowgrafen
            String[] readEdges = line.split(" ");
            int l = Integer.parseInt(readEdges[0]);
            int r = Integer.parseInt(readEdges[1]);
            int c = Integer.parseInt(readEdges[2]);
            if (!cf.containsKey(l + " " + r) && !cf.containsKey(r + " " + l)) {
                edges.get(l).add(r);
                edges.get(r).add(l);
            }
            cf.put(l + " " + r, c);
            cf.put(r + " " + l, 0);
            f.put(l + " " + r, 0);
            f.put(r + " " + l, 0);
            line = br.readLine();
        }
    }


    // Skriver ut flowlösning av sträng
    public String writeMaxFlowSolution() {
        edgesToPrint = new LinkedList<>();
        StringBuilder maxFlowSolution = new StringBuilder();

        edmondsKarp();

        maxFlowSolution.append(v + "\n");
        maxFlowSolution.append(s + " " + t + " " + totFlow + "\n");

        e = 0;
        // Räknar kanterna som har flöde och sparar dessa kanterna. Kan inte skriva ut dem direkt
        // eftersom vi måste räkna antalet kanterna först
        for (int i = 1; i < edges.size(); i++) {
            neighbours = edges.get(i);
            for (int n : neighbours) {
                if (f.get(i + " " + n) > 0) {       // Ointresserad av kanterna som inte har flöde
                    e++;
                    edgesToPrint.add(i + " " + n + " " + f.get(i + " " + n));
                }
            }
        }
        maxFlowSolution.append(e + "\n");
        for (String s : edgesToPrint) maxFlowSolution.append(s + "\n");
        return maxFlowSolution.toString();
    }


    MaxFlow() {
        //io = new Kattio(System.in, System.out);
        //readFlowGraph();
        //writeMaxFlowSolution();
        //io.close();
    }


    /*public static void main(String args[]) {
       new MaxFlow();
    }*/

}


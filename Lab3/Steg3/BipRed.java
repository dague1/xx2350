import java.io.IOException;
import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
    Kattio io;
    int[] l, r, f;
    int x, y, e;
    int totflow;

    // Oändrad från steg1
    void readBipartiteGraph() {

        x = io.getInt();
        y = io.getInt();
        e = io.getInt();
        l = new int[e];
        r = new int[e];


        for (int i = 0; i < e; ++i) {
            l[i] = io.getInt();
            r[i] = io.getInt();
        }
    }


    public String writeFlowGraph() {
        int v = x+y+2, s = x+y+1, t = x+y+2, c = 1;
        StringBuilder flowGraph = new StringBuilder(v + "\n");


        flowGraph.append(s + " " + t + "\n");
        flowGraph.append(e+x+y + "\n");

        for (int i = 1; i <= x; i++){
            flowGraph.append(s + " " + i + " " + c + "\n");
        }
        for (int i = 0; i < e; ++i) {

            flowGraph.append(l[i] + " " + r[i] + " " + c + "\n");
        }

        for (int i = x+1; i <= v-2; i++){
            flowGraph.append(i + " " + t + " " + c + "\n");
        }
        return flowGraph.toString();
    }



    void readMaxFlowSolution(String maxFlowSolution) throws IOException{
        BufferedReader br = new BufferedReader(new StringReader(maxFlowSolution));
        String line = br.readLine();

        int v = Integer.parseInt(line);
        line = br.readLine();
        String[] stf = line.split(" ");
        int s = Integer.parseInt(stf[0]);
        int t = Integer.parseInt(stf[1]);
        totflow = Integer.parseInt(stf[2]);
        e = Integer.parseInt(br.readLine());

        l = new int[e];
        r = new int[e];
        f = new int[e];

        line = br.readLine();
        int i = 0;
        while (line != null) {
            String[] edges = line.split(" ");
            l[i] = Integer.parseInt(edges[0]);
            r[i] = Integer.parseInt(edges[1]);
            f[i] = Integer.parseInt(edges[2]);
            i++;
            line = br.readLine();
        }
    }


    // Oändrad från steg1
    void writeBipMatchSolution() {
        io.println(x + " " + y);
        io.println(totflow);

        for (int i = 0; i < e; ++i) {
            if (l[i]!=(x+y+1) && r[i]!=(x+y+2)) {
                io.println(l[i] + " " + r[i]);
            }
        }
        io.flush();
    }


    BipRed() {
        io = new Kattio(System.in, System.out);
        MaxFlow maxFlow = new MaxFlow();

        readBipartiteGraph();

        String flowGraph = writeFlowGraph();

        try {maxFlow.readFlowGraph(flowGraph);} catch (Exception e) {
            System.out.println("Exception");
        }

        String maxFlowSolution = maxFlow.writeMaxFlowSolution();

        try {readMaxFlowSolution(maxFlowSolution);} catch (Exception e) {
            System.out.println("Exception");
        }

        writeBipMatchSolution();

        io.close();
    }


    public static void main(String args[]) {
        new BipRed();
    }
}


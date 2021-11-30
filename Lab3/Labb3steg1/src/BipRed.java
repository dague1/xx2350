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


    void writeFlowGraph() {
        int v = x+y+2, s = x+y+1, t = x+y+2, c = 1;


        io.println(v);
        io.println(s + " " + t);
        io.println(e+x+y);
        for (int i = 1; i <= x; i++){
            io.println(s + " " + i + " " + c);
        }
        for (int i = 0; i < e; ++i) {

            io.println(l[i] + " " + r[i] + " " + c);
        }
        for (int i = x+1; i <= v-2; i++){
            io.println(i + " " + t + " " + c);
        }

        io.flush();


    }


    void readMaxFlowSolution() {
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        totflow = io.getInt();
        e = io.getInt();

        l = new int[e];
        r = new int[e];
        f = new int[e];

        for (int i = 0; i < e; ++i) {
            l[i] = io.getInt();
            r[i] = io.getInt();
            f[i] = io.getInt();
        }
    }


    void writeBipMatchSolution() {

        io.println(x + " " + y);
        io.println(totflow);

        for (int i = 0; i < e; ++i) {
            if (l[i]!=(x+y+1) && r[i]!=(x+y+2)) {
                io.println(l[i] + " " + r[i]);
            }
        }
    }

    BipRed() {
        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        writeFlowGraph();

        readMaxFlowSolution();

        writeBipMatchSolution();
        io.close();
    }

    public static void main(String args[]) {
        new BipRed();
    }
}


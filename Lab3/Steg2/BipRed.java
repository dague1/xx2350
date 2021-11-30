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
        // Läs antal hörn och kanter
        x = io.getInt();
        y = io.getInt();
        e = io.getInt();
        l = new int[e];
        r = new int[e];

        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            l[i] = io.getInt();
            r[i] = io.getInt();
        }
    }


    void writeFlowGraph() {
        int v = x+y+2, s = x+y+1, t = x+y+2, c = 1;    //v = x+y +s och t dvs +2

        // Skriv ut antal hörn och kanter samt källa och sänka
        io.println(v);
        io.println(s + " " + t);
        io.println(e+x+y);     //Nya kanter är nu e + (s till l) och (r till t) = e+x+y.
        for (int i = 1; i <= x; i++){
            io.println(s + " " + i + " " + c);
        }
        for (int i = 0; i < e; ++i) {
            // Kant från a till b med kapacitet c
            io.println(l[i] + " " + r[i] + " " + c);
        }
        for (int i = x+1; i <= v-2; i++){
            io.println(i + " " + t + " " + c);
        }
        // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
        io.flush();

        // Debugutskrift
        //System.err.println("Skickade iväg flödesgrafen");
    }


    void readMaxFlowSolution() {
        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        totflow = io.getInt();
        e = io.getInt();

        l = new int[e];
        r = new int[e];
        f = new int[e];

        for (int i = 0; i < e; ++i) {
            // Flöde f från a till b
            l[i] = io.getInt();
            r[i] = io.getInt();
            f[i] = io.getInt();
        }
    }


    void writeBipMatchSolution() {

        // Skriv ut antal hörn och storleken på matchningen
        io.println(x + " " + y);
        io.println(totflow);

        for (int i = 0; i < e; ++i) {
            // Kant mellan a och b ingår i vår matchningslösning
            if (l[i]!=(x+y+1) && r[i]!=(x+y+2)) {
                io.println(l[i] + " " + r[i]);
            }
        }
        //io.flush();
    }

    BipRed() {
        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        writeFlowGraph();
        
        readMaxFlowSolution();

        writeBipMatchSolution();

        // debugutskrift
        //System.err.println("Bipred avslutar\n");

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }

    public static void main(String args[]) {
        new BipRed();
    }
}


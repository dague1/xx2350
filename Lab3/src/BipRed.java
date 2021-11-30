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


    public String writeFlowGraph() {
        int v = x+y+2, s = x+y+1, t = x+y+2, c = 1;    // v = x+y +s och t dvs +2
        StringBuilder flowGraph = new StringBuilder(v + "\n");

        // Skriv ut antal hörn och kanter samt källa och sänka
        flowGraph.append(s + " " + t + "\n");
        flowGraph.append(e+x+y + "\n");     // Nya kanter är nu e + (s till l) och (r till t) = e+x+y.
        // Skriver kanterna från källa till noderna i mängden x
        for (int i = 1; i <= x; i++){
            flowGraph.append(s + " " + i + " " + c + "\n");
        }
        for (int i = 0; i < e; ++i) {
            // Kant från a till b med kapacitet c
            flowGraph.append(l[i] + " " + r[i] + " " + c + "\n");
        }
        // Skriver kanterna från noderna i mängden y till sinken
        for (int i = x+1; i <= v-2; i++){
            flowGraph.append(i + " " + t + " " + c + "\n");
        }
        return flowGraph.toString();
    }


    // Läser sträng av flow-lösning
    void readMaxFlowSolution(String maxFlowSolution) throws IOException{
        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        BufferedReader br = new BufferedReader(new StringReader(maxFlowSolution));
        String line = br.readLine();

        int v = Integer.parseInt(line);
        line = br.readLine();
        String[] stf = line.split(" ");     // Andra raden har s, t, och maxFlow
        int s = Integer.parseInt(stf[0]);
        int t = Integer.parseInt(stf[1]);
        totflow = Integer.parseInt(stf[2]);
        e = Integer.parseInt(br.readLine());

        l = new int[e];
        r = new int[e];
        f = new int[e];

        line = br.readLine();
        int i = 0;
        // Läser alla rader på sträng inputen
        while (line != null) {
            String[] edges = line.split(" ");       // l r f på raderna(a b f)
            // Flöde f från a till b
            l[i] = Integer.parseInt(edges[0]);
            r[i] = Integer.parseInt(edges[1]);
            f[i] = Integer.parseInt(edges[2]);
            i++;
            line = br.readLine();
        }
    }


    // Oändrad från steg1
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
        io.flush();
    }


    BipRed() {
        io = new Kattio(System.in, System.out);
        MaxFlow maxFlow = new MaxFlow();

        readBipartiteGraph();       // Läser input graf data

        String flowGraph = writeFlowGraph();        // Skriver ut flowgraf av sträng

        try {maxFlow.readFlowGraph(flowGraph);} catch (Exception e) {       // Läser flowgraf
            System.out.println("Exception");
        }

        String maxFlowSolution = maxFlow.writeMaxFlowSolution();        // Löser flowgraf

        try {readMaxFlowSolution(maxFlowSolution);} catch (Exception e) {       // Läser lösningen
            System.out.println("Exception");
        }

        writeBipMatchSolution();        // Skriver ut Bip-graf
        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }


    public static void main(String args[]) {
        new BipRed();
    }
}


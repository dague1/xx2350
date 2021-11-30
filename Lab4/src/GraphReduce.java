/**
 * The program takes in a graph with certain values and returns the graph reduced into the cast of a movie.
 *
 * @author David Ljunggren & Shotaro Ishii
 * @version 1.0
 * @since 2020-11-09
 */

public class GraphReduce {
    private int v; // Number of vertices
    private int e; // Number of edges
    private int m; // Max no. of colors
    private int n; // Number of roles
    private int s; // Number of scenes
    private int k; // Number of actors
    private Kattio io; // Kattio instance.
    private int[][] edges; // Edges-matrix
    boolean[] connected; // Element true if vertex corresponding to the index is connected with an edge.
    private String roleStr; // String for roles to be printed.
    private String sceneStr; // String for scenes to be printed.

    /**
     * Constructor
     */
    public GraphReduce() {
        io = new Kattio(System.in, System.out);
        readFirstThreeLines();
        readEdges();
        setScenes();
        setRoles();
        printResult();
        io.flush();
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        GraphReduce graphToCast = new GraphReduce();
    }

    /**
     * Takes in the three first lines of the input graph.
     */
    public void readFirstThreeLines() {
        this.v = io.getInt();
        this.e = io.getInt();
        this.m = io.getInt();

    }

    /**
     * Initiates the edge-matrix and the "connected" boolean-array.
     * Edges are in order of input. Each index consists of the two connected vertices, each on index 0 and 1.
     * The "connected" array's elements are true if the vertices corresponding to its index have been connected.
     * Unconnected nodes are handled in the setScenes() method. O(e)
     */
    public void readEdges() {
        edges = new int[e][2];
        connected = new boolean[v + 4];
        for (int i = 0; i < e; i++) {
            edges[i][0] = io.getInt() + 3;
            edges[i][1] = io.getInt() + 3;
            connected[edges[i][0]] = true;
            connected[edges[i][1]] = true;
        }
    }

    /**
     * Scenes are set using Stringbuilder.
     * In the first for-loop, connected vertices (complete scenes) are appended to scenePrinter.
     * In the second for-loop, unconnected vertices
     * (roles which are not playing in any scene) are connected to role 1 by default.
     * The global variable sceneStr is written correctly formatted. O(e+v)
     */
    public void setScenes() {
        StringBuilder scenePrinter = new StringBuilder();
        for (int i = 0; i < e; i++) {
            scenePrinter.append("2" + " " + edges[i][0] + " " + edges[i][1] + "\n");
        }
        for (int i = 4; i < this.connected.length; i++) {
            if (!connected[i]) {
                e++;
                scenePrinter.append("2 1 " + i + "\n");
            }
        }
        this.sceneStr = scenePrinter.toString();
    }

    /**
     * Roles are set using Stringbuilder.
     * Number of roles will be number of vertices + 3.
     * Number of scenes will be number of edges + 2
     * Number of actors will be number of vertices + 3 if number of colors > number of vertices,
     * else number of colors + 3. f(n)=k-4 -> O(k)
     */
    public void setRoles() {
        StringBuilder rolePointer = new StringBuilder();
        this.n = v + 3;
        this.s = e + 2;

        if (m > v) {
            this.k = v + 3;
        } else {
            this.k = m + 3;
        }
        for (int i = 3; i <= k - 1; i++) {
            rolePointer.append(i + 1);
            if (i != k) { // The last iteration we do not want to print a blank space. (Might work anyway, but safe>sorry)
                rolePointer.append(" ");
            }
        }
        this.roleStr = k - 3 + " " + rolePointer.toString();
    }

    /**
     * Printing method.
     * Prints number of roles, scenes and actors followed by the two given conditions.
     * The for-loops ensures every actor can play every role except for p1,p2 and p3 since these are in the other gadget. O(v)
     */
    public void printResult() {
        io.println(n);
        io.println(s);
        io.println(k);
        io.println("1 1");  //Role 1 played by p1
        io.println("1 2");  //Role 2 played by p2
        io.println("1 3");  //Role 3 played by p3
        for (int i = 0; i < v; i++) {
            io.println(roleStr);
        }
        io.println("2 1 3"); // Scene 1 played by role 1 and 3
        io.println("2 2 3"); // Scene 2 played by role 2 and 3
        io.println(sceneStr);
    }
}




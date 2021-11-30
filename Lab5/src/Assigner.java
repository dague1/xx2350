import java.util.ArrayList;

public class Assigner {
    private int n;
    private int s;
    private int k;
    private int a;
    private int b;
    private int c;
    private Kattio io;
    private ArrayList<ArrayList<Integer>> roles;
    private ArrayList<ArrayList<Integer>> scenes;
    private ArrayList<Integer> rolePlayedBy;
    private ArrayList<ArrayList<Integer>> actorPlaysRole;
    private boolean[] ununsedActors;
    private boolean[] ununsedSuperActors;
    private ArrayList<ArrayList<Integer>> connectedRoles;
    private int diva1;
    private int diva2;

    public static void main(String[] args) {
        Assigner assigner = new Assigner();
        assigner.testMethod();

    }

    public Assigner()  {
        io = new Kattio(System.in, System.out);
        roles = new ArrayList<>();
        scenes = new ArrayList<>();
        connectedRoles = new ArrayList<>();
        rolePlayedBy = new ArrayList<>();
        actorPlaysRole = new ArrayList<>();
        readInput();
        connectRoles();
        assignDivas();

    }

    public void readInput() {
        this.n = io.getInt();
        this.s = io.getInt();
        this.k = io.getInt();
        this.a = 0;
        ununsedActors = new boolean[k+1];
        ununsedSuperActors = new boolean[n];

        for (int i = 0; i < n + 1; i++)
            roles.add(new ArrayList<>());

        for (int i = 0; i < s + 1; i++)
            scenes.add(new ArrayList<>());

        // Read n roles
        for (int i = 0; i < n; i++) {
            a = io.getInt();
            for (int j = 0; j < a; j++) {
                b = io.getInt();
                if(ununsedActors[b] == false) {
                    ununsedActors[b] = true;
                }
                roles.get(i+1).add(b);

            }
        }
        // Read s scenes
        for (int i = 0; i < s; i++) {
            a = io.getInt();
            for (int j = 0; j < a; j++) {
                c = io.getInt();
                scenes.get(i+1).add(c);
            }
        }
    }



    public void connectRoles() {
        int firstRole;
        int secondRole;
        for (int i = 0; i < n + 1; i++) { // fill up roles again ( this time we will keep track of who plays with who ).
            connectedRoles.add(new ArrayList<>());
        }
        for (int i = 1; i<n+1.; i++) {
            rolePlayedBy.add(0);
        }
        for (int i = 1; i<k+2.; i++) {
            actorPlaysRole.add(new ArrayList<>());
        }

        for (int i = 1; i <= s; i++) {
            for (int j = 0; j < scenes.get(i).size(); j++) {
                firstRole = scenes.get(i).get(j);
                for (int k = 0; k < scenes.get(i).size(); k++) {

                    secondRole = scenes.get(i).get(k);

                    if (j != k && !connectedRoles.get(firstRole).contains(secondRole)) {
                        connectedRoles.get(firstRole).add(secondRole);
                    }
                }
            }
        }
    }

    public void assignDivas() {
        int candidate1;
        int candidate2;
        int breaker1 = 0;
        int breaker2 = 0;
        for (int i = 1; i<connectedRoles.size(); i++) {

            if (connectedRoles.get(i).size() < n - 1 && breaker1 == 0) {
                candidate1 = i;
                this.ununsedActors[candidate1] = false;
                this.diva1 = i;
                breaker1 = 1;
                rolePlayedBy.set(i, 1);
                actorPlaysRole.get(1).add(i);
            }
            else {
                continue;
            }
            for (int j = 0; j < connectedRoles.get(i).size(); j++ ) {
                // if(connectedRoles.get(i).get(j))


                for (int k = 1; k < n + 1 ; k++) {
                    if(k != candidate1 && !connectedRoles.get(i).contains(k) && breaker2 == 0) {
                        candidate2 = k;
                        this.ununsedActors[candidate2] = false; // assign diva 2
                        breaker2 = 1;
                        this.diva2 = k;
                        rolePlayedBy.set(i, 2);
                        actorPlaysRole.get(2).add(k);
                    }
                    else {
                        continue;
                    }
                }

            }

        }


    }

    public void assignActors() {

        for (int i = 1; i < roles.size(); i++) {
            for (int j = 1; j<roles.get(i).size(); j++) {
                if(rolePlayedBy.get(i) == 0) {
                    for(int k = 0; k < actorPlaysRole.size(); k++) {
                        if()

                    }


                }



            }
        }

    }



    public boolean rolesAreInSameScene(int role1, int role2) {
        if (connectedRoles.get(role1).contains(role2)) {
            return true;
        }
        return false;
    }



    public void testMethod() {
        System.out.println(roles.toString());
        System.out.println(scenes.toString());
        for(int i = 0; i<ununsedActors.length; i++) {
            System.out.println(ununsedActors[i]);


        }
        for (int i = 0; i<connectedRoles.size(); i++) {
            System.out.println(connectedRoles.get(i));

        }
        System.out.println("Diva 1 is actor 1 and plays role " + this.diva1 +" and diva 2 is actor 2 and plays role " + this.diva2);

        for(int i = 1; i < k+1; i++) {
            System.out.println("Actor " + i + " plays roles: " + actorPlaysRole.get(i));
        }
    }



}
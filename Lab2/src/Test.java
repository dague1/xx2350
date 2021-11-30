import java.util.*;


public class Test {
    public static void main(String args[]) {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();

        hm.put("1" + " " + "2", 1);
        hm.put("1" + " " + "2", hm.get("1" + " " + "2")+1);
        System.out.println(hm.get("1" + " " + "2"));
    }
}

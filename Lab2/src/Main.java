/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {

    public static List<String> readWordList(BufferedReader input) throws IOException {
        LinkedList<String> list = new LinkedList<String>();
        while (true) {
            String s = input.readLine();
            if (s.equals("#"))
                break;
            list.add(s);
        }
        return list;
    }

    public static void main(String args[]) throws IOException {
        //    long t1 = System.currentTimeMillis();
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));



        List<String> wordList = readWordList(stdin);
        String word;
        while ((word = stdin.readLine()) != null) {
            ClosestWords closestWords = new ClosestWords(word, wordList);

            System.out.print(word + " (" + closestWords.getMinDistance() + ")");
            for (String w : closestWords.getClosestWords()) {

                System.out.print(" " + w);
                System.out.println();
            }
        }


    }
}

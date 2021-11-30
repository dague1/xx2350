/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
    LinkedList<String> closestWords = null;

    int closestDistance = -1;
    String formerWord = "";
    int [][] m = new int [40][40];

    int partDist(String w1, String w2, int w1len, int w2len) {
        int min;

        if (w1len == 0){
            return w2len;
        }
        if (w2len == 0){
            return w1len;
        }

        int matches = findCharMatches(formerWord, w2);
        for (int n = matches + 1; n <= w2len; n++){
            for (int j = 1; j <= w1len; j++){
                min = m [j-1][n-1] + (w1.charAt(j-1) == w2.charAt(n-1) ? 0 : 1);
                min = Math.min(min, m [j-1][n] + 1);
                min = Math.min(min, m [j][n-1] + 1);
                m [j][n] = min;
            }
        }

        formerWord = w2;
        return m[w1len][w2len];
    }

    int distance(String w1, String w2) {
        return partDist(w1, w2, w1.length(), w2.length());
    }

    int findCharMatches(String formerWord, String currentWord) {
        int matches = 0;
        int minLength = Math.min(formerWord.length(), currentWord.length());
        for (int i = 0; i < minLength; i++){
            if (formerWord.charAt(i) == currentWord.charAt(i)){
                matches++;
            }
            else break;
        }
        return matches;
    }

    public ClosestWords(String w, List<String> wordList) {
        for (int i = 0; i < 40; i++) {
                m[i][0] = i;
                m[0][i] = i;
        }

        for (String s : wordList) {
            if (Math.abs(s.length() - w.length()) > closestDistance && closestDistance != -1){
                continue;
            }

            int dist = distance(w, s);


            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords = new LinkedList<String>();
                closestWords.add(s);
                if (dist == 0){
                    break;
                }
            }
            else if (dist == closestDistance)
                closestWords.add(s);
        }
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}

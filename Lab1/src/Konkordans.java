import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Konkordans {
    public static void main(String args[]) {
        File inputFile = new File("/Users/shotaroishii/desktop/codes/adk/labb1/korpus");
        File tokenizedFile = new File("myTest");
        File hashFile = new File("adkHashFile.txt");
        long timeAtStart = System.nanoTime();
        if (args.length == 0) {
            try {
                generateHashIndexFile(tokenizedFile, hashFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return;
        } else if (args.length == 1) {
            ArrayList<Long> wordOffsets;
            try {
                wordOffsets = findInstances(args[0].toLowerCase(), hashFile, tokenizedFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return;
            }
            long timeAtFinish = System.nanoTime();
            System.out.println("Konkordansen tog " + (timeAtFinish - timeAtStart) / 1000000 + " millisekunder");
            System.out.println("Det finns " + wordOffsets.size() + " förekomster av ordet.");
            if (wordOffsets.size() > 25) {
                System.out.println("Vill du se alla förekomster av ordet? (y/n)");
                Scanner scanner = new Scanner(System.in);
                if (!scanner.nextLine().equals("y"))
                    return;
            }
            try {
                printOccurences(inputFile, wordOffsets, args[0].length());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else if (args.length > 1) {
            System.out.println("För många ord, mata endast in ett.");
            return;
        }
    }

    //Metoden som går in i korpus.
    //Tar in korpus, offsets-listan (innehåller ALLA positioner för det sökta ordet i korpus), length längden av det sökta ordet (antal karaktärer).
    //Läser 60 tecken + längden av ordet ur korpus.
    // Börjar läsa på offset-positionen -30.

    public static void printOccurences(File korpus, ArrayList<Long> offsets, int length) throws IOException {
        RandomAccessFile korpusRAF = new RandomAccessFile(korpus, "r"); // kan hoppa i filen jamfort med BufferedReader som maste borja fran borjan(?)
        long korpusLength = korpus.length();

        for (long offset : offsets) {
            byte word[] = new byte[60 + length];
            try {

                long startPos = Math.max(offset - 30, 0);
                korpusRAF.seek(startPos);
                int j = 0;
                for (long i = startPos; i < offset + 30 + length; ++i) {

                    byte c = korpusRAF.readByte();


                    switch (c) {
                        case '\t':
                        case '\n':
                        case '\r':
                            word[j] = ' ';
                            break;
                        default:
                            word[j] = c;
                            break;

                    }
                    j++;
                }
            } catch (Exception e) {

            }
            String testtest = new String(word, "ISO-8859-1");
            System.out.println((testtest));
        }
    }

    public static int findNextHash(Long hashArray[], int index) {
        for(int i = index+1; i < hashArray.length; ++i) {
            if (hashArray[i] != null)
                return i;
        }
        return index;
    }


    public static ArrayList<Long> findInstances(String word, File hashFile, File tokenizedFile) throws IOException {
        long hash;
        char characters[] = word.toCharArray();
        //Räknar ut hashen av ordet
        hash = (long) convertChar(characters[0]) * 900;
        if (characters.length >= 2)
            hash += (long) convertChar(characters[1]) * 30;
        if (characters.length >= 3)
            hash += (long) convertChar(characters[2]);


        BufferedReader hashReader = new BufferedReader(new InputStreamReader(new FileInputStream(hashFile), "ISO-8859-1"));
        String line = hashReader.readLine();
        Long hashArray[] = new Long[27930]; //största hashet 27930 == ööö
        boolean found = false;

        while (line != null) {

            String hashPositions[] = line.split(" ");
            long fileHash = Long.parseLong(hashPositions[0]);

            hashArray[(int) fileHash] = Long.parseLong(hashPositions[1]);

            if (!found && fileHash == hash) {
                found = true;
            }
            line = hashReader.readLine();

        }
        if (!found)
            return new ArrayList<Long>();

        RandomAccessFile tokenizedRAF = new RandomAccessFile(tokenizedFile, "r");

        int nextHash = findNextHash(hashArray, (int)hash);
        boolean wordFound = false;

        long offset = hashArray[(int)hash];
        long nextOffset = hashArray[nextHash];

        while(nextOffset - offset > 1000 && !wordFound) {
            int m = (int)(nextOffset + offset) / 2;
            tokenizedRAF.seek(m);
            if(tokenizedRAF.readLine() != null) {
                String[] wordPostion = tokenizedRAF.readLine().split(" ");
                if(wordPostion[0].compareTo(word) == 0){
                    wordFound = true;
                    break;
                }
                if(wordPostion[0].compareTo(word) < 0){
                    offset = m;
                }
                else {
                    nextOffset = m;
                }
            }
        }

        ArrayList<Long> offsetList = new ArrayList<>();
        BufferedReader tokenizedReader = new BufferedReader(new InputStreamReader(new FileInputStream(tokenizedFile), "ISO-8859-1"));
        tokenizedReader.skip(offset);



        while(true) {
            String tokenLine = tokenizedReader.readLine();
            if(tokenLine == null){
                break;
            }
            String[] wordPostion = tokenLine.split(" ");
            int compare = wordPostion[0].compareTo(word);
            if (compare == 0) {
                offsetList.add(Long.parseLong(wordPostion[1]));
            } else if (compare > 0) {
                break;
            }
        }

        return offsetList;
    }


    public static void generateHashIndexFile(File tokenizedFile, File hashFile) throws IOException {
        BufferedReader tokenizedReader = new BufferedReader(new InputStreamReader(new FileInputStream(tokenizedFile), "ISO-8859-1"));

        FileWriter fileWriter = new FileWriter(hashFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String previousWord = "";

        String line = tokenizedReader.readLine();
        Long offset = 0l;
        Long hash = 0l;

        while (line != null) {

            String word[] = line.split(" ");
            String string;
            if (word[0].length() >= 3) string = word[0].substring(0, 3);
            else string = word[0];

            if (!string.equals(previousWord)) {
                char characters[] = string.toCharArray();
                hash = (long) convertChar(characters[0]) * 900;
                if (characters.length >= 2)
                    hash += (long) convertChar(characters[1]) * 30;
                if (characters.length >= 3)
                    hash += (long) convertChar(characters[2]);

                bufferedWriter.write(hash + " " + offset);
                bufferedWriter.newLine();
                previousWord = string;
            }

            offset += line.length() + 1;
            line = tokenizedReader.readLine();
        }

        tokenizedReader.close();
        bufferedWriter.close();
    }

    static char convertChar(char c) {
        if (c == 'å') {
            return 27;
        } else if (c == 'ä') {
            return 28;
        } else if (c == 'ö') {
            return 29;
        } else {
            return (char) (c - 96);
        }
    }


}

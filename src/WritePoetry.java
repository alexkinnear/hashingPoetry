import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


// key: word
// value: WordFreqInfo Object
public class WritePoetry {
    public String WritePoem(String file, String beginningWord, int lengthOfPoem, boolean print) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("data/"+file));
        StringBuilder sbpoem = new StringBuilder();

        // read through the poem line by line
        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().toLowerCase();
            // RegEx to get rid of special characters
            line = line.replaceAll("[^a-z\\s+]", "");
            sbpoem.append(line).append(" ");
        }
        String poem = sbpoem.toString();
        String[] wordsInPoem = poem.split(" ");
        ArrayList<String> myArr = new ArrayList<>();
        for (String word : wordsInPoem) {
            if (!word.equals("")) {
                myArr.add(word);
            }
        }
        wordsInPoem = myArr.toArray(new String[0]);

        // create Hash table to store WordFreqInfo objects
        HashTable<String, WordFreqInfo> HT = new HashTable<String, WordFreqInfo>();

        // keep track of all unique words in the poem
        ArrayList<String> poemWords = new ArrayList<>();
        // loop through each individual word and make WordFreqInfo object WORD and insert in HashTable HT
        for (int i = 0; i < wordsInPoem.length; i++) {
            // new words
            if (!poemWords.contains(wordsInPoem[i])) {
                WordFreqInfo WORD = new WordFreqInfo(wordsInPoem[i], 0);
                WORD.occurCt+= 1;
                if (i + 1 < wordsInPoem.length) {
                    WORD.followList.add(new WordFreqInfo.Freq(wordsInPoem[i + 1], 1));
                    HT.insert(wordsInPoem[i], WORD);
                }
                else {
                    WORD.followList.add(new WordFreqInfo.Freq(wordsInPoem[0], 1));
                    HT.insert(wordsInPoem[i], WORD);
                }
                poemWords.add(wordsInPoem[i]);
            }
            // repeated word
            else {
                WordFreqInfo repeatWord = HT.find(wordsInPoem[i]);
                repeatWord.occurCt++;

                // update follow list
                if (i + 1 < wordsInPoem.length) {
                    int count = 0;
                    for (WordFreqInfo.Freq followWordObj : repeatWord.followList) {
                        if (followWordObj.follow.equals(wordsInPoem[i+1])) {
                            followWordObj.followCt++;
                        }
                        else {
                            count++;
                        }
                    }
                    if (count == repeatWord.followList.size()) {
                        repeatWord.followList.add(new WordFreqInfo.Freq(wordsInPoem[i+1], 1));
                    }
                }
            }
        }

        // Add new poem words to ArrayList poemRemix
        ArrayList<String> poemRemix = new ArrayList<>();
        poemRemix.add(beginningWord);
        for (int i = 1; i < lengthOfPoem; i++) {
            String lastWord = poemRemix.get(i-1);
            poemRemix.add(getNextWord(lastWord, HT));
        }

        // return the words in the ArrayList as a String
        StringBuilder poemRemixStr = new StringBuilder();
        for (String word : poemRemix) {
            poemRemixStr.append(word).append(" ");
        }
        // Decide whether or not to print to console
        if (print) {
            return poemRemixStr.toString();
        }
        else {
            return "";
        }
    }

    private String getNextWord(String lastWord, HashTable<String, WordFreqInfo> HT) {
        WordFreqInfo currentWord = HT.find(lastWord);
        int runningCount = 0;
        int randomNum = (int) (Math.random() * currentWord.occurCt);
        for (WordFreqInfo.Freq word : currentWord.followList) {
            runningCount += word.followCt;
            if (runningCount >= randomNum) {
                return word.follow;
            }
        }

        return "";
    }


}

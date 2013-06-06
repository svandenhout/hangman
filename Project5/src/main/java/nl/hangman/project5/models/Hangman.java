package nl.hangman.project5.models;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by steven on 5/28/13.
 */
public class Hangman {
    private static final String TAG = "Hangman";

    private String playerName;
    private int wordLength;
    private int tries;

    //
    public static final String[] USER_INPUT_STATES = {
        "already used",
        "invalid input",
        "wrong guess",
        "correct guess"
    };


    private ArrayList<String> wordList = new ArrayList<String>() ;
    private String currentWord;
    private String currentWordState;
    private String usedLetters;
    private String computerMonologue;

    // the constructor takes all of the game settings as arguments
    public Hangman(String playerName, int wordLength, int tries) {
        this.playerName = playerName;
        this.wordLength = wordLength;
        this.tries = tries;

        this.usedLetters = "";
    }

    // returns the current word (the one that will be played with)
    public String getCurrentWord() {
        return this.currentWord;
    }

    public String getCurrentWordState() {
        return this.currentWordState;
    }

    public String getUsedLetters() {
        return this.usedLetters;
    }

    // initialises an empty currentWordState variable
    public void initEmptyCurrentWordState() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < wordLength; i ++) {
            s.append("_");
        }
        this.currentWordState = s.toString();
    }

    // when called fills the wordlist array
    // needs an inputstream as argument
    public void initList(InputStream is) throws XmlPullParserException, IOException {
        String line;
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(is, "utf8");

        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.TEXT) {
                line = xpp.getText();
                line = line.trim();
                if(!line.equals("")) {
                    this.wordList.add(xpp.getText().toLowerCase());
                }
            }
            eventType = xpp.next();
        }
        this.wordList.trimToSize();
    }

    // uses Pseudorandomness to pick and set the current word from the wordlist
    public void chooseRandomWord() {
        Random random = new Random();
        int number = random.nextInt(this.wordList.size());
        this.currentWord = this.wordList.get(number);
    }

    // checks for the validaty of the entered character, the returnvalue refers to a
    // USER_INPUT_STATE
    public String doUserInput(int key) {
        StringBuilder s = new StringBuilder();
        char c = (char) key;

        // check if input is actually a-z (only lowercase right now)
        if(key < 96 && key > 123) {
            // invalid input
            return USER_INPUT_STATES[1];
        }

        if(this.usedLetters.indexOf(key) == -1) {

            s.append(this.usedLetters);
            s.append(c);
            this.usedLetters = s.toString();

            char[] ca = this.currentWordState.toCharArray();
            int i = 0;
            while(true) if (this.currentWord.indexOf(key, i) != -1) {
                i = this.currentWord.indexOf(key, i);
                ca[i] = c;
                i++;
            }else {
                break;
            }

            // if i is still 0 that means it hasn't incremented inside the while loop
            // that means a correct match has not been found
            if(i == 0) {
                // wrong guess
                return USER_INPUT_STATES[2];
            }else {
                this.currentWordState = new String(ca);
                // correct guess
                return USER_INPUT_STATES[3];
            }
        }else {
            // letter already used
            return USER_INPUT_STATES[0];
        }
    }
}
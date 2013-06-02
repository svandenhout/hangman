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
    }

    // returns the current word (the one that will be played with)
    public String getCurrentWord() {
        return currentWord;
    }

    public String getCurrentWordState() {
        return currentWordState;
    }

    // initialises an empty currentWordState variable
    public void initEmptyCurrentWordState() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < wordLength; i ++) {
            s.append("_");
        }
        currentWordState = s.toString();
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
                    wordList.add(xpp.getText());
                    Log.d(TAG,"Text " + xpp.getText());
                }
            }
            eventType = xpp.next();
        }
        wordList.trimToSize();
    }

    // uses Pseudorandomness to pick and set the current word from the wordlist
    public void chooseRandomWord() {
        Random random = new Random();
        int number = random.nextInt(wordList.size());
        currentWord = wordList.get(number);
    }

    // checks for the validaty of the entered character, the returnvalue refers to a
    // USER_INPUT_STATE
    public void doUserInput(int key) {
        // TODO: user string builder .append() to make used letters list

        // check if the letter has been pushed already
        // so a user can not check the same letter twice
        if(this.usedLetters.indexOf(key) != -1) {
            char c = (char) key;
            StringBuilder s = new StringBuilder();
            s.append(this.usedLetters);
            s.append(c);
        }else {
            // ALLREADY PUSHED THE LETTER
        }

        this.usedLetters = s.toString();

        Log.d(TAG, String.format("usedKeys = %s", this.usedLetters));
    }
}
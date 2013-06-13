package nl.hangman.project5.models;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Random;

/**
 * The hangman class covers all of the aspects to actually play the game of hangman,
 *
 * Created by steven on 5/28/13.
 */
public class Hangman {
    private static final String TAG = "Hangman";

    // all of the game states
    public static final int ALREADY_USED = 0;
    public static final int INVALID_INPUT = 1;
    public static final int WRONG_GUESS = 2;
    public static final int CORRECT_GUESS = 3;
    public static final int GAME_WON = 4;
    public static final int GAME_LOST = 5;

    private static final int MIN_UNICODE_INDEX = 96;
    private static final int MAX_UNICODE_INDEX = 123;

    private int wordLength;
    private int wrongGuesses;
    private int wrongGuessesDone;

    private ArrayList<String> wordList = new ArrayList<String>() ;
    private String currentWord;
    private String currentWordState;
    private String usedLetters;

    /*
     * the constructor takes all of the game settings as arguments
     */
    public Hangman(int wordLength, int wrongGuesses) {
        this.wordLength = wordLength;
        this.wrongGuesses = wrongGuesses;
        this.usedLetters = "";
        this.wrongGuessesDone = 0;
    }

    /*
     * returns the current word (the one that will be played with)
     */
    public String getCurrentWord() {
        return this.currentWord;
    }

    public String getCurrentWordState() {
        return this.currentWordState;
    }

    public String getUsedLetters() {
        return this.usedLetters;
    }

    public int getWrongGuessesDone() {
        return this.wrongGuessesDone;
    }

    /*
     * initialises an empty currentWordState variable
     */
    public void initEmptyCurrentWordState() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.wordLength; i ++) {
            s.append("_");
        }

        this.currentWordState = s.toString();
    }

    /*
     * returns XmlPullParser from InputStream
     */
    protected static XmlPullParser parseXml(InputStream is) throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(is, "utf8");
        return xpp;
    }

    /*
     * when called fills the wordlist array
     * needs an inputstream as argument
     */
    public void initList(InputStream is) throws XmlPullParserException, IOException {
        String line;

        XmlPullParser xpp = parseXml(is);
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.TEXT) {
                line = xpp.getText();
                line = line.trim();
                if(!line.equals("") && line.length() == this.wordLength) {
                    this.wordList.add(line.toLowerCase());
                }
            }
            eventType = xpp.next();
        }
        this.wordList.trimToSize();
        is.close();
    }

    /*
     * uses Pseudorandomness to pick and set the current word from the wordlist
     */
    public void chooseRandomWord() {
        Random random = new Random();
        int number = random.nextInt(this.wordList.size());
        this.currentWord = this.wordList.get(number);
    }

    /*
     * checks for the validaty of the entered character
     */
    public int doUserInput(int key) {
        StringBuilder s = new StringBuilder();
        char c = (char) key;

        if(this.usedLetters.indexOf(key) == -1) {

            s.append(this.usedLetters);
            s.append(c);
            this.usedLetters = s.toString();

            char[] ca = this.currentWordState.toCharArray();
            int i = 0;
            while(true) {
                if (this.currentWord.indexOf(key, i) != -1) {
                    i = this.currentWord.indexOf(key, i);
                    ca[i] = c;
                    i++;
                }else {
                    break;
                }
            }

            /*
             * if i is still 0 that means it hasn't incremented inside the while loop
             * that means a correct match has not been found
             */
            if(i == 0) {
                // wrong guess
                this.wrongGuessesDone++;
                // when true the game is lost
                if(this.wrongGuessesDone == this.wrongGuesses) {
                    return GAME_LOST;
                // check if input is actually a-z (only lowercase right now) (it's always a wrong
                // guess)
                }else if(key < MIN_UNICODE_INDEX && key > MAX_UNICODE_INDEX){
                    return INVALID_INPUT;
                }else {
                    return WRONG_GUESS;
                }
            }else {
                this.currentWordState = new String(ca);
                // when true the game is won
                if(this.currentWordState.equals(this.currentWord)) {
                    return GAME_WON;
                }else {
                    return CORRECT_GUESS;
                }
            }
        }else {
            return ALREADY_USED;
        }
    }

    public void resetValues() {
        this.usedLetters = "";
        this.wrongGuessesDone = 0;
    }
}
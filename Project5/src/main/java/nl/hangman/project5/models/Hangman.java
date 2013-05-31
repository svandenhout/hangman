package nl.hangman.project5.models;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

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


    public ArrayList<String> wordList = new ArrayList<String>() ;
    private String currentWord;
    private String currentWordState;
    private String usedLetters;
    private String computerMonologue;

    public Hangman() {
        // do constructor stuff
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
}
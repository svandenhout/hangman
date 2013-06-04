package nl.hangman.project5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import nl.hangman.project5.models.Hangman;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    Hangman hangman;

    String userState;
    String currentWord;
    String currentWordState;
    String usedLetters;

    TextView currentWordView;
    TextView currentWordStateView;
    TextView usedLettersView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // my code
        hangman = new Hangman("steven", 4, 10);
        hangman.initEmptyCurrentWordState();

        try {
            hangman.initList(getResources().openRawResource(R.raw.small));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hangman.chooseRandomWord();

        currentWord = hangman.getCurrentWord();
        currentWordState = hangman.getCurrentWordState();

        currentWordView = (TextView) findViewById(R.id.currentWord);
        currentWordStateView = (TextView) findViewById(R.id.currentWordState);
        usedLettersView = (TextView) findViewById(R.id.usedLetters);

        currentWordView.setText(currentWord);
        currentWordStateView.setText(currentWordState);
        usedLettersView.setText(usedLetters);
        // should set on screen keyboard to allways visible TODO: it doesnt though
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int key = event.getUnicodeChar();
        hangman.doUserInput(key);

        currentWordState = hangman.getCurrentWordState();
        currentWordStateView.setText(currentWordState);

        usedLetters = hangman.getUsedLetters();
        usedLettersView.setText(usedLetters);

        return true;
    }
}
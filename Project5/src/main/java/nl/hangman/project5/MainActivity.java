package nl.hangman.project5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import nl.hangman.project5.models.Hangman;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    Hangman hangman;
    InputStream wordList;

    String computerMonologue;
    String currentWord;
    String currentWordState;
    String usedLetters;

    TextView computerMonologueView;
    TextView currentWordView;
    TextView currentWordStateView;
    TextView usedLettersView;

    SharedPreferences preferences;
    String userName;
    int wordLength;
    int amountOfTurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        Intent intent;

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.hiScores:

                return true;
            case R.id.settings:
                intent = new Intent(context, PreferencesActivity.class);
                startActivity(intent);
                return true;
            case R.id.reset:
                resetGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int key = event.getUnicodeChar();
        computerMonologue = hangman.doUserInput(key);
        computerMonologueView.setText(computerMonologue);

        currentWordState = hangman.getCurrentWordState();
        currentWordStateView.setText(currentWordState);

        usedLetters = hangman.getUsedLetters();
        usedLettersView.setText(usedLetters);

        if(computerMonologue.equals(R.string.game_won)) {
            resetGame();
        }

        if(computerMonologue.equals(R.string.game_lost)) {
            resetGame();
        }

        return true;
    }

    private void resetGame() {
        preferences = getSharedPreferences("hangman_preferences", 0);
        userName = preferences.getString("user_name_preference", "Player 1");
        wordLength = preferences.getInt("word_length_preference", 4);
        amountOfTurns = preferences.getInt("incorrect_guesses_preference", 10);

        hangman = new Hangman(userName, wordLength, amountOfTurns);
        hangman.initEmptyCurrentWordState();

        try {
            wordList = getResources().openRawResource(R.raw.words);
            hangman.initList(wordList);
            wordList.close();
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
        computerMonologueView = (TextView) findViewById(R.id.computerDialogue);

        currentWordView.setText(currentWord);
        currentWordStateView.setText(currentWordState);
        usedLettersView.setText(usedLetters);
        computerMonologueView.setText(computerMonologue);
    }
}
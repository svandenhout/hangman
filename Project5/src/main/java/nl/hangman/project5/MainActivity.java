package nl.hangman.project5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import nl.hangman.project5.models.Hangman;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    private Hangman hangman;
    private InputStream wordList;

    private int userInputState;
    private String currentWord;
    private String currentWordState;

    private TextView computerMonologueView;
    TextView currentWordView;
    TextView currentWordStateView;
    TextView usedLettersView;

    SharedPreferences preferences;
    boolean changedPrefs;
    String userName;
    int wordLength;
    int wrongGuesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        changedPrefs = intent.getBooleanExtra("changedPrefs", false);

        if(hangman != null) {
            if(changedPrefs) {
                newGame();
            }
            setupGame();
        }else {
            newGame();
            setupGame();
        }
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
                setupGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int key = event.getUnicodeChar();
        final Context context = this;

        userInputState = hangman.doUserInput(key);
        // computerMonologueView.setText(computerMonologue);

        currentWordStateView.setText(hangman.getCurrentWordState());

        usedLettersView.setText(hangman.getUsedLetters());

        if(userInputState == hangman.GAME_WON) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setMessage("YOU WIN!");
            alertDialogBuilder.setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    setupGame();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        if(userInputState == hangman.GAME_LOST) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setMessage("YOU LOSE!");
            alertDialogBuilder.setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    setupGame();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        return true;
    }

    private void newGame() {
        preferences = getSharedPreferences("hangman_preferences", 0);
        userName = preferences.getString("user_name_preference", "Player 1");
        wordLength = preferences.getInt("word_length_preference", 4);
        wrongGuesses = preferences.getInt("incorrect_guesses_preference", 10);

        hangman = new Hangman(wordLength, wrongGuesses);

        try {
            wordList = getResources().openRawResource(R.raw.words);
            hangman.initList(wordList);
            wordList.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupGame() {
        hangman.resetValues();
        hangman.initEmptyCurrentWordState();
        hangman.chooseRandomWord();

        currentWord = hangman.getCurrentWord();
        currentWordState = hangman.getCurrentWordState();

        currentWordView = (TextView) findViewById(R.id.currentWord);
        currentWordStateView = (TextView) findViewById(R.id.currentWordState);
        usedLettersView = (TextView) findViewById(R.id.usedLetters);
        computerMonologueView = (TextView) findViewById(R.id.computerDialogue);

        currentWordView.setText(currentWord);
        currentWordStateView.setText(currentWordState);
        usedLettersView.setText(hangman.getUsedLetters());
        // computerMonologueView.setText(computerMonologue);
    }

    private void showAlert(String message) {

    }
}
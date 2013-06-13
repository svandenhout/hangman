package nl.hangman.project5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import nl.hangman.project5.models.Hangman;
import nl.hangman.project5.models.HiScores;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    private Hangman hangman;
    private HiScores hiScores;
    private InputStream wordList;

    private int userInputState;
    private String currentWord;
    private String currentWordState;

    private TextView computerMonologueView;
    private TextView currentWordView;
    private TextView currentWordStateView;
    private TextView usedLettersView;

    private SharedPreferences preferences;
    private boolean changedPrefs;
    private String userName;
    private int wordLength;
    private int wrongGuesses;

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

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.hiScores:
                Intent startHiScore = new Intent(context, HiScoreActivity.class);
                startActivity(startHiScore);
                return true;
            case R.id.settings:
                Intent startPreferences = new Intent(context, PreferencesActivity.class);
                startActivity(startPreferences);
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
            hiScores.doNewScore(userName, hangman.getWrongGuessesDone());

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

    @Override
    public void onStop() {
        super.onStop();

        try {
            FileOutputStream fos = openFileOutput("appData", Context.MODE_PRIVATE);
            hiScores.saveHiScores(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newGame() {
        preferences = getSharedPreferences("hangman_preferences", 0);
        userName = preferences.getString("user_name_preference", "Player 1");
        wordLength = preferences.getInt("word_length_preference", 4);
        wrongGuesses = preferences.getInt("incorrect_guesses_preference", 10);

        hangman = new Hangman(wordLength, wrongGuesses);
        hiScores = new HiScores();

        try {
            wordList = getResources().openRawResource(R.raw.words);
            hangman.initList(wordList);
            wordList.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = openFileInput("appData");
            hiScores = new HiScores(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("IO_EXCEPTION", "/////////////////////////////////////////////");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
}
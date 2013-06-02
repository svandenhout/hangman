package nl.hangman.project5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import nl.hangman.project5.models.Hangman;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    Hangman hangman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // my code
        hangman = new Hangman("steven", 5, 10);
        hangman.initEmptyCurrentWordState();
        String state = hangman.getCurrentWordState();
        TextView tx = (TextView) findViewById(R.id.textView);
        tx.setText(state);
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
        return true;
    }

    // happens when i push a button right now........
    public void showState(View view) {
        // loads of exceptions for initList, since i needed both the
        // IOException and the XmlPullParserException
        try {
            hangman.initList(getResources().openRawResource(R.raw.small));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hangman.chooseRandomWord();

        String state = hangman.getCurrentWord();
        TextView tx = (TextView) findViewById(R.id.textView);
        tx.setText(state);
    }
}
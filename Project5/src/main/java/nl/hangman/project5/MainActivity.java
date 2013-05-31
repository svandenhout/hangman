package nl.hangman.project5;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import nl.hangman.project5.models.Hangman;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // should set on screen keyboard to allways visible
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // volgens mij heb ik het redelijk in de vingers deze
    // kan ik gebruiken om een speelklasse op de achtergrond te maken
    public void showState(View view) {
        Hangman hangman = new Hangman();

        // weet ik gelijk hoe exceptions ook alweer werkte
        try {
            hangman.initList(getResources().openRawResource(R.raw.small));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String state = hangman.wordList.get(0);

        TextView tx = (TextView) findViewById(R.id.textView);
        tx.setText(state);
    }
}
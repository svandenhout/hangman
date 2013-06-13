package nl.hangman.project5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import nl.hangman.project5.models.HiScores;
import nl.hangman.project5.models.Score;

/**
 * Created by steven on 6/6/13.
 */
public class HiScoreActivity extends Activity {
    HiScores hiScores;
    List<Score> hiScoreList;
    String[] formattedHiScoreList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_score);

        try {
            FileInputStream fos = openFileInput("appData");
            ObjectInputStream oos = new ObjectInputStream(fos);
            hiScoreList = (List<Score>) oos.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        hiScores = new HiScores(hiScoreList);
        hiScores.sortList();

        int listLength = hiScoreList.size();
        formattedHiScoreList = new String[listLength];
        for(int i = 0; i < listLength; i++) {
            // retrieve the formatted score from the objects
            Log.d("FORMATTED_SCORE", hiScoreList.get(i).getFormattedScore());
            formattedHiScoreList[i] = hiScoreList.get(i).getFormattedScore();
        }

        ListView listview = (ListView) findViewById(R.id.hi_score_list);
        ArrayAdapter adapter = new ArrayAdapter<String>(
                this, R.layout.simple_list_item_1, formattedHiScoreList);
        listview.setAdapter(adapter);
    }
}
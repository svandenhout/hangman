package nl.hangman.project5.models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * HiScores can add a new hiscore to the hiScoreList, sort the list and save it to the local
 * memory of the device
 *
 * Created by steven on 6/7/13.
 */
public class HiScores {
    // using Score objects to fill hi-score array
    Score hiScore;
    private List<Score> hiScoreList;

    //! call when file doesn't exist
    public HiScores() {
        hiScoreList = new ArrayList<Score>();
    }

    //! call when hiScoreList is present
    public HiScores(List<Score> hsl) {
        hiScoreList = hsl;
    }

    //! call when file exists
    public HiScores(FileInputStream fis) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(fis);
        hiScoreList = (List<Score>) ois.readObject();
        ois.close();
    }

    public List<Score> getHiScoreList() {
        return hiScoreList;
    }

    public void doNewScore(String userName, int wrongGuessesDone) {
        hiScore = new Score(userName, wrongGuessesDone);
        hiScoreList.add(hiScore);
    }

    public void sortList() {
        Collections.sort(hiScoreList, new Comparator<Score>() {

            @Override
            public int compare(Score c1, Score c2) {
                int score1 = c1.getScore();
                int score2 = c2.getScore();

                if (score1 == score2)
                    return 0;
                else if (score1 > score2)
                    return 1;
                else
                    return -1;
            }
        });
    }

    public void saveHiScores(FileOutputStream fos) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(hiScoreList);
        oos.close();
    }
}
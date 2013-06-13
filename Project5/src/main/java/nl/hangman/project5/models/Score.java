package nl.hangman.project5.models;

import java.io.Serializable;

/**
 * Created by steven on 6/10/13.
 */
public class Score implements Serializable {
    public int score;
    public String userName;
    public String formattedScore;

    public Score(String userName, int wrongGuessesDone) {
        this.score = wrongGuessesDone;
        this.userName = userName;
        this.formattedScore = String.format("%s -- %d wrong guesses", userName, score);
    }

    public int getScore() {
        return this.score;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFormattedScore() {
        return this.formattedScore;
    }
}
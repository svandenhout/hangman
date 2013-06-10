package nl.hangman.project5.models;

/**
 * Created by steven on 6/7/13.
 */
public class HiScores {
    private static final int ScoreListLength = 10;
    private int maxWrongGuesses;
    private String[] hiScoreNamesList = new String[ScoreListLength];
    private int[] hiScoreScoresList = new int[ScoreListLength];

    public HiScores(int maxWrongGuesses) {

    }

    public void doNewScore(String userName, int wrongGuessesDone) {
        for(int i = 0; i < ScoreListLength; i++) {
            //if(hiScoreScoresList[i] <= )
        }
    }
}
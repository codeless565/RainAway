package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 1/25/2018.
 */

public class Game_Data {
    public final static Game_Data Instance = new Game_Data();

    //Variables
    private float gameTime;
    private float TgameTime;
    private float score;
    private float Ascore;
    private float scoreMultiplier;
    private int enemiesDefeated;
    private int goalPassed;

    //=========================================
    //Setters
    //=========================================

    public void setTgameTime(float tgameTime) {
        TgameTime = tgameTime;
    }

    public void setAscore(float ascore) {
        Ascore = ascore;
    }
    public void setGameTime(float newValue)
    {gameTime = newValue;}

    public void setScore(float newValue)
    {score = newValue;}

    public void setScoreMultiplier (float newValue)
    {scoreMultiplier = newValue;}

    public void setEnemiesDefeated(int newValue)
    {enemiesDefeated = newValue;}

    public void setGoalPassed(int newValue)
    {goalPassed = newValue;}

    //=========================================
    //Getters
    //=========================================

    public float getTgameTime() {
        return TgameTime;
    }

    public float getAscore() {
        return Ascore;
    }

    public float getGameTime ()
    {return gameTime;}

    public float getScore ()
    {return score;}

    public float getScoreMultiplier ()
    {return scoreMultiplier;}

    public int getEnemiesDefeated()
    {return enemiesDefeated;}

    public int getGoalPassed()
    {return goalPassed;}

    //=========================================
    //RESET DATA
    //=========================================
    public void resetData()
    {
        gameTime = 0;
        score = 0;
        enemiesDefeated = 0;
        goalPassed = 0;
    }
}

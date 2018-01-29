package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 1/25/2018.
 */

public class Game_Data {
    public final static Game_Data Instance = new Game_Data();

    //Variables
    private float gameTime;
    private float score;

    //=========================================
    //Setters
    //=========================================
    //Set GameTime of Previous Game played
    public void setGameTime(float newValue)
    {gameTime = newValue;}

    //Set Score of Previous Game played
    public void setScore(float newValue)
    {score = newValue;}

    //=========================================
    //Getters
    //=========================================
    //Get GameTime of Previous Game played
    public float getGameTime ()
    {return gameTime;}

    //Get GameTime of Previous Game played
    public float getScore ()
    {return score;}


    //=========================================
    //RESET DATA
    //=========================================
    public void resetData()
    {//Resets all values to 0
        gameTime = 0;
        score = 0;
    }
}

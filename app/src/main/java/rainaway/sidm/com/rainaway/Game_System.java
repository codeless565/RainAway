package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 12/4/2017.
 */

import android.content.SharedPreferences;
import android.graphics.Canvas;

import android.view.SurfaceView;

public class Game_System{
    //MEMBER VARIABLES
    public final static Game_System Instance = new Game_System();

    enum GameChoice
    {
        NORMAL,
        TIMEATTACK,
        ARCADE,
        TOTAL
    }

    private boolean isPaused = false;

    private GameChoice gameChoice = GameChoice.NORMAL;

    //SharedPreferences
    public final static String SHARED_PREF_ID = "HighScoresFile"; //Game Save File ID
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor sharedPrefEditor = null;

    //FUNCTIONS
    public void setGameChoice(GameChoice _gameChoice)
    {gameChoice = _gameChoice;}

    public GameChoice getGameChoice()
    {return gameChoice;}

    public boolean getIsPaused()
    {return isPaused;}

    public void setIsPaused(boolean _isPaused)
    {isPaused=_isPaused;}

    private Game_System() {

    }

    public void Init(SurfaceView _view)
    {
        StateManager.Instance.AddState(new GameState_Intro());
        StateManager.Instance.AddState(new GameState_Normal());
        StateManager.Instance.AddState(new GameState_TimeAttack());
        StateManager.Instance.AddState(new GameState_Arcade());

        StateManager.Instance.AddState(new GameState_GameOverTime());
        StateManager.Instance.AddState(new GameState_GameOverScore());
    }

    public void Update(float _dt) {

    }

    public void Render(Canvas _canvas) {
    }


    //******************************
    //SharedPRef
    //******************************
    //Save Record
    public void SaveRecord(String _key, float _value)
    {
        SaveEditBegin();
        if(gameChoice == GameChoice.TIMEATTACK)
        {
            if (_value > GetfloatFromSave(_key))
                SetfloatToSave(_key, _value);
        }
        else
        {
            if (_value > GetintFromSave(_key))
                SetintToSave(_key, (int) _value);
        }

        SaveEditEnd();
    }

    public void InitSharedPref()
    {
        sharedPref = Page_MainMenu.Instance.getSharedPreferences(SHARED_PREF_ID, 0);
    }
    public void SetintToSave(String _key, int _value)
    {
        //Only allow if there is an editor
        if(sharedPrefEditor == null)
            return;

        sharedPrefEditor.putInt(_key,_value);
    }
    public int GetintFromSave(String _key)
    {
        //Attempt to get value from _key, if fail, will return default Variable
        return sharedPref.getInt(_key, 0);
    }

    public void SetfloatToSave(String _key, float _value)
    {
        //Only allow if there is an editor
        if(sharedPrefEditor == null)
            return;

        sharedPrefEditor.putFloat(_key,_value);
    }
    public float GetfloatFromSave(String _key)
    {
        //Attempt to get value from _key, if fail, will return default Variable
        return sharedPref.getFloat(_key, 0.f);
    }
    //Begin and End
    public void SaveEditBegin()
    {
        //Safety check, make sure no1 else is doing an edit (eg. have an editor)
        if(sharedPrefEditor != null)
            return;

        //Start Editing
        sharedPrefEditor = sharedPref.edit();

    }
    public void SaveEditEnd()
    {
        //Safety Check, only allow if there is and editor available
        if(sharedPrefEditor == null)
            return;

        sharedPrefEditor.commit();
        sharedPrefEditor = null; // clean up editor so that system will work.
        //eg. Begin > toSave > Commit > delete >cont.
    }
}

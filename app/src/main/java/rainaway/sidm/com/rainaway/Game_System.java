package rainaway.sidm.com.rainaway;
// TODO
// FONT
// AUDIO
// PAUSE
/**
 * Created by 164347E on 12/4/2017.
 */

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

    //FUNCTIONS
    public void setGameChoice(GameChoice _gameChoice)
    {gameChoice=_gameChoice;}

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

        StateManager.Instance.AddState(new GameState_GameOverNormal());
    }

    public void Update(float _dt) {

    }

    public void Render(Canvas _canvas) {
    }
}

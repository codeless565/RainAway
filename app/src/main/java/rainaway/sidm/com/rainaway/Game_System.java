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

    public final static Game_System Instance = new Game_System();
    private boolean isPaused=false;

    private Game_System() {

    }

    public void Init(SurfaceView _view)
    {
        StateManager.Instance.AddState(new MainGameState());
        StateManager.Instance.AddState(new IntroState());
    }



    public void Update(float _dt) {

    }

    public void Render(Canvas _canvas) {
    }

}

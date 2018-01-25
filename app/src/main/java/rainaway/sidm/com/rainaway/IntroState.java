package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Debug;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by 164353M on 1/8/2018.
 */

public class IntroState implements StateBase
{
    private final String Tag = "IntroState";
    private float timer=5.f;
    private Bitmap logo = null;

    @Override
    public String GetName() {
        return "Default";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        //TODO  resource manager
        logo =  BitmapFactory.decodeResource(_view.getResources(), R.drawable.stone);
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
        timer -= _dt;
        if (timer <= 0.0f)
        {
            //we are done change to next state
            if (Game_System.Instance.getGameChoice() == Game_System.GameChoice.NORMAL) {
                Log.d(Tag,"MainGame");
                StateManager.Instance.ChangeState("MainGame");
            }
            else if (Game_System.Instance.getGameChoice() == Game_System.GameChoice.TIMEATTACK){
                Log.d(Tag,"TimeAttackGame");
                StateManager.Instance.ChangeState("TimeAttackGame");
            }
        }

    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.postTranslate(-logo.getWidth()*0.5f, -logo.getHeight()*0.5f);
        transform.postScale(timer,timer);
        transform.postTranslate(_canvas.getWidth()*0.5f,_canvas.getHeight()*0.5f);
        _canvas.drawBitmap(logo,transform,null);
    }
}


package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Debug;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by 164353M on 1/8/2018.
 */

public class GameState_Intro implements StateBase
{
    private final String Tag = "GameState_Intro";
    SurfaceView view;
    Typeface myfont;

    private float timer;
    private Bitmap logo = null;

    @Override
    public String GetName() {
        return "GameState_Intro";
    }

    @Override
    public void CollisionResponse(Entity.ENTITYTYPE type) {
        //No Collisions at all
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        timer = 3.f;
        logo =  BitmapFactory.decodeResource(_view.getResources(), R.drawable.player);

        view = _view;
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void OnExit()
    {
        logo = null;
    }

    @Override
    public void Update(float _dt) {
        timer -= _dt;
        //Delay to the Game to prepare player
        if (timer <= 0.0f)
        {
            //we are done change to next state
            if (Game_System.Instance.getGameChoice() == Game_System.GameChoice.NORMAL) {
                StateManager.Instance.ChangeState("GameNormal");
            }
            else if (Game_System.Instance.getGameChoice() == Game_System.GameChoice.TIMEATTACK){
                StateManager.Instance.ChangeState("GameTimeAttack");
            }
            else if (Game_System.Instance.getGameChoice() == Game_System.GameChoice.ARCADE){
                StateManager.Instance.ChangeState("GameArcade");
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

        //Print "GET READY" on screen
        Paint GameOver = new Paint();
        GameOver.setColor(Color.WHITE);
        GameOver.setTextSize(100);
        GameOver.setTypeface(myfont);
        _canvas.drawText("GET READY!", view.getWidth() * 0.5f - GameOver.getTextSize() * 2.5f, view.getWidth() * 0.4f + GameOver.getTextSize(), GameOver);

    }
}


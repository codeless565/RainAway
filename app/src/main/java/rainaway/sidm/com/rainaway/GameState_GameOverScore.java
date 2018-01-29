package rainaway.sidm.com.rainaway;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

/**
 * Created by 164353M on 1/29/2018.
 */

public class GameState_GameOverScore implements StateBase{

    SurfaceView view;
    private final String Tag = "GameState_GameOverScore";
    private Bitmap logo = null;
    Typeface myfont;

    private float timer; //bounceTime in case user hand is already down

    @Override
    public String GetName()
    { return "GameState_GameOverScore"; }

    @Override
    public void CollisionResponse(Entity.ENTITYTYPE type) {
        //No Collision Responses in GameOver Screens
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        view = _view;
        timer = 1.f;
        logo =  BitmapFactory.decodeResource(_view.getResources(), R.drawable.backgnd);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void OnExit() {
        logo = null;
    }

    @Override
    public void Update(float _dt) {
        timer -= _dt;
        if (timer >= 0.f) //BounceTime for touch
            return;

        //Go back to main menu
        if (TouchManager.Instance.HasTouch())
            Page_Game.Instance.ExitGame();
    }

    @Override
    public void Render(Canvas _canvas) {
        //_canvas.drawBitmap(logo, 0,0,null);

        //Show Game Score
        Paint score = new Paint();
        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(myfont);
        _canvas.drawText("Score: " + String.valueOf((int) Game_Data.Instance.getScore()), view.getWidth() * 0.4f, view.getHeight() * 0.5f, score);

        //Show Message
        Paint multiplier = new Paint();
        multiplier.setColor(Color.WHITE);
        multiplier.setTextSize(60);
        multiplier.setTypeface(myfont);
        _canvas.drawText("Tap screen to return to main menu", view.getWidth() * 0.1f, view.getHeight() * 0.7f, multiplier);

        //Print "GAME OVER" on screen
        Paint GameOver = new Paint();
        GameOver.setColor(Color.WHITE);
        GameOver.setTextSize(100);
        GameOver.setTypeface(myfont);
        _canvas.drawText("GAME OVER", view.getWidth() * 0.5f - GameOver.getTextSize() * 2.5f, view.getWidth() * 0.4f + GameOver.getTextSize(), GameOver);

    }
}

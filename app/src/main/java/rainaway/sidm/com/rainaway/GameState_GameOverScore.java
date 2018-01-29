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
    private final String Tag = "GameState_GameOverScore";
    private float timer;
    private Bitmap logo = null;
    Typeface myfont;
    SurfaceView view;

    @Override
    public String GetName() {
        return "GameState_GameOverScore";
    }

    @Override
    public void CollisionResponse(Entity.ENTITYTYPE type) {

    }

    @Override
    public void OnEnter(SurfaceView _view) {
        view = _view;
        timer = 5.f;
        logo =  BitmapFactory.decodeResource(_view.getResources(), R.drawable.backgnd);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void OnExit() {
        logo = null;
    }

    @Override
    public void Update(float _dt) {
        if (TouchManager.Instance.HasTouch())
            Page_Game.Instance.ExitGame();
    }

    @Override
    public void Render(Canvas _canvas) {
        //_canvas.drawBitmap(logo, 0,0,null);

        Paint score = new Paint();
        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(myfont);
        _canvas.drawText("Score: " + String.valueOf((int) Game_Data.Instance.getScore()), view.getWidth() * 0.4f, view.getHeight() * 0.5f, score);

        Paint multiplier = new Paint();
        multiplier.setColor(Color.WHITE);
        multiplier.setTextSize(60);
        multiplier.setTypeface(myfont);
        _canvas.drawText("Tap screen to return to main menu", view.getWidth() * 0.1f, view.getHeight() * 0.7f, multiplier);
    }
    // logo width and height
}

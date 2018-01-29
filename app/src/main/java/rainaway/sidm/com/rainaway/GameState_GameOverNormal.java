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

public class GameState_GameOverNormal implements StateBase{
    private final String Tag = "GameState_GameOverNormal";
    private float timer;
    private Bitmap logo = null;
    Typeface myfont;
    SurfaceView view;
    private Entity test;

    @Override
    public String GetName() {
        return "GameState_GameOverNormal";
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

    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.postScale(-logo.getWidth()*0.5f, -logo.getHeight()*0.5f);
        _canvas.drawBitmap(logo,transform,null);

        Paint multiplier = new Paint();
        multiplier.setColor(Color.WHITE);
        multiplier.setTextSize(70);
        multiplier.setTypeface(myfont);
        _canvas.drawText("Score: " + String.valueOf((int) Game_Data.Instance.getScore()), view.getWidth() * 0.4f, view.getWidth() * 0.5f, multiplier);
    }
    // logo width and height
    // create gameobject that has collision with touch, and move to Page_Game.Instance.ExitGame();
}

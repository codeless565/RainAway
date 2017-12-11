package rainaway.sidm.com.rainaway;
// TODO
// FONT
// AUDIO
// PAUSE
/**
 * Created by 164347E on 12/4/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import java.util.Random;

public class Game_Normal implements Game_Scene {
    public final static Game_Normal Instance = new Game_Normal(); // Singleton
    private float timer;
    float Score;

    SurfaceView view;
    Entity Player;
    float MovementSpeed = 10.f;
    private boolean isPaused;

    // FONT
    Typeface myfont;

    public boolean getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean _isPaused) {
        isPaused = _isPaused;
    }

    // this is to not allow anyone else to create another game instance
    private Game_Normal() {

    }

    public void Init(SurfaceView _view) {
        EntityManager.Instance.Init(_view);
        SampleBackGround.Create();
        SamplePauseButton.Create();
        view = _view;
        // now can create
        Vector2 PlayerPos = new Vector2(0.5f * _view.getWidth(), 0.1f * _view.getHeight());
        Player = Player.Create(Entity.ENTITYTYPE.ENTITY_PLAYER, PlayerPos, new Vector2(0, 0));
        /********************
         TODO
         - Countdown
         - Init Player Life
         - Init Score to 0
         - Update Score as player keep playing
         *********************/
        Player.Life = 3;
        Score = 0;
        isPaused = false;

        AudioManager.Instance.PlayAudio(R.raw.ssr);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(),"fonts/Gemcut.otf");
    }

    public void Update(float _dt) {
        if (!Game_Normal.Instance.getIsPaused())

        {
            if (Player.Life <= 0)
                return;

            //SAMPLE STUFF DONT DO THIS
            /****************************************
             * OBJECT *
             *****************************************/
            timer += _dt;
            if (timer >= 1.f) {
                Random ranGen = new Random();

                Entity.Create(Entity.ENTITYTYPE.OBSTACLE_ROCK,
                        new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                        new Vector2(0, -view.getHeight() * 0.5f));

                timer = 0.f;
            }

            Score += 10 * _dt;

            /****************************************
             * CONTROLS *
             *****************************************/
            //Start Accelerating towards direction
            if (TouchManager.Instance.HasTouch()) {
                if (TouchManager.Instance.getCurrTouch().x >= view.getWidth() * 0.5f)
                    Player.Dir.x += MovementSpeed * _dt;

                if (TouchManager.Instance.getCurrTouch().x < view.getWidth() * 0.5f)
                    Player.Dir.x -= MovementSpeed * _dt;

                if (Player.Dir.x >= MovementSpeed)
                    Player.Dir.x = MovementSpeed;

                if (Player.Dir.x <= -MovementSpeed)
                    Player.Dir.x = -MovementSpeed;
            } else {
                //Decelerate to 0
                if (Player.Dir.x > 0.f)
                    Player.Dir.x -= MovementSpeed * _dt;
                else if (Player.Dir.x < 0.f)
                    Player.Dir.x += MovementSpeed * _dt;
            }

            //Update Player Position
            Player.Pos.x += Player.Dir.x;
            //Boundary for Player
            if (Player.Pos.x > view.getWidth() - Player.GetRadius()) {
                Player.Pos.x = view.getWidth() - Player.GetRadius();
                Player.Dir.x = 0;
            }
            if (Player.Pos.x < Player.GetRadius()) {
                Player.Pos.x = Player.GetRadius();
                Player.Dir.x = 0;
            }
        }
        /****************************************
         * ENTITY MANAGER *
         *****************************************/
        //Update all the Entity in the List
        EntityManager.Instance.Update(_dt);

        /****************************************
         * TRANSITION *
         *****************************************/

    }

    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        _canvas.drawText("Lives: " + String.valueOf(Player.Life), paint.getTextSize(), paint.getTextSize(), paint);

        Paint score = new Paint();
        score.setColor(Color.BLACK);
        score.setTextSize(60);
        score.setTypeface(myfont);
        _canvas.drawText("Score: " + String.valueOf((int) Score), view.getWidth() * 0.6f, score.getTextSize(), score);

    }

}

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
    private float timer, goalTimer, eTime, ResumeTimer;
    float Score, S_Multiplier;


    SurfaceView view;
    Entity Player;
    float MovementSpeed = 10.f;
    private boolean isPaused;

    // FONT
    Typeface myfont;

    //Game Indicator
    Entity Indicator, Goal;

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
         - Update Score as player keep playing for pickup
         *********************/
        eTime = 0.f;
        goalTimer = 0.f;
        ResumeTimer = 3.5f;

        Player.Life = 1;
        Score = 0;
        S_Multiplier = 1;
        isPaused = false;

        //OBJ
        Indicator = null;
        Goal = null;

        //Audio
        AudioManager.Instance.PlayAudio(R.raw.ssr);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    public void Update(float _dt) {
         if (Player.Life <= 0) // player dies, go to game over screen
             return;

        eTime += _dt; // Elapsed Time / Bounce Time

        /****************************************
         * PAUSE *
         *****************************************/
        if (TouchManager.Instance.HasTouch())
            if (TouchManager.Instance.getCurrTouch().y <= view.getWidth() * 0.3f && eTime >= 0.5f && ResumeTimer < 0.f) {

                if(getIsPaused())
                    ResumeTimer = 3.5f;

                setIsPaused(!getIsPaused());
                eTime = 0.f;
            }

        if (getIsPaused()) {
            return;
        }

        /****************************************
         * RUNNING TIMER *
         *****************************************/
        timer += _dt;
        goalTimer += _dt;
        ResumeTimer -= _dt;

        if (ResumeTimer > 0.f)
            return;

        /****************************************
         * OBJECT * (Spawns only 1 obj at 1 time)
         *****************************************/
        //Goal Spawn
        if (goalTimer >= 10.f)
        {
            Random ranGen = new Random();
            Goal = Entity.Create(Entity.ENTITYTYPE.OBSTACLE_GOAL,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight() * 1.5f),
                    new Vector2(0, -view.getHeight() * 0.5f)); //type, pos, dir

            //Indicator
            Indicator = Entity.Create(Entity.ENTITYTYPE.GHOST_INDICATOR,
                    new Vector2(Goal.GetPosX(), view.getHeight() * 0.9f),
                    new Vector2(0, 0)); //type, pos, dir

            goalTimer = 0.f;
            timer = -1.f;
        }

        //Random Object Spawn
        if (timer >= 1.f) {
            Random ranGen = new Random();
            Entity.Create(Entity.ENTITYTYPE.OBSTACLE_ROCK,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                    new Vector2(0, -view.getHeight() * 0.5f)); //type, pos, dir

            timer = 0.f;
        }

        Score += 10 * _dt * S_Multiplier;

        /****************************************
         * CONTROLS *
         *****************************************/
        //Start Accelerating towards direction
        if (TouchManager.Instance.HasTouch()) {
            if (TouchManager.Instance.getCurrTouch().x >= view.getWidth() * 0.5f)
                Player.Dir.x += MovementSpeed * _dt;

            if (TouchManager.Instance.getCurrTouch().x < view.getWidth() * 0.5f)
                Player.Dir.x -= MovementSpeed * _dt;

            //Lock to not go over speed limit
            if (Player.Dir.x >= MovementSpeed)
                Player.Dir.x = MovementSpeed;

            if (Player.Dir.x <= -MovementSpeed)
                Player.Dir.x = -MovementSpeed;
        }
        else {
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

        /****************************************
         * GAME LOGIC * Goals
         ****************************************/
        if(Goal != null)
            if(Goal.GetPosY() < Goal.GetRadius()) //Player missed the goal
            {
                --Player.Life;
                Goal.SetIsDone(true);
            }

        if(Indicator != null)
            if(Goal.GetPosY() < view.getHeight())
                Indicator.SetIsDone(true);

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

        Paint multiplier = new Paint();
        multiplier.setColor(Color.BLACK);
        multiplier.setTextSize(60);
        multiplier.setTypeface(myfont);
        _canvas.drawText("    X: " + String.valueOf((int) S_Multiplier), view.getWidth() * 0.6f, score.getTextSize() + multiplier.getTextSize(), multiplier);

        if(ResumeTimer >= 0.f)
        {
            Paint Resume = new Paint();
            Resume.setColor(Color.BLACK);
            Resume.setTextSize(100);
            Resume.setTypeface(myfont);
            _canvas.drawText(String.valueOf((int) ResumeTimer), view.getWidth() * 0.5f, view.getWidth() * 0.4f + Resume.getTextSize(), Resume);
        }

        if(isPaused)
        {
            Paint Pause = new Paint();
            Pause.setColor(Color.BLACK);
            Pause.setTextSize(100);
            Pause.setTypeface(myfont);
            _canvas.drawText("Paused" , view.getWidth() * 0.5f - Pause.getTextSize(), view.getWidth() * 0.4f + Pause.getTextSize(), Pause);
        }

        if(Player.Life <= 0.f)
        {
            Paint GameOver = new Paint();
            GameOver.setColor(Color.BLACK);
            GameOver.setTextSize(100);
            GameOver.setTypeface(myfont);
            _canvas.drawText("GAME OVER", view.getWidth() * 0.5f - GameOver.getTextSize() * 2.5f, view.getWidth() * 0.4f + GameOver.getTextSize(), GameOver);
        }

    }

}

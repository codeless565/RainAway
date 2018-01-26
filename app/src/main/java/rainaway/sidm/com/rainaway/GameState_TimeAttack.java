package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Administrator on 18/1/2018.
 */

public class GameState_TimeAttack implements StateBase {
    public final static GameState_TimeAttack Instance = new GameState_TimeAttack();

    private float obstacleSpawnTimer, pauseBounceTime, ResumeTimer;
    private float gameTime;
    SurfaceView view;
    Entity Player;

    private float ClockSec;
    private float ClockMin;

    float MovementSpeed = 10.f;

    // FONT
    Typeface myfont;



    @Override
    public String GetName() {
        return "GameTimeAttack";
    }

    @Override
    public void CollisionResponse(Entity.ENTITYTYPE type) {
        switch (type) {
            case OBSTACLE_ROCK:

        }
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        EntityManager.Instance.Init(_view);
        SampleBackGround.Create();
        view = _view;
        // now can create
        Vector2 PlayerPos = new Vector2(0.5f * _view.getWidth(), 0.1f * _view.getHeight());
        Player = Player.Create(Entity.ENTITYTYPE.ENTITY_PLAYER, PlayerPos, new Vector2(0, 0));
        /********************
         TODO
         - Countdown
         - Update Score as player keep playing for pickup
         *********************/
        gameTime = 0.f;
        pauseBounceTime = 0.f;
        ResumeTimer = 3.5f;

        Player.Life = 1;

        ClockSec=0;
        ClockMin=0;

        //Audio
        AudioManager.Instance.PlayAudio(R.raw.ssr,true);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void OnExit() {
        // TODO
        // Step 1: Write all the delete and clean up functions for all other managers
        // Step 2: Call them here
        EntityManager.Instance.Terminate();
        AudioManager.Instance.StopAllAudio();
    }

    @Override
    public void Update(float _dt) {
        if (Player.Life <= 0) // player dies, go to game over screen
        {//Saves data over to scene_Data
            Game_Data.Instance.setGameTime(gameTime);

            //Go to GameOver Screen
            return;
        }

        /****************************************
         * PAUSE *
         *****************************************/
        pauseBounceTime += _dt; // Elapsed Time / Bounce Time
        if (TouchManager.Instance.HasTouch())
            if (TouchManager.Instance.getCurrTouch().y <= view.getWidth() * 0.3f && pauseBounceTime >= 0.5f && ResumeTimer < 0.f) {

                if(Game_System.Instance.getIsPaused())
                    ResumeTimer = 3.5f;

                // Trigger our pause confirmation
                Game_System.Instance.setIsPaused(!Game_System.Instance.getIsPaused());
                pauseBounceTime = 0.f;
            }

        //Game is Paused, Dont process anything after this
        if (Game_System.Instance.getIsPaused())
            return;

        ResumeTimer -= _dt;
        if (ResumeTimer > 0.f)
            return;

        /****************************************
         * RUNNING TIMER *
         *****************************************/
        gameTime += _dt;
        obstacleSpawnTimer += _dt;
        ResumeTimer -= _dt;
        ClockSec += _dt;


        if (ResumeTimer > 0.f)
            return;

        /****************************************
         * RUNNING Clock *
         *****************************************/
        if (ClockSec>=60.f)
        {
            ++ClockMin;
            ClockSec=0;
        }

        /****************************************
         * OBJECT * (Spawns only 1 obj at 1 time)
         ****************************************/
        //Random Object Spawn
        if (obstacleSpawnTimer >= 1.f) {
            Random ranGen = new Random();
            Entity.Create(Entity.ENTITYTYPE.OBSTACLE_ROCK,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                    new Vector2(0, -view.getHeight() * 0.5f)); //type, pos, dir

            obstacleSpawnTimer = 0.f;
        }

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
         * ENTITY MANAGER *
         *****************************************/
        //Update all the Entity in the List
        EntityManager.Instance.Update(_dt);

        /****************************************
         * TRANSITION *
         *****************************************/
    }
    @Override
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
        _canvas.drawText("ClockMin: " + String.valueOf((int) ClockMin), view.getWidth() * 0.6f, score.getTextSize(), score);

        Paint multiplier = new Paint();
        multiplier.setColor(Color.BLACK);
        multiplier.setTextSize(60);
        multiplier.setTypeface(myfont);
        _canvas.drawText("    ClockSec: " + String.valueOf((int) ClockSec), view.getWidth() * 0.6f, score.getTextSize() + multiplier.getTextSize(), multiplier);

        if(ResumeTimer >= 0.f)
        {
            Paint Resume = new Paint();
            Resume.setColor(Color.BLACK);
            Resume.setTextSize(100);
            Resume.setTypeface(myfont);
            _canvas.drawText(String.valueOf((int) ResumeTimer), view.getWidth() * 0.5f, view.getWidth() * 0.4f + Resume.getTextSize(), Resume);
        }

        if(Game_System.Instance.getIsPaused())
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
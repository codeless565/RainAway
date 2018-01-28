package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by 164347E on 1/26/2018.
 */

public class GameState_Arcade implements StateBase
{
    public final static GameState_Arcade Instance = new GameState_Arcade();
    private float pauseBounceTime, ResumeTimer;
    private float gameTime;
    private float Score, S_Multiplier;
    SurfaceView view;
    Entity Player;

    float movementSpeed;

    // FONT
    Typeface myfont;

    //Object
    private float objectSpeed;
    private float objectSpawnDelay, objectBounceTimer;

    //Buff Timers
    private float buffSpawnDelay, buffBounceTimer;
    private float m_speed;
    private float slowplayerTimer, freezeTimer, shroudTimer;

    @Override
    public String GetName() {
        return "GameArcade";
    }

    @Override
    public void CollisionResponse(Entity.ENTITYTYPE type) {
        switch (type) {
            case OBSTACLE_ROCK:
            {
                --Player.Life;
                S_Multiplier = 1.f;
                break;
            }
            case POWERUP_SLOWDOWN:
            {
                objectSpeed *= 0.9f; //Decrease speed
                objectSpawnDelay *= 1.05f; //increase spawn delay
                break;
            }
            case POWERUP_SLOWSPEED: //slow player move speed by half for period of time
            {
                slowplayerTimer = 5.f;
                movementSpeed = 30.f;
                break;
            }
            case POWERUP_FREEZE: //slow m_speed for awhile
            {
                freezeTimer = 5.f;
                m_speed = 0.5f;
                break;
            }
            case POWERUP_SHROUD: // player invincible for awhile
            {
                shroudTimer = 5.f;
                Player.isShrouded = true;
                break;
            }
            case POWERUP_ADDHP: //Add hp
            {
                ++Player.Life;
                break;
            }
            case POWERUP_ADDMULTIPLIER: //add multiplier
            {
                ++S_Multiplier;
                break;
            }
        }
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        Log.d("GameState_Arcade", "onEnter");

        EntityManager.Instance.Init(_view);
        SampleBackGround.Create();
        view = _view;
        // now can create
        Vector2 PlayerPos = new Vector2(0.5f * _view.getWidth(), 0.1f * _view.getHeight());
        Player = Player.Create(Entity.ENTITYTYPE.ENTITY_PLAYER, PlayerPos, new Vector2(0, 0));
        movementSpeed = 50.f;
        /********************
         TODO
         - Countdown
         - Update Score as player keep playing for pickup
         *********************/
        gameTime = 0.f;
        pauseBounceTime = 0.f;
        ResumeTimer = 3.5f;

        Player.Life = 3;
        Player.isShrouded = false;
        Score = 0;
        S_Multiplier = 1;
        m_speed = 1.f;

        //OBJ
        objectSpawnDelay = 1.5f;
        objectSpeed = 0.3f;

        //Buff timers
        buffSpawnDelay = 5.f;
        buffBounceTimer = 0.f;

        slowplayerTimer = 0.f;
        freezeTimer = 0.f;
        shroudTimer = 0.f;

        //Audio
        AudioManager.Instance.PlayAudio(R.raw.ssr,true);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void OnExit() {
        Log.d("GameState_Arcade", "onExit ");
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
            if (Score > Game_Data.Instance.getAscore())
                Game_Data.Instance.setAscore(Score);
            Game_Data.Instance.setScoreMultiplier(S_Multiplier);

            //Go to GameOverScreen
            return;
        }

        /****************************************
         * PAUSE *
         *****************************************/
        pauseBounceTime += _dt; //Bounce Time
        if (TouchManager.Instance.HasTouch())
            if (TouchManager.Instance.getCurrTouch().y <= view.getWidth() * 0.3f && pauseBounceTime >= 0.5f && ResumeTimer < 0.f) {

                if(Game_System.Instance.getIsPaused())
                    ResumeTimer = 3.5f;

                // Trigger our pause confirmation
                Game_System.Instance.setIsPaused(!Game_System.Instance.getIsPaused());
                pauseBounceTime = 0.f;
            }

        if (Game_System.Instance.getIsPaused())
            return;

        //if not paused but resume time is yet to count finish

        if (ResumeTimer > 0.f)
        {
            ResumeTimer -= _dt;
            return;
        }

        /****************************************
         * RUNNING TIMER *
         *****************************************/
        gameTime += _dt * m_speed;
        buffBounceTimer += _dt * m_speed;
        buffSpawnDelay += 0.01 * _dt * m_speed;
        objectBounceTimer += _dt * m_speed;
        objectSpeed += 0.01 * _dt * m_speed;
        objectSpawnDelay -= 0.01 * _dt * m_speed;

        //Buff Timer
        if (slowplayerTimer >= 0.f)
            slowplayerTimer -= _dt;
        else if (slowplayerTimer < 0.f && movementSpeed != 50.f)
            movementSpeed = 50.f;

        if (freezeTimer >= 0.f)
            freezeTimer -= _dt;
        else if (freezeTimer < 0.f && m_speed < 1.f)
            m_speed = 1.f;

        if (shroudTimer >= 0.f)
            shroudTimer -= _dt;
        else if (shroudTimer < 0.f && Player.isShrouded)
            Player.isShrouded = false;
        /****************************************
         * OBJECT * (Spawns only 1 obj at 1 time)
         *****************************************/
        //Random Object Spawn
        if (objectBounceTimer >= objectSpawnDelay) {
            Random ranGen = new Random();
            Entity.Create(Entity.ENTITYTYPE.OBSTACLE_ROCK,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                    new Vector2(0, -view.getHeight() * objectSpeed)); //type, pos, dir-speed
            objectBounceTimer = 0.f;
        }

        if (buffBounceTimer >= buffSpawnDelay) {
            Random ranGen = new Random(); //random x position

            int randomBuff = ranGen.nextInt(7);
            Entity.ENTITYTYPE buffType = null;

            if (randomBuff == 0)
                buffType = Entity.ENTITYTYPE.POWERUP_SLOWDOWN;
            else if (randomBuff == 1)
                buffType = Entity.ENTITYTYPE.POWERUP_SLOWSPEED;
            else if (randomBuff == 2)
                buffType = Entity.ENTITYTYPE.POWERUP_FREEZE;
            else if (randomBuff == 3)
                buffType = Entity.ENTITYTYPE.POWERUP_SHROUD;
            else if (randomBuff == 4)
                buffType = Entity.ENTITYTYPE.POWERUP_ADDHP;
            else if (randomBuff == 5)
                buffType = Entity.ENTITYTYPE.POWERUP_ADDMULTIPLIER;

            if (buffType != null) {
                Entity.Create(buffType,
                        new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                        new Vector2(0, -view.getHeight() * objectSpeed)); //type, pos, dir-speed
            }
            buffSpawnDelay -= 0.1f;

            buffBounceTimer = 0.f;
        }

        Score += (10 + objectSpeed) * S_Multiplier  * _dt * m_speed;

        /****************************************
         * CONTROLS * not affected Freeze
         *****************************************/
        //Start Accelerating towards direction
        if (TouchManager.Instance.HasTouch()) {
            if (TouchManager.Instance.getCurrTouch().x >= view.getWidth() * 0.5f)
                Player.Dir.x += movementSpeed * _dt;

            if (TouchManager.Instance.getCurrTouch().x < view.getWidth() * 0.5f)
                Player.Dir.x -= movementSpeed * _dt;

            //Lock to not go over speed limit
            if (Player.Dir.x >= movementSpeed)
                Player.Dir.x = movementSpeed;

            if (Player.Dir.x <= -movementSpeed)
                Player.Dir.x = -movementSpeed;
        }
        else {
            //Decelerate to 0
            if (Player.Dir.x > 0.f)
            {
                if (Player.Dir.x - movementSpeed * _dt <= 0.f)
                    Player.Dir.x = 0.f;
                else
                    Player.Dir.x -= movementSpeed * _dt;
            }
            else if (Player.Dir.x < 0.f)
            {
                if (Player.Dir.x + movementSpeed * _dt >= 0.f)
                    Player.Dir.x = 0.f;
                else
                    Player.Dir.x += movementSpeed * _dt;
            }
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
         * ENTITY MANAGER * affect by Freeze
         *****************************************/
        //Update all the Entity in the List
        EntityManager.Instance.Update(_dt * m_speed);

        /****************************************
         * TRANSITION *
         *****************************************/
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);

        //Show Player Life
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        _canvas.drawText("Lives: " + String.valueOf(Player.Life), paint.getTextSize(), paint.getTextSize(), paint);

        //Show Player Score
        Paint score = new Paint();
        score.setColor(Color.BLACK);
        score.setTextSize(60);
        score.setTypeface(myfont);
        _canvas.drawText("Score: " + String.valueOf((int) Score), view.getWidth() * 0.6f, score.getTextSize(), score);

        //Show Multiplier
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

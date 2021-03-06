package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by 164347E on 1/26/2018.
 */

public class GameState_Arcade implements StateBase
{
    public final static GameState_Arcade Instance = new GameState_Arcade();
    SurfaceView view;

    //Player
    Entity Player;       //Player Object
    float movementSpeed; //Player movement Speed

    //Timers
    private float pauseBounceTime, ResumeTimer; //Resumer button timer
    private float gameTime;             //Gameplay timer, affected by Pause
    private float Score, S_Multiplier;  //Score

    // FONT
    Typeface myfont;

    //Object variable
    private float objectSpeed;
    private float objectSpawnDelay, objectBounceTimer;

    //Buff Timers
    private float buffSpawnDelay;   //Interval between each buff spawning
    private float buffBounceTimer;  //Timer for buff spawn
    private float slowplayerTimer, freezeTimer, shroudTimer; //Buff Activation time

    //Simulation/Game speed
    private float m_speed;

    @Override
    public String GetName() {
        return "GameArcade";
    }

    @Override
    public void CollisionResponse(Entity.ENTITYTYPE type) {
        switch (type) {
            case OBSTACLE_ROCK: //Damage Player and Reset Score Multiplier
            {
                --Player.Life;
                S_Multiplier = 1.f;
                break;
            }
            case POWERUP_SLOWDOWN: //Slow down obstacles
            {
                objectSpeed *= 0.8f; //Decrease speed
                objectSpawnDelay *= 1.1f; //increase spawn delay
                break;
            }
            case POWERUP_SLOWSPEED: //slow player move speed by half for period of time
            {
                slowplayerTimer = 5.f;
                movementSpeed = 30.f;
                break;
            }
            case POWERUP_FREEZE: //slow Game speed for awhile
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
            case POWERUP_ADDHP: //Add hp to player
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
        EntityManager.Instance.Init(_view);
        SampleBackGround.Create();
        view = _view;

        //Creates Player for the Game
        Vector2 PlayerPos = new Vector2(0.5f * _view.getWidth(), 0.1f * _view.getHeight());
        Player = Player.Create(Entity.ENTITYTYPE.ENTITY_PLAYER, PlayerPos, new Vector2(0, 0));

        /********************************************
        //Initiallise all variable to start values
        ********************************************/
        //Player Info
        Player.Life = 3;
        Player.isShrouded = false;
        movementSpeed = 50.f;

        //Game Info
        Score = 0;
        S_Multiplier = 1;
        m_speed = 1.f;

        //OBJ Info
        objectSpawnDelay = 1.5f;
        objectSpeed = 0.3f;

        //Timers
        gameTime = 0.f;
        pauseBounceTime = 0.f;
        ResumeTimer = 3.5f;

        //Buff Spawn timers
        buffSpawnDelay = 5.f;
        buffBounceTimer = 0.f;

        //Buff elapse timers
        slowplayerTimer = 0.f;
        freezeTimer = 0.f;
        shroudTimer = 0.f;

        //Audio & Fonts
        AudioManager.Instance.PlayAudio(R.raw.ssr,true);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Terminate();
        AudioManager.Instance.StopAllAudio();
    }

    @Override
    public void Update(float _dt)
    {
        /****************************************
         * GAME OVER *
         *****************************************/
        if (Player.Life <= 0) // player dies, go to game over screen
        {//Saves data over to scene_Data
            Game_Data.Instance.setGameTime(gameTime);
            Game_Data.Instance.setScore(Score);

            //Save Record
            Game_System.Instance.SaveRecord("Arcade", Score);

            //Go to GameOverScreen
            StateManager.Instance.ChangeState("GameState_GameOverScore");
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
         * RUNNING TIMER * Affected by Pause
         *****************************************/
        gameTime += _dt * m_speed;               //Elasped play time
        buffBounceTimer += _dt * m_speed;        //Buff Spawning bounceTime
        buffSpawnDelay  += 0.01 * _dt * m_speed; //Increase delay between spawn as game progresses
        objectBounceTimer += _dt * m_speed;      //Object Spawning bounceTime
        objectSpeed += 0.01 * _dt * m_speed;     //Increase Object's Speed as game progresses
        objectSpawnDelay -= 0.01 * _dt * m_speed;//Increase the rate of object spawning as game progresses

        //Buff Timer
        if (slowplayerTimer >= 0.f) //Slow Debuff is Active
            slowplayerTimer -= _dt;
        else if (slowplayerTimer < 0.f && movementSpeed != 50.f) //Reset the player movement speed back to normal
            movementSpeed = 50.f;

        if (freezeTimer >= 0.f) //Freeze time buff is Active
            freezeTimer -= _dt;
        else if (freezeTimer < 0.f && m_speed < 1.f) // Reset the Game Speed
            m_speed = 1.f;

        if (shroudTimer >= 0.f) //Shroud buff is Active
            shroudTimer -= _dt;
        else if (shroudTimer < 0.f && Player.isShrouded) //Remove the buff off the player
            Player.isShrouded = false;

        /****************************************
         * OBJECT * (Spawns only 1 obj at 1 time)
         *****************************************/
        //Obstacle Spawn
        if (objectBounceTimer >= objectSpawnDelay)
        {
            Random ranGen = new Random();
            Entity.Create(Entity.ENTITYTYPE.OBSTACLE_ROCK,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                    new Vector2(0, -view.getHeight() * objectSpeed)); //type, pos, dir-speed
            objectBounceTimer = 0.f; //reset timer
        }

        //Random buff Spawn
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
            else
                buffSpawnDelay -= 0.1f; //reduce spawnDelay if nothing is spawned

            buffBounceTimer = 0.f; //reset timer
        }

        Score += (10 + objectSpeed) * S_Multiplier  * _dt * m_speed; //Score per second x Score Multiplier x deltaTime x game speed

        /****************************************
         * CONTROLS * not affected Freeze
         *****************************************/
        //Start Accelerating towards direction
        if (TouchManager.Instance.HasTouch()) {
            //Speed up towards Right
            if (TouchManager.Instance.getCurrTouch().x >= view.getWidth() * 0.5f)
                Player.Dir.x += movementSpeed * _dt;
            //Speed up towards Left
            if (TouchManager.Instance.getCurrTouch().x < view.getWidth() * 0.5f)
                Player.Dir.x -= movementSpeed * _dt;

            //Speed Limit - so player cannot go over speed limit of 50
            if (Player.Dir.x >= movementSpeed)
                Player.Dir.x = movementSpeed;
            if (Player.Dir.x <= -movementSpeed)
                Player.Dir.x = -movementSpeed;
        }
        else {
            //Decelerate to 0 when there is no touch
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

        //Boundary for Player - so player cannot go out of bound
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
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);

        //Show Player Life
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        _canvas.drawText("Lives: " + String.valueOf(Player.Life), paint.getTextSize(), paint.getTextSize(), paint);

        //Show Powerup indicator
        Paint powerup = new Paint();
        powerup.setColor(Color.BLACK);
        powerup.setTextSize(60);
        powerup.setTypeface(myfont);
        if(freezeTimer > 0.f)
            _canvas.drawText("Freeze active", powerup.getTextSize(), powerup.getTextSize() + powerup.getTextSize(), powerup);
        else if (slowplayerTimer > 0.f)
            _canvas.drawText("Movement Slowed", powerup.getTextSize(), powerup.getTextSize() + powerup.getTextSize(), powerup);
        else if (shroudTimer > 0.f)
            _canvas.drawText("Shroud active", powerup.getTextSize(), powerup.getTextSize() + powerup.getTextSize(), powerup);

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

        //Show Resume Timer
        if(ResumeTimer >= 0.f)
        {
            Paint Resume = new Paint();
            Resume.setColor(Color.BLACK);
            Resume.setTextSize(100);
            Resume.setTypeface(myfont);
            _canvas.drawText(String.valueOf((int) ResumeTimer), view.getWidth() * 0.5f, view.getWidth() * 0.4f + Resume.getTextSize(), Resume);
        }

        //Print "PAUSED" on screen
        if(Game_System.Instance.getIsPaused())
        {
            Paint Pause = new Paint();
            Pause.setColor(Color.BLACK);
            Pause.setTextSize(100);
            Pause.setTypeface(myfont);
            _canvas.drawText("Paused" , view.getWidth() * 0.5f - Pause.getTextSize(), view.getWidth() * 0.4f + Pause.getTextSize(), Pause);
        }

        //Print "GAME OVER" on screen
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

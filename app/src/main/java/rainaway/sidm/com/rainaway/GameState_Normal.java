package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import java.util.Random;

public class GameState_Normal extends Activity implements StateBase
{
    public final static GameState_Normal Instance = new GameState_Normal();
    SurfaceView view;

    private float objectSpawnDelay, objectBounceTimer, objectSpeed;
    private float pauseBounceTime;
    private float ResumeTimer;
    private float gameTime;
    private float Score, S_Multiplier;

    //Player Info
    Entity Player;
    private float movementSpeed;

    // FONT
    Typeface myfont;

    //Goal & Goal Indicator
    Entity Indicator, Goal;
    private float goalTimer;

    @Override
    public String GetName() {
        return "GameNormal";
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
            case OBSTACLE_GOAL:
            {
                Score += 100;
                S_Multiplier += 1.f;
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
        movementSpeed = 20.f;

        //Game Info
        Score = 0;
        S_Multiplier = 1; //Score Multiplier

        //OBJ Info
        objectSpawnDelay = 1.f; //Delay between Spawn
        objectBounceTimer = 0.f; //Elasped Time for object Spawn
        objectSpeed = 0.5f;

        //Timers
        gameTime = 0.f;
        pauseBounceTime = 0.f;
        ResumeTimer = 3.5f;

        //OBJ
        Indicator = null;
        Goal = null;

        //Audio
        AudioManager.Instance.PlayAudio(R.raw.ssr,true);
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Terminate();
        AudioManager.Instance.StopAllAudio();
    }

    @Override
    public void Update(float _dt) {
        /****************************************
         * GAME OVER *
         *****************************************/
        if (Player.Life <= 0) // player dies, go to game over screen
        {
            //Saves data over to scene_Data
            Game_Data.Instance.setScore(Score);
            Game_Data.Instance.setGameTime(gameTime);

            //Save Record
            Game_System.Instance.SaveRecord("Normal", Score);

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

        ResumeTimer -= _dt; //Countdown resumes when it is unpaused
        if (ResumeTimer > 0.f)
            return;

        /****************************************
         * RUNNING TIMER *
         *****************************************/
        gameTime += _dt;
        objectBounceTimer += _dt;
        goalTimer += _dt;

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
                    new Vector2(0, -view.getHeight() * objectSpeed)); //type, pos, dir

            //Indicator
            Indicator = Entity.Create(Entity.ENTITYTYPE.GHOST_INDICATOR,
                    new Vector2(Goal.GetPosX(), view.getHeight() * 0.9f),
                    new Vector2(0, 0)); //type, pos, dir

            goalTimer = 0.f;
            objectBounceTimer= -1.f;
        }

        //Random Object Spawn
        if (objectBounceTimer >= objectSpawnDelay) {
            Random ranGen = new Random();
            Entity.Create(Entity.ENTITYTYPE.OBSTACLE_ROCK,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                    new Vector2(0, -view.getHeight() * objectSpeed)); //type, pos, dir

            objectBounceTimer = 0.f;
        }

        Score += 10 * _dt * S_Multiplier; //Score per second x deltaTime x Score multiplier

        /****************************************
         * CONTROLS *
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

        //Boundary for Player - so player cannot move out of bound
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
            {//player loses a life if he misses a goal
                --Player.Life;
                Goal.startVibrate();
                Goal.SetIsDone(true);
            }
        //Remove indicator once goal is on screen
        if(Indicator != null)
            if(Goal.GetPosY() < view.getHeight())
                Indicator.SetIsDone(true);

        /****************************************
         * ENTITY MANAGER *
         *****************************************/
        //Update all the Entity in the List
        EntityManager.Instance.Update(_dt);
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

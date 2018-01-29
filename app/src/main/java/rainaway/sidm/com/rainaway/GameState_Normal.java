package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import java.util.Random;

public class GameState_Normal extends Activity implements StateBase
{
    public final static GameState_Normal Instance = new GameState_Normal();
    private float obstacleSpawnTimer, goalTimer, pauseBounceTime, ResumeTimer;
    private float gameTime;
    private float Score, S_Multiplier;
    SurfaceView view;
    Entity Player;

    float MovementSpeed;

    // FONT
    Typeface myfont;

    //Game Indicator
    Entity Indicator, Goal;

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
        // now can create
        Vector2 PlayerPos = new Vector2(0.5f * _view.getWidth(), 0.1f * _view.getHeight());
        Player = Player.Create(Entity.ENTITYTYPE.ENTITY_PLAYER, PlayerPos, new Vector2(0, 0));
        MovementSpeed = 10.f;

        gameTime = 0.f;
        pauseBounceTime = 0.f;
        goalTimer = 0.f;
        ResumeTimer = 3.5f;

        Player.Life = 1;
        Score = 0;
        S_Multiplier = 1;

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
        if (Player.Life <= 0) // player dies, go to game over screen
        {//Saves data over to scene_Data
                Game_Data.Instance.setScore(Score);

            Game_Data.Instance.setGameTime(gameTime);
            Game_Data.Instance.setScoreMultiplier(S_Multiplier);

            //Go to GameOverScreen
            Game_System.Instance.SaveRecord("Normal", Score);
            //Page_Game.Instance.ExitGame();
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

        ResumeTimer -= _dt;
        if (ResumeTimer > 0.f)
            return;

        /****************************************
         * RUNNING TIMER *
         *****************************************/
        gameTime += _dt;
        obstacleSpawnTimer += _dt;
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
                    new Vector2(0, -view.getHeight() * 0.5f)); //type, pos, dir

            //Indicator
            Indicator = Entity.Create(Entity.ENTITYTYPE.GHOST_INDICATOR,
                    new Vector2(Goal.GetPosX(), view.getHeight() * 0.9f),
                    new Vector2(0, 0)); //type, pos, dir

            goalTimer = 0.f;
            obstacleSpawnTimer = -1.f;
        }

        //Random Object Spawn
        if (obstacleSpawnTimer >= 1.f) {
            Random ranGen = new Random();
            Entity.Create(Entity.ENTITYTYPE.OBSTACLE_ROCK,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                    new Vector2(0, -view.getHeight() * 0.5f)); //type, pos, dir

            obstacleSpawnTimer = 0.f;
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
                Goal.startVibrate();
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

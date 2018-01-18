package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 11/20/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread
{
    enum GAMEMODE
    {
        GAME_NONE,
        GAME_NORMAL,
        GAME_ARCADE,
        GAME_TIME
    }

    GAMEMODE currGameMode = GAMEMODE.GAME_NONE;
    Game_Scene Scene;
    static final long targetFPS = 60;
    private GameView view = null;
    private SurfaceHolder holder = null;
    private boolean isRunning = false; //Start not running

    public UpdateThread(GameView _view, GAMEMODE CurrGameMode)
    {
        view = _view;
        holder = _view.getHolder();

        switch (CurrGameMode){
            case GAME_NORMAL:
                Scene = Game_Normal.Instance; break;
            case GAME_ARCADE:
                //Scene = Game_Arcade.Instance; break;
            case GAME_TIME:
                Scene = Game_TimeAttack.Instance; break;
        }

        AudioManager.Instance.Init(_view);
        StateManager.Instance.Init(_view);
        EntityManager.Instance.Init(_view);
    }

    public boolean IsRunning(){return isRunning;}

    public void Initialize()
    {
        isRunning = true;
        //Sample
    }

    public void SetGameMode(GAMEMODE newMode)
    {
        currGameMode = newMode;
    }

    public void Terminate()
    {
        isRunning = false;
    }

    @Override
    public void run()
    {
        long framePerSecond = 1000/targetFPS;
        long startTime = 0;
        // we need another variable to calculate delta time
        long prevTime = System.nanoTime();

        Scene.Init(view);
        //StateManager.Instance.Start("Default");


        while (isRunning)
        {
            //UPDATE //////////////////////////////////////////////////////////////////////////////
            //here can be your state Manager update

            //This is for FRC
            startTime = System.currentTimeMillis(); // this is for frame rate controller

            // this is to get deltatime using prev time vs curr time
            long CurrTime = System.nanoTime();
            float deltaTime = (float)(CurrTime - prevTime)/1000000000.0f;
            prevTime = CurrTime;

            // we wanna have this awesome update
            //TODO remove
            Scene.Update(deltaTime);


            //StateManager.Instance.Update(deltaTime);

            //render
            Canvas canvas = holder.lockCanvas(null);
            if(canvas != null)
            {
                //To prevent 2 Threads from rendering at the same time
                synchronized (holder)
                {
                    //Render the whole screen black
                    canvas.drawColor(Color.BLACK);

                    //Insert stuff here
                    //TODO remove
                    Scene.Render(canvas);

                    //StateManager.Instance.Render(canvas);
                    // EntityManager.Instance.Render(canvas);
                    //StateManager.Instance.Init(_view);
                }
                holder.unlockCanvasAndPost(canvas);
            }
            //pos update/render
            try
            {
                long sleepTime = (framePerSecond-System.currentTimeMillis()-startTime);
                if (sleepTime>0)
                    sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
                Terminate();
            }
            //should have something to limit frame rate
        }
    }
}

// TODO implement pause using the state manager
// Create a class State pause -> use it then
// .. somewhere in your object code: StateManager.Instance.Changestate(new StatePause());
//
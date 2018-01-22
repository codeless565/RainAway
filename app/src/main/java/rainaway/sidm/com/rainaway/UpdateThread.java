package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 11/20/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread
{
    static final long targetFPS = 60;
    private GameView view = null;
    private SurfaceHolder holder = null;
    private boolean isRunning = false; //Start not running

    public UpdateThread(GameView _view)
    {
        view = _view;
        holder = _view.getHolder();


        AudioManager.Instance.Init(_view);
        EntityManager.Instance.Init(_view);
        Game_System.Instance.Init(_view);
        StateManager.Instance.Init(_view);
    }

    public boolean IsRunning(){return isRunning;}

    public void Initialize()
    {
        isRunning = true;
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

        //Scene.Init(view);
        StateManager.Instance.Start("Default");
        String Tag = "Update thread -- ";
        String text = StateManager.Instance.GetCurrentState();
        Log.d(Tag,text);


        while (isRunning)
        {
            //This is for FRC
            startTime = System.currentTimeMillis(); // this is for frame rate controller

            // this is to get deltatime using prev time vs curr time
            long CurrTime = System.nanoTime();
            float deltaTime = (float)(CurrTime - prevTime)/1000000000.0f;
            prevTime = CurrTime;

            StateManager.Instance.Update(deltaTime);

            //render
            Canvas canvas = holder.lockCanvas(null);
            if(canvas != null)
            {
                //To prevent 2 Threads from rendering at the same time
                synchronized (holder)
                {
                    //Render the whole screen black
                    canvas.drawColor(Color.BLACK);

                    StateManager.Instance.Render(canvas);
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
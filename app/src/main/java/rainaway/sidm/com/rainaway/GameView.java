package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 11/20/2017.
 */

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView
{
    private SurfaceHolder holder = null;
    private UpdateThread updateThread = new UpdateThread(this, UpdateThread.GAMEMODE.GAME_NORMAL);

    public GameView(Context _context)
    {
        //Do what the base surface view will do to setup
        super(_context);
        holder = getHolder();

        if(holder != null)
        {
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    //INIT
                    if(!updateThread.IsRunning())
                        updateThread.Initialize();

                    //Kick start the thread if not alive
                    if (!updateThread.isAlive())
                        updateThread.start();
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    //NOTHING TO DO HERE
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    //CLEAN UP
                    AudioManager.Instance.StopAllAudio();
                    updateThread.Terminate();
                }

            });

        }
    }
}

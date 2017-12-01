package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

/**
 * Created by 164347E on 11/27/2017.
 */

public class GamePage extends Activity {
        @Override
        public void onCreate(Bundle instance)
        {
            super.onCreate(instance);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //GameView thingy
            setContentView(new GameView(this));

            MediaPlayer myPlayer = MediaPlayer.create(GamePage.this, R.raw.ssr);
            myPlayer.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            int x = (int) event.getX();
            int y = (int) event.getY();

            TouchManager.Instance.Update(x, y, event.getAction());

            return true;
        }
}

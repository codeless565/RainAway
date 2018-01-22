package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 164347E on 11/27/2017.
 */

public class Page_Game extends Activity {
    public static Page_Game Instance =null;

        @Override
        public void onCreate(Bundle instance)
        {
            super.onCreate(instance);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            Instance=this;
            //GameView thingy
            setContentView(new GameView(this));
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            int x = (int) event.getX();
            int y = (int) event.getY();

            TouchManager.Instance.Update(event);

            return true;
        }

        public void Update(int Life)
        {
            Intent intent = new Intent();

            if (Life <= 0)
            {
                intent.setClass(this, Page_MainMenu.class);
            }
            startActivity(intent);
        }

    @Override
    protected void onDestroy() {super.onDestroy(); }
}

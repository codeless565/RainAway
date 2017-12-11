package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * Created by 164347E on 11/20/2017.
 */

public class Page_Splash extends Activity{
    private ProgressBar loading_bar;
    private int mStatus=0;

    protected  boolean _active = true;
    protected int _splashTime = 5000; //time to display the splash screen in ms

    /*called when the activity is first created*/

    @Override
    public void onCreate(Bundle savedInstanceStats){
        super.onCreate(savedInstanceStats);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //Hide Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashpage);

        loading_bar = (ProgressBar)findViewById(R.id.progressBar);
        loading_bar.setMax(100);

        //thread for displaying the Splash Screen
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    int waited  = 0;
                    while (_active && (waited < _splashTime))
                    {
                        sleep(200);
                        if(_active)
                        {
                            mStatus+=5;
                            waited += 200;

                            loading_bar.setProgress(mStatus);
                        }
                    }
                    if (loading_bar.getProgress() >= 100 || !_active)
                    {
                        //Create new activity based on and intend with CurrentACtivity
                        Intent intent = new Intent(Page_Splash.this, Page_MainMenu.class);
                        startActivity(intent);
                    }
                }
                catch (InterruptedException e)
                {
                    //Do nothing
                }
            }
        };
        splashThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
//     if (TouchManager.Instance.HasTouch())
//         _active=false;

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            _active = false;
        }
//
      return true;
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}

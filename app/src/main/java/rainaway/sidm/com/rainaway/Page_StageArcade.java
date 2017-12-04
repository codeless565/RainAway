package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Created by Administrator on 3/12/2017.
 */

public class Page_StageArcade extends Activity implements OnClickListener{
    private Button btn_start;
    private Button btn_back;
    private Button btn_help;

    private Vector2 CurrTouch, RecordedTouch;
    private boolean touching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This is using layout! Not what we want!
        setContentView(R.layout.stage_arcadescreen); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_start = (Button) findViewById(R.id.btn_arcadestart);
        btn_start.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_arcadeback);
        btn_back.setOnClickListener(this);

        btn_help = (Button) findViewById(R.id.btn_arcadehelp);
        btn_help.setOnClickListener(this);

    }
    //Invoke a callback on clicked event on a view

    public void onClick(View _view) {
        Intent intent = new Intent();

        if (_view == btn_start) {
            intent.setClass(this, Page_Game.class); // stageSelect Page
        } else if (_view == btn_back)//For other button like Helppage
        {
            intent.setClass(this, Page_MainMenu.class);
        }
        else if(_view == btn_help)//For other button like Helppage
        {
            intent.setClass(this, Page_HelpArcade.class);
        }


        startActivity(intent);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        Intent intent = new Intent();
        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x, y, event.getAction());

        if (TouchManager.Instance.HasTouch() && !touching)
        {
            touching = true;
            RecordedTouch = new Vector2(x, y);
            CurrTouch = RecordedTouch;

        }
        else
        {
            touching = false;
            CurrTouch = new Vector2(x, y);
        }

        if(!touching && CurrTouch.x < RecordedTouch.x) {
            intent.setClass(this, Page_StageTimeAttack.class);
            startActivity(intent);
        }
        else if (!touching && CurrTouch.x > RecordedTouch.x) {
            intent.setClass(this, Page_StageNormal.class);
            startActivity(intent);
        }

        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

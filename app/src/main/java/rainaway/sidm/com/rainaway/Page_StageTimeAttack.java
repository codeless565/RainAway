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
import android.widget.TextView;

/**
 * Created by Administrator on 3/12/2017.
 */

public class Page_StageTimeAttack extends Activity implements OnClickListener{
    private Button btn_start;
    private Button btn_back;
    private Button btn_help;
    private TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.stage_timeattackscreen);

        // Define Button by ID
        //Set Listener to button
        btn_start = (Button) findViewById(R.id.btn_timeattackstart);
        btn_start.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_timeattackback);
        btn_back.setOnClickListener(this);

        btn_help = (Button) findViewById(R.id.btn_timeattackhelp);
        btn_help.setOnClickListener(this);

        // Define Text By ID
        // Set HighScore to text
        text = (TextView)findViewById(R.id.THighscore1);
        // Split Recordtime aka Gametime to Mins and Seconds
        float RecordTime = Game_System.Instance.GetfloatFromSave("TimeAttack");
        if (RecordTime >= 0.f) // If there is a record/played
        {
            float timemin = RecordTime/60;      // Evaluate the Minutes
            float timesec = RecordTime-(60*(int)timemin); // Evaluate the Seconds

            text.setText(String.valueOf((int)timemin) + ":" + String.valueOf((int)timesec) + " mins");
        }
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view) {

        if (_view == btn_start) { // Set GameSystem to run TimeAttack Game
            Intent intent = new Intent();
            Game_System.Instance.setGameChoice(Game_System.GameChoice.TIMEATTACK);
            intent.setClass(this, Page_Game.class);
            startActivity(intent);
        }
        else if (_view == btn_back)
        {
            finish();
            Intent intent = new Intent();
            intent.setClass(this, Page_MainMenu.class);
            startActivity(intent);
        }
        else if(_view == btn_help)
        {
            Intent intent = new Intent();
            intent.setClass(this, Page_HelpTimeAttack.class);
            startActivity(intent);
        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        // Update the TouchManager to read Touches
        TouchManager.Instance.Update(event);

        // SWITCH ACTIVITY
        if (TouchManager.Instance.getTouchResult() == TouchManager.TouchResult.SWIPELEFT)
        {
            Intent intent = new Intent();
            // On Left Swipe
            intent.setClass(this, Page_StageNormal.class);
            startActivity(intent);
        }
        else if (TouchManager.Instance.getTouchResult() == TouchManager.TouchResult.SWIPERIGHT)
        {
            Intent intent = new Intent();
            // On Right Swipe
            intent.setClass(this, Page_StageArcade.class);
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

    public void onBackPressed() {

        finish();
        Intent intent = new Intent();
        intent.setClass(this, Page_MainMenu.class);
        startActivity(intent);
    }

}

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

public class Page_StageArcade extends Activity implements OnClickListener{
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

        setContentView(R.layout.stage_arcadescreen);

        // Define Button by ID
        //Set Listener to button
        btn_start = (Button) findViewById(R.id.btn_arcadestart);
        btn_start.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_arcadeback);
        btn_back.setOnClickListener(this);

        btn_help = (Button) findViewById(R.id.btn_arcadehelp);
        btn_help.setOnClickListener(this);

        // Define Text By ID
        // Set HighScore to text
        text= (TextView)findViewById(R.id.AHighscore1);
        text.setText(String.valueOf(Game_System.Instance.GetintFromSave("Arcade")));
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view) {

        if (_view == btn_start)
        { // Set GameSystem to run Arcade Game
            Intent intent = new Intent();
            Game_System.Instance.setGameChoice(Game_System.GameChoice.ARCADE);
            intent.setClass(this, Page_Game.class);
            startActivity(intent);
            finish();
        }
        else if (_view == btn_back)
        {
            finish();
            Intent intent = new Intent();
            intent.setClass(this, Page_MainMenu.class);
            startActivity(intent);
        }
        else if(_view == btn_help) {
            Intent intent = new Intent();
            intent.setClass(this, Page_HelpArcade.class);
            startActivity(intent);
            finish();
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
            intent.setClass(this, Page_StageTimeAttack.class);
            startActivity(intent);
            finish();
        }
        else if (TouchManager.Instance.getTouchResult() == TouchManager.TouchResult.SWIPERIGHT)
        {
            Intent intent = new Intent();
            // On Right Swipe
            intent.setClass(this, Page_StageNormal.class);
            startActivity(intent);
            finish();
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

package rainaway.sidm.com.rainaway;


import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Page_StageNormal extends Activity implements OnClickListener {
    private static final String TAG = "StageNormal";
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

        setContentView(R.layout.stage_normalscreen);

        // Define Button by ID
        //Set Listener to button
        btn_start = (Button) findViewById(R.id.btn_normalstart);
        btn_start.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_normalback);
        btn_back.setOnClickListener(this);

        btn_help = (Button) findViewById(R.id.btn_normalhelp);
        btn_help.setOnClickListener(this);

        // Define Text By ID
        // Set HighScore to text
        text = (TextView)findViewById(R.id.NHighscore1);
        text.setText(String.valueOf(Game_System.Instance.GetintFromSave("Normal")));
    }

        //Invoke a callback on clicked event on a view
    public void onClick(View _view) {

        if (_view == btn_start) { // Set GameSystem to run Normal Game
            Intent intent = new Intent();
            Game_System.Instance.setGameChoice(Game_System.GameChoice.NORMAL);
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
        else if(_view == btn_help)
        {
            Intent intent = new Intent();
            intent.setClass(this, Page_HelpNormal.class);
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
            intent.setClass(this, Page_StageArcade.class);
            startActivity(intent);
        }
        else if (TouchManager.Instance.getTouchResult() == TouchManager.TouchResult.SWIPERIGHT)
        {
            Intent intent = new Intent();
            // On Right Swipe
            intent.setClass(this, Page_StageTimeAttack.class);
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

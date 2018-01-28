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

        //This is using layout! Not what we want!
        setContentView(R.layout.stage_normalscreen); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_start = (Button) findViewById(R.id.btn_normalstart);
        btn_start.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_normalback);
        btn_back.setOnClickListener(this);

        btn_help = (Button) findViewById(R.id.btn_normalhelp);
        btn_help.setOnClickListener(this);

        text= (TextView)findViewById(R.id.NHighscore1);
        text.setText(String.valueOf((int)Game_Data.Instance.getScore()));
    }

        //Invoke a callback on clicked event on a view
    public void onClick(View _view) {
        Intent intent = new Intent();

        if (_view == btn_start) {
            Game_System.Instance.setGameChoice(Game_System.GameChoice.NORMAL);
            intent.setClass(this, Page_Game.class); // stageSelect Page
        } else if (_view == btn_back)//For other button like Helppage
        {
            intent.setClass(this, Page_MainMenu.class);
        }
        else if(_view == btn_help)//For other button like Helppage
        {
            intent.setClass(this, Page_HelpNormal.class);
        }

        startActivity(intent);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        Intent intent = new Intent();

        TouchManager.Instance.Update(event);

        // SWITCH ACTIVITY
        if (TouchManager.Instance.getTouchResult() == TouchManager.TouchResult.SWIPELEFT)
        {
            intent.setClass(this, Page_StageArcade.class);
            startActivity(intent);
        }
        else if (TouchManager.Instance.getTouchResult() == TouchManager.TouchResult.SWIPERIGHT)
        {
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
}

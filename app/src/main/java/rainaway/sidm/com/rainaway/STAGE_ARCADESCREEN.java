package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Created by Administrator on 3/12/2017.
 */

public class STAGE_ARCADESCREEN extends Activity implements OnClickListener{
    private Button btn_start;
    private Button btn_back;
    private Button btn_help;
    private Button btn_next;
    private Button btn_before;

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
        btn_start = (Button) findViewById(R.id.btn_normalstart);
        btn_start.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_normalback);
        btn_back.setOnClickListener(this);

        btn_help = (Button) findViewById(R.id.btn_normalhelp);
        btn_help.setOnClickListener(this);

        btn_next = (Button) findViewById(R.id.btn_nextstagescreen);
        btn_next.setOnClickListener(this);

        btn_before = (Button) findViewById(R.id.btn_beforestagescreen);
        btn_before.setOnClickListener(this);
    }
    //Invoke a callback on clicked event on a view

    public void onClick(View _view) {
        Intent intent = new Intent();

        if (_view == btn_start) {
            intent.setClass(this, GamePage.class); // stageSelect Page
        } else if (_view == btn_back)//For other button like Helppage
        {
            intent.setClass(this, MainMenu.class);
        }
        // TODO Complete Arcade Help Screen
        else if(_view == btn_help)//For other button like Helppage
        {
            intent.setClass(this, HELP_ARCADESCREEN.class);
        }
        else if(_view == btn_next)//For other button like Helppage
        {
            intent.setClass(this, STAGE_NORMALSCREEN.class);
        }
        else if(_view == btn_before)//For other button like Helppage
        {
            intent.setClass(this, STAGE_TIMEATTACKSCREEN.class);
        }


        startActivity(intent);
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

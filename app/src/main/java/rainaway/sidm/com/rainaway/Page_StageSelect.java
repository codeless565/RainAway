package rainaway.sidm.com.rainaway;

// TODO DELETE THIS
/**
 * Created by 164347E on 11/21/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Page_StageSelect extends Activity implements OnClickListener {

    //define button as Object
    private Button btn_normal;
    private Button btn_arcade;
    private Button btn_time;
    private Button btn_option;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This is using layout! Not what we want!
        setContentView(R.layout.stageselectpage); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_normal = (Button)findViewById(R.id.btn_normal);
        btn_normal.setOnClickListener(this);
        btn_arcade = (Button)findViewById(R.id.btn_arcade);
        btn_arcade.setOnClickListener(this);
        btn_time = (Button)findViewById(R.id.btn_time);
        btn_time.setOnClickListener(this);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        Intent intent = new Intent();

        if (_view == btn_normal)
        {
            //start normal stage
        }
        else if(_view == btn_arcade)//For other button like Helppage
        {
            //start arcade stage
        }
        else if (_view == btn_time)
        {
            //start time stage
        }
        else if (_view == btn_back)
        {
            intent.setClass(this, Page_MainMenu.class);
        }
        startActivity(intent);
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
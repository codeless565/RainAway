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
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class Page_HelpTimeAttack extends Activity implements OnClickListener, View.OnTouchListener {

    //define button as Object
    private Button btn_back;
    private TextView text;
    private ImageButton btn_slow;
    private ImageButton btn_hp;
    private ImageButton btn_freeze;
    private ImageButton btn_stone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This is using layout! Not what we want!
        setContentView(R.layout.help_timeattackscreen); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_back = (Button)findViewById(R.id.btn_timeattackhelpback);
        btn_back.setOnClickListener(this);
    text = (TextView)findViewById(R.id.Tdescriptionbox);

        btn_slow = (ImageButton)findViewById(R.id.SlowButton);
        btn_slow.setOnTouchListener(this);
        btn_freeze = (ImageButton)findViewById(R.id.FreezeButton);
        btn_freeze.setOnTouchListener(this);
        btn_hp = (ImageButton)findViewById(R.id.healthButton);
        btn_hp.setOnTouchListener(this);
        btn_stone = (ImageButton)findViewById(R.id.StoneButton);
        btn_stone.setOnTouchListener(this);
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        Intent intent = new Intent();
        if(_view == btn_back)
            intent.setClass(this, Page_StageTimeAttack.class);
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

    @Override
    public boolean onTouch(View _view, MotionEvent event) {
        if (_view == btn_slow)
            text.setText("Slows down time");
        else if (_view == btn_freeze)
            text.setText("Freezes time");
        else if (_view == btn_hp)
            text.setText("Increases player life");
        else if (_view == btn_stone)
            text.setText("Decreases the player's health");

        if (event.getAction() == MotionEvent.ACTION_DOWN)
            text.setVisibility(View.VISIBLE);
        else
            text.setVisibility(View.INVISIBLE);
        return false;
    }
}
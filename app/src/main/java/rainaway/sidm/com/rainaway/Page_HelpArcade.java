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

public class Page_HelpArcade extends Activity implements OnClickListener,View.OnTouchListener {

    //define button as Object
    private Button btn_back;

    private TextView text;
    private ImageButton btn_slow;
    private ImageButton btn_hp;
    private ImageButton btn_freeze;
    private ImageButton btn_shroud;
    private ImageButton btn_multiplier;
    private ImageButton btn_slowspeed;

    private ImageButton btn_stone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.help_arcadescreen);

        //Set Listener to button
        btn_back = (Button)findViewById(R.id.btn_arcadehelpback);   // Back Button
        btn_back.setOnClickListener(this);                          // Set ClickListener to Back Button

        text = (TextView)findViewById(R.id.Adescriptionbox);        // Description TextBox

        btn_slow = (ImageButton)findViewById(R.id.SlowButton);      // Slow Button
        btn_slow.setOnTouchListener(this);                          // Set TouchListener to Slow Button
        btn_freeze = (ImageButton)findViewById(R.id.FreezeButton);  // Freeze Button
        btn_freeze.setOnTouchListener(this);                        // Set TouchListener to Freeze Button
        btn_hp = (ImageButton)findViewById(R.id.healthButton);      // Health Button
        btn_hp.setOnTouchListener(this);                            // Set TouchListener to Health Button
        btn_shroud = (ImageButton)findViewById(R.id.ShroudButton);  // Shroud Button
        btn_shroud.setOnTouchListener(this);                        // Set TouchListener to Shroud Button
        btn_multiplier = (ImageButton)findViewById(R.id.multiplierButton);// Multiplier Button
        btn_multiplier.setOnTouchListener(this);                    // Set TouchListener to Multiplier Button
        btn_slowspeed = (ImageButton)findViewById(R.id.slowspeedButton);// Slow Speed Button
        btn_slowspeed.setOnTouchListener(this);                     // Set TouchListener to Slow Speed Button
        btn_stone = (ImageButton)findViewById(R.id.StoneButton);    // Stone Button
        btn_stone.setOnTouchListener(this);                         // Set TouchListener to Stone Button
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {

        if(_view == btn_back)                               // If click is on Back Button
        {
            finish();
        }
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
        if (_view == btn_slow)                              // If touch is on Slow Button
            text.setText("Slows down time");                // Set text value
        else if (_view == btn_freeze)                       // If touch is on Freeze Button
            text.setText("Freezes time");                   // Set text value
        else if (_view == btn_hp)                           // If touch is on Health Button
            text.setText("Increases player life");          // Set text value
        else if (_view == btn_shroud)                       // If touch is on Shroud Button
            text.setText("Gives player invulnerability");   // Set text value
        else if (_view == btn_multiplier)                   // If touch is on Multipler Button
            text.setText("Increases multiplier");           // Set text value
        else if (_view == btn_slowspeed)                    // If touch is on Slow Speed Button
            text.setText("Slows player speed");             // Set text value
        else if (_view == btn_stone)                        // If touch is on Stone Button
            text.setText("Decreases the player's health");  // Set text value

        if (event.getAction() != MotionEvent.ACTION_UP)     // If There is touch
            text.setVisibility(View.VISIBLE);               // Set Text Visibility(boolean) to true
        else
            text.setVisibility(View.INVISIBLE);             // Set Text Visibility(boolean) to false


        return false;
    }

    public void onBackPressed() {
        finish();
    }

}
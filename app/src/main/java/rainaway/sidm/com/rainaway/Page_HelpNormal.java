package rainaway.sidm.com.rainaway;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class Page_HelpNormal extends Activity implements OnClickListener,View.OnTouchListener{

    //define button as Object
    private Button btn_back;
    private TextView text;
    private ImageButton btn_goal;
    private ImageButton btn_stone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.help_normalscreen);

        //Set Listener to button
        btn_back = (Button)findViewById(R.id.btn_normalhelpback); // Back Button
        btn_back.setOnClickListener(this);                          // Set ClickListener to Back Button

        text =(TextView)findViewById(R.id.descriptionbox);          // Description TextBox

        btn_goal = (ImageButton)findViewById(R.id.GoalButton);      // Health Button
        btn_goal.setOnTouchListener(this);                          // Set TouchListener to Health Button
        btn_stone = (ImageButton)findViewById(R.id.StoneButton);    // Stone Button
        btn_stone.setOnTouchListener(this);                         // Set TouchListener to Stone Button
    }

    @Override
    public boolean onTouch(View _view, MotionEvent event) {
        if (_view == btn_goal)                                              // If touch is on Goal Button
            text.setText("Gives players points and increase multiplier");   // Set text value
        else if (_view == btn_stone)                                        // If touch is on Stone Button
            text.setText("Decreases the player's health");                  // Set text value

        if (event.getAction() != MotionEvent.ACTION_UP)                     // If There is touch
            text.setVisibility(View.VISIBLE);                               // Set Text Visibility(boolean) to true
        else
            text.setVisibility(View.INVISIBLE);                             // Set Text Visibility(boolean) to false

        return false;
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

    public void onBackPressed() {
        finish();
    }

}
package rainaway.sidm.com.rainaway;
// TODO on touch hold image wil lshow information
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
import android.widget.SeekBar;
import android.widget.TextView;

public class Page_HelpNormal extends Activity implements OnClickListener {

    //define button as Object
    private Button btn_back;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This is using layout! Not what we want!
        setContentView(R.layout.help_normalscreen); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_back = (Button)findViewById(R.id.btn_normalhelpback);
        btn_back.setOnClickListener(this);

        text =(TextView)findViewById(R.id.textView2);
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        Intent intent = new Intent();

        if(_view == btn_back)
        {
            intent.setClass(this, Page_StageNormal.class);
        }
        startActivity(intent);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        TouchManager.Instance.Update(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN)
                text.setVisibility(View.VISIBLE);
        else
            text.setVisibility(View.INVISIBLE);


        return true;
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
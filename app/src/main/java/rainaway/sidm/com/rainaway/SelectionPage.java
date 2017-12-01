package rainaway.sidm.com.rainaway;
// TODO add in TA,A screens
// TODO swiping for touchmanager? else, use left right arrow
// TODO rename SelectionPage to like idk NORMALPAGE?
// TODO HELP screen
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by 164353M on 12/1/2017.
 */

public class SelectionPage extends Activity implements View.OnClickListener {
    private Button btn_start;
    private Button btn_back;
    private Button btn_help;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This is using layout! Not what we want!
        setContentView(R.layout.selectionpage); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btn_help = (Button)findViewById(R.id.btn_help);
        btn_help.setOnClickListener(this);
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        Intent intent = new Intent();

        if (_view == btn_start)
        {
           intent.setClass(this, GamePage.class); // stageSelect Page
        }
        else if(_view == btn_back)//For other button like Helppage
        {
            intent.setClass(this, MainMenu.class);
        }
        // TODO add in helppage for normal
        //else if(_view == btn_help)//For other button like Helppage
        //{
        //    intent.setClass(this, HelpPage.class);
        //}


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

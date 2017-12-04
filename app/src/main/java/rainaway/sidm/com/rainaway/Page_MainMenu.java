package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Page_MainMenu extends Activity implements OnClickListener {

    //define button as Object
    private Button btn_start;
    private Button btn_help;
    private Button btn_option;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This is using layout! Not what we want
        setContentView(R.layout.mainmenu); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        btn_help = (Button)findViewById(R.id.btn_help);
        btn_help.setOnClickListener(this);
        btn_option = (Button)findViewById(R.id.btn_option);
        btn_option.setOnClickListener(this);
        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        Intent intent = new Intent();

        if (_view == btn_start)
        {
<<<<<<< HEAD:app/src/main/java/rainaway/sidm/com/rainaway/Page_MainMenu.java
            intent.setClass(this, Page_Game.class); // stageSelect Page
        }
        else if(_view == btn_help)//For other button like Helppage
        {
            intent.setClass(this, Page_Help.class);
=======
            intent.setClass(this, STAGE_NORMALSCREEN.class); // stageSelect Page
>>>>>>> master:app/src/main/java/rainaway/sidm/com/rainaway/MainMenu.java
        }
        else if (_view == btn_option)
        {
            intent.setClass(this, Page_Options.class);
        }
        else if (_view == btn_exit)
        {
            System.exit(1); // exits the app
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


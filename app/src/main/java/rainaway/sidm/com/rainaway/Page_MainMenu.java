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
    public static Page_MainMenu Instance = null;

    //define button as Object
    //private Button btn_highscore;
    private Button btn_start;
    private Button btn_option;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);

        Instance = this;
        Game_System.Instance.InitSharedPref();

        //=========================================
        // HighScore button but not working
        //=========================================
        //Set Listener to button
        //btn_highscore = (Button)findViewById(R.id.btn_highscore);
        //btn_highscore.setOnClickListener(this);

        btn_start = (Button)findViewById(R.id.btn_start);   // Start Button
        btn_start.setOnClickListener(this);                 // Set Clicklistener to Start Button
        btn_option = (Button)findViewById(R.id.btn_option); // Option Button
        btn_option.setOnClickListener(this);                // Set Clicklistener to Option Button
        btn_exit = (Button)findViewById(R.id.btn_exit);     // Exit Button
        btn_exit.setOnClickListener(this);                  // Set Clicklistener to Exit Button
    }

    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        Intent intent = new Intent();

        if (_view == btn_start)
            intent.setClass(this, Page_StageNormal.class);  // Move from MainMenu to StageNormal
        else if (_view == btn_option)
            intent.setClass(this, Page_Options.class);      // Move from MainMenu to Options
        else if (_view == btn_exit)
        {//Close all Activity and exit application
            finishAffinity();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

        //=========================================
        // HighScore button but not working
        //=========================================
        //else if (_view == btn_highscore)
        //intent.setClass(this, Page_HighScore.class);

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

    public void onBackPressed() {
        //Close all Activity and exit application
        finishAffinity();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}


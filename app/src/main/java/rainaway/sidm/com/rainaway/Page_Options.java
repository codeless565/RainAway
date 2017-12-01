package rainaway.sidm.com.rainaway;
//TODO vibration, sensitivity,control(by touch/tilt)?
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.media.AudioManager;
import android.widget.SeekBar;
import android.view.View.OnClickListener;


public class Page_Options extends Activity implements OnClickListener {

    //define button as Object
    private Button btn_mainmenu;
    private SeekBar seekBar_volume;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This is using layout! Not what we want!
        setContentView(R.layout.optionpage); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_mainmenu = (Button)findViewById(R.id.btn_mainmenu);
       btn_mainmenu.setOnClickListener(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls();
    }
    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        Intent intent = new Intent();

        if(_view == btn_mainmenu)
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

    private void initControls()
    {
        try
        {
            seekBar_volume = (SeekBar)findViewById(R.id.SFXSeekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            seekBar_volume.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            seekBar_volume.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            seekBar_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
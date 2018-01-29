package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.media.AudioManager;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.view.View.OnClickListener;


public class Page_Options extends Activity implements OnClickListener {


    public static final String Tag = "Options - ";
    //define button as Object
    private Button btn_mainmenu;
    private SeekBar seekBar_volume;
    private Button btn_reset;

    private AudioManager audioManager;
    private CheckBox vibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.optionpage);

        //Set Listener to button
        btn_mainmenu = (Button)findViewById(R.id.btn_mainmenu); // Main Menu Button
        btn_mainmenu.setOnClickListener(this);                  // Set Clicklistener to MainMenu Button
        btn_reset = (Button)findViewById(R.id.ResetSP);         // Reset Shared Preference Button
        btn_reset.setOnClickListener(this);                     // Set Clicklistener to Reset Button

        vibration = (CheckBox)findViewById(R.id.Vibration);     // Vibration Checkbox
        if (TouchManager.Instance.getVibration())               // Default Value of checkbox onCreate based on TouchManager.Vibration(Boolean)
            vibration.setChecked(true);                         // If Vibration Enabled, Tick CheckBox
        else
            vibration.setChecked(false);                        // If Vibration Enabled, Untick CheckBox


        seekBar_volume = (SeekBar)findViewById(R.id.SFXSeekBar);// AudioVolume SeekBar
        setVolumeControlStream(AudioManager.STREAM_MUSIC);      // Set AudioStream to modify
        initControls();                                         // Function for Audio Volume
    }
    //Invoke a callback on clicked event on a view
    public void onClick(View _view)
    {
        if(_view == btn_mainmenu)                               // If click is on Main Menu Button
        {
            Intent intent = new Intent();

            if (vibration.isChecked())                          // Saving to Touch Manager when leaving the Activity
                TouchManager.Instance.setVibration(true);       // If CheckBox is ticked, Save TouchManager.Vibration(Boolean) to true
            else
                TouchManager.Instance.setVibration(false);      // If CheckBox is ticked, Save TouchManager.Vibration(Boolean) to false

            intent.setClass(this, Page_MainMenu.class);         // Move from Options to MainMenu
            startActivity(intent);
            finish();
        }

        if (_view==btn_reset)                                   // If click is on Reset Button
            Game_System.Instance.ResetSharedPreference();       // Reset Function for SharedPreference

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
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);              // Get AudioStream to edit
            seekBar_volume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));  // Maximum Value for SeekBar based on Phone's AudioStream
            seekBar_volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));// Current Value for SeekBar based on Phone's AudioStream


            seekBar_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()     // Set SeekBarChangeListener to Seekbar
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
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)         // If there is change in value aka progress
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);       // Set Phone's AudioStream Volume to progress

                    if (!rainaway.sidm.com.rainaway.AudioManager.Instance.getAudioMap().isEmpty())  // Change the volume of every music in AudioManager
                    {
                        for (int i:rainaway.sidm.com.rainaway.AudioManager.Instance.getAudioMap().keySet()) // Point to the StorageContainer(HashMap<K,V>) of Audio Manager Value(the audio)
                        {
                            rainaway.sidm.com.rainaway.AudioManager.Instance.getAudio(i).setVolume(progress,progress);  // Set the Volume of the current Audio(V) based on the Key to the progress for both Left and Right
                        }
                    }
                }


            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        finish();
    }
}
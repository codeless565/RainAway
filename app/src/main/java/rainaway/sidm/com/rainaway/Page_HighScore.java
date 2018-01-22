package rainaway.sidm.com.rainaway;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Arrays;
import java.util.List;


public class Page_HighScore extends Activity implements OnClickListener {
    private Button btn_back;
    private Button btn_fbLogin;
    private Button btn_fbShare;

    boolean LoggedIn=false;
    private CallbackManager _callbackmanager;
    private LoginManager _loginmanager;

    ProfilePictureView _profilepic;
    List<String> PERMISSIONS = Arrays.asList("publish_actions");

    int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Hide the top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //This is using layout! Not what we want!
        setContentView(R.layout.highscore); //We will use GameView instead
        //setContentView(new GameView(this));

        //Set Listener to button
        btn_back = (Button) findViewById(R.id.BackButton);
        btn_back.setOnClickListener(this);

        btn_fbLogin=(LoginButton)findViewById(R.id.fblogin);
        btn_fbLogin.setOnClickListener(this);

        btn_fbShare=(Button)findViewById(R.id.ShareButton);
        btn_fbShare.setOnClickListener(this);

        _profilepic=(ProfilePictureView)findViewById(R.id.picture);
        _callbackmanager = CallbackManager.Factory.create();

        // TODO GetHighscore
        // week 15/13 shared preferenced
        //highscore = GameSystem.Instance.GetIntFromSave("Score");

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                if (currentAccessToken == null)
                {
                    // User logged out of Facebook
                    _profilepic.setProfileId(" ");
                }
                else
                {
                    _profilepic.setProfileId(Profile.getCurrentProfile().getId());
                }

            }
        };
        accessTokenTracker.startTracking();

        _loginmanager = LoginManager.getInstance();
        _loginmanager.logInWithPublishPermissions(this,PERMISSIONS);
        _loginmanager.registerCallback(_callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                _profilepic.setProfileId(Profile.getCurrentProfile().getId());
                // TODO call method to share score
            }

            @Override
            public void onCancel() {
                System.out.println("Login attempt cancelled");
                // OR Print text on screen
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("Login attempt failed");
                // OR Print text on screen
            }
        });
    }

    @Override
    //Invoke a callback on clicked event on a view
    public void onClick(View _view) {
        Intent intent = new Intent();

        if (_view == btn_back)
        {
            intent.setClass(this, Page_MainMenu.class);
        }
        else if(_view == btn_fbShare) {
            AlertDialog.Builder _alertbuilder = new AlertDialog.Builder(Page_HighScore.this);
            _alertbuilder.setTitle("Share on Facebook?");
            _alertbuilder.setMessage("Do you want to share on Facebook?");
            _alertbuilder.setCancelable(false);
            _alertbuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // Call method to score
                }
            });
            _alertbuilder.setNegativeButton("No",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // dialog is to cancel
                    dialog.cancel();
                }
            });
            _alertbuilder.show();
        }
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // TODO
    // public void shareScore()
    /*
    {
    Bitmap _image = BitmapFactory.decodeResource(getResource(),R.mipmap.ic_launcher_round); // The post on FB will have your app icon
    SharePhoto _photo = new SharePhoto.Builder()
    .setBitmap(_image)
    .setCaption("Thank you for playing XX! Your highscore is " + highscore)
    .build();

    // Share it out
    SharePhotoContent _content = new SharePhotoContent.Builder()
    .addPhoto(_photo)
    .build();

    ShareApi.share(_content,null);
    }

    // FB use callback manager to manage login
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        _callbackmanager.onActivityResult(requestCode,resultCode,data);
    }
     */
}
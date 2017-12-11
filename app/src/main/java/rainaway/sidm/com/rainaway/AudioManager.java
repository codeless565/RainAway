package rainaway.sidm.com.rainaway;

import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.HashMap;

/**
 * Created by Administrator on 3/12/2017.
 */

public class AudioManager {
    public final static AudioManager Instance = new AudioManager();

    private HashMap<Integer,MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();
    private SurfaceView view = null;

    private AudioManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void PlayAudio(int _id)
    {
        // Check if the audio is loaded or not
        if (audioMap.containsKey(_id))
        {
            // we got it
            MediaPlayer curr = audioMap.get(_id);
            curr.reset();
            curr.start();
        }

        // we do not have the resource loaded therefore load them
        MediaPlayer newAudio = MediaPlayer.create(view.getContext(),_id);
        audioMap.put(_id,newAudio);
        newAudio.start();
    }

    public boolean isPlaying()
    {
        return false;
    }
}

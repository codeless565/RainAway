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

    public void PlayAudio(int _id, boolean looping)
    {
        // Check if the audio is loaded or not
        if (audioMap.containsKey(_id))
        {
            // we got it
            MediaPlayer curr = audioMap.get(_id);
            curr.setLooping(looping);
            curr.reset();
            curr.start();
        }

        // we do not have the resource loaded therefore load them
        MediaPlayer newAudio = MediaPlayer.create(view.getContext(),_id);
        audioMap.put(_id,newAudio);
        newAudio.setLooping(looping);
        newAudio.start();
    }

    public boolean isPlaying()
    {
        return false;
    }

    public void StopAudio(int _id)
    {
        if (audioMap.containsKey(_id))
        {
            MediaPlayer curr = audioMap.get(_id);
            curr.stop();
        }
    }

    public void StopAllAudio()
    {
        if (!audioMap.isEmpty())
        {
            for (int i : audioMap.keySet()) {
                MediaPlayer curr = audioMap.get(i);
                curr.stop();
            }
        }
    }

    public HashMap<Integer,MediaPlayer> getAudioMap(){return audioMap;}

    public MediaPlayer getAudio(int _id)
    {
        if (audioMap.containsKey(_id))
            return audioMap.get(_id);

        return null;
    }
}

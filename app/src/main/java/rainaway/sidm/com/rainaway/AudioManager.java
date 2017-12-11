package rainaway.sidm.com.rainaway;
//TODO singleton this * player

import android.media.MediaPlayer;

/**
 * Created by Administrator on 3/12/2017.
 */

public class AudioManager {
    public final static AudioManager Instance = new AudioManager();
    private MediaPlayer myPlayer = null;
    private ArrayList<Integer> MusicList = new  ArrayList<Integer>();

}

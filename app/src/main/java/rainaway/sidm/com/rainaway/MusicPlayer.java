package rainaway.sidm.com.rainaway;
//TODO singleton this * player

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 3/12/2017.
 */

public class MusicPlayer {
    public final static MusicPlayer Instance = new MusicPlayer();
    private MediaPlayer myPlayer = null;
    private ArrayList<Integer> MusicList = new  ArrayList<Integer>();

    private MusicPlayer(){
    }

    public void addSong(int newSong)
    {
        if (!MusicList.contains(newSong))
        {
            MusicList.add(newSong);
        }
    }

    public void play(int songName)
    {
        if (myPlayer==null)
            myPlayer=new MediaPlayer();

        if (MusicList.contains(songName))
        {
            myPlayer.stop();
            try {
                String finalname= "R.raw." + songName;

                myPlayer.setDataSource(finalname);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                myPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            myPlayer.start();
        }
    }
}

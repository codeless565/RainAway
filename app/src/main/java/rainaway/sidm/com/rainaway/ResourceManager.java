package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.view.SurfaceView;
import java.util.HashMap;

// TODO RESOURCE MANAGER
/**
 * Created by 164353M on 12/11/2017.
 */

public class ResourceManager {
    public final static ResourceManager Instance = new ResourceManager();

    private HashMap<Integer,Bitmap> resourceMap = new HashMap<Integer, Bitmap>();
    private SurfaceView view = null;

    private ResourceManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void PlayAudio(int _id)
    {
//        // Check if the audio is loaded or not
//        if (resourceMap.containsKey(_id))
//        {
//            // we got it
//            MediaPlayer curr = audioMap.get(_id);
//            curr.reset();
//            curr.start();
//        }
//
//        // we do not have the resource loaded therefore load them
//        resourceMap newResource = MediaPlayer.create(view.getContext(),_id);
//        resourceMap.put(_id,newAudio);
//        newAudio.resourceMap();
    }
}

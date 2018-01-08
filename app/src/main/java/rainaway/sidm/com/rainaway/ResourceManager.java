package rainaway.sidm.com.rainaway;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private Resources res = null;

    private ResourceManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        res = _view.getResources();
    }

    public Bitmap GetBitmap(int _id)
    {
        if (resourceMap.containsKey(_id))
            return resourceMap.get(_id);

        Bitmap result = BitmapFactory.decodeResource(res, _id);
        resourceMap.put(_id, result);
        return result;
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

package rainaway.sidm.com.rainaway;
/**
 * Created by 164347E on 11/20/2017.
 */

import android.graphics.Canvas;
import android.view.SurfaceView;

public class SampleGame
{
    public final static SampleGame Instance = new SampleGame(); // Singleton
    private float timer;
    // this is to not allow anyone else to create another game instance
    private SampleGame()
    {

    }

    public void Init (SurfaceView _view)
    {
        EntityManager.Instance.Init(_view);
        SampleBackGround.Create();
    }

    public void Update(float _dt)
    {
        //SAMPLE STUFF DONT DO THIS
        timer += _dt;
        if (timer >=0.5f)
        {
            //ntity.Create();
            timer = 0.f;
        }

        EntityManager.Instance.Update(_dt);
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

}


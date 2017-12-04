package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 12/4/2017.
 */

import android.graphics.Canvas;
import android.view.SurfaceView;

public class Game_Normal implements Game_Scene
{
    public final static Game_Normal Instance = new Game_Normal(); // Singleton
    private float timer;
    // this is to not allow anyone else to create another game instance
    private Game_Normal()
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
            Entity.Create();
            timer = 0.f;
        }

        EntityManager.Instance.Update(_dt);
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

}

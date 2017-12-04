package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 12/4/2017.
 */

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

public class Game_Normal implements Game_Scene
{
    public final static Game_Normal Instance = new Game_Normal(); // Singleton
    private float timer;

    SurfaceView view;
    Entity Player;

    // this is to not allow anyone else to create another game instance
    private Game_Normal()
    {

    }

    public void Init (SurfaceView _view)
    {
        EntityManager.Instance.Init(_view);
        SampleBackGround.Create();
        view = _view;
        // now can create
        Vector2 PlayerPos = new Vector2(0.5f * _view.getWidth(),0.1f * _view.getHeight());
        Player.Create(Entity.ENTITYTYPE.ENTITY_PLAYER, PlayerPos, new Vector2(0,0));
    }

    public void Update(float _dt)
    {
        //SAMPLE STUFF DONT DO THIS
        timer += _dt;
        //if (timer >=0.5f)
        //{
        //    Entity.Create(Entity.ENTITYTYPE.ENTITY_OBSTACLE, new Vector2(0,0), new Vector2(0,0));
        //    timer = 0.f;
        //}
        if(TouchManager.Instance.isDown() && TouchManager.Instance.GetPosX() >= view.getWidth()* 0.5f)
        {
            Player.Pos.x += 100 * _dt;
        }

        if(TouchManager.Instance.isDown() && TouchManager.Instance.GetPosX() < view.getWidth()* 0.5f)
        {
            Player.Pos.x -= 100 * _dt;
        }

        EntityManager.Instance.Update(_dt);
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

}

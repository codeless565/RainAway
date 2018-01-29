package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceView;

/**
 * Created by 164353M on 1/8/2018.
 */

public interface StateBase
{
    String GetName();    //Returns name/String of the State for stateManager
    void CollisionResponse(Entity.ENTITYTYPE type);    //Collision Responses in that state for Player v Other object

    void OnEnter(SurfaceView _view);//"Init"
    void OnExit();                  //"Destructor"

    void Update(float _dt);     //Per Frame Update
    void Render(Canvas _canvas);//Per Frame Rendering
}

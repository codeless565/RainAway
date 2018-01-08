package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceView;

/**
 * Created by 164353M on 1/8/2018.
 */

public interface StateBase
{
    String GetName();
    void OnEnter(SurfaceView _view);
    void OnExit();
    void Update(float _dt);
    void Render(Canvas _canvas);
}

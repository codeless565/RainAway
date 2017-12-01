package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public interface EntityBase
{
    boolean IsDone();
    void SetIsDone(boolean _IsDone);

    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render (Canvas _canvas);
}

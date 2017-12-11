package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public interface EntityBase
{
    //Can get Entity's Variable and activeness.
    //Functions
    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render (Canvas _canvas);

    boolean IsDone();
    void SetIsDone(boolean _IsDone);
    int getEntityLayer();
    void setEntityLayer(int _EntityLayer);
}
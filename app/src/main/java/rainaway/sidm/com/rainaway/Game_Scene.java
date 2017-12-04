package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceView;

/**
 * Created by 164347E on 12/4/2017.
 */

public interface Game_Scene {
    void Init (SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

}

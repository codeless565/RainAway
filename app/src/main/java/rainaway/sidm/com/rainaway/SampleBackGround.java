package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class SampleBackGround implements EntityBase
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos, offset;
    private SurfaceView view = null;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _IsDone) {
        isDone = _IsDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        view = _view;
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene);
        offset = bmp.getWidth() * 0.2f;
    }

    @Override
    public void Update(float _dt) {
        offset -= _dt * bmp.getWidth() * 0.5f;

        if(offset <= -bmp.getWidth() * 0.2f)
            offset = bmp.getWidth() * 0.2f;
    }

    @Override
    public void Render(Canvas _canvas) {
        xPos = 0.5f * view.getWidth();
        yPos = 0.5f * view.getHeight();

        //float xOffset = (float)Math.sin(offset) * bmp.getWidth() * 0.3f; //this give the bckgnd moving back and forth

        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f + offset, yPos - bmp.getHeight() * 0.5f, null);
    }

    public static SampleBackGround Create()
    {
        SampleBackGround result = new SampleBackGround();
        EntityManager.Instance.AddEntity(result);
        return result;
    }
}

package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class SamplePauseButton implements EntityBase
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private int xPos, yPos;


    @Override
    public boolean IsDone() {
        return false;
    }

    @Override
    public void SetIsDone(boolean _IsDone) {
        isDone=_IsDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        // TODO
        // Resource Manager
        //bmp = ResourceManager.Instance.GetbitMap
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.button);
        xPos =100;
        yPos =100;
    }

    @Override
    public void Update(float _dt) {
        if(TouchManager.Instance.HasTouch())
        {
            float imgRadius = bmp.getHeight() * 0.5f;
            if(Collision.SphereToSphere(TouchManager.Instance.getCurrTouch().x, TouchManager.Instance.getCurrTouch().y, 0.0f,xPos,yPos,imgRadius))
            {
                // Button Clicked!
                Game_Normal.Instance.setIsPaused(!Game_Normal.Instance.getIsPaused());
            }
        }

    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp,xPos-bmp.getWidth()*0.5f,yPos-bmp.getHeight()*0.5f,null);
    }

    @Override
    public int getEntityLayer() {
        //return Layerconstants.UI_LAYER;
        return 0;
    }

    @Override
    public void setEntityLayer(int _EntityLayer) {
            return;
    }

    public static SamplePauseButton Create()
    {
        SamplePauseButton result = new SamplePauseButton();
        EntityManager.Instance.AddEntity(result);
        return result;
    }
}

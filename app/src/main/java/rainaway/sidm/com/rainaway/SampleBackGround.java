package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.util.DisplayMetrics;

public class SampleBackGround implements EntityBase
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos, offset;
    private SurfaceView view = null;
    private int EntityLayer;
    // TODO
    // private int EntityLayer = ConstantLayout.BKGRD

    // Screen resizing
//    int ScreenWidth, ScreenHeight;
//    private Bitmap scaledbmp = null;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _IsDone) {
        isDone = _IsDone;
    }

    @Override
    public int getEntityLayer() {
        return EntityLayer;
    }

    @Override
    public void setEntityLayer(int _EntityLayer) {
        EntityLayer = _EntityLayer;
    }

    @Override
    public void Init(SurfaceView _view) {
        // TODO RESOURCE MANAGER
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene);

        // Retrieve information of your surface view or any device size view WEEK 9???
//        DisplayMetrics metrics = _view.getResources().getDisplayMetrics(); // Gets the width and height based on any device
//        ScreenWidth=metrics.widthPixels;
//        ScreenHeight=metrics.heightPixels;
//        scaledbmp = Bitmap.createScaledBitmap(bmp,ScreenWidth,ScreenHeight,true);

        view = _view;
        offset = bmp.getWidth() * 0.2f;
        EntityLayer=-1;
    }

    @Override
    public void Update(float _dt) {
        if (!MainGameState.Instance.getIsPaused()) {

            offset -= _dt * bmp.getHeight() * 0.2f;

            if (offset <= -bmp.getHeight() * 0.25f)
                offset = bmp.getHeight() * 0.25f;

            // Week 9
//        offset -= _dt * bmp.getHeight() * 0.2f;
//
//        if(offset <= -bmp.getHeight() * 0.25f)
//            offset = bmp.getHeight() * 0.25f;
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        xPos = 0.5f * view.getWidth();
        yPos = 0.5f * view.getHeight();

        //float xOffset = (float)Math.sin(offset) * bmp.getWidth() * 0.3f; //this give the bckgnd moving back and forth

        // Week 9
        //_canvas.drawBitmap(scaledbmp,xPos,yPos,null);
        //_canvas.drawBitmap(scaledbmp,xPos,yPos+ScreenHeight,null);
        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f + offset, null);
    }

    public static SampleBackGround Create()
    {
        SampleBackGround result = new SampleBackGround();
        EntityManager.Instance.AddEntity(result);
        return result;
    }
}


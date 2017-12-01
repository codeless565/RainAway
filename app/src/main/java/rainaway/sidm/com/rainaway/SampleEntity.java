package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.method.Touch;
import android.view.SurfaceView;

import java.util.Random;

public class SampleEntity implements EntityBase, Collidable
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos, xDir, yDir, lifeTime;

    SampleEntity()
    {
        //Init my variable here, that is suppose to be standardized and not changed
    }

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _IsDone)
    {
        isDone = _IsDone;
    }

    @Override
    public void Init(SurfaceView _view)
    {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);
        lifeTime = 100.5f;
        Random ranGen = new Random();

        //Init variable
        xPos = ranGen.nextFloat() * _view.getWidth(); //nextFloat will generate a float from 0.f to 1.f;
        yPos = ranGen.nextFloat() * _view.getHeight();

        xDir = ranGen.nextFloat() * 100.f - 50.f;
        yDir = ranGen.nextFloat() * 100.f - 50.f;
    }

    @Override
    public void Update(float _dt)
    {
        lifeTime -= _dt;
        if(lifeTime < 0.0f)
            SetIsDone(true);

        xPos += xDir * _dt;
        yPos += yDir * _dt;

        if(TouchManager.Instance.isDown())
        {
            float imgRadius = bmp.getHeight() * 0.5f;
            if(Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f,xPos,yPos,imgRadius))
                SetIsDone(true);
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
    }

    //Our global create function
    //So anyone can create "SampleEntities"
    public static SampleEntity Create()
    {
        SampleEntity result = new SampleEntity();
        EntityManager.Instance.AddEntity(result);
        return result;
    }

    @Override
    public String GetType() {
        return "SampleEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getHeight() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {
    //COLLISION WITH OTHER ITEMS
        if(_other.GetType() == "SampleEntity")
        {
            SetIsDone(true);
        }
    }
}

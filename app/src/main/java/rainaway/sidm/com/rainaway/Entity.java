package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class Entity implements EntityBase, EntityCollidable
{
    //Entity's Variable are here
    enum ENTITYTYPE
    {
        ENTITY_NONE,
        ENTITY_PLAYER,
        ENTITY_OBSTACLE,
        ENTITY_POWERUP
    }
    ENTITYTYPE m_type;
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos, yPos, xDir, yDir, lifeTime;
    private int Life;

    //Our global create function
    //So anyone can create "SampleEntities"
    public static Entity Create()
    {
        Entity result = new Entity();
        EntityManager.Instance.AddEntity(result);
        return result;
    }

    Entity()
    {
        //Init my variable here, that is suppose to be standardized and not changed
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
    public void SetEntityType(ENTITYTYPE _type) {
        m_type = _type;
    }

    @Override
    public ENTITYTYPE GetEntityType() {
        return m_type;
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
    public void OnHit(EntityCollidable _other) {
    //COLLISION WITH OTHER ITEMS
        if(m_type == ENTITYTYPE.ENTITY_PLAYER)
        {//PLAYER COLLISION RESPONSE WITH OTHER OBJECT
            if (_other.GetEntityType() == ENTITYTYPE.ENTITY_OBSTACLE)
            {//COLLIDED WITH OBSTACLE
                --Life;
                EntityBase OtherEntity = (EntityBase) _other;
                OtherEntity.SetIsDone(true);
            }
            /***********************************************
            TODO
             - Collision with HP item
             - Collision with Slow Time
             - Collision with Freeze Time
             - Collision with Shroud
             - Collision with Slow Speed
             - Collision with
            ***********************************************/

        }
    }
}

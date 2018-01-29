package rainaway.sidm.com.rainaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.SurfaceView;

import java.util.Random;

public class Entity implements EntityBase, EntityCollidable
{
    //Entity's Variable are here
    enum ENTITYTYPE
    {
        ENTITY_NONE,
        ENTITY_PLAYER,

        POWERUP_SLOWDOWN,
        POWERUP_SLOWSPEED,
        POWERUP_FREEZE,
        POWERUP_SHROUD,
        POWERUP_ADDHP,
        POWERUP_ADDMULTIPLIER,

        OBSTACLE_ROCK,
        OBSTACLE_GOAL,

        GHOST_INDICATOR,
    }
    ENTITYTYPE m_type;
    private Bitmap bmp = null;
    private boolean isDone = false;
    public Vector2 Pos, Dir;
    private float lifeTime;
    private int EntityLayer=0;

    public Vibrator _vibrator;

    public int Life;

    boolean isShrouded;

    //Our global create function
    //So anyone can create "SampleEntities"
    public static Entity Create(ENTITYTYPE _type, Vector2 pos, Vector2 dir)
    {
        Entity result = new Entity();
        result.m_type = _type;

        if (result.m_type == ENTITYTYPE.ENTITY_PLAYER)
            result.setEntityLayer(result.m_type.ordinal());

        result.Pos = pos;
        result.Dir = dir;


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
        if(m_type == ENTITYTYPE.OBSTACLE_ROCK)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.stone);
        else if (m_type == ENTITYTYPE.ENTITY_PLAYER)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.player);
        else if (m_type == ENTITYTYPE.OBSTACLE_GOAL)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.goal);
        else if (m_type == ENTITYTYPE.GHOST_INDICATOR)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.indicator);
        else if (m_type == ENTITYTYPE.POWERUP_SLOWDOWN)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.slowdown);
        else if (m_type == ENTITYTYPE.POWERUP_SLOWSPEED)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.slowspeed);
        else if (m_type == ENTITYTYPE.POWERUP_FREEZE)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.freezetime);
        else if (m_type == ENTITYTYPE.POWERUP_SHROUD)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.shroud);
        else if (m_type == ENTITYTYPE.POWERUP_ADDHP)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.health);
        else if (m_type == ENTITYTYPE.POWERUP_ADDMULTIPLIER)
            bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.multiplier);



        lifeTime = 100.5f;
        Random ranGen = new Random();

        //Init variable
//        if (m_type == ENTITYTYPE.ENTITY_PLAYER)
//        {
//            return;
//        }
//
//        Pos.x = ranGen.nextFloat() * _view.getWidth(); //nextFloat will generate a float from 0.f to 1.f;
//        Pos.y = ranGen.nextFloat() * _view.getHeight();
//
//        Dir.x = ranGen.nextFloat() * 100.f - 50.f;
//        Dir.y = ranGen.nextFloat() * 100.f - 50.f;
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);
    }

    // Vibrator Event
    public void startVibrate(){
        long pattern[] = {0,50,0};
        // P1 = TIME TO WAIT BEFORE VIBRATOR IS ON
        // P2 = TIME TO KEEP VIBRATOR ON
        // P3 = TIME TILL VIBRATION IS OFF OR TILL VIBRATOR IS ON

        // 1000 = Vibrate 1 sec

        // Amplitude is an int value. its the strength of the vibration
        // this must be a value between 1 and 255, or DEFAULT_AMPLITUDE which is -1
        // VibrationEffect effect = VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE);
        if (Build.VERSION.SDK_INT >= 26)
            _vibrator.vibrate(VibrationEffect.createOneShot(150,10));
        else
            _vibrator.vibrate(pattern,-1);
    }

    @Override
    public void Update(float _dt)
    {
        if (!Game_System.Instance.getIsPaused()) {
            //lifeTime -= _dt;
            if (lifeTime < 0.0f)
                SetIsDone(true);

            Pos.x += Dir.x * _dt;
            Pos.y += Dir.y * _dt;
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
        _canvas.drawBitmap(bmp, Pos.x - bmp.getWidth() * 0.5f, Pos.y - bmp.getHeight() * 0.5f, null);
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
    public int getEntityLayer() {
        return EntityLayer;
    }

    @Override
    public void setEntityLayer(int _EntityLayer) {
        EntityLayer=_EntityLayer;
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
        return Pos.x;
    }

    @Override
    public float GetPosY() {
        return Pos.y;
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
            switch (_other.GetEntityType())
            {
                case OBSTACLE_ROCK:
                {
                    if (isShrouded)
                        return;

                    //In Game Collision Response differs by its game type
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.OBSTACLE_ROCK);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);
                    if (TouchManager.Instance.getVibration())
                        startVibrate();

                    break;
                }
                case OBSTACLE_GOAL:
                {
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.OBSTACLE_GOAL);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);

                    if (TouchManager.Instance.getVibration())
                        startVibrate();
                    break;
                }
                case POWERUP_SLOWDOWN:
                {
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.POWERUP_SLOWDOWN);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);
                    break;
                }
                case POWERUP_SLOWSPEED:
                {
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.POWERUP_SLOWSPEED);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);
                    break;
                }
                case POWERUP_FREEZE:
                {
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.POWERUP_FREEZE);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);
                    break;
                }
                case POWERUP_SHROUD:
                {
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.POWERUP_SHROUD);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);
                    break;
                }
                case POWERUP_ADDHP:
                {
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.POWERUP_ADDHP);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);
                    break;
                }
                case POWERUP_ADDMULTIPLIER:
                {
                    StateManager.Instance.getCurrState().CollisionResponse(ENTITYTYPE.POWERUP_ADDMULTIPLIER);
                    EntityBase OtherEntity = (EntityBase) _other;
                    OtherEntity.SetIsDone(true);
                    break;
                }

            }

            /***********************************************
            TODO
             - Collision Effect with Slow Time
             - Collision Effect with Freeze Time
             - Collision Effect with Shroud
             - Collision Effect with Slow Speed
             - Collision with
            ***********************************************/

        }
    }
}

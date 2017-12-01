package rainaway.sidm.com.rainaway;

import android.view.MotionEvent;

public class TouchManager
{
    public final static TouchManager Instance = new TouchManager();

    public enum TouchState
    {
        NONE,
        DOWN,
        MOVE
    }

    private TouchState status = TouchState.NONE;
    private int posX, posY;

    private TouchManager()
    {
        posX = 0;
        posY = 0;
    }

    //Check if have finger / click or holds
    public boolean HasTouch()
    {
        return status == TouchState.DOWN ||
               status == TouchState.MOVE;
    }

    //Check for clicks
    public boolean isDown()
    {
        return status == TouchState.DOWN;
    }

    public int GetPosX()
    {
        return posX;
    }

    public int GetPosY()
    {
        return posY;
    }

    public void Update(int _posX, int _posY, int motionEventStatus)
    {
        posX = _posX;
        posY = _posY;

        //Android Version of Stuff
        switch (motionEventStatus)
        {
            case MotionEvent.ACTION_DOWN:
                status = TouchState.DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                status = TouchState.MOVE;
                break;

            case MotionEvent.ACTION_UP:
                status = TouchState.NONE;
                break;
        }
    }
}

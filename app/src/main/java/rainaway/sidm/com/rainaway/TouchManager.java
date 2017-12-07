package rainaway.sidm.com.rainaway;

import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;

public class TouchManager
{
    public final static TouchManager Instance = new TouchManager();

    public enum TouchResult
    {
        NONE,
        TAP,
        DOUBLETAP,
        HOLD,
        SWIPELEFT,
        SWIPERIGHT,
    }

    private TouchResult status = TouchResult.NONE;

    private Vector2 CurrTouch,RecordedTouch;
    private boolean Touching,Swiping;
    float TouchTimer;


    private TouchManager()
    {
//        posX = 0;
//        posY = 0;
        CurrTouch = new Vector2(0,0);
        RecordedTouch= new Vector2(0,0);
        Touching=false;
        Swiping=false;
        TouchTimer=0.0f;
    }

    //Check if have finger / click or holds
    public boolean HasTouch() {return Touching;}
    public boolean getSwiping() {return Swiping;}
    public Vector2 getCurrTouch() {return CurrTouch;}
    public Vector2 getRecordedTouch() {return RecordedTouch;}
    public TouchResult getTouchResult() {return status;}
    public void setCurrTouch(Vector2 _currTouch) { CurrTouch=_currTouch;}
    public void setRecordedTouch(Vector2 _RecordedTouch) {RecordedTouch=_RecordedTouch;}
    public void setTouching(boolean _Touching) {Touching =_Touching;}
    public void setSwiping(boolean _Swiping) {Swiping=_Swiping;}

    public void Update(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
           Touching=true;
        if (event.getAction() == MotionEvent.ACTION_UP)
            Touching=false;

        if (Touching && !Swiping)
        {
            Swiping=true;
            RecordedTouch=new Vector2(event.getX(),event.getY());
            CurrTouch=RecordedTouch;
        }
        else
        {
            Swiping=false;
            CurrTouch=new Vector2(event.getX(),event.getY());
        }

        if (!Touching && status != TouchResult.NONE)
        {
            CurrTouch=new Vector2(0,0);
            RecordedTouch=new Vector2(0,0);
            TouchTimer=0.0f;
            status = TouchResult.NONE;
        }
        else
            TouchTimer+=0.1f;

        if (TouchTimer>0.5f && Touching)
            status = TouchResult.HOLD;
        else if (TouchTimer<0.5f && Touching) // swipe left
        {
            if (CurrTouch.x <RecordedTouch.x)
                status = TouchResult.SWIPELEFT;
            else if (CurrTouch.x >RecordedTouch.x)
                status = TouchResult.SWIPERIGHT;
            else
                status = TouchResult.TAP;
        }
    }
}

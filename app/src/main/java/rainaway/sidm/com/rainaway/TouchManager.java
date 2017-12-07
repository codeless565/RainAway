package rainaway.sidm.com.rainaway;

import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;

public class TouchManager
{
    //Singleton
    public final static TouchManager Instance = new TouchManager();

    public enum TouchResult
    {
        NONE,
        TAP,
        DOUBLETAP,
        SWIPELEFT,
        SWIPERIGHT,
    }

    // Class Members
    private TouchResult status = TouchResult.NONE; // Whether gesture is Tap,Swipe,Hold
    private Vector2 CurrTouch;      // Position of Current Touch
    private Vector2 RecordedTouch;  // Position of Recorded Touch
    private boolean Touching;       // Boolean for touch on screen
    private boolean Swiping;        // Boolean for Swiping gesture

    // Constructor
    private TouchManager()
    {
        CurrTouch = new Vector2(0,0);
        RecordedTouch= new Vector2(0,0);
        Touching=false;
        Swiping=false;
    }

    // Getters
    public boolean HasTouch() {return Touching;}
    public boolean getSwiping() {return Swiping;}
    public Vector2 getCurrTouch() {return CurrTouch;}
    public Vector2 getRecordedTouch() {return RecordedTouch;}
    public TouchResult getTouchResult() {return status;}
    // Setters
    public void setCurrTouch(Vector2 _currTouch) { CurrTouch=_currTouch;}
    public void setRecordedTouch(Vector2 _RecordedTouch) {RecordedTouch=_RecordedTouch;}
    public void setTouching(boolean _Touching) {Touching =_Touching;}
    public void setSwiping(boolean _Swiping) {Swiping=_Swiping;}

    public void Update(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
            Touching=false;
        else
            Touching = true;

        // It is always enabling swipe every touch
        // However, it does not affect whether it is used
        // Save the Current position of the Touch
        if (Touching && !Swiping)                                                       // WHILE THERE IS TOUCH ON SCREEN
        {
                Swiping=true;
                RecordedTouch=new Vector2(event.getX(),event.getY());
                CurrTouch=RecordedTouch;
        }
        else                                                                            // WHILE THERE IS NO TOUCH ON SCREEN
        {
            Swiping=false;
            CurrTouch=new Vector2(event.getX(),event.getY());
        }

        // Conditions for Touch Status
        // Position of touch did not change == Tap
        // Position of exit < Position of enter == Swipe Left
        // Position of exit > Position of enter == Swipe Right
        if (CurrTouch.x <RecordedTouch.x)
            status = TouchResult.SWIPELEFT;
        else if (CurrTouch.x >RecordedTouch.x)
            status = TouchResult.SWIPERIGHT;
        else if (CurrTouch.x == RecordedTouch.x)
            status = TouchResult.TAP;

        // If there is no touch on screen
        if (!Touching && status != TouchResult.NONE)                                    // if status is Holding/Swiping/Tap
            ResetTouch();                                                               // Reset Touch Positions, Status and Touch on screen
    }

    public void ResetTouch()
    {
        CurrTouch=new Vector2(0,0);
        RecordedTouch=new Vector2(0,0);
        status = TouchResult.NONE;
    }
}

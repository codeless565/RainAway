package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.HashMap;

/**
 * Created by 164353M on 1/8/2018.
 */

public class StateManager {
    public static final StateManager Instance = new StateManager();
    private SurfaceView view = null;
    // container to store all our states

    private HashMap<String,StateBase> stateMap = new HashMap<String,StateBase>();

    private StateBase currState = null;
    private StateBase nextState = null;

    private StateManager()
    {
    }
    public HashMap<String,StateBase> getHashMap() {return stateMap;}
    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void Update(float _dt)
    {
        if (nextState != currState)
        {
            currState.OnExit();
            nextState.OnEnter(view);
            currState = nextState;
        }
        if (currState == null)
            return;

        currState.Update(_dt);
    }

    void Render(Canvas _canvas)
    {
        currState.Render(_canvas);
    }

    void ChangeState(String _newState)
    {
        nextState = stateMap.get(_newState);

        if (nextState == null)
            nextState = currState;
    }

    void AddState(StateBase _newState)
    {
        if (!stateMap.containsValue(_newState))
            stateMap.put(_newState.GetName(),_newState);
    }

    void Start(String _newCurrent)
    {
        // To ensure that this can only be called once
        if (currState != null || nextState != null)
            return;

        currState=stateMap.get(_newCurrent);
        if (currState != null)
        {
            currState.OnEnter(view);
            nextState=currState;
        }
    }

    String GetCurrentState()
    {
        if (currState == null)
            return "INVALID";

        return currState.GetName();
    }
}

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

    //States holder
    private StateBase currState = null;
    private StateBase nextState = null;

    //FUNCTIONS
    private StateManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void Update(float _dt)
    {
        // Cleanly move to next state if there is one
        if (nextState != currState)
        {
            currState.OnExit();
            nextState.OnEnter(view);
            currState = nextState;
        }

        // If currstate == null, no need to update anything
        if (currState == null)
            return;

        // Update currstate
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

        currState = stateMap.get(_newCurrent);
        if (currState != null)
        {
            nextState = currState;
            currState.OnEnter(view);
        }
    }

    //=========================================
    //Getter
    //=========================================

    //Get currState name
    String GetCurrentState()
    {
        if (currState == null)
            return "INVALID";

        return currState.GetName();
    }

    //Get currState StateBase
    public StateBase getCurrState()
    {
        return currState;
    }
}

package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 12/4/2017.
 */

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;

public class Game_Normal implements Game_Scene
{
    public final static Game_Normal Instance = new Game_Normal(); // Singleton
    private float timer;

    SurfaceView view;
    Entity Player;
    float MovementSpeed = 10.f;

    // this is to not allow anyone else to create another game instance
    private Game_Normal()
    {

    }

    public void Init (SurfaceView _view)
    {
        EntityManager.Instance.Init(_view);
        SampleBackGround.Create();
        view = _view;
        // now can create
        Vector2 PlayerPos = new Vector2(0.5f * _view.getWidth(),0.1f * _view.getHeight());
        Player = Player.Create(Entity.ENTITYTYPE.ENTITY_PLAYER, PlayerPos, new Vector2(0,0));

        /********************
        TODO
         - Countdown
         - Init Player Life
         - Init Score to 0
         - Update Score as player keep playing
        *********************/
    }

    public void Update(float _dt)
    {
        //SAMPLE STUFF DONT DO THIS
        timer += _dt;
        if (timer >= 1.f)
        {
            Random ranGen = new Random();

            Entity.Create(Entity.ENTITYTYPE.ENTITY_OBSTACLE,
                    new Vector2(ranGen.nextFloat() * view.getWidth(), view.getHeight()),
                    new Vector2(0, -view.getHeight() * 0.5f));

            timer = 0.f;
        }

        //Start Accelerating towards direction
        if(TouchManager.Instance.HasTouch())
        {
            if (TouchManager.Instance.GetPosX() >= view.getWidth()* 0.5f)
                Player.Dir.x += MovementSpeed * _dt;

            if (TouchManager.Instance.GetPosX() < view.getWidth()* 0.5f)
                Player.Dir.x -= MovementSpeed * _dt;

            if(Player.Dir.x >= MovementSpeed)
                Player.Dir.x = MovementSpeed;

            if(Player.Dir.x <= -MovementSpeed)
                Player.Dir.x = -MovementSpeed;
        }
        else
        {
            //Decelerate to 0
            if (Player.Dir.x > 0.f)
                Player.Dir.x -= MovementSpeed * _dt;
            else if (Player.Dir.x < 0.f)
                Player.Dir.x += MovementSpeed * _dt;
        }

        //Update Player Position
        Player.Pos.x += Player.Dir.x;
        //Boundary for Player
        if(Player.Pos.x > view.getWidth() - Player.GetRadius()) {
            Player.Pos.x = view.getWidth() - Player.GetRadius();
            Player.Dir.x = 0;
        }
        if(Player.Pos.x < Player.GetRadius()) {
            Player.Pos.x = Player.GetRadius();
            Player.Dir.x = 0;
        }

        //Update all the Entity in the List
        EntityManager.Instance.Update(_dt);
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

}

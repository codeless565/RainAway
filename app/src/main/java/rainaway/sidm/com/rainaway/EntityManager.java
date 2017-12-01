package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.LinkedList;

public class EntityManager
{
    public final static EntityManager Instance = new EntityManager();
    private SurfaceView view = null;
    private LinkedList<EntityBase> entityList = new LinkedList<EntityBase>();

    // If constructor of a singelton class is not private, the constuctor can be called multiple times and multiple instance which is not how a singelton should work
    private EntityManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        //This function is only called once at the start of our update Thread
        //Cannot be used to init our objects
        view = _view;
    }

    public void Update(float _dt)
    {
        //List for object that needs to be deleted.
        LinkedList<EntityBase> removalList = new LinkedList<EntityBase>();

        //TODO
        for (EntityBase currEntity :entityList)
        {
            currEntity.Update(_dt);

            if(currEntity.IsDone())
            {
                //We need to remove this object, but since we cannot remove while iterating, we add it to a removalList
                removalList.add(currEntity);
            }
        }

        //Remove everything from removalList
        for (EntityBase currEntity :removalList)
        {
            entityList.remove(currEntity);
        }
        removalList.clear(); //Keeping things clean and tidy

        //Resolve some collision
        for (int i = 0; i < entityList.size(); ++i)
        {
            EntityBase currEntity = entityList.get(i);

            if(currEntity instanceof EntityCollidable)
            {
                EntityCollidable first = (EntityCollidable) currEntity; //Casting to a collidable

                for (int j = i + 1; j < entityList.size(); ++j)
                {
                    EntityBase otherEntity = entityList.get(j);

                    if(otherEntity instanceof EntityCollidable)
                    {
                        EntityCollidable second = (EntityCollidable) otherEntity;

                        //we got 2 collidable! Check collision here
                        if(Collision.SphereToSphere(first.GetPosX(),first.GetPosY(),first.GetRadius(),second.GetPosX(),second.GetPosY(),second.GetRadius()))
                        {
                            //COLLIDED! Notify the both of them
                            first.OnHit(second);
                            second.OnHit(first);
                        }
                    }
                }
            }

            if(currEntity.IsDone())
            {
                //We need to remove this object, but since we cannot remove while iterating, we add it to a removalList
                removalList.add(currEntity);
            }
        }

        //Remove everything from removalList
        for (EntityBase currEntity :removalList)
        {
            entityList.remove(currEntity);
        }
        removalList.clear(); //Keeping things clean and tidy
    }

    public void Render(Canvas _canvas)
    {
        //TODO
        //Render all the entities in our list
        for (EntityBase currEntity : entityList)
        {
            currEntity.Render(_canvas);
        }
    }

    public void AddEntity(EntityBase _newEntity)
    {
        _newEntity.Init(view);
        entityList.add(_newEntity);
    }

    //Terminate (Should write this but leave this to you. "Up to you")
}


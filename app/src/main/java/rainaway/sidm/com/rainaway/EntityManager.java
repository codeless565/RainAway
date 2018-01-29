package rainaway.sidm.com.rainaway;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class EntityManager
{
    public final static EntityManager Instance = new EntityManager();

    private SurfaceView view = null;

    private LinkedList<EntityBase> entityList = new LinkedList<EntityBase>();   //Game Object List

    // If constructor of a singelton class is not private, the constuctor can be called multiple times and multiple instance which is not how a singelton should work
    private EntityManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        //This function is only called once at the start of our update Thread
        //Cannot be used to init our objects
        entityList.clear();//Clear everything and set to default
        view = _view;
    }

    public LinkedList<EntityBase> getEntityList() {
        return entityList;
    }

    public void Update(float _dt)
    {
        //List for object that needs to be deleted.
        LinkedList<EntityBase> removalList = new LinkedList<EntityBase>();

        //Update all game object in the list
        for (EntityBase currEntity :entityList)
        {
            currEntity.Update(_dt);

            //Remove any object that isDone
            if(currEntity.IsDone())
            {
                //We need to remove this object, but since we cannot remove while iterating, we add it to a removalList
                removalList.add(currEntity);
            }
        }

        //Remove everything from removalList in the entityList
        for (EntityBase currEntity :removalList)
        {
            entityList.remove(currEntity);
        }
        removalList.clear(); //Keeping things clean and tidy

        //Collision Checkers
        for (int i = 0; i < entityList.size(); ++i)
        {
            EntityBase currEntity = entityList.get(i);

            //Check if 1st object is collidable
            if(currEntity instanceof EntityCollidable)
            {
                EntityCollidable first = (EntityCollidable) currEntity; //Casting to a collidable

                for (int j = i + 1; j < entityList.size(); ++j)
                {
                    EntityBase otherEntity = entityList.get(j);

                    //Check if 2nd object is a collidable
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

                //1st Object is Out Of Bound
                if(first.GetPosX() > view.getWidth() + first.GetRadius() || first.GetPosX() < -first.GetRadius() || first.GetPosY() > view.getHeight() * 2 + first.GetRadius() || first.GetPosY() < -first.GetRadius())
                    currEntity.SetIsDone(true);
            }

            //check if object isDone after resolving collision
            if(currEntity.IsDone())
            {
                //We need to remove this object, but since we cannot remove while iterating, we add it to a removalList
                removalList.add(currEntity);
            }
        }

        //Remove everything from removalList in the entityList
        for (EntityBase currEntity :removalList)
        {
            entityList.remove(currEntity);
        }
        removalList.clear(); //Keeping things clean and tidy
    }

    public void Render(Canvas _canvas)
    {
        // We willl use the new "rendering layer" to sort the render orfer
        Collections.sort(entityList, new Comparator<EntityBase>() {
            @Override
            public int compare(EntityBase o1, EntityBase o2) {
                return o1.getEntityLayer() - o2.getEntityLayer();
            }
        });

        //Render all the entities in our list
        for (EntityBase currEntity : entityList)
        {
            currEntity.Render(_canvas);
        }
    }

    //Add entity to the entityList
    public void AddEntity(EntityBase _newEntity)
    {
        _newEntity.Init(view);
        entityList.add(_newEntity);
    }

    //Terminate (Should write this but leave this to you. "Up to you")
    public void Terminate()
    {
        view = null;
        entityList.clear();
    }
}


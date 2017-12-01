package rainaway.sidm.com.rainaway;

public interface EntityCollidable
{
    //Collidable entity just to check what it is and where it is
    void SetEntityType(Entity.ENTITYTYPE _type);
    Entity.ENTITYTYPE GetEntityType();
    float GetPosX();
    float GetPosY();
    float GetRadius();

    void OnHit(EntityCollidable _other);
}

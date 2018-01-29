package rainaway.sidm.com.rainaway;

public interface EntityCollidable
{
    //Collidable entity just to check what it is and where it is

    //Setter
    void SetEntityType(Entity.ENTITYTYPE _type);

    //Getter
    Entity.ENTITYTYPE GetEntityType();
    float GetPosX();
    float GetPosY();
    float GetRadius();

    //Collision Checker
    void OnHit(EntityCollidable _other);
}

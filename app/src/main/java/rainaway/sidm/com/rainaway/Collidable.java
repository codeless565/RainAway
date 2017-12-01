package rainaway.sidm.com.rainaway;

public interface Collidable
{
    String GetType();
    float GetPosX();
    float GetPosY();
    float GetRadius();

    void OnHit(Collidable _other);
}

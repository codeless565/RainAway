package rainaway.sidm.com.rainaway;

/**
 * Created by 164347E on 12/4/2017.
 */

import java.lang.Math;

public class Vector2 {
    float x;
    float y;

    Vector2(float v)
    {
        x = 0.f;
        y = 0.f;
    }

    Vector2( float a, float b )
    {
        x = a;
        y = b;
    }

    Vector2(final Vector2 rhs )
    {
        x = rhs.x;
        y = rhs.y;
    }

    void SetZero()
    {
        x = y = 0.f;
    }

    void Set( float a, float b )
    {
        x = a;
        y = b;
    }

    float Length()
    {
        return (float)Math.sqrt(x * x + y * y);
    }

    float LengthSquared()
    {
        return x * x + y * y;
    }

    float Dot(Vector2 rhs )
    {
        return x * rhs.x + y * rhs.y;
    }

    Vector2 Normalized( )
    {
        float d = Length();
        if(d == 0)
            return new Vector2(0.f,0.f);

        return new Vector2(x / d, y / d);
    }
}

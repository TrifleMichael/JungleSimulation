package Utilities;

public class Vector2d {
    public final int x,y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }


    public boolean equals(Object other) {
        if (!(other instanceof Vector2d))
            return false;

        if (this.x == ((Vector2d) other).x && this.y == ((Vector2d) other).y)
            return true;
        return false;
    }


    public String toString() {
        return "("+Integer.toString(this.x)+","+Integer.toString(this.y)+")";
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += this.x * 37;
        hash += this.y * 39;
        return hash;
    }
}

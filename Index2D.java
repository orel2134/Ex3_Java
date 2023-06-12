import org.junit.jupiter.api.Test;

public class Index2D implements Pixel2D {
    private int _x, _y;

    public Index2D() {
        this(0, 0);
    }

    public Index2D(int x, int y) {
        _x = x;
        _y = y;
    }

    public Index2D(Pixel2D t) {

        this(t.getX(), t.getY());
    }

    @Override
    public int getX() {

        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    public double distance2D(Pixel2D t) {
        double ans = 0;
        /////// add your code below ///////
        if (t == null)
            throw new RuntimeException();
        double dx = t.getX() - this._x;
        double dy = t.getY() - this._y;

        ans = Math.sqrt(dx * dx + dy * dy);///////////////////////////////////
        return ans;

    }


    @Override
    public String toString() {
        return getX() + "," + getY();

    }

}

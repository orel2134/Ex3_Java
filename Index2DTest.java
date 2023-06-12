import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Index2DTest {


    @Test
    void distance1() {
        int x1 = 3;
        int y1 = 5;
        int x2 = 3;
        int y2 = 8;

        // Calculate the 2D distance using the formula
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);
   assertEquals(3,distance);

    }
    @Test
    /**
     * Tests that Math.sqrt((t.getX() - _x)^2 + (t.getY() - _y)^2) == R
     */
    void testDistance2() {
        Pixel2D t = new Index2D(-15,-17);
        Pixel2D e = new Index2D(-1,-16);
        double ans = t.distance2D(e);
        assertEquals(14.04,ans,0.1);
    }

    @Test
    void testToString() {
        Pixel2D pixel = new Index2D(10, 20);
        String expected = "10,20";
        String actual = pixel.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        Pixel2D t = new Index2D(0,0);
        Pixel2D p = new Index2D(0,1);
        boolean ans = t.equals(p);
        assertFalse(ans );
    }
    @Test
    void equals(){
        Pixel2D a=new Index2D(7,7);
        Pixel2D b=new Index2D(7,8);
        boolean ans = a.equals(b);
        assertFalse(ans);
    }
}
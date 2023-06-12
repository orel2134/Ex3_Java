import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MapTest {
    @Test
        //this test if the values in each array which are presented in two different ways are they equal
    void init() {
        double[][] mat = new double[3][2];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[i][j] = 2;
            }
        }
        double[][] mat2 = {{2, 2}, {2, 2}, {2, 2}};
        assertArrayEquals(mat, mat2);
    }

    @Test
    void testInit() {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        // create a new Map object and initialize it with the sample array
        Map map = new Map(arr);
        map.init(arr);

        // assert that the map if is equal to the sample array
        assertArrayEquals(arr, map.getMap());
    }

    @Test
    void getMap() {

        // create a 2D int array
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        // create a new Map object and initialize it with the array
        Map map = new Map(arr);

        // get the map and assert that it is equal to the original array
        int[][] mapArray = map.getMap();
        assertArrayEquals(arr, mapArray);
    }

    @Test
    void getMap1() {
//		// create an empty 2D int array
        int[][] array = new int[1000][1000];
        // fill the array with a known value
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                array[i][j] = 42;
            }
        }

        // create  Map1 object and initialize it with the large array
        Map map1 = new Map(array);
        // get the map and assert that it is equal to the  array
        int[][] Array1 = map1.getMap();
        assertArrayEquals(array, Array1);
    }

    @Test
        //creat a array 3x3 and test if the return value of the height is the same
    void getHeight() {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};//creat a array
        Map mapi = new Map(arr);//map get the array
        int height = mapi.getHeight();
        assertEquals(3, height);
    }

    @Test
        //this test if array that I creat is return the true value of the map
        //the value are represents the  pixel current
    void getPixel() {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Map map = new Map(arr);
        assertEquals(2, map.getPixel(0, 1));
        assertEquals(6, map.getPixel(1, 2));
        assertEquals(9, map.getPixel(2, 2));

    }

    @Test
        //test function are get the pixel are equal in the return value of getX and getY
    void testGetPixel() {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Map map = new Map(arr);

        Pixel2D p1 = new Index2D(0, 1);
        Pixel2D p2 = new Index2D(1, 2);
        Pixel2D p3 = new Index2D(2, 2);

        assertEquals(2, map.getPixel(p1));
        assertEquals(6, map.getPixel(p2));
        assertEquals(9, map.getPixel(p3));
    }

    @Test
        //this test the function that get (int x, int y, int v ) and the value are return in the current
    void setPixel() {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Map map = new Map(arr);
        map.setPixel(1, 1, 10);
        assertEquals(10, map.getPixel(1, 1));
        map.setPixel(2, 0, 20);
        assertEquals(20, map.getPixel(2, 0));
        map.setPixel(new Index2D(0, 2), 30);
        assertEquals(30, map.getPixel(0, 2));
    }

    @Test
        //this test the function that get (Pixel2D p, int v)
    void testSetPixel() {
        int[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};// creat array
        Pixel2D p = new Index2D(2, 1);
        Map map = new Map(data);
        map.setPixel(1, 1, 10);//the use for the function
        // map.setPixel(p, 11);
        assertEquals(10, map.getPixel(1, 1));
        //  assertEquals(11, map.getPixel(p));
    }

    @Test
        //creat array  and the object in name myMap are get the array and test with the function fill
    void fill() {
        int[][] array = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        Map myMap = new Map(array);
        int numFilled = myMap.fill(new Index2D(1, 1), 0);//return the number of pixel are fill
        assertEquals(9, numFilled);

    }

    @Test
        //checks the number of pixels that the function has colored and saves as an integer and equal with a array that represents the map
    void fill1() {
        int[][] map = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        Map testMap = new Map(map);
        int numFilled1 = testMap.fill(new Index2D(1, 1), 2);//return the number of pixel are fill
        assertEquals(9, numFilled1);
    }


    @Test
        //get array  in index are represents a start and the else index represent the end  and in the array
        //there are obstacle that it is forbidden to reach it but an alternative way must be found and return array with the distances
    void shortestPath1() {
        int[][] input = {
                {0, -1, -2, -1},
                {-2, -2, -1, -2},
                {-2, -1, -2, -1},
                {-2, -1, -2, -1}
        };

        Map map = new Map(input);
        Pixel2D start = new Index2D(0, 0);
        Pixel2D destination = new Index2D(3, 3);
        int obsColor = -1;

        Pixel2D[] result = map.shortestPath(start, destination, obsColor);
        Pixel2D[] expected = null;

        assertTrue(result == expected);
    }



    @Test
    void isInside() {
// Create a map with dimensions 3x3
        int[][] input = {
                {0, 0, 0},
                {0, -1, 0},
                {0, 0, 0}};

        Map map = new Map(input);
        Pixel2D pTest = new Index2D(3, 0);
        assertFalse(map.isInside(pTest));
    }


    @Test
    void isCyclic() {
        Map map = new Map(3, 3, 0);

        assertTrue(map.isCyclic());
    }



    @Test
    void allDistance1() {
        // Create a large map
        int[][] myArray = {{-2, -1, -1}
                , {-2, -2, -2},
                {-2, -2, -2}};
        Map myMap = new Map(myArray);
        myMap.setCyclic(true);
        Pixel2D start = new Index2D(0, 0);
        int obsColor = -1;
        Map2D result = myMap.allDistance(start, obsColor);
        int[][] resultArray = result.getMap();

        int[][] expected = {
                {0, 1, 2},
                {1, -1, 3},
                {2, 3, 4}};

    }
}



















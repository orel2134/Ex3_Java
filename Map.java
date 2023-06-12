import java.util.ArrayList;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */

public class Map implements Map2D {

	private int[][] _map;
	private boolean _cyclicFlag = true;
	public int[][] getMatrix;

	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 *
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {
		init(w, h, v);
	}

	/**
	 * Constructs a square map (size*size).
	 *
	 * @param size
	 */
	public Map(int size) {
		this(size, size, 0);
	}

	/**
	 * Constructs a map from a given 2D array.
	 *
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

	@Override
	public void init(int w, int h, int v) {//this function get three integers and create an array in the value of the function get
		/////// add your code below ///////
		double[][] mat = new double[w][h];//create the array
		for (int i = 0; i < w; i++) {//loop on the width
			for (int j = 0; j < h; j++) {//loop on the height
				mat[i][j] = v;//the v input is fill the array
			}
		}
		///////////////////////////////////
	}

	@Override
	public void init(int[][] arr) {
		/////// add your code below ///////
		if (arr == null) {

		}
		if (arr.length == 0) {
		}
		int num = arr[0].length;
		for (int i = 0; i < arr.length; i++)
			if (arr[i].length != num) {
				throw new RuntimeException("");
			}

		this._map = new int[arr.length][arr[0].length];// create a new map with dimensions matching the input array
		for (int i = 0; i < arr.length; i++) {//loop on the width
			for (int j = 0; j < arr[0].length; j++) {//loop on the height
				_map[i][j] = arr[i][j];// copy the values from the input array to the map
			}
		}
	}

	@Override
	public int[][] getMap() {
		int[][] ans = null;
		/////// add your code below ///////
		// get the dimensions of the map
		int theHeight = this._map.length;
		int theWidth = this._map[0].length;
		ans = new int[theHeight][theWidth];// create a new 2D array with the same dimensions as the map
		for (int i = 0; i < theHeight; i++) {
			for (int j = 0; j < theWidth; j++) {
				ans[i][j] = this._map[i][j];
			}
		}
		///////////////////////////////////
		return ans;// return the copy of the map as a 2D array
	}

	@Override
	/////// add your code below ///////
	public int getWidth() {
		if (this._map[0].length == 0)// check if the map is empty
			return 0;
		int getWidth = this._map[0].length;// retrieve the width of the map
		return getWidth;//return the width of the map
	}

	@Override
	/////// add your code below ///////
	public int getHeight() {
		if (this._map.length == 0)//check if the map heigth equal to zero
			return 0;
		int getHeight = this._map.length;//update the getHeight to the height of the map
		return getHeight;
	}

	@Override
	/////// add your code below ///////
	public int getPixel(int x, int y) {//returns the value of the pixel at the specified coordinates.
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight())// Check if the coordinates are outside the map limits
			throw new RuntimeException("that invalid input");
		return _map[x][y];// return the value of the pixel at the specified coordinates
	}

	@Override
	/////// add your code below ///////
	public int getPixel(Pixel2D p) {//this function get a p object p

		return this.getPixel(p.getX(), p.getY());//and return the value of the object in the x and y
	}

	@Override
	/////// add your code below ///////
	public void setPixel(int x, int y, int v) {//take the int v and fill the map in the value of x and y
		_map[x][y] = v;
	}

	@Override
	/////// add your code below ///////
	//p The Pixel2D object representing the coordinates of the pixel
	//v The value to be assigned to the pixel
	public void setPixel(Pixel2D p, int v) {
		setPixel(p.getX(), p.getY(), v);
	}

	@Override
	/**
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */

	public int fill(Pixel2D xy, int new_v) {
		int ans = 0;
		// check if the provided pixel is inside the map and if the new value is already set
		if (!isInside(xy) || getPixel(xy) == new_v) {
			return 0;
		}

		int color = getPixel(xy); // get the color value at xy and store it in the variable color
		setPixel(xy, new_v); // set the new value at xy

		// create an ArrayList called myList to keep track of the pixels to be processed and add xy to myList
		ArrayList<Pixel2D> myList = new ArrayList<>();
		myList.add(xy);

		while (!myList.isEmpty()) {
			ans++; // increas the counter for each processed pixel
			xy = myList.remove(0); // Remove and retrieve the first pixel from myList

			Pixel2D neighbor;
			if (!_cyclicFlag) { // if the limits is not cyclic
				neighbor = new Index2D(xy.getX(), xy.getY() + 1); // right neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}

				neighbor = new Index2D(xy.getX(), xy.getY() - 1); // left neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}

				neighbor = new Index2D(xy.getX() - 1, xy.getY()); // up neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}

				neighbor = new Index2D(xy.getX() + 1, xy.getY()); // down neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}
			} else { // If the board is cyclic
				int xRight = xy.getX() + 1;
				int xLeft = xy.getX() - 1;
				int yUp = xy.getY() + 1;
				int yDown = xy.getY() - 1;

				if (xy.getX() == 0) {//if the object in coordinates x are equal to zero
					xLeft = getWidth() - 1;//update the value of  the variable to the most width
				}
				if (xy.getX() == getWidth() - 1) {//if the object in coordinates x are equal to the most width
					xRight = 0;//update the value of  the variable to zero
				}
				if (xy.getY() == 0) {//if the object in coordinates y are equal to zero
					yDown = getHeight() - 1;//update the value of  the variable to most height
				}
				if (xy.getY() == getHeight() - 1) {//if the object in coordinates y are equal to most height
					yUp = 0;//update the value of  the variable to zero
				}

				neighbor = new Index2D(xy.getX(), yUp); // Up neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}

				neighbor = new Index2D(xy.getX(), yDown); // down neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}

				neighbor = new Index2D(xRight, xy.getY()); // right neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}

				neighbor = new Index2D(xLeft, xy.getY()); // left neighbor
				if (isInside(neighbor) && getPixel(neighbor) == color) {
					setPixel(neighbor, new_v);
					myList.add(neighbor);
				}
			}
		}

		return ans; // return the number of pixels that were filled
	}


	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
		if (!isInside(p1) || !isInside(p2)) {
			return null; // return null if either of the pixels is outside the map limits.
		}

		Map2D copyMap = allDistance(p1, obsColor); // get a map with all distances from p1 to other pixels.
		int distance = copyMap.getPixel(p2); // get the distance from p1 to p2 in the copyMap.

		if (distance == -1) {
			return null; // return null if there is no path from p1 to p2 (distance is -1).
		}

		if (p1.equals(p2)) {
			Pixel2D[] ans = new Pixel2D[1]; // create an array to store the path.
			ans[0] = p1; // the path contains only p1.
			return ans;
		}

		Pixel2D[] ans = new Pixel2D[distance + 1]; // create an array to store the path.
		ans[distance] = p2; // set the last element of the path to p2.

		while (distance > 0) {
			distance--;
			Pixel2D[] neighbors = getNeighbors(p2, copyMap); // get the neighboring pixels of p2.

			for (int i = 0; i < neighbors.length; i++) {
				Pixel2D neighbor = neighbors[i];
				if (copyMap.getPixel(neighbor) == distance) {
					p2 = neighbor; // move to the neighbor pixel with the current distance.
					ans[distance] = p2; // add the neighbor pixel to the path.
					break;
				}
			}
		}

		return ans; // return the path from p1 to p2.
	}

	private Pixel2D[] getNeighbors(Pixel2D p, Map2D map) {
		int x = p.getX();
		int y = p.getY();
		int width = map.getWidth();
		int height = map.getHeight();
		boolean isCyclic = false; // set this to true if you want cyclic behavior
		Pixel2D[] neighbors;

		if (isCyclic) {
			// get cyclic neighbors by wrapping around the map limits.
			neighbors = new Pixel2D[4];
			neighbors[0] = new Index2D((x + 1) % width, y); // right neighbor
			neighbors[1] = new Index2D((x - 1 + width) % width, y); // left neighbor
			neighbors[2] = new Index2D(x, (y + 1) % height); // down neighbor
			neighbors[3] = new Index2D(x, (y - 1 + height) % height); // up neighbor
		} else {
			// get non-cyclic neighbors without wrapping around the map boundaries.
			neighbors = new Pixel2D[4];
			if (x + 1 < width) {
				neighbors[0] = new Index2D(x + 1, y); // right neighbor
			}
			if (x - 1 >= 0) {
				neighbors[1] = new Index2D(x - 1, y); // left neighbor
			}
			if (y + 1 < height) {
				neighbors[2] = new Index2D(x, y + 1); // down neighbor
			}
			if (y - 1 >= 0) {
				neighbors[3] = new Index2D(x, y - 1); // up neighbor
			}
		}

		return neighbors; // return the neighboring pixels.
	}




	@Override
	/////// add your code below ///////
	public boolean isInside(Pixel2D p) {
		// Check if the pixel coordinates are within the map limit
		if(p.getX()<this.getWidth() && p.getY()<this.getHeight() && p.getX()>=0 && p.getY()>=0)
			return true;// Pixel is inside the map limit
		return false;
	}


	@Override
	/////// add your code below ///////
	public boolean isCyclic() {
		return _cyclicFlag;//the cyclic flag are in the member
	}

	@Override
	/////// add your code below ///////
	public void setCyclic(boolean cy) {//get a value boolean and implment to the _cyclicFlag member
		this._cyclicFlag=cy;
	}

	public Pixel2D findTargetPixel(Pixel2D start, int obstacleColor) {
		Map2D mapCopy = new Map(this.getMap()); // Create a copy of the current map.
		Pixel2D targetPixel;

		// Check if the start point is outside the map boundaries.
		if (!isInside(start)) {
			mapCopy = new Map(this.getWidth(), this.getHeight(), -1); // Create a new map filled with -1.
			return null;
		}

		// Check if the start point is on an obstacle.
		if (this.getPixel(start) == obstacleColor) {
			mapCopy = new Map(this.getWidth(), this.getHeight(), -1); // Create a new map filled with -1.
			return null;
		}

		// Iterate over all the pixels in the map.
		for (int i = 1; i < this.getWidth() - 1; i++) {
			for (int j = 1; j < this.getHeight() - 1; j++) {
				// Set the obstacle pixels to -1 in the new map.
				if (this.getPixel(i, j) == obstacleColor) {
					mapCopy.setPixel(i, j, -1);
				}
				// Set pixels with values 3 or 7 to -7 in the new map.
				else if (mapCopy.getPixel(i, j) == 3 || mapCopy.getPixel(i, j) == 7) {
					mapCopy.setPixel(i, j, -7);
				}
				// Set all other pixels to -2 in the new map.
				else {
					mapCopy.setPixel(i, j, -2);
				}
			}
		}

		mapCopy.setPixel(start, 0); // Set the start pixel value to 0.
		ArrayList<Pixel2D> queue = new ArrayList<Pixel2D>();
		queue.add(start);
		boolean flag = false;

		// Perform a breadth-first search on the map.
		while (!queue.isEmpty()) {
			start = queue.remove(0);
			Pixel2D check;

			// Check the neighboring pixels based on cyclicFlag value.
			if (_cyclicFlag == false) { // Not cyclic
				check = new Index2D(start.getX(), start.getY() + 1); // Right neighbor

				if (isInside(check)) {
					// If the neighbor pixel is a target pixel, return it.
					if (mapCopy.getPixel(check) == -7) {
						return check;
					}
					// if the neighbor pixel is an unvisited pixel, set its value and add it to the queue.
					if (mapCopy.getPixel(check) == -2) {
						mapCopy.setPixel(check, 0);
						queue.add(check);
					}
				}

				// repeat the above process for the other three neighboring pixels.
				check = new Index2D(start.getX(), start.getY() - 1); // Left neighbor

				if (isInside(check)) {
					if (mapCopy.getPixel(check) == -7) { // the point that we want to return
						return check;
					}
					if (mapCopy.getPixel(check) < -1) {
						mapCopy.setPixel(check, mapCopy.getPixel(start) + 1);
						queue.add(check);
					}
				}

				check = new Index2D(start.getX() + 1, start.getY()); // down neighbor

				if (isInside(check)) {
					if (mapCopy.getPixel(check) == -7) {
						return check;
					}
					if (mapCopy.getPixel(check) < -1) {
						mapCopy.setPixel(check, mapCopy.getPixel(start) + 1);
						queue.add(check);
					}
				}

				check = new Index2D(start.getX() - 1, start.getY()); // up neighbor

				if (isInside(check)) {
					if (mapCopy.getPixel(check) == -7) {
						return check;
					}
					if (mapCopy.getPixel(check) < -1) {
						mapCopy.setPixel(check, mapCopy.getPixel(start) + 1);
						queue.add(check);
					}
				}
			} else { // if it is cyclic
				// search the neighboring pixels taking cyclic limit into account.
				int xRight = start.getX() + 1;
				int xLeft = start.getX() - 1;
				int yUp = start.getY() + 1;
				int yDown = start.getY() - 1;

				// match cyclic limit for pixels on the map's edges.
				if (start.getX() == 0) {
					xLeft = mapCopy.getWidth() - 1;
				}
				if (start.getX() == mapCopy.getWidth() - 1) {
					xRight = 0;
				}
				if (start.getY() == 0) {
					yDown = mapCopy.getHeight() - 1;
				}
				if (start.getY() == mapCopy.getHeight() - 1) {
					yUp = 0;
				}

				check = new Index2D(start.getX(), yUp); // up neighbor

				if (isInside(check)) {
					if (mapCopy.getPixel(check) == -7) {
						return check;
					}
					if (mapCopy.getPixel(check) < -1) {
						mapCopy.setPixel(check, mapCopy.getPixel(start) + 1);
						queue.add(check);
					}
				}

				// repeat the above process for the other three cyclic neighboring pixels.
				check = new Index2D(start.getX(), yDown); // down neighbor

				if (isInside(check)) {
					if (mapCopy.getPixel(check) == -7) {
						return check;
					}
					if (mapCopy.getPixel(check) < -1) {
						mapCopy.setPixel(check, mapCopy.getPixel(start) + 1);
						queue.add(check);
					}
				}

				check = new Index2D(xRight, start.getY()); // right neighbor

				if (isInside(check)) {
					if (mapCopy.getPixel(check) == -7) {
						return check;
					}
					if (mapCopy.getPixel(check) < -1) {
						mapCopy.setPixel(check, mapCopy.getPixel(start) + 1);
						queue.add(check);
					}
				}

				check = new Index2D(xLeft, start.getY()); //left neighbor

				if (isInside(check)) {
					if (mapCopy.getPixel(check) == -7) {
						return check;
					}
					if (mapCopy.getPixel(check) == -2) {
						mapCopy.setPixel(check, mapCopy.getPixel(start) + 1);
						queue.add(check);
					}
				}
			}
		}

		return null; // No target pixel found.
	}

	/**public Pixel2D[] getShortesPath( Map2D map, Pixel2D p1,Pixel2D p2,int obsColor) {
		return map.shortestPath(p1, p2, obsColor);
	}
	public Map2D getDist(Map2D map,Pixel2D p,int obsColor){
		return map.allDistance(p,obsColor);
	}
	 **/
	@Override
	/////// add your code below ///////
	public Map2D allDistance(Pixel2D start, int obsColor) {
		/////// add your code below ///////if (this._map == null) {

		Map2D ans = new Map(this.getMap());// the object is name ans is the end result.
		if (!isInside(start)) {
			ans = new Map(this.getWidth(), this.getHeight(), -1); // create a new map with all values set to -1
			return ans;
		}
		if (this.getPixel(start) == obsColor) {// check if the start pixel is an obstacle
			ans = new Map(this.getWidth(), this.getHeight(), -1);
			return ans;
		}
		for (int i = 0; i < this.getWidth()-1; i++) {
			for (int j = 0; j < this.getHeight()-1; j++) {
				if (this.getPixel(i, j) == obsColor) {
					ans.setPixel(i, j, -1);// Set obstacle pixels to -1
				} else {
					ans.setPixel(i, j, -2);// Set the not obstacle pixels to -2

				}
			}
		}
		ans.setPixel(start, 0); //set the distance of the start pixel as 0
		ArrayList<Pixel2D> myList = new ArrayList<>();// Create a list to store pixels to be visited
		myList.add(start); // Add the start pixel to the list
		myList.add(start);
		while (!myList.isEmpty()) {
			start = myList.remove(0);// Check if the map is not cyclic
			Pixel2D p;
			if (!_cyclicFlag) { // Check if the map is not cyclic
				//Check the adjacent pixels in the four directions


				p = new Index2D(start.getX(), start.getY() + 1);// Right
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {// If the pixel is unvisited
						ans.setPixel(p, ans.getPixel(start) + 1);// Update its distance as the distance of the current pixel + 1
						myList.add(p);// Add the pixel to the list for check again
					}
				}
				p = new Index2D(start.getX(), start.getY() - 1);//left
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {
						ans.setPixel(p, ans.getPixel(start) + 1);
						myList.add(p);
					}
				}

				p = new Index2D(start.getX() + 1, start.getY());//down
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {
						ans.setPixel(p, ans.getPixel(start) + 1);
						myList.add(p);
					}
				}
				p = new Index2D(start.getX() - 1, start.getY());//up
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {
						ans.setPixel(p, ans.getPixel(start) + 1);
						myList.add(p);

					}
				}
			} else { // If the map is cyclic
				// check the adjacent pixels and without the obstacle

				int xRight = start.getX() + 1;
				int xLeft = start.getX() - 1;
				int yUp = start.getY() + 1;
				int yDown = start.getY() - 1;
				xLeft = ans.getWidth() - 1;

				if (start.getX() == ans.getWidth() - 1) {//if the packmen is found in the rghitmost column then udepts him to 0
					xRight = 0;
				}
				if (start.getY() == 0) {
					yDown = ans.getHeight() - 1;
				}
				if (start.getY() == ans.getHeight() - 1) {
					yUp = 0;
				}
				p = new Index2D(start.getX(), yUp);
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {
						ans.setPixel(p, ans.getPixel(start) + 1);
						myList.add(p);
					}
				}
				p = new Index2D(start.getX(), yDown);
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {
						ans.setPixel(p, ans.getPixel(start) + 1);
						myList.add(p);
					}
				}
				p = new Index2D(xRight, start.getY());
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {
						ans.setPixel(p, ans.getPixel(start) + 1);
						myList.add(p);
					}
				}
				p = new Index2D(xLeft, start.getY());
				if (isInside(p)) {
					if (ans.getPixel(p) == -2) {
						ans.setPixel(p, ans.getPixel(start) + 1);
						myList.add(p);
					}
				}
			}
			for (int i = 0; i < ans.getWidth()-1; i++) {
				for (int j = 0; j < ans.getHeight()-1; j++) {
					if (ans.getPixel(i, j) == -2)
						ans.setPixel(i, j, -1);
				}
			}

		}

		return ans;

	}

}

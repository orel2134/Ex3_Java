import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

import java.awt.*;



/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 *
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo {
	private int _count;

	public Ex3Algo() {
		_count = 0;
	}

	@Override
	/**
	 *  Add a short description for the algorithm as a String.
	 */
	public String getInfo() {
		return null;
	}

	@Override
	/**
	 * This ia the main method - that you should design, implement and test.
	 */
	public int move(PacmanGame game) {
		if (_count == 0 || _count == 300) {
			int code = 0;
			int[][] board = game.getGame(0);
			printBoard(board);
			int blue = Game.getIntColor(Color.BLUE, code);
			int pink = Game.getIntColor(Color.PINK, code);
			int black = Game.getIntColor(Color.BLACK, code);
			int green = Game.getIntColor(Color.GREEN, code);
			System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
			String pos = game.getPos(code).toString();
			System.out.println("Pacman coordinate: " + pos);
			GhostCL[] ghosts = game.getGhosts(code);
			printGhosts(ghosts);
			int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
		}

		GhostCL[] ghosts = game.getGhosts(0);
		String[] pacmans = game.getPos(0).split(",");
		int x = Integer.parseInt(pacmans[0]);
		int y = Integer.parseInt(pacmans[1]);
		Pixel2D pacmanPos = new Index2D(x,y);
		int mindistance = Integer.MAX_VALUE;
		int index = 0;
		int[][] board1 = game.getGame(0);
		Map2D map = new Map(board1);

		Pixel2D nearGhost = null;


		for (int i = 0; i < ghosts.length;i++){

			String[] pos1 = ghosts[i].getPos(0).split(",");
			int x1 = Integer.parseInt(pos1[0]);
			int y1= Integer.parseInt(pos1[1]);
			Pixel2D palce = new Index2D(x1, y1);
			Pixel2D[] distance = map.shortestPath(pacmanPos, palce, 1);
			if (distance != null && ghosts[i].getStatus() > 0) {
				if (distance.length < mindistance) {
					mindistance = distance.length;
					nearGhost = palce;

				}
			}
		}

		Map map1 = new Map(board1);
		if (ghosts[0].remainTimeAsEatable(0) > 0.7) {
			if (pacmanPos.getX() != 11 || pacmanPos.getY() != 14) {
				int dir = moveeToGhost(pacmanPos, nearGhost, map1);
			}

		}else{
			Pixel2D pixel = map1.findTargetPixel(pacmanPos, 1);
			moveeToGhost(pacmanPos, pixel,map1);

		}


		_count++;
		int dir1 = getCoin(game);
		return dir1;
	}
	private Pixel2D[] getShortesPath( Map2D map, Pixel2D p1,Pixel2D p2,int obsColor) {
		return map.shortestPath(p1, p2, obsColor);
	}
	private Map2D getDist(Map2D map,Pixel2D p,int obsColor){
		return map.allDistance(p,obsColor);
	}



	public int moveeToGhost(Pixel2D p1, Pixel2D p2, Map map) {

		// galculate the shortest path between p1 and p2 on the map
		Pixel2D[] a = map.shortestPath(p1, p2, 1);

		// get the second element of the path array
		Pixel2D b = a[1];

		// check if the map is not cyclic
		if (!map.isCyclic()) {
			// check the x-coordinate of b relative to p1's x-coordinate
			if (b.getX() == p1.getX()) {
				// if b is above p1, return the constant for moving up, otherwise return the constant for moving down
				if (b.getY() > p1.getY()) {
					return Game.UP;
				} else {
					return Game.DOWN;
				}
			} else {
				// if b is to the right of p1, return the constant for moving right, otherwise return the constant for moving left
				if (b.getX() > p1.getX()) {
					return Game.RIGHT;
				} else {
					return Game.LEFT;
				}
			}
		} else {
			// if the map is cyclic, check the position of b

			// check if b is at the top or bottom edge of the map
			if (b.getY() == 0 && b.getY() == map.getHeight() - 1) {
				return Game.UP;
			}
			if (b.getY() == map.getHeight() - 1 && b.getY() == 0) {
				return Game.DOWN;
			}

			// check if b is at the right or left edge of the map
			if (b.getX() == map.getWidth() - 1 && b.getX() == 0) {
				return Game.RIGHT;
			}
			if (b.getX() == 0 && b.getY() == map.getWidth() - 1) {
				return Game.LEFT;
			}

			// if b is not at the edge, determine the direction based on the x and y coordinates

			// check the x-coordinate of b relative to p1's x-coordinate
			if (b.getX() == p1.getX()) {
				// if b is above p1, return the constant for moving up, otherwise return the constant for moving down
				if (b.getY() > p1.getY()) {
					return Game.UP;
				} else {
					return Game.DOWN;
				}
			} else {
				// if b is to the right of p1, return the constant for moving right, otherwise return the constant for moving left
				if (b.getX() > p1.getX()) {
					return Game.RIGHT;
				} else {
					return Game.LEFT;
				}
			}
		}
	}

	private static void printBoard(int[][] b) {
		for (int y = 0; y < b[0].length; y++) {
			for (int x = 0; x < b.length; x++) {
				int v = b[x][y];
				System.out.print(v + "\t");
			}
			System.out.println();
		}
	}

	private static void printGhosts(GhostCL[] gs) {
		for (int i = 0; i < gs.length; i++) {
			GhostCL g = gs[i];
			System.out.println(i + ") status: " + g.getStatus() + ",  type: " + g.getType() + ",  pos: " + g.getPos(0) + ",  time: " + g.remainTimeAsEatable(0));
		}
	}

	private static int randomDir() {
		int[] dirs = {Game.UP, Game.LEFT, Game.DOWN, Game.RIGHT};
		int ind = (int) (Math.random() * dirs.length);
		return dirs[ind];
	}

	private static int getCoin(PacmanGame game) {
		// get the ghosts in the game
		GhostCL[] ghosts = game.getGhosts(0);

		// get the position of the Pacman
		String[] pacman = game.getPos(0).split(",");
		Pixel2D pacmanPos = new Index2D(Integer.parseInt(pacman[0]), Integer.parseInt(pacman[1]));

		// get the game board
		int[][] board = game.getGame(0);

		// create a map based on the game board
		Map map = new Map(board);

		// find the nearest target pixel to Pacman
		Pixel2D nearPoint = map.findTargetPixel(pacmanPos, 1);

		// calculate the distances between all pixels and Pacman's position
		Map2D map1 = map.allDistance(pacmanPos, 1);

		// check if there is no near point found
		if (nearPoint == null) {
			System.out.println("Pacman location: " + pacmanPos);
		}

		System.out.println("Pacman location: " + pacmanPos.toString() + ", Closest point: " + nearPoint.toString());

		// check if the nearPoint is a specific coordinate
		if (nearPoint.equals(new Index2D(18, 15))) {
			System.out.println("");
		}

		// find the shortest path from pacman's position to the nearPoint
		Pixel2D[] path = map.shortestPath(pacmanPos, nearPoint, 1);

		// check if the second-to-last element of the path is null
		if (path[path.length - 2] == null) {
			System.out.println("");
		}

		// search the direction to move based on pacman's position and the second-to-last point in the path

		// check if Pacman is at the right edge of the map and the second-to-last point is at the left edge
		if (pacmanPos.getX() == map.getWidth() - 1 && path[path.length - 2].getX() == 0) {
			return Game.RIGHT;
		}

		// check if Pacman is at the left edge of the map and the second-to-last point is at the right edge
		if (pacmanPos.getX() == 0 && path[path.length - 2].getX() == map.getWidth() - 1) {
			return Game.LEFT;
		}

		// check if the second-to-last point has the same x-coordinate as Pacman's position
		if (path[path.length - 2].getX() == pacmanPos.getX()) {
			// if the second-to-last point is above Pacman, return the constant for moving up, otherwise return the constant for moving down
			if (path[path.length - 2].getY() > pacmanPos.getY()) {
				return Game.UP;
			} else {
				return Game.DOWN;
			}
		} else {
			// if the second-to-last point is to the right of Pacman, return the constant for moving right, otherwise return the constant for moving left
			if (path[path.length - 2].getX() > pacmanPos.getX()) {
				return Game.RIGHT;
			} else {
				return Game.LEFT;
			}
		}
	}

}





import java.util.ArrayList;
import java.util.Arrays;

public class Snake {

	private int[] headPos, tailPos, neckPos;
	private ArrayList<int[]> positions; // First index is tail
	private int length;
	private int growthIncrement = 4;
	private boolean[] dir;
	private boolean isGrowing;

	private long time, dTime;
	private int gameSpeed = 100; // In milliseconds

	public Snake(int[] headPos, int initLength) {
		this.headPos = headPos;
		this.length = initLength;
		dir = new boolean[4]; // Up, right, down, left
		isGrowing = true;
		positions = new ArrayList<int[]>();
		int[] newHeadPos = new int[] { headPos[0], headPos[1] };
		neckPos = new int[] { headPos[0], headPos[1] };
		positions.add(newHeadPos);
		tailPos = positions.get(0);
	}

	public int[] getHeadPos() {
		return headPos;
	}

	public int[] getTailPos() {
		return tailPos;
	}

	public int getLength() {
		return length;
	}

	public int[] getNeckPos() {
		return neckPos;
	}

	public void move() {
		dTime += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();

		if (dTime > gameSpeed) {
			neckPos = new int[] { headPos[0], headPos[1] };
			if (dir[0] || dir[1] || dir[2] || dir[3]) {
				if (dir[0]) { // Up
					headPos[1]--;
				} else if (dir[1]) { // Right
					headPos[0]++;
				} else if (dir[2]) { // Down
					headPos[1]++;
				} else if (dir[3]) { // Left
					headPos[0]--;
				}
				int[] newHeadPos = new int[] { headPos[0], headPos[1] };
				positions.add(newHeadPos);
			}
			dTime = 0;
			
			if (positions.size() > length) {
				isGrowing = false;
				tailPos = positions.get(0);
				positions.remove(0);
			} else if (positions.size() <= length) {
				isGrowing = true;
			}
		}
	}

	public void grow() {
		length += growthIncrement;
	}

	public boolean isGrowing() {
		return isGrowing;
	}

	public void changeDir(int dirPos) {
		if (dirPos == 0 && dir[2] != true || dirPos == 1 && dir[3] != true || dirPos == 2 && dir[0] != true
				|| dirPos == 3 && dir[1] != true) {
			Arrays.fill(dir, false);
			dir[dirPos] = true;
			move();
		}
	}
}

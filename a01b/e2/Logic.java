package a01b.e2;

import java.util.List;

public interface Logic {
	
	public static enum Pieces{
		EMPTY,
		K,
		P
	}
	
	public List<Pair<Integer, Integer>> getEnemiesCoord();

	public boolean moveIfValid(Pair<Integer,Integer> coords);

	
}

package a04.e2;

import java.util.Map;

public interface Logic {
	
	public Map<Pair<Integer,Integer>,Pieces> getPiecesPositions();
	
	public void move(Pair<Integer,Integer> coords);
	
	public boolean isOver();

}

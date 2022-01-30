package a03c.e2;

import java.util.Map;

public interface Logic {

	/***
	 * head is biggest number
	 */
	public Map<Pair<Integer, Integer>, Integer> snakePieces();

	public void moveSnake();
	
	public void moveSnake(Pair<Integer, Integer> coords);
}

package a03c.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {

	private final int size;
	private final Map<Pair<Integer, Integer>, Integer> snakePieces;
	private Direction lastDir;

	private enum Direction {
		UP(new Pair<>(0, -1)), DOWN(new Pair<>(0, 1)), LEFT(new Pair<>(-1, 0)), RIGHT(new Pair<>(1, 0)),;

		private Pair<Integer, Integer> i;

		private Direction(Pair<Integer, Integer> coords) {
			this.i = coords;
		}

	}

	public LogicImpl(int size) {
		this.size = size;
		this.snakePieces = new HashMap<>();
		this.lastDir = Direction.DOWN;
		Random random = new Random();
		var init = random.nextInt(this.size);
		snakePieces.put(new Pair<>(init, 0), snakePieces.size());
		snakePieces.put(new Pair<>(init, 1), snakePieces.size());
	}

	@Override
	public Map<Pair<Integer, Integer>, Integer> snakePieces() {
		return Map.copyOf(snakePieces);
	}

	@Override
	public void moveSnake() {
		moveSnake(new Pair<>(headPos().getX()+lastDir.i.getX(),headPos().getY()+lastDir.i.getY()));
	}

	@Override
	public void moveSnake(Pair<Integer, Integer> coords) {
		if(coords.getX()>=0&&coords.getX()<this.size&&coords.getY()>=0&&coords.getY()<this.size&&snakePieces.containsKey(coords)==false) {
			//Sono coordinate valide ma sono adiacenti alla testa?
			
			if(Math.abs(coords.getX()-headPos().getX())==1&&Math.abs(coords.getY()-headPos().getY())==0) {
				//Laterale
				
				if(coords.getX()>headPos().getX()) {
					//La nuova testa è più a dx
					lastDir=Direction.RIGHT;
				}
				else {
					//La nuova testa è più a sx
					lastDir=Direction.LEFT;
				}
				snakePieces.put(coords, snakePieces.size());
			}
			else if(Math.abs(coords.getX()-headPos().getX())==0&&Math.abs(coords.getY()-headPos().getY())==1) {
				//In verticale
				if(coords.getY()>headPos().getY()) {
					//La nuova testa è più giù
					lastDir=Direction.DOWN;
				}
				else {
					//La nuova testa è più a su
					lastDir=Direction.UP;
				}
				snakePieces.put(coords, snakePieces.size());
			}
		}

	}
	
	private Pair<Integer, Integer> headPos(){
		return snakePieces.entrySet().stream().sorted((a,b)->b.getValue().compareTo(a.getValue())).findFirst().get().getKey();
	}

}

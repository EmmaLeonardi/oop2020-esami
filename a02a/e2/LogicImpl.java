package a02a.e2;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

	private final int size;
	private Pair<Integer, Integer> kingPos;
	private Set<Pair<Integer, Integer>> enemiesPos=new HashSet<>();
	private final int ENEMIES;
	private final Random random;

	public LogicImpl(int size) {
		this.size = size;
		ENEMIES=size;
		random = new Random();
		kingPos = new Pair<>(random.nextInt(this.size), this.size-1);
		while (enemiesPos.size() < ENEMIES) {
			enemiesPos.add(new Pair<>(random.nextInt(size), random.nextInt(size - 1)));
		}
	}

	@Override
	public List<Pair<Integer, Integer>> piecesPos() {
		return List.copyOf(enemiesPos);
	}

	private void eat(Pair<Integer, Integer> p) {
		enemiesPos.remove(p);
		kingPos = p;
	}

	@Override
	public boolean next() {
		var dx = new Pair<>(kingPos.getX() - 1, kingPos.getY() - 1);
		var sx = new Pair<>(kingPos.getX() + 1, kingPos.getY() - 1);
		var up = new Pair<>(kingPos.getX(), kingPos.getY() - 1);
		if (enemiesPos.contains(dx)) {
			eat(dx);
			return true;
		} else if (enemiesPos.contains(sx)) {
			eat(sx);
			return true;
		} else if (enemiesPos.contains(up) == false && up.getY() >= 0) {
			kingPos = new Pair<>(up.getX(), up.getY());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Pair<Integer, Integer> kingPos() {
		return new Pair<>(kingPos.getX(), kingPos.getY());
	}

}

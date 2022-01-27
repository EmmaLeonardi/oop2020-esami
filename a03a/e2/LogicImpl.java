package a03a.e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

	private final int size;
	private Pair<Integer, Integer> towerPos;
	private final Set<Pair<Integer, Integer>> enemiesPos;

	public LogicImpl(int size) {
		this.size = size;
		var random = new Random();
		this.towerPos = new Pair<>(random.nextInt(size), random.nextInt(size));
		this.enemiesPos = new HashSet<>();
		while (enemiesPos.size() < this.size) {
			var pos = new Pair<Integer, Integer>(random.nextInt(size), random.nextInt(size));
			if (!pos.equals(towerPos)) {
				// Se non è nella posizione della torre
				enemiesPos.add(pos);
			}
		}
	}

	@Override
	public Set<Pair<Integer, Integer>> enemiesPos() {
		return Set.copyOf(enemiesPos);
	}

	@Override
	public Set<Pair<Integer, Integer>> possibleMoves() {
		Set<Pair<Integer, Integer>> moves = new HashSet<>();
		// Right
		int x = towerPos.getX(), y = towerPos.getY();
		while (x < this.size && !enemiesPos.contains(new Pair<>(x, y))) {
			x++;
			moves.add(new Pair<>(x, y));
		}
		// Left
		x = towerPos.getX();
		y = towerPos.getY();
		while (x >= 0 && !enemiesPos.contains(new Pair<>(x, y))) {
			x--;
			moves.add(new Pair<>(x, y));
		}
		// Up
		x = towerPos.getX();
		y = towerPos.getY();
		while (y >= 0 && !enemiesPos.contains(new Pair<>(x, y))) {
			y--;
			moves.add(new Pair<>(x, y));
		}
		// Down
		x = towerPos.getX();
		y = towerPos.getY();
		while (y < this.size && !enemiesPos.contains(new Pair<>(x, y))) {
			y++;
			moves.add(new Pair<>(x, y));
		}
		return enemiesPos.size()==0?Set.of():moves;
	}

	@Override
	public Pair<Integer, Integer> getTowerPos() {
		return new Pair<>(towerPos.getX(), towerPos.getY());
	}

	@Override
	public boolean move(Pair<Integer, Integer> coords) {
		if (coords.getX() >= 0 && coords.getX() < this.size && coords.getY() >= 0 && coords.getY() < this.size) {
			this.towerPos = new Pair<>(coords.getX(), coords.getY());
			enemiesPos.remove(coords);
			return enemiesPos.size() != 0;
		}
		return false;
	}

}

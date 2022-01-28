package a03b.e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

	private final int size;
	private Pair<Integer, Integer> bishopPos;
	private final Set<Pair<Integer, Integer>> enemiesPos;

	public LogicImpl(int size) {
		this.size = size;
		var random = new Random();
		this.bishopPos = new Pair<>(random.nextInt(size), random.nextInt(size));
		this.enemiesPos = new HashSet<>();
		while (enemiesPos.size() < this.size) {
			var pos = new Pair<Integer, Integer>(random.nextInt(size), random.nextInt(size));
			if (!pos.equals(bishopPos)) {
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
		if (enemiesPos.size() == 0) {
			return Set.of();
		}

		Set<Pair<Integer, Integer>> moves = new HashSet<>();
		// Up-Right
		int x = bishopPos.getX(), y = bishopPos.getY();
		while (x < this.size && y >= 0 && !enemiesPos.contains(new Pair<>(x, y))) {
			x++;
			y--;
			moves.add(new Pair<>(x, y));
		}
		// Up-Left
		x = bishopPos.getX();
		y = bishopPos.getY();
		while (x >= 0 && y >= 0 && !enemiesPos.contains(new Pair<>(x, y))) {
			x--;
			y--;
			moves.add(new Pair<>(x, y));
		}
		// Down-Right
		x = bishopPos.getX();
		y = bishopPos.getY();
		while (x < this.size && y < this.size && !enemiesPos.contains(new Pair<>(x, y))) {
			x++;
			y++;
			moves.add(new Pair<>(x, y));
		}
		// Down-Left
		x = bishopPos.getX();
		y = bishopPos.getY();
		while (x >= 0 && y < this.size && !enemiesPos.contains(new Pair<>(x, y))) {
			x--;
			y++;
			moves.add(new Pair<>(x, y));
		}
		return moves;
	}

	@Override
	public Pair<Integer, Integer> getBishopPos() {
		return new Pair<>(bishopPos.getX(), bishopPos.getY());
	}

	@Override
	public boolean move(Pair<Integer, Integer> coords) {
		if (coords.getX() >= 0 && coords.getX() < this.size && coords.getY() >= 0 && coords.getY() < this.size) {
			this.bishopPos = new Pair<>(coords.getX(), coords.getY());
			enemiesPos.remove(coords);
			return enemiesPos.size() != 0;
		}
		return false;
	}

}

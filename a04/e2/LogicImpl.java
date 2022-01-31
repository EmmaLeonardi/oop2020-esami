package a04.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {

	private final int size;
	private final Map<Pair<Integer, Integer>, Pieces> grid;
	private final static int NPAWNS = 3;

	public LogicImpl(final int size) {
		this.size = size;
		this.grid = new HashMap<>();
		// Un re e 3 pedine
		Random random = new Random();
		this.grid.put(new Pair<>(random.nextInt(this.size), random.nextInt(this.size)), Pieces.KING);
		while (grid.size() < NPAWNS + 1) {
			grid.putIfAbsent(new Pair<>(random.nextInt(this.size), random.nextInt(this.size)), Pieces.PAWN);
		}
	}

	@Override
	public Map<Pair<Integer, Integer>, Pieces> getPiecesPositions() {
		return Map.copyOf(grid);
	}

	@Override
	public void move(final Pair<Integer, Integer> coords) {
		if (coords.getX() >= 0 && coords.getX() < this.size && coords.getY() >= 0 && coords.getY() < this.size) {
			Pair<Integer, Integer> uniquePos = null;
			if (grid.containsValue(Pieces.KING)) {
				// Si muove come re, 1 cella di distanza
				uniquePos = getUniquePiecesPos(Pieces.KING);
				if (Math.abs(coords.getX() - uniquePos.getX()) <= 1
						&& Math.abs(coords.getY() - uniquePos.getY()) <= 1) {
					// Mossa valida
					if (grid.get(coords) == null) {
						// Non c'era nulla, non ho mangiato
						grid.put(coords, Pieces.KING);
					} else {
						grid.put(coords, Pieces.HORSE);
					}
					grid.remove(uniquePos);
				}
			} else if (grid.containsValue(Pieces.HORSE)) {
				uniquePos = getUniquePiecesPos(Pieces.HORSE);
				if (Math.abs(coords.getX() - uniquePos.getX()) == 1 && Math.abs(coords.getY() - uniquePos.getY()) == 2
						|| Math.abs(coords.getX() - uniquePos.getX()) == 2
								&& Math.abs(coords.getY() - uniquePos.getY()) == 1) {
					// Mossa valida
					if (grid.get(coords) == null) {
						// Non c'era nulla, non ho mangiato
						grid.put(coords, Pieces.HORSE);
					} else {
						grid.put(coords, Pieces.KING);
					}
					grid.remove(uniquePos);
				}
			}
		}
	}

	private Pair<Integer, Integer> getUniquePiecesPos(Pieces piece) {
		for (var elem : grid.entrySet()) {
			if (elem.getValue().equals(piece)) {
				return elem.getKey();
			}
		}
		return null;
	}

	@Override
	public boolean isOver() {
		return !grid.containsValue(Pieces.PAWN);
	}

}

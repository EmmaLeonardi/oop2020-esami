package a01b.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

	private final Map<Pair<Integer, Integer>, Pieces> grid;
	private final int size;
	private final Random randomGenerator = new Random();
	private static final int SAFEAREA = 2;
	private static final int ENEMYNUMBER = 3;
	private Pair<Integer, Integer> kingPos;

	public LogicImpl(int size) {
		this.size = size;
		grid = new HashMap<>();
		while (grid.size()<ENEMYNUMBER) {
			var p = new Pair<>(randomGenerator.nextInt(this.size), (randomGenerator.nextInt(this.size - SAFEAREA)));
			grid.put(p,Pieces.P);
			System.out.println(p.toString());
		}
		kingPos = new Pair<>(this.size - 1, this.size - 1);
		grid.put(kingPos, Pieces.K);
	}

	@Override
	public List<Pair<Integer, Integer>> getEnemiesCoord() {
		return grid.entrySet().stream().filter(a -> a.getValue().equals(Pieces.P)).map(a -> a.getKey())
				.collect(Collectors.toList());
	}

	@Override
	public boolean moveIfValid(Pair<Integer, Integer> coords) {
		 if (Math.abs(coords.getX() - kingPos.getX()) > 1 || Math.abs(coords.getY() - kingPos.getY()) > 1) {
	            return false;
	        } else {
	            for (int i = coords.getX() - 1; i <= coords.getX() + 1; i++) {
	                // Riga sopra
	                if (grid.containsKey(new Pair<>(i, coords.getY() - 1))) {
	                    if (grid.get(new Pair<>(i, coords.getY() - 1)).equals(Pieces.P)) {
	                        return false;
	                    }
	                }
	            }
	            if (grid.getOrDefault(new Pair<>(coords.getX() - 1, coords.getY()), Pieces.EMPTY).equals(Pieces.P)
	                    || grid.getOrDefault(new Pair<>(coords.getX() + 1, coords.getY()), Pieces.EMPTY).equals(Pieces.P)
	                    || grid.getOrDefault(new Pair<>(coords.getX(), coords.getY() + 1), Pieces.EMPTY).equals(Pieces.P)) {
	                return false;
	            }
	        }

	        grid.remove(kingPos);
	        grid.remove(coords);
	        kingPos = coords;
	        grid.put(kingPos, Pieces.K);
	        return true;
		}
		
	}


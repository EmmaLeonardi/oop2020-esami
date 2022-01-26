package a02c.e2;

import java.nio.file.DirectoryStream.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

	private final Map<Pair<Integer, Integer>, Integer> cells;
	private final int size;

	public LogicImpl(int size) {
		this.size=size;
		this.cells = new HashMap<>();
	}

	@Override
	public Optional<String> press(Pair<Integer, Integer> coords) {
		if (!cells.containsKey(coords)) {
			cells.put(coords, cells.size());
			return Optional.of(Integer.toString(cells.size()));
		}
		return Optional.empty();
	}

	@Override
	public boolean isWinning(Pair<Integer, Integer> coords) {
		return areAllAdjacent(cells.entrySet().stream().filter(a -> Math.abs(a.getValue() - cells.get(coords)) < 4)
				.filter(a -> Math.abs(a.getKey().getX() - coords.getX()) <= 1
						&& Math.abs(a.getKey().getY() - coords.getY()) <= 1)
				.sorted((a, b) -> a.getValue().compareTo(b.getValue())).map(a -> a.getKey())
				.collect(Collectors.toList()));
	}

	private boolean areAllAdjacent(final List<Pair<Integer, Integer>> coords) {
		// Controllo che siano 4 caselle adiacenti
		if (coords.size() != 4) {
			return false;
		}
		for (int i = 0; i < coords.size(); i++) {
			for (int j = i; j < coords.size(); j++) {
				// Controllo adiacenza
				if (Math.abs(coords.get(i).getX() - coords.get(j).getX()) > 1
						&& Math.abs(coords.get(i).getY() - coords.get(j).getY()) > 1) {
					return false;
				}
				// Controllo vicinanza del bordo
				if (coords.get(i).getX() == 0 || coords.get(i).getX() == this.size - 1 || coords.get(i).getY() == 0
						|| coords.get(i).getY() == this.size - 1) {
					return false;
				}
			}
		}
		return true;
	}

}

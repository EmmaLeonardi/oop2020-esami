package a03c.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TableImpl<R, C, V> implements Table<R, C, V> {

	private final Map<Pair<R, C>, V> map;

	public TableImpl(final Map<Pair<R, C>, V> map) {
		this.map = new HashMap<>(map);
	}

	@Override
	public Set<R> rows() {
		return map.keySet().stream().map(k -> k.getX()).collect(Collectors.toSet());
	}

	@Override
	public Set<C> columns() {
		return map.keySet().stream().map(k -> k.getY()).collect(Collectors.toSet());
	}

	@Override
	public Map<C, Map<R, V>> asColumnMap() {
		Map<C, Map<R, V>> colMap = new HashMap<>();
		map.keySet().forEach(k -> {
			colMap.put(k.getY(), new HashMap<R, V>
				(map.entrySet().stream().filter(e -> e.getKey().getY().equals(k.getY()))
					.collect(Collectors.toMap(e->e.getKey().getX(), e->e.getValue()))));
		});
		return colMap;
	}

	@Override
	public Map<R, Map<C, V>> asRowMap() {
		Map<R, Map<C, V>> rowMap = new HashMap<>();
		map.keySet().forEach(k -> {
			rowMap.put(k.getX(), new HashMap<C, V>
				(map.entrySet().stream().filter(e -> e.getKey().getX().equals(k.getX()))
					.collect(Collectors.toMap(e->e.getKey().getY(), e->e.getValue()))));
		});
		return rowMap;
	}

	@Override
	public Optional<V> getValue(R row, C column) {
		return Optional.ofNullable(map.get(new Pair<>(row, column)));
	}

	@Override
	public void putValue(R row, C column, V value) {
		map.put(new Pair<>(row, column), value);

	}

}

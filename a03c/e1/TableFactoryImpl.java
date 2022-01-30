package a03c.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TableFactoryImpl implements TableFactory {

	@Override
	public <R, C, V> Table<R, C, V> fromMap(Map<Pair<R, C>, V> map) {
		return new TableImpl<R, C, V>(map);
	}

	@Override
	public <R, C, V> Table<R, C, V> fromFunction(Set<R> rows, Set<C> columns, BiFunction<R, C, V> valueFunction) {
		Map<Pair<R, C>, V> map = new HashMap<>();
		rows.forEach(r -> {
			columns.forEach(c -> {
				map.put(new Pair<R, C>(r, c), valueFunction.apply(r, c));
			});
		});
		return fromMap(map);
	}

	@Override
	public <G> Table<G, G, Boolean> graph(Set<Pair<G, G>> edges) {

		return fromFunction(edges.stream().map(p -> p.getX()).collect(Collectors.toSet()),
				edges.stream().map(p -> p.getX()).collect(Collectors.toSet()),
				(a, b) -> edges.contains(new Pair<>(a, b)));

	}

	@Override
	public <V> Table<Integer, Integer, V> squareMatrix(V[][] values) {
		Map<Pair<Integer, Integer>, V> map = new HashMap<>();
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				map.put(new Pair<>(i, j), values[i][j]);
			}
		}
		return fromMap(map);
	}

}

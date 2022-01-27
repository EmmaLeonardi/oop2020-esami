package a03a.e1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class GroupingFactoryImpl implements GroupingFactory {

	@Override
	public <G, V> Grouping<G, V> fromPairs(Iterable<Pair<G, V>> values) {
		Map<V, G> map = new HashMap<>();
		values.forEach(a -> map.put(a.getY(), a.getX()));
		return fromFunction(map.keySet(), a -> map.get(a));
	}

	@Override
	public <V> Grouping<V, V> singletons(Set<V> values) {
		return fromFunction(values, a -> a);
	}

	@Override
	public <V> Grouping<V, V> withChampion(Set<V> values, BiPredicate<V, V> sameGroup, Predicate<V> champion) {
		List<V> keys=new ArrayList<>();
		Map<V,V> map=new HashMap<>();
		values.stream().filter(a->champion.test(a)).forEach(a->keys.add(a));
		values.forEach(v->{
			keys.forEach(k->{
				if(sameGroup.test(k, v)) {
					map.put(v, k);
				}
			});
		});
		
		return fromFunction(map.keySet(), map::get);
	}

	@Override
	public <G, V> Grouping<G, V> fromFunction(Set<V> values, Function<V, G> mapper) {
		return new GroupingImpl<G,V>(values, mapper);
	}

}

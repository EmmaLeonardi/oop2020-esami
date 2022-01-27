package a03a.e1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class GroupingImpl<G, V> implements Grouping<G, V> {

	private final Map<G, Set<V>> values;

	public GroupingImpl(Set<V> values, Function<V, G> mapper) {
		this.values = new HashMap<>();
		values.forEach(v -> {
			if (!this.values.containsKey(mapper.apply(v))) {
				this.values.put(mapper.apply(v), new HashSet<>());
			}
			this.values.get(mapper.apply(v)).add(v);
		});
	}
	
	private GroupingImpl(Map<G,Set<V>> map) {
		this.values=Map.copyOf(map);
	}

	@Override
	public Set<V> getValuesOfGroup(G group) {
		return Set.copyOf(values.get(group));
	}

	@Override
	public Set<G> getGroups() {
		return Set.copyOf(values.keySet());
	}

	@Override
	public Optional<G> getGroupOf(V data) {
		return values.entrySet().stream().filter(e -> e.getValue().contains(data)).map(e -> e.getKey()).findFirst();
	}

	@Override
	public Map<G, Set<V>> asMap() {
		return Map.copyOf(values);
	}

	@Override
	public Grouping<G, V> combineGroups(G initial1, G initial2, G result) {
		Map<G,Set<V>> mappedValues=new HashMap<>(values);
		
		if(!mappedValues.containsKey(result)) {
			mappedValues.put(result, new HashSet<>());
		}
		mappedValues.get(result).addAll(mappedValues.get(initial1));
		mappedValues.get(result).addAll(mappedValues.get(initial2));
		mappedValues.remove(initial1);
		mappedValues.remove(initial2);
		
		return new GroupingImpl<G,V>(mappedValues);
	}

}

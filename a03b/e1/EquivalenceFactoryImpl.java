package a03b.e1;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EquivalenceFactoryImpl implements EquivalenceFactory {

	@Override
	public <X> Equivalence<X> fromPartition(Set<Set<X>> partition) {
		return new EquivalenceImpl<X>(partition);
	}

	@Override
	public <X> Equivalence<X> fromPredicate(Set<X> domain, BiPredicate<X, X> predicate) {
		Set<Set<X>> partition = new HashSet<>();
		domain.stream().forEach(a -> {
			for (var elem : partition) {
				if (predicate.test(elem.stream().findFirst().get(), a)) {
					elem.add(a);
					return;
				}
			}
			Set<X> newSet = new HashSet<>();
			newSet.add(a);
			partition.add(newSet);
		});
		return fromPartition(partition);
	}

	@Override
	public <X> Equivalence<X> fromPairs(Set<Pair<X, X>> pairs) {
		return fromPartition(pairs.stream().filter(a->a.getX()!=a.getY()).map(a->Set.of(a.getX(),a.getY())).collect(Collectors.toSet()));
		
	}

	@Override
	public <X, Y> Equivalence<X> fromFunction(Set<X> domain, Function<X, Y> function) {
		return fromPredicate(domain, (x1, x2) -> domain.contains(x1) && domain.contains(x2)
				&& function.apply(x1).equals(function.apply(x2)));
	}

}

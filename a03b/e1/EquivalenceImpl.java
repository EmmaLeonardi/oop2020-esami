package a03b.e1;

import java.util.HashSet;
import java.util.Set;

public class EquivalenceImpl<X> implements Equivalence<X> {

	private final Set<Set<X>> partition;

	public EquivalenceImpl(Set<Set<X>> partition) {
		this.partition = Set.copyOf(partition);
	}

	@Override
	public boolean areEquivalent(X x1, X x2) {
		return this.partition.stream().anyMatch(a -> a.contains(x1) && a.contains(x2));
	}

	@Override
	public Set<X> domain() {
		Set<X> domain = new HashSet<>();
		this.partition.forEach(a -> domain.addAll(a));
		return domain;
	}

	@Override
	public Set<X> equivalenceSet(X x) {
		return Set.copyOf(this.partition.stream().filter(a -> a.contains(x)).findFirst().get());
	}

	@Override
	public Set<Set<X>> partition() {
		return Set.copyOf(this.partition);
	}

	@Override
	public boolean smallerThan(Equivalence<X> eq) {
		for(var a: this.partition) {
			Set<X> difference=new HashSet<>(a);
			difference.removeAll(eq.equivalenceSet(a.stream().findAny().get()));
			if(difference.size()!=0) {
				return false;
			}
		}
		return true;
	}

}

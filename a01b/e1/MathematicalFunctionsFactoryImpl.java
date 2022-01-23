package a01b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class MathematicalFunctionsFactoryImpl implements MathematicalFunctionsFactory {

	@Override
	public <A, B> MathematicalFunction<A, B> constant(Predicate<A> domainPredicate, B value) {
		return fromFunction(domainPredicate, x -> value);
	}

	@Override
	public <A, B> MathematicalFunction<A, A> identity(Predicate<A> domainPredicate) {
		return fromFunction(domainPredicate, x -> x);
	}

	@Override
	public <A, B> MathematicalFunction<A, B> fromFunction(Predicate<A> domainPredicate, Function<A, B> function) {
		return new MathematicalFunction<A, B>() {

			@Override
			public Optional<B> apply(A a) {
				if (inDomain(a)) {
					return Optional.of(function.apply(a));
				}
				return Optional.empty();
			}

			@Override
			public boolean inDomain(A a) {
				return domainPredicate.test(a);
			}

			@Override
			public <C> MathematicalFunction<A, C> composeWith(MathematicalFunction<B, C> f) {
				return fromFunction(domainPredicate, x -> f.apply(function.apply(x)).get());
			}

			@Override
			public MathematicalFunction<A, B> withUpdatedValue(A domainValue, B codomainValue) {
				return fromFunction(x->domainPredicate.test(x)||x.equals(domainValue), x -> {
					if (x.equals(domainValue)) {
						return codomainValue;
					} else {
						return function.apply(x);
					}
				});
			}

			@Override
			public MathematicalFunction<A, B> restrict(Set<A> subDomain) {
				return fromFunction(x->domainPredicate.test(x)&&subDomain.contains(x), function);
			}

		};
	}

	@Override
	public <A, B> MathematicalFunction<A, B> fromMap(Map<A, B> map) {
		return fromFunction(x -> map.containsKey(x), y -> map.get(y));
	}

}

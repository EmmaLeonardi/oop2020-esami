package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ScannerFactoryImpl implements ScannerFactory {

	@Override
	public <X, Y> Scanner<X, List<Y>> collect(Predicate<X> filter, Function<X, Y> mapper) {
		return new Scanner<X, List<Y>>() {

			@Override
			public List<Y> scan(Iterator<X> input) {
				List<Y> result = new ArrayList<>();
				while (input.hasNext()) {
					var next = input.next();
					if (filter.test(next)) {
						result.add(mapper.apply(next));
					}
					//input.forEachRemaining(a->result.add(a));
					//return reult.stream().filter(filter).map(mapper).collect(Collectors.toList());
				}
				return result;
			}

		};
	}

	@Override
	public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
		return new Scanner<X, Optional<X>>() {

			@Override
			public Optional<X> scan(Iterator<X> input) {
				while (input.hasNext()) {
					var next = input.next();
					if (filter.test(next)) {
						return Optional.of(next);
					}
				}
				return Optional.empty();
			}

		};

	}

	@Override
	public Scanner<Integer, Optional<Integer>> maximalPrefix() {
		return new Scanner<Integer, Optional<Integer>>(){

			@Override
			public Optional<Integer> scan(Iterator<Integer> input) {
				if(input.hasNext()==false) {
					return Optional.empty();
				}
				
				List<Integer> list_all=new ArrayList<>();
				input.forEachRemaining(a->list_all.add(a));
				List<Integer> longest=new ArrayList<>();
				List<Integer> current=new ArrayList<>();
				current.add(list_all.get(0));
				list_all.stream().reduce((a,b)->{
					if(b>a) {
						//Se maggiore aggiungo a current
						current.add(b);
					}
					else {
						//Se minore
						if(current.size()>longest.size()) {
							longest.clear();
							longest.addAll(current); //Current è la più lunga
						}
						current.clear();
						current.add(b);
					}
					return b;
				});
				if(current.size()>longest.size()) {
					longest.clear();
					longest.addAll(current); //Current è la più lunga
				}
				
				return longest.stream().max(Integer::compare);
			}
			
		};
	}

	@Override
	public Scanner<Integer, List<Integer>> cumulativeSum() {
		return new Scanner<Integer, List<Integer>>(){

			@Override
			public List<Integer> scan(Iterator<Integer> input) {
				List<Integer> list=new ArrayList<>();
				List<Integer> result=new ArrayList<>();
				input.forEachRemaining(a->list.add(a));
				result.add(list.get(0));
				list.stream().reduce((a,b)->{
					a+=b;
					result.add(a);
					return a;
					
				});
				return result;
			}
			
		};
	}

}

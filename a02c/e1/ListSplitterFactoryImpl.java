package a02c.e1;

import java.util.function.Predicate;

public class ListSplitterFactoryImpl implements ListSplitterFactory {

	@Override
	public <X> ListSplitter<X> asPairs() {
		return new ListSplitterImpl<>((list,accumulator)->accumulator.size()==2);
	}

	@Override
	public <X> ListSplitter<X> asTriplets() {
		return new ListSplitterImpl<>((list,accumulator)->accumulator.size()==3);
	}

	@Override
	public <X> ListSplitter<X> asTripletsWithRest() {
		return new ListSplitterImpl<>((list,accumulator)->list.size()==0||accumulator.size()==3);
	}

	@Override
	public <X> ListSplitter<X> bySeparator(X separator) {
		return new ListSplitterImpl<>((list,accumulator)->
					list.size()==0||accumulator.get(0).equals(separator)||list.get(0).equals(separator));
	}

	@Override
	public <X> ListSplitter<X> byPredicate(Predicate<X> predicate) {
		return new ListSplitterImpl<>((list,accumulator)->
					list.size()==0||predicate.test(list.get(0))!=predicate.test(accumulator.get(accumulator.size()-1)));
	}

}

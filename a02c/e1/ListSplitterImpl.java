package a02c.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

public class ListSplitterImpl<X> implements ListSplitter<X> {

	BiPredicate<List<X>, List<X>> predicate;

	/**
	 * @param BiPredicate<List<X>,List<X>> stratedy is used to separate the pieces of the list
	 * the first paramenter is the old list
	 * the second parameter is the piece of the new list
	 * */
	public ListSplitterImpl(BiPredicate<List<X>, List<X>> strategy) {
		this.predicate = strategy;

	}

	@Override
	public List<List<X>> split(List<X> list) {
		List<X> accumulator = new ArrayList<>();
		List<List<X>> output = new ArrayList<>();
		List<X> listCopy=new ArrayList<>(list);
		Iterator<X> listIterator = listCopy.iterator();
		while (listIterator.hasNext()) {
			var elem = listIterator.next();
			accumulator.add(elem);
			listIterator.remove();
			if (predicate.test(listCopy, accumulator)) {
				output.add(accumulator);
				accumulator = new ArrayList<>();
			}
		}
		return output;
	}

}

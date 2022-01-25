package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PatternExtractorImpl<X, Y> implements PatternExtractor<X, Y> {

	private final Predicate<X> predicate;
	private final Function<List<X>, Y> function;

	/**
	 * @param predicate is the predicate that is used to construct the sequences
	 * @param function used to unite te sequences
	 * */
	public PatternExtractorImpl(Predicate<X> predicate, Function<List<X>, Y> function) {
		this.predicate = predicate;
		this.function = function;
	}

	@Override
	public List<Y> extract(List<X> input) {
		List<List<X>> output = new ArrayList<>();
		List<X> singlePattern = new ArrayList<>();
		for (X elem : input) {
			if (predicate.test(elem)) {
				// Se va bene
				singlePattern.add(elem);
			} else {
				// fine pattern
				if (!singlePattern.isEmpty()) {
					output.add(singlePattern);
				}
				singlePattern = new ArrayList<>();
			}
		}
		// Per l'ultimo pattern
		if (!singlePattern.isEmpty()) {
			output.add(singlePattern);
		}

		return output.stream().map(function).collect(Collectors.toList());
	}

}

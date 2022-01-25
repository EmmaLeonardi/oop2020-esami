package a02b.e1;

import java.util.stream.Collectors;

public class PatternExtractorFactoryImpl implements PatternExtractorFactory {

	@Override
	public PatternExtractor<Integer, Integer> countConsecutiveZeros() {
		return new PatternExtractorImpl<>(a -> a.equals(0), list -> Math.toIntExact(list.stream().count()));
	}

	@Override
	public PatternExtractor<Double, Double> averageConsecutiveInRange(double min, double max) {
		return new PatternExtractorImpl<>(a -> (a >= min && a <= max),
				list -> list.stream().collect(Collectors.averagingDouble(b -> b)));
	}

	@Override
	public PatternExtractor<String, String> concatenateBySeparator(String separator) {
		return new PatternExtractorImpl<>(a -> !a.equals(separator),list -> list.stream().collect(Collectors.joining()));
	}

	@Override
	public PatternExtractor<String, Double> sumNumericStrings() {
		return new PatternExtractorImpl<>(a->{
			try {
				Double.parseDouble(a);
				return true;
			}catch(NumberFormatException e) {
				return false;
				
			}
		}, list->list.stream().mapToDouble(Double::parseDouble).sum());
	}

}

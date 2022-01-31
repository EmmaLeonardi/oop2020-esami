package a04.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalStreamFactoryImpl implements FunctionalStreamFactory {

	private static abstract class FunctionalStreamImpl<E> implements FunctionalStream<E> {

		private NextResult<E> getNext() {
			return next();
		}

		@Override
		public List<E> toList(int size) {
			Iterator<E> it = this.toIterator();
			return Stream.generate(it::next).limit(size).collect(Collectors.toList());
		}

		@Override
		public Iterator<E> toIterator() {
			return new Iterator<E>() {
				NextResult<E> nextResult = getNext();

				@Override
				public boolean hasNext() {
					return nextResult.getElement() != null;
				}

				@Override
				public E next() {
					E result = nextResult.getElement();
					nextResult = nextResult.getStream().next();
					return result;
				}
			};
		}

	};

	@Override
	public <E> FunctionalStream<E> fromListRepetitive(List<E> list) {
		return new FunctionalStreamImpl<E>() {
			@Override
			public NextResult<E> next() {
				return new NextResult<E>() {

					@Override
					public E getElement() {
						return list.get(0);
					}

					@Override
					public FunctionalStream<E> getStream() {
						List<E> newList = new ArrayList<>(list);
						newList.add(newList.remove(0));
						return fromListRepetitive(newList);
					}

				};
			}
		};
	}

	@Override
	public <E> FunctionalStream<E> iterate(E initial, UnaryOperator<E> op) {
		return new FunctionalStreamImpl<E>() {
			@Override
			public NextResult<E> next() {
				return new NextResult<E>() {

					@Override
					public E getElement() {
						return initial;
					}

					@Override
					public FunctionalStream<E> getStream() {
						return iterate(op.apply(initial), op);
					}

				};
			}
		};
	}

	@Override
	public <A, B> FunctionalStream<B> map(FunctionalStream<A> fstream, Function<A, B> mapper) {
		return new FunctionalStreamImpl<B>() {

			@Override
			public NextResult<B> next() {
				return new NextResult<B>() {

					@Override
					public B getElement() {
						return mapper.apply(fstream.next().getElement());
					}

					@Override
					public FunctionalStream<B> getStream() {
						return map(fstream.next().getStream(),mapper);
					}

				};
			}
			
		};
	}

}

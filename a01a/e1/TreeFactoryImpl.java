package a01a.e1;

import java.util.ArrayList;
import java.util.List;

public class TreeFactoryImpl implements TreeFactory {

	@Override
	/**{@inheritDoc}*/
	public <E> Tree<E> singleValue(E root) {
		return new TreeImpl<E>(root);
	}

	@Override
	/**{@inheritDoc}*/
	public <E> Tree<E> twoChildren(E root, Tree<E> child1, Tree<E> child2) {
		return new TreeImpl<E>(root, List.of(child1,child2));
	}

	@Override
	/**{@inheritDoc}*/
	public <E> Tree<E> oneLevel(E root, List<E> children) {
		List<Tree<E>> c=new ArrayList<>();
		for(E child : children) {
			c.add(new TreeImpl<E>(child));
		}
		return new TreeImpl<E>(root, c);
	}

	@Override
	/**{@inheritDoc}*/
	public <E> Tree<E> chain(E root, List<E> list) {
		if(list.isEmpty()) {
			return singleValue(root);
		}
		else {
			return new TreeImpl<E>(root, List.of(chain(list.get(0), list.subList(1, list.size()))));
		}
	}

}

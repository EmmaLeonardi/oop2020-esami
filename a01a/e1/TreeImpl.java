package a01a.e1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeImpl<E> implements Tree<E> {

	private E root;
	private List<Tree<E>> children;

	public TreeImpl(E root) {
		this(root, new ArrayList<>());
	}

	public TreeImpl(E root, List<Tree<E>> children) {
		this.root = root;
		this.children = new ArrayList<>(children);
	}

	@Override
	/**{@inheritDoc}*/
	public E getRoot() {
		return root;
	}

	@Override
	/**{@inheritDoc}*/
	public List<Tree<E>> getChildren() {
		return new ArrayList<>(children);
	}

	@Override
	/**{@inheritDoc}*/
	public Set<E> getLeafs() {
		if (children.isEmpty()) {
			return Set.of(root);
		} else {
			Set<E> leafs = new HashSet<>();
			for (Tree<E> elem : children) {
				leafs.addAll(elem.getLeafs());
			}
			return leafs;
		}
	}

	@Override
	/**{@inheritDoc}*/
	public Set<E> getAll() {
		Set<E> elements = new HashSet<>();
		elements.add(root);
		for (Tree<E> elem : children) {
			elements.addAll(elem.getAll());
		}
		return elements;
	}

	@Override
	/**{@inheritDoc}*/
	public String toString() {
		if (children.isEmpty()) {
			return root + "[]";
		} else {
			return root + children.toString();
		}
	}

}

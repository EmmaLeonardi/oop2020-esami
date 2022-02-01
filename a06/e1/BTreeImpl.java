package a06.e1;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class BTreeImpl<L> implements BTree<L> {

	private final Optional<L> leaf;
	private final Optional<BTree<L>> leftTree;
	private final Optional<BTree<L>> rightTree;

	public BTreeImpl(final L leaf) {
		this.leaf = Optional.ofNullable(leaf);
		this.leftTree = Optional.empty();
		this.rightTree = Optional.empty();
	}

	public BTreeImpl(final BTree<L> left, final BTree<L> right) {
		this.leaf = Optional.empty();
		this.leftTree = Optional.ofNullable(left);
		this.rightTree = Optional.ofNullable(right);
	}

	@Override
	public boolean isLeaf() {
		return this.leaf.isEmpty() ? false : true;
	}

	@Override
	public L getLeaf() {
		if (this.leaf.isEmpty()) {
			throw new NoSuchElementException("BTree is not a leaf");
		}
		return this.leaf.get();
	}

	@Override
	public BTree<L> getLeft() {
		if (this.leftTree.isEmpty()) {
			throw new NoSuchElementException("BTree is a leaf");
		}
		return this.leftTree.get();
	}

	@Override
	public BTree<L> getRight() {
		if (this.rightTree.isEmpty()) {
			throw new NoSuchElementException("BTree is a leaf");
		}
		return this.rightTree.get();
	}

	@Override
	public L compute(BinaryOperator<L> function) {
		if (isLeaf()) {
			return this.leaf.get();
		}
		return function.apply(this.leftTree.get().compute(function), this.rightTree.get().compute(function));
	}

	@Override
	public <O> BTree<O> map(Function<L, O> mapper) {
		if (isLeaf()) {
			return new BTreeImpl<O>(mapper.apply(getLeaf()));
		} else {
			return new BTreeImpl<>(this.leftTree.get().map(mapper), this.rightTree.get().map(mapper));
		}
	}

	@Override
	public BTree<L> symmetric() {
		if (isLeaf()) {
			return this;
		} else {
			return new BTreeImpl<L>(this.rightTree.get().symmetric(), this.leftTree.get().symmetric());
		}

	}

	@Override
	public String toString() {
		String s = "(";
		if (isLeaf()) {
			return "(" + this.leaf.get().toString() + ")";
		} else {
			if (this.leftTree.get().isLeaf()) {
				s += this.leftTree.get().getLeaf().toString();
			} else {
				s += this.leftTree.get().toString();
			}
			s += ",";
			if (this.rightTree.get().isLeaf()) {
				s += this.rightTree.get().getLeaf().toString();
			} else {
				s += this.rightTree.get().toString();
			}
		}
		s += ")";
		return s;
	}

	@Override
	public int hashCode() {
		return Objects.hash(leaf, leftTree, rightTree);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		else {
			BTreeImpl<L> other = (BTreeImpl<L>) obj;
			return Objects.equals(leaf, other.leaf) && Objects.equals(leftTree, other.leftTree)
					&& Objects.equals(rightTree, other.rightTree);
		}
	}

}

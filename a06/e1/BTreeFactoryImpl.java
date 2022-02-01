package a06.e1;

import java.util.ArrayList;
import java.util.List;

public class BTreeFactoryImpl implements BTreeFactory {

	@Override
	public <L> BTree<L> leaf(L value) {
		return new BTreeImpl<L>(value);
	}

	@Override
	public <L> BTree<L> compose(BTree<L> left, BTree<L> right) {
		return new BTreeImpl<>(left, right);
	}

	@Override
	public <L> BTree<L> nested(List<L> leafs) {
		if (leafs.size() == 1) {
			return new BTreeImpl<L>(leafs.get(0));
		} else if (leafs.size() == 2) {
			return new BTreeImpl<L>(new BTreeImpl<L>(leafs.get(0)), new BTreeImpl<L>(leafs.get(1)));
		} else {
			List<L> remaining=new ArrayList<>(leafs);
			remaining.remove(0);
			return new BTreeImpl<>(new BTreeImpl<L>(leafs.get(0)), nested(remaining));
		}
	}

}

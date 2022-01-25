package a02b.e2;

import java.util.Set;

public interface Logic {
	
	public Set<Pair<Integer,Integer>> starCoords();
	
	public void up();
	
	public void down();
	
	public void addStar(Pair<Integer,Integer> coords);

}

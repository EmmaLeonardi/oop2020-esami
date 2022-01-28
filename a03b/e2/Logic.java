package a03b.e2;

import java.util.Set;

public interface Logic {
	
	public Set<Pair<Integer,Integer>> enemiesPos();
	
	public Set<Pair<Integer,Integer>> possibleMoves();
	
	public Pair<Integer,Integer> getBishopPos();
	
	public boolean move(Pair<Integer,Integer> coords);

}

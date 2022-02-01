package a05.e2;

import java.util.Map;

public interface Logic {
	
	public Map<Pair<Integer,Integer>,String> getGrid();
	
	public void pressed(Pair<Integer,Integer> coords);
	
	public boolean isOver();

}

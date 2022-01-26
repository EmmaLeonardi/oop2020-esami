package a02c.e2;

import java.util.Optional;

public interface Logic {
	
	/**returns the number of the button pressed,or an empty optional*/
	public Optional<String> press(Pair<Integer,Integer> coords);
	
	public boolean isWinning(Pair<Integer,Integer> coords);
}

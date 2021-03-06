package a01a.sol2;

public interface Logics{
	
	enum Player { 
		O, X;
		
		public Player other() {
			return this == O ? X : O;
		}
	}
	
	Player next();
	
	boolean hit(int x, int y);
	
	boolean isOver();
    
}

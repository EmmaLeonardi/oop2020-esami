package a04.e2;

public enum Pieces {
	PAWN('\u2659'),
	KING('\u265A'),
	HORSE('\u265E');
	
	
	private final char c;
	private Pieces(char c){
		this.c=c;
	}
	
	public String toString() {
		return Character.toString(c);
	}

}

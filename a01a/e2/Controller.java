package a01a.e2;

public interface Controller {

	public static enum GridValue{
		FULL_GRID,
		NOT_VALID,
		VALID_1,
		VALID_2
	}
	
	public static enum Win{
		WIN1,
		WIN2,
		NOT_WIN
	}

	public GridValue checkValidity(int position);

	public Win hasWon();

}

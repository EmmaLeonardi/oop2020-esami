package a05.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {

	private final Map<Pair<Integer, Integer>, String> grid;
	private final int size;
	private final static int BOUND = 10;
	private final static String CROSS = "X";

	public LogicImpl(int size) {
		this.size = size;
		this.grid = new HashMap<>();
		Random random = new Random();
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.grid.put(new Pair<>(j, i), Integer.toString(random.nextInt(BOUND)));
			}
		}
	}

	@Override
	public Map<Pair<Integer, Integer>, String> getGrid() {
		return Map.copyOf(grid);
	}

	@Override
	public void pressed(Pair<Integer, Integer> coords) {
		if (coords != null && coords.getX() >= 0 && coords.getX() < this.size && coords.getY() >= 0
				&& coords.getY() < this.size) {
			// Coordinata valida
			if (grid.get(coords).equals(CROSS)) {
				// Devo prendere i valori attorno e sommarli (se non sono X)
				int sum=0;
				for(int i : List.of(-1,0,1)) {
					for(int j : List.of(-1,0,1)) {
						Pair<Integer, Integer> cell=new Pair<>(coords.getX()+i,coords.getY()+j);
						if(cell.getX()==coords.getX()&&cell.getY()==coords.getY()) {
							//Nulla
						}else if(grid.containsKey(cell)&&grid.get(cell).equals(CROSS)==false) {
							//C'è il valore e non è una X
							sum=sum+Integer.parseInt(grid.get(cell));
							grid.replace(cell, CROSS);
						}
					}
				}
				grid.replace(coords, sum+"");
				
			} else {
				// E' un numero, devo metterlo a X e mettere il valore attorno (se non sono X)
				for(int i : List.of(-1,0,1)) {
					for(int j : List.of(-1,0,1)) {
						Pair<Integer, Integer> cell=new Pair<>(coords.getX()+i,coords.getY()+j);
						if(cell.getX()==coords.getX()&&cell.getY()==coords.getY()) {
							//Nulla
						}else if(grid.containsKey(cell)&&grid.get(cell).equals(CROSS)==false) {
							//C'è il valore e non è una X
							grid.replace(cell, grid.get(coords));
						}
					}
				}
				grid.replace(coords, CROSS);
			}
		}

	}

	@Override
	public boolean isOver() {
		for (var elem : grid.entrySet()) {
			if (!elem.getValue().equals(CROSS)) {
				// Almeno un valore non è la X, il gioco non è finito
				return false;
			}
		}
		return true;
	}

}

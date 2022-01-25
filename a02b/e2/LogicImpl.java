package a02b.e2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

	private final int size;
	private Set<Pair<Integer, Integer>> stars;

	public LogicImpl(int size) {
		this.size = size;
		this.stars = new HashSet<>();
	}

	@Override
	public Set<Pair<Integer, Integer>> starCoords() {
		return Set.copyOf(stars);
	}

	@Override
	public void up() {
		Set<Pair<Integer,Integer>> upStars=new HashSet<>();
		stars.stream().collect(Collectors.groupingBy(a->a.getX())).forEach((x,list)->{
			for(int i=0;i<list.size();i++) {
				upStars.add(new Pair<>(x,i));
			}
		});
		this.stars=upStars;
	}

	@Override
	public void down() {
		Set<Pair<Integer,Integer>> downStars=new HashSet<>();
		stars.stream().collect(Collectors.groupingBy(a->a.getX())).forEach((x,list)->{
			for(int i=0;i<list.size();i++) {
				downStars.add(new Pair<>(x,this.size-1-i));
			}
		});
		this.stars=downStars;

	}

	@Override
	public void addStar(Pair<Integer, Integer> coords) {
		if (coords.getX() >= 0 && coords.getX() < this.size 
		    && coords.getY() >= 0 && coords.getY() < this.size) {
			stars.add(coords);
		}
	}

}

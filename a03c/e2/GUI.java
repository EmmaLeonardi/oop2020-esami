package a03c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
	private final JButton next = new JButton(">");
	private final Logic logic;

	public GUI(int size) {
		this.logic = new LogicImpl(size);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(100 * size, 100 * size);

		JPanel grid = new JPanel(new GridLayout(size, size));
		this.getContentPane().add(BorderLayout.CENTER, grid);
		this.getContentPane().add(BorderLayout.SOUTH, next);

		next.addActionListener(e -> {
			logic.moveSnake();
			refreshGrid();
		});

		ActionListener el = e -> {
			var button = (JButton) e.getSource();
			logic.moveSnake(this.cells.get(button));
			refreshGrid();

		};

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final JButton jb = new JButton(" ");
				this.cells.put(jb, new Pair<>(j, i));
				grid.add(jb);
				jb.addActionListener(el);
			}
		}
		
		refreshGrid();
		this.setVisible(true);
	}
	
	private void refreshGrid() {
		this.cells.keySet().forEach(b->{
			var pos=this.cells.get(b);
			if(logic.snakePieces().containsKey(pos)) {
				b.setText(logic.snakePieces().get(pos).toString());
			}
			else {
				b.setText("");
			}
		});
	}

}

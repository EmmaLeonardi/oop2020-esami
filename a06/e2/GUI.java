package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;

public class GUI extends JFrame {

	private static final long serialVersionUID = -6218820567019985015L;
	private final List<JButton> cells = new LinkedList<>();
	private final JButton next = new JButton(">");
	private final Logic logic;
	private final static int MAX = 5;
	private final static int MIN = 1;

	public GUI(int size) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(size * 100, 100);
		this.logic = new LogicImpl(size, MIN, MAX);

		JPanel panel = new JPanel(new GridLayout(1, size));
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.getContentPane().add(next, BorderLayout.SOUTH);

		next.addActionListener(e -> {
			logic.tick();
			refreshView();
			if (logic.isOver()) {
				JOptionPane.showMessageDialog(panel, "Game over!");
				next.setEnabled(false);
			}
		});

		for (int j = 0; j < size; j++) {
			final JButton jb = new JButton(" ");
			this.cells.add(jb);
			panel.add(jb);
		}
		refreshView();
		this.setVisible(true);
	}

	private void refreshView() {
		cells.forEach(b -> b.setText(logic.getPositions().get(cells.indexOf(b))));
	}
}

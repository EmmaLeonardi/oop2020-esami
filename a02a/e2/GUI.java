package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer,Integer>,JButton> cells = new HashMap<>();
    private final JButton next = new JButton(">");
    private final Logic logic;
    
    public GUI(int size) {
    	this.logic=new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,next);
        
        next.addActionListener(e -> {
        	if(logic.next()) {
        		refreshGrid();
        	}
        	else {
        		this.next.setEnabled(false);
        		JOptionPane.showMessageDialog(grid, "End of the game");
        	}
        	
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Pair<>(j,i),jb);
                grid.add(jb);
            }
        }
        refreshGrid();
        this.setVisible(true);
    }    
    
    private void refreshGrid() {
    	this.cells.forEach((a,b)->{
    		if(this.logic.piecesPos().contains(a)) {
    			b.setText("*");
    		}
    		else {
    			b.setText("");
    		}
    	});
    	this.cells.get(logic.kingPos()).setText("P");
    }
}

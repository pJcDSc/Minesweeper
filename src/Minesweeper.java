import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Minesweeper implements MouseListener{
	int dim = 20;
	int[][] cells;
	int[][] shownCells;
	boolean[][] clickedCells;
	int numMines = dim * dim / 7;
	int[] dx8 = {-1, -1, -1, 0, 1, 1, 1, 0};
	int[] dy8 = {-1, 0, 1, 1, -1, 0, 1, -1};
	int firstClickX;
	int firstClickY;
	boolean clicked = false;
	
	private Random generator = new Random();
	
	JFrame frame = new JFrame("MineSweetPurr");
	MinePanel panel;
	
	public Minesweeper() {
		cells = new int[dim][dim];
		shownCells = new int[dim][dim];
		clickedCells = new boolean[dim][dim];
		
		frame.setSize(800, 800);
		frame.setLayout(new BorderLayout());
		
		panel = new MinePanel(shownCells, clickedCells, (int)(frame.getWidth() / (double)dim),
				(int)(frame.getHeight() / (double)dim));
		panel.addMouseListener(this);
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void createBoard() {
		for(int i = 0; i < numMines; i++) {
			double nextDim = generator.nextDouble() * dim * dim;
			int x = (int)nextDim / dim;
			int y = (int)nextDim % dim;
			if(cells[x][y] == -1 || Math.abs(x - firstClickX) < 3 && Math.abs(y - firstClickY) < 3) {
				i--;
				continue;
			}
			 else {
				cells[x][y] = -1;
				for(int j = 0; j < 8; j++) {
					if(x+dx8[j] < cells.length && y+dy8[j] < cells[0].length && 
							x+dx8[j] >= 0 && y + dy8[j] >= 0 &&
							cells[x+dx8[j]][y+dy8[j]] >= 0) {
						cells[x+dx8[j]][y+dy8[j]]++;
					}
				}
			}
		}
	}
	
	public void reveal(int x, int y) {
		if(clickedCells[x][y] || shownCells[x][y] == -2)return;
		clickedCells[x][y] = true;
		shownCells[x][y] = cells[x][y];
		if(cells[x][y] == -1) {
			for (int i = 0; i < cells.length; i++) {
				for (int j = 0; j < cells[0].length; j++) {
					if (cells[i][j] == -1 && shownCells[i][j] != -2) {
						shownCells[i][j] = cells[i][j];
						clickedCells[i][j] = true;
					}
					if (shownCells[i][j] == -2 && cells[i][j] != -1) {
						shownCells[i][j] = -5;
						clickedCells[i][j] = true;
					}
				}
			}
			shownCells[x][y] = -4;
			frame.repaint();
			int n = JOptionPane.showConfirmDialog(
				    frame,
				    "You lose. Play again?",
				    "xd bad",
				    JOptionPane.YES_NO_OPTION);
			if (n == 1 || n == -1) { //quit
				JOptionPane.showMessageDialog(frame, "Thanks for playing!", "u scared boi", JOptionPane.DEFAULT_OPTION);
				System.exit(0);
			}
			reset();
		}
		else if(cells[x][y] == 0) {
			for(int i = 0; i < 8; i++) {
				if(x + dx8[i] < cells.length && x + dx8[i] >= 0 &&
						y + dy8[i] < cells[0].length && y + dy8[i] >= 0)
					reveal(x + dx8[i], y + dy8[i]);
			}
		}
		frame.repaint();
	}
	
	public void reset() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				cells[i][j] = 0;
				shownCells[i][j] = 0;
				clickedCells[i][j] = false;
			}
		}
		clicked = false;
		
		frame.repaint();
	}
	
	public void flag(int x, int y) {
		if(clickedCells[x][y]) return;
		else {
			shownCells[x][y] = shownCells[x][y] == -2 ? 0 : -2;
		}
		frame.repaint();
	}
	
	public boolean checkWin() {
		for (int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if (cells[i][j] != -1 && !clickedCells[i][j]) return false;
				if (cells[i][j] == -1 && shownCells[i][j] != -2) return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		new Minesweeper();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		double width = (double) panel.getWidth() / cells[0].length;
        double height = (double) panel.getHeight() / cells.length;
        int column = Math.min(cells[0].length - 1, (int) (e.getX() / width));
        int row = Math.min(cells.length - 1, (int) (e.getY() / height));
		
		if(!clicked) {
			clicked = true;
			firstClickX = row;
			firstClickY = column;
			createBoard();
		}

        if(e.getButton() == MouseEvent.BUTTON1) {
        	reveal(row, column);
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
        	flag(row, column);
        }
        
        if (checkWin()) {
        	int n = JOptionPane.showConfirmDialog(
				    frame,
				    "You win! Play again?",
				    "hax",
				    JOptionPane.YES_NO_OPTION);
			if (n == 1 || n == -1) { //quit
				JOptionPane.showMessageDialog(frame, "Thanks for playing!", "ok bai", JOptionPane.DEFAULT_OPTION);
				System.exit(0);
			}
			else {
				reset();
				frame.repaint();
			}
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
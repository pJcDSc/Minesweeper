import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MinePanel extends JPanel{
	
	int[][] cells;
	boolean[][] clicked;
	double width;
	double height;
	int imX;
	int imY;
	
	public MinePanel(int[][] in, boolean[][] clicked, int x, int y) {
		cells = in; 
		this.clicked = clicked;
		imX = x + 1;
		imY = y + 1;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width = (double)this.getWidth() / (double)cells[0].length ;
		height = (double)this.getHeight() / (double)cells.length ;
		
		for(int row = 0; row < cells.length; row++) {
			for(int column = 0; column < cells[0].length; column++) {
				if(!clicked[row][column] && cells[row][column] != -2) drawNum(-3, row, column, g);
				else drawNum(cells[row][column], row, column, g);
			}
		}
	}
	
	public Image getImage (String name, int x, int y) {
		Image image = null;
		try {
			image = ImageIO.read(new File(name)).getScaledInstance(x, y, Image.SCALE_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public void drawNum(int num, int row, int col, Graphics g) {
		Image myImage = null;
		switch(num) {
			case(-5):
				myImage = getImage("x_mine.png", imX, imY);
				break;
			case(-4):
				myImage = getImage("red_mine.png", imX, imY);
				break;
			case(-3):
				myImage = getImage("blank.png", imX, imY);
				break;
			case(-2):
				myImage = getImage("flag.png", imX, imY);
				break;
			case(-1):
				myImage = getImage("mine.png", imX, imY);
				break;
			case(0):
				myImage = getImage("pblank.png", imX, imY);
				break;
			case(1):
				myImage = getImage("one.png", imX, imY);
				break;
			case(2):
				myImage = getImage("two.png", imX, imY);
				break;
			case(3):
				myImage = getImage("three.png", imX, imY);
				break;
			case(4):
				myImage = getImage("four.png", imX, imY);
				break;
			case(5):
				myImage = getImage("five.png", imX, imY);
				break;
			case(6):
				myImage = getImage("six.png", imX, imY);
				break;
			case(7):
				myImage = getImage("seven.png", imX, imY);
				break;
			case(8):
				myImage = getImage("eight.png", imX, imY);
				break;
		}
		g.drawImage(myImage,  (int)Math.round((double)col * width), (int)Math.round((double)row * height), null);
	}
}
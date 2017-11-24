import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
   TODO Write a one-sentence summary of your class here.
   TODO Follow it with additional details about its purpose, what abstraction
   it represents, and how to use it.

   @author  TODO Your Name: Me
   @version TODO Date: Still single

   Period - TODO Your Period: Im a guy
   Assignment - TODO Name of Assignment: Not sure

   Sources - TODO list collaborators: My imaginary friend

 */
public class TextTest extends JPanel implements KeyListener, MouseListener
{
  // TODO Your Instance Variables Here
	double m = 1.0;

	int r = (int)((Math.random() * 32)) + 160;
	int g = (255-64) + (int)((1 - Math.pow(Math.random(), 2)) * 64);
	int b = 255;
	Color sky;
	Color purple = new Color(140,5,150);
	Color blue = new Color(0,0,220);
	Color green = new Color(0,254,0);
	Color orange = new Color(210,180,0);
	
	int initXPos = 200;
	int xPos = initXPos;
	int initYPos = 440;
	int yPos = initYPos;
	int speed = 40;
	int size = 100;
	int jumpSpeed = 100;
	
	int tempTime = 0;
	
	Image house, sun, tree, character;
	
	boolean houseAsImage = true;
	boolean sunAsImage = true;
	boolean treeAsImage = true;
	boolean showMessage = false;
	
	double ratioX, ratioY;
	
	String message1 = "Hello! My name is Ajfain Blorkazbaifinski.";
	String message2 = "Nice weather today. Every day, actually.";
	String message3 = "Why do I have a garage if I don't have a car?";
	String message4 = "These invisible walls are bothersome...";
	String message = "";
	
	int index = 0;
	
  public TextTest () {
	  super();
	  
	  house = (new ImageIcon("8841854_orig.png").getImage());
	  sun = (new ImageIcon("sun.png").getImage());
	  tree = (new ImageIcon("tree.png").getImage());
	  character = (new ImageIcon("dood.png").getImage());
	  
		r = (int)(r * m);
		if (r>255)
			r = 255;
		
		g = (int)(g * m);
		if (g>255)
			g = 255;
		
		sky = new Color(r, g, b);

	  setBackground(sky);
	  // TODO Add more customizations to the panel
	  
	  index = (int)(Math.random() * 4 + 1);
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background

    int width = getWidth();
    int height = getHeight();
    
  ratioX = width/800.0;
  ratioY = height/600.0;
    
    Graphics2D g2 = (Graphics2D)g;
    g2.scale(ratioX, ratioY);
    
	  //message = "" + (System.currentTimeMillis() - (long)(Math.random() * 100000));

	// TODO Add any custom drawings here
    
    if (!houseAsImage)
    {
    g.setColor(purple);
    g.fillRect(480, 260, 150, 180);
    g.setColor(blue);
    
    int[] x = {480, 630, 555};
    int[] y = {260, 260, 210};
    
    g.fillPolygon(x, y, 3);
    }
    else
    g.drawImage(house, 500, 240, 230, 220, this);
    
    if (!treeAsImage)
    {
    
    g.setColor(Color.BLACK);
    g.fillRect(180, 260, 57, 180);
    
    g.setColor(green);
    g.fillOval(130, 210, 160, 95);
    
    }
    else
    g.drawImage(tree, 138, 200, 160, 240, this);
    
    g.setColor(Color.BLACK);
    
    Font font = new Font("Garamond", Font.BOLD, 42);
    g.setFont(font);
    g.drawString("T H E  T I M B E R M E N", 100, 500);
    
    g.setColor(green);  
    g.drawLine(0, 440, 10000, 440);
    
    if (!sunAsImage)
    {
    g.setColor(orange);
    g.drawOval(610, 100, 100, 100);
    }
    else
    g.drawImage(sun, 610, 100, 100, 100, this);
    
    if (xPos < 0)
    	xPos = 0;
    
    if (xPos > width / ratioX - size)
    	xPos = (int)(width / ratioX) - size;
    
    g.drawImage(character, xPos, 440-size, size, size, this);
    
    //g.setColor(colorCycler());
    
    int fontsize = 24;
    font = new Font("SansSerif", Font.BOLD, fontsize);
    g.setFont(font);
    
    String text = "";
    
    if(showMessage)
    	text = message;
    else
    	text = "(Q)";
    
    //Surprisingly, the following actually WORKS
    g.setColor(Color.BLACK);
    g.fillRect(xPos + size/3, yPos - size - 2 * fontsize, (int)(text.length() * (fontsize * 0.48)) + 3, (int)(fontsize * 1.5));
    g.setColor(Color.WHITE);
    g.drawString(text, xPos + size/3, yPos - size - fontsize);	
  }
  
  //May be used in the future >:)
  private Color colorCycler()
  {
	  /*
	  index++;
	  */
	  if (index>5)
		  index = 0;
	  
	  if(index == 0)
		  return Color.RED;
	  if(index == 1)
		  return Color.ORANGE;
	  if(index == 2)
		  return Color.YELLOW;
	  if(index == 3)
		  return Color.GREEN;
	  if(index == 4)
		  return Color.BLUE;
	  if(index == 5)
		  return Color.MAGENTA;
	  else
		  return Color.BLACK;
  }


  // As your program grows, you may want to...
  //   1) Move this main method into its own 'main' class
  //   2) Customize the JFrame by writing a class that extends it, then creating that type of object in your main method instead.
  //   3) Rename this class (SimpleWindow is not a good name - this class actually represents the *Panel*, not the window)
  public static void main(String[] args)
  {
    JFrame w = new JFrame("Simple Window");
    w.setBounds(100, 100, 800, 600);
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    TextTest panel = new TextTest();
    w.add(panel);
    w.setResizable(true);
    w.setVisible(true);
    w.addKeyListener(panel);
    panel.addMouseListener(panel);
  }

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_1)
			houseAsImage = !houseAsImage;
		
		if (code == KeyEvent.VK_2)
			sunAsImage = !sunAsImage;
		
		if (code == KeyEvent.VK_3)
			treeAsImage = !treeAsImage;
		
		if (code == KeyEvent.VK_Q)
		{
			showMessage = !showMessage;
			
		    int i = (int)(Math.random() * 4) + 1;
			
		    if (i == 1)
		    	message = message1;
		    else if (i == 2)
		    	message = message2;
		    else if (i == 3)
		    	message = message3;
		    else if (i == 4)
		    	message = message4;
		}
		
		if (code == KeyEvent.VK_0)
		{
			sunAsImage = !sunAsImage;
			treeAsImage = !treeAsImage;
			houseAsImage = !houseAsImage;			
		}
		
		if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
			xPos -= speed;
		
		if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
			xPos += speed;
		
		repaint();
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		int x = arg0.getX();
		int xUnscaled = (int)(x / ratioX);
		int y = arg0.getY();
		int yUnscaled = (int)(y / ratioY);
		
		if ((xUnscaled < xPos + size && xUnscaled > xPos) && (yUnscaled < yPos && yUnscaled > yPos - size))
		{
			xPos = 600;
		}
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

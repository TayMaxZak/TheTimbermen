
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/*
 * Represents a moving, appearing/disappearing image.
 *
 * by: Shelby
 * on: 5/3/16
 * 
 * (ahem) Represents a moving, appearing/disappearing, *animating* image.
 * 
 * edited by: Thomas Z
 * on: 5/16/16
 */
 
public class Sprite{
	
	// FIELDS
	
	private double x, y;
	private double width, height;
	private Image[] image; //Must be a sequence of images in order to have an animation
	private int index = 0; //The index of the current displayed image
	private boolean isVisible;
	private boolean facingForward = true;
	
	// CONSTRUCTORS
	
	public Sprite(String filename, double x, double y, double w, double h) {
		this((new ImageIcon(filename)).getImage(),x,y,w,h);
	}
	
	public Sprite(String[] images, double x, double y, double w, double h) {
		//Goes through every given name and adds it to the array in order.
		image = new Image[images.length];
		for(int i = 0; i < images.length; i++)
		{
			image[i] = (new ImageIcon(images[i])).getImage();
		}
		
		this.x = x;
		width = w;
		
		this.y = y;
		height = h;
		isVisible = true;
	}
	
	public Sprite(Image img, double x, double y, double w, double h) {
		image = new Image[1];
		image[0] = img;
		
		this.x = x;
		width = w;
		
		this.y = y;
		height = h;
		isVisible = true;
	}
	
	
	// METHODS
	
	public void setVisibility(boolean vis) {
		isVisible = vis;
	}
	
	public void setFacingDirection(boolean dir) {
		facingForward = dir;
	}
	
	public void moveToLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveByAmount(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-this.width);
		y = Math.min(y,windowHeight-this.height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	public boolean isPointInImage(double mouseX, double mouseY) {
		if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height)
			return true;
		return false;
	}
	
	public void resize(double w, double h) {
		width = w;
		height = h;
	}
	
	public void cycleImage(int min, int max)
	{
		//Dont want to cycle to a nonexistant image
		if(max >= image.length)
			max = image.length-1;
		
		//If we hit the end of a loop, go back to the beginning
		//If we were in a different loop, go to the start of this one
		if (index < min || index > max)
			index = min;
		
		index++;
		
		if (index < min || index > max)
			index = min;
	}
	
	public void draw(Graphics g, ImageObserver io, double offsetX, double offsetY) {
		if (isVisible)
		{
			if (facingForward)
				g.drawImage(image[index],(int)(x - offsetX),(int)(y - offsetY + 0.5),(int)width,(int)height,io);
			else
				g.drawImage(image[index],(int)(x - offsetX + width),(int)(y - offsetY + 0.5),-(int)width,(int)height,io);
		}
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	
	
}











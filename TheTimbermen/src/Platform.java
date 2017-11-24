import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
TODO This is a collidable sprite.
TODO  Platforms are used as walls, floors, and the basis for many stationary, collidable objects, such as powerups.

@author  TODO Thomas Z
@version TODO 5/17/16

Period - TODO 4
Assignment - TODO Final Project

Sources - TODO Thomas Z
*/
public class Platform extends Sprite implements Collidable {
	// FIELDS
	String tag = "Platform"; //Extremely useful for defining custom platform derivatives like powerups and level tokens
	ArrayList<Platform> list; //List of platforms in the level
	
	// CONSTRUCTOR
	public Platform(String filename, double x, double y, double w, double h) {
		super((new ImageIcon(filename)).getImage(),x,y,w,h);
	}
	
	public Platform(String filename, double x, double y, double w, double h, String tag, ArrayList<Platform> list) {
		this(filename, x, y, w, h);
		this.tag = tag;
		this.list = list;
	}

	@Override
	public double[] getBoundsX() {
		// TODO Auto-generated method stub
		double[] bounds = new double[]{0,0};
		
		if (getWidth() > 0)
			bounds = new double[]{getX(), getX() + getWidth()};
		else
			bounds = new double[]{getX() + getWidth(), getX()}; //Special case if the platform is flipped horizontally
		
		return bounds;
	}

	@Override
	public double[] getBoundsY() {
		// TODO Auto-generated method stub
		double[] bounds = {getY(), getY() + getHeight()};
		return bounds;
	}

	@Override
	public boolean check(Collidable c) {
		list.remove(this);
		return false;
		// TODO Auto-generated method stub
		//This method will stay empty because platforms never move, so they dont need to check collision
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return tag;
	}
	
}
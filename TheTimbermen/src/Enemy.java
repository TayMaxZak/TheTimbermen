import java.util.ArrayList;

/**
TODO This is an enemy.
TODO  This class handles AI (including not falling off of platforms, detecting the player, etc.) and
moving the enemy itself (moving towards the player, etc.) The basis for more advanced enemy types.

@author  TODO Thomas Z
@version TODO 5/17/16

Period - TODO 4
Assignment - TODO Final Project

Sources - TODO Thomas Z
*/

public class Enemy extends Sprite implements Collidable, Locomotion {
	// FIELDS
	private double velX, velY;
	private double maxSpeed = 3;
	private boolean onSurface;
	
	private int health = 1; //How many times we can take damage
	
	private double jumpVelocity = 7; ///WAS 15
	private double airMoveMult = 0.9;
	
	private ArrayList<Enemy> parentList; //Reference to the list of enemies
	
	//Timers
	private int stunnedLeft = 0; //Time, in ticks, until enemy stops being stunned
	
	private double gravity  = 0.6;
	
	private double[] patrolBoundaries; //Where the Enemy will walk back and forth
	
	//Credit to Jake S for telling me about protected
	protected int direction = 1; //-1 go left, 1 go right
	
    private boolean vis = true;
	
	// CONSTRUCTOR
	public Enemy(int x, int y, ArrayList<Enemy> list) {
		super("TimbermanStanding2.png",x,y,128,256);
		velX = 0;
		velY = 0;
		onSurface = false;
		parentList = list;
		
		patrolBoundaries = new double[2];
	}
	
	// CONSTRUCTOR
	public Enemy(int x, int y, String imageName, ArrayList<Enemy> list) {
		super(imageName,x,y,128,256);
		velX = 0;
		velY = 0;
		onSurface = false;
		parentList = list;
		
		patrolBoundaries = new double[2];
	}
	
	// METHODS
	public void walk() {
		
		if (Math.abs(velX) < maxSpeed)
		{
			velX += direction;
		}
		
		double center = (getBoundsX()[0] + getBoundsX()[1])/2.0;
		
		if (center > patrolBoundaries[1])
		{
			direction = -1;
		}
		if (center < patrolBoundaries[0])
		{
			direction = 1;
		}
		
		int moveMult = 0;
		
		if (stunnedLeft <= 0)
			moveMult = 1;
		
		velX = Math.abs(velX) * direction * moveMult;
		
		if (direction > 0)
			super.setFacingDirection(true);
		else
			super.setFacingDirection(false);
		//System.out.println(velX);
	}
	
	public void jump() {
		if (onSurface && stunnedLeft <= 0)
		{
			velY = -jumpVelocity;
		}
	}
	
	public void stun()
	{
		stunnedLeft = 150;
	}
	
	public void tick() {
		
		velY += gravity; // Gravity
		//velX *= airMoveMult; // Friction
		///RANDOM ADDED
		double random = Math.random();
		if (random > 0.996)
			jump();
		
		moveByAmount(velX * random, velY);	
		
		onSurface = false;
		walk();
		
		vis = !vis;
		
		stunnedLeft--;
		if (stunnedLeft <= 0)
		{
			stunnedLeft = 0;
			setVisibility(true);
		}
		else if (stunnedLeft > 0)
			setVisibility(vis);	
	}
	
	public void delete()
	{
		parentList.remove(this);
		//System.out.println(projType);
	}
	
	public boolean isStunned()
	{
		if (stunnedLeft > 0)
			return true;
		return false;
	}
	
	public boolean check(Collidable c) {
		//First lets gather the other collider's information
		double otherLBound = c.getBoundsX()[0];
		double otherRBound = c.getBoundsX()[1];
		
		double otherTBound = c.getBoundsY()[0];
		double otherBBound = c.getBoundsY()[1];
		
		//Now our info
		double myLBound = getBoundsX()[0];
		double myRBound = getBoundsX()[1];
		
		double myTBound = getBoundsY()[0];
		double myBBound = getBoundsY()[1];
		
		//Now we can go through all potential overlap cases
		if (c.getTag() != "HealthPickup" && c.getTag() != "ShieldPickup" && c.getTag() != "GrenadePickup") //Dont want to step onto powerups!
		if ((myLBound+myRBound)/2.0f >= otherLBound && myBBound >= otherTBound && (myLBound+myRBound)/2.0f <= otherRBound && myBBound <= otherBBound)
		{
			velY = 0;
			onSurface = true;
			moveByAmount(0,-(myBBound - otherTBound));
			//System.out.println("x: " + getBoundsX()[0] + " y: " + getBoundsY()[1]);
			if (c.getTag() == "Platform")
				patrolBoundaries = new double[]{otherLBound + 32, otherRBound - 32};
			
			//System.out.println("Left: " + patrolBoundaries[0] + " Right: " + patrolBoundaries[1] + " X: " + getX());
				
			return true;
		}
		return false;
	}

	@Override
	public double[] getBoundsX() {
		// TODO Auto-generated method stub
		double[] bounds = {getX() + 9 * 4, getX() + getWidth() - 9 * 4};
		return bounds;
	}

	@Override
	public double[] getBoundsY() {
		// TODO Auto-generated method stub
		double[] bounds = {getY() + 13 * 4, getY() + getHeight()};
		return bounds;
	}

	@Override
	public double getVelX() {
		// TODO Auto-generated method stub
		return velX;
	}

	@Override
	public double getVelY() {
		// TODO Auto-generated method stub
		return velY;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return "Enemy";
	}
	
}
import java.util.ArrayList;

public class Projectile extends Sprite implements Collidable, Locomotion {
	// FIELDS
	private double velX, velY; //Initial velocity
	
	//private int size = 64;
	
	private double initVelocityX = 15; //Change in x-position every tick
	private double initVelocityY = 15; //Change in y-position every tick
	
	private double accelX  = 0.0; //Change in x-velocity every tick
	private double accelY  = 0.0; //Change in y-velocity every tick
	
	private ArrayList<Projectile> parentList; //The list that this projectile belongs to
	private ArrayList<Locomotion> parentList2;
	private String projType = "UNDEFINED";
	
	private int projDuration = 100;
	private int timeAlive = 0;
	
	// CONSTRUCTORS
	public Projectile(String name, double x, double y, double initVelX, double initVelY, int size) {
		super(name,x - size/2,y - size/2,size,size);
		initVelocityX = initVelX;
		initVelocityY = initVelY;
		
		velX = initVelocityX;
		velY = initVelocityY;
	}
	
	public Projectile(String name, double x, double y, double initVelX, double initVelY, double initAccelX,
			double initAccelY, int size, ArrayList<Projectile> list, ArrayList<Locomotion> list2, String type, int duration) {
		this(name,x,y,initVelX,initVelY, size);
		accelX = initAccelX;
		accelY = initAccelY;
		parentList = list;
		parentList2 = list2;
		projType = type;
		projDuration = duration;
	}
	
	public void tick() {
		
		velY += accelY; // Gravity
		velX += accelX; // Drag
		
		moveByAmount(velX, -velY);
		
		timeAlive++;
		
		if (getDurationLeft() < 0)
			delete();
	}
	
	public void delete()
	{
		parentList.remove(this);
		parentList2.remove(this);
		//System.out.println(projType);
	}
	
	public String getType()
	{
		return projType;
	}
	
	public int getTimeAlive()
	{
		return timeAlive;
	}
	
	public int getDurationLeft()
	{
		return projDuration - timeAlive;
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
		//TOP
		if (getTag() == "Shield")
		{
			if (myRBound >= otherLBound && myBBound >= otherTBound && myLBound <= otherRBound && myBBound <= otherBBound)
			{
				delete();
				return true;
			}
		}	
		else if ((myLBound+myRBound)/2 >= otherLBound && (myTBound+myBBound)/2 >= otherTBound && (myLBound+myRBound)/2 <= otherRBound && (myTBound+myBBound)/2 <= otherBBound)
		{
			delete();
			return true;
		}
		return false;
	}

	@Override
	public double[] getBoundsX() {
		// TODO Auto-generated method stub
		double[] bounds = {getX(), getX() + getWidth()};
		return bounds;
	}

	@Override
	public double[] getBoundsY() {
		// TODO Auto-generated method stub
		double[] bounds = {getY(), getY() + getHeight()};
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
		return projType;
	}
	
}
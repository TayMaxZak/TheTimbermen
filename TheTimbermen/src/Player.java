
public class Player extends Sprite implements Collidable, Locomotion {
	// FIELDS
	private double velX, velY;
	private double maxSpeed = 9; ///was 9
	private boolean onSurface;
	
	private double jumpVelocity = 14; ///was 14
	private double airMoveMult = 0.9;
	
	private double gravity  = 0.6;
	
	private int health = 0;  
    private int grenadeCount = 0; //How many grenades the player has
    private int shieldCount = 0; //How many shields the player has
    
    private int damageFlashingLeft = 0;
    private boolean vis = true;
	  
	// CONSTRUCTOR
	public Player(int x, int y) {
		super("AliceStanding2.png",x,y,128,128);
		velX = 0;
		velY = 0;
		onSurface = false;
	}
	
	public Player(int x, int y, int h, int g, int s) {
		this(x,y);
		health = h;
		grenadeCount = g;
		shieldCount = s;
	}
	
	// METHODS
	public void walk(int dir) {
		if (Math.abs(velX) < maxSpeed)
			velX += dir;
		
		if (dir > 0)
			super.setFacingDirection(true);
		else
			super.setFacingDirection(false);
	}
	
	public void jump(double vel, boolean checkGround) {
		if (!checkGround || onSurface) {
			velY = vel;
		}
	}
	
	public void jump() {
		jump(-jumpVelocity, true);
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getGrenadeNum() {
		return grenadeCount;
	}
	
	public int getShieldNum() {
		return shieldCount;
	}
	
	public void addShieldNum(int num)
	{
		shieldCount += num;
	}
	
	public void damage(int num)
	{
		if (num > 0)
		{
			if (damageFlashingLeft > 0)
				return;
			jump(-9, false);
			damageFlashingLeft = 25;
		}
		
		health -= num;
	}
	
	public void addGrenadeNum(int num)
	{
		grenadeCount += num;
	}
	
	public void tick() {
		
		velY += gravity; // Gravity
		velX *= airMoveMult; // Friction
		
		moveByAmount(velX, velY);
		
		onSurface = false;
		
		vis = !vis;
		
		damageFlashingLeft--;
		if (damageFlashingLeft < 0)
		{
			damageFlashingLeft = 0;
			setVisibility(true);
		}
		else if (damageFlashingLeft > 0)
			setVisibility(vis);
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
		//Platform
		if (c.getTag() == "Platform")
		{
			//If 2/3 of the way to the left is whithin boundaries, and 2/3 of the way to the right is in boundaries,
			//and our bottom edge is inside the object, and our bottom edge is above 32 pixels down into the object
			if ((myLBound+2 * myRBound)/3.0f >= otherLBound && myBBound >= otherTBound &&
					(2 * myLBound+myRBound)/3.0f <= otherRBound && myBBound <= otherTBound + 32)
			{
				velY = 0;
				onSurface = true;
				moveByAmount(0,-(myBBound - otherTBound));
				//System.out.println(myBBound - otherTBound+" vs 32");
				return true;
			}
		} else if (c.getTag() == "JumpPad")
		{
			if ((myLBound+2 * myRBound)/3.0f >= otherLBound && myBBound >= otherTBound && (2 * myLBound+myRBound)/3.0f <= otherRBound && myBBound <= otherBBound)
			{
				if (velY > 0)
					velY = 0;
				
				velY += -22;
				
				
				onSurface = false;
				moveByAmount(0,-(myBBound - otherTBound));
				return true;
			}
		} else { //Enemies, powerups, etc.
			if ((myRBound >= otherLBound && myBBound >= otherTBound && myLBound <= otherRBound && myBBound <= otherBBound))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double[] getBoundsX() {
		// TODO Auto-generated method stub
		double[] bounds = {getX() + 10 * 4, getX() + getWidth() - 11 * 4};
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
		return "Player";
	}
	
}
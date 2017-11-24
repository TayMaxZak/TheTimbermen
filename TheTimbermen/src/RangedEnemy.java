import java.util.ArrayList;

public class RangedEnemy extends Enemy {
	private ArrayList<Projectile> projectiles;
	private ArrayList<Locomotion> movingObjects;
	
	private int reload = 0;
	private int burstReload = 0;
	private int burstSize = 0;
	
	private double projSpeed = 6; ///WAS 8
	private int projLife = 154; ///WAS 100
	private int projReload = 100; ///WAS 80
	
	private int projBurstSize = 3;
	private int projburstReload = 6;
	
	//Needs access to the projectiles and the moving objects so that it could create projectiles
	public RangedEnemy(int x, int y, ArrayList<Enemy> list, ArrayList<Projectile> projs, ArrayList<Locomotion> locs)
	{
		super(x,y,"TimbermanFireballStanding1.png",list);
		projectiles = projs;
		movingObjects = locs;
	}
	
	public void tick()
	{
		super.tick();
		
  		double projX = (getBoundsX()[0] + getBoundsX()[1])/2;
  		double projY = (getBoundsY()[0] + getBoundsY()[1])/2;
  		
  		//System.out.println("ASIH(*ASG*A(*GA(SGH(ASHG(U*ASG");
  		
  		reload--;
  		burstReload--;
  		
		
  		if (burstReload <= 0 && !isStunned() && burstSize > 0)
  		{
  			burstSize--;
	  		Projectile fireball = new Projectile("FireballStatic.png", projX, ///direction * 10
	  				projY, direction * projSpeed, projSpeed * 0.04 * (Math.random() - 0.5), 0.0, 0.0, 64, projectiles, movingObjects, "Fireball", projLife); ///last number was 100
	  		
	  		movingObjects.add(fireball); //Add it to the list of moving objects
	  		projectiles.add(fireball); //Add it to the list of objects to draw
	  		

	  		burstReload = projburstReload;
  		}
  		
  		if (reload <= 0 && !isStunned())
  		{
  			burstSize = projBurstSize;
	  		reload = projReload; ///WAS 20
  		}
	}
}

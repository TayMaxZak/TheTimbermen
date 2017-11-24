import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

/**
   TODO This is the main hub of the program.
   TODO  The Game class is responsible for tracking inputs,
   the current level, updating positions, and drawing everything. It
   heavily relies on the Level class to function.

   @author  TODO Thomas Z
   @version TODO 5/16/16

   Period - TODO 4
   Assignment - TODO Final Project

   Sources - TODO Thomas Z
 */
public class Game extends JPanel implements KeyListener
{
  //WINDOW CREATION
  public static final int DRAWING_WIDTH = 1280;
  public static final int DRAWING_HEIGHT = 720;
  
  //PROGRESS TRACKING
  ///WAS 0
  private int currentLevel = 0; //Stores progress in the game. 0 = menu.
  
  //OBJECTS
  private Player alice; //The object representing the player
  private Level level; //The object representing the current level
  private ArrayList<Locomotion> movingObjects;
  
  //GAME DATA
  private int[] playerSpawn; //The spawn position of the player, format {x,y}. Determined by current level
  
  private double cameraOffsetX; //Used to keep the player onscreen at all times
  private double cameraOffsetY;
  
  private double cameraOffsetWeight = 1.0; //Used to make the camera move more gradually
  //private boolean increaseWeight = true;
  
  //INPUT
  private boolean leftKeyPressed, rightKeyPressed, upKeyPressed; //Input tracking
  
  //GUI
  private Image bkgImage;
  private Image titleImage;
  
  private Font textBlockFont; //Used for large bodies of text
  private Font textTitleBigFont; //Used for BIG titles
  private Font textTitleSmallFont; //Used for smaller titles
  private Font textCaptionFont; //Used for captions
  private Font textUIFont; //Used for captions
  
  private int levelTextOffset = 100; //Offset from center of screen
  
  private int guiIconOffset = 32;
  private int guiIconSpacing = 96;
  private int guiTextOffsetX = 38;
  private int guiTextOffsetY = 58;
  
  private Sprite health; //Icon for health
  private Sprite grenades; //Icon for grenades
  private Sprite shields; //Icon for shields
  
  private double tooltipTime; //How much longer the tooltip will stay up

  public Game () {
	  super();
	  
	  bkgImage = new ImageIcon("Bkg.png").getImage();
	  titleImage = new ImageIcon("Title2.png").getImage();
	  
	  movingObjects = new ArrayList<Locomotion>();
	  
	  setBackground(new Color(34, 47, 50));
	  //level = new Level(currentLevel);
	  
	  //Retrieve the player spawn from the initial level
	  respawn(false);
	  //alice = new Player(playerSpawn[0], playerSpawn[1]);
	  
	  //setBackground(new Color(45*2, 57*2, 55*2));
	 
	  textBlockFont = new Font("Cambria", Font.PLAIN, 20);
	  textUIFont = new Font("Calibri", Font.PLAIN, 24);
	  textTitleBigFont = new Font("Cambria", Font.BOLD, 42);
	  textTitleSmallFont = new Font("Calibri", Font.PLAIN, 52);
	  textCaptionFont = new Font("Calibri", Font.BOLD, 24);
	  
	  //movingObjects.add(alice);
	  
	  health = new Sprite("IconHealth.png", guiIconOffset, guiIconOffset, 32, 32);
	  grenades = new Sprite("IconGrenade.png", guiIconOffset + guiIconSpacing, guiIconOffset, 32, 32);
	  shields = new Sprite("IconShield.png", guiIconOffset + guiIconSpacing * 2, guiIconOffset, 32, 32);
  }
  
  //Initialize Game values based on current level
  private void initLevel()
  {
	level = new Level(currentLevel, this);
	  
  	playerSpawn = level.getPlayerSpawn();
  	alice = new Player(3, 3); //Doesnt matter what we initialize here, we just want Alice to exist so that we can add her to the list of moving objects
  	movingObjects.clear();
  	movingObjects.add(alice);
  	
	for(int i = 0; i < level.getEnemies().size(); i++) {
		Enemy ai = level.getEnemies().get(i);
		movingObjects.add(ai);
	}
	
	tooltipTime = 200;
  }
  
  

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background
    
    double tooltipOpacity = tooltipTime/200.0;

    //Turn on that silky smooth anti-aliasing that Java pretends it doesnt have
    //Credit to: http://stackoverflow.com/questions/4285464/java2d-graphics-anti-aliased
    Graphics2D g2d = (Graphics2D)g;
    
    g2d.setRenderingHint(
    	    RenderingHints.KEY_ANTIALIASING,
    	    RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(
    	    RenderingHints.KEY_TEXT_ANTIALIASING,
    	    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    int width = getWidth();
    int height = getHeight();
    
    //Handle camera stuff
    //cameraOffsetX = alice.getX() - playerSpawn[0];
    cameraOffsetX = alice.getX() + alice.getWidth()/2 - width/2 ;
    //cameraOffsetY = alice.getY() - playerSpawn[1];
    cameraOffsetY = alice.getY() + alice.getHeight()/2 - height/2 ;
    
    int curOffsetX = (int)(cameraOffsetX * cameraOffsetWeight);
    int curOffsetY = (int)(cameraOffsetY * cameraOffsetWeight);
    
    g.drawImage(bkgImage, 0, 0, width, height, this);
        
    g.setFont(textBlockFont);
    
    int ii = 0;
    //Credit to: http://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
    g.setColor(new Color(211,255,211,40));
    
    for (String line : level.getDesc().split("\n"))
    	//movingObjects
    {
    	int lineHeight = (int)(g.getFontMetrics().getHeight() * 0.9);
        g.drawString(line, (int)(levelTextOffset/4 - curOffsetX), (int)(playerSpawn[1] - curOffsetY - levelTextOffset + 20) + (ii + 1) * lineHeight);
    	ii++;
    }
    
    if (currentLevel > 5)
    {
	    //Drawing level text
    	g.setColor(new Color(211,255,211,40 + (int)(215 * tooltipOpacity)));
    	
	    g.setFont(textTitleBigFont);
	    g.drawString("Thanks for playing!", (int)(levelTextOffset/4 - curOffsetX), (int)(playerSpawn[1] - curOffsetY - levelTextOffset));    	
    }
    else if (currentLevel > 0)
    {
	    //Drawing level text
    	g.setColor(new Color(211,255,211,40 + (int)(215 * tooltipOpacity)));
    	
	    g.setFont(textTitleSmallFont);
	    g.drawString("CHAPTER " + currentLevel, (int)(levelTextOffset/4 - curOffsetX), (int)(playerSpawn[1] - curOffsetY - levelTextOffset));
    } else
    {
    	g.drawImage(titleImage, width/2 - 34 * 8, height/4 - 6 * 8, 68 * 8, 12 * 8, this);
    }
    
    
    
    //Go through all of the level objects and render them
	for(int i = 0; i < level.getPlatforms().size(); i++) {
		Platform plat = level.getPlatforms().get(i);
		plat.draw(g,this, curOffsetX, curOffsetY);
	}
	
	for(int i = 0; i < level.getProps().size(); i++) {
		Sprite prop = level.getProps().get(i);
		prop.draw(g,this, curOffsetX, curOffsetY);
	}
	
	for(int i = 0; i < level.getProjs().size(); i++) {
		Projectile proj = level.getProjs().get(i);
		proj.draw(g,this, curOffsetX, curOffsetY);
	}
	
	//Draw the player
    alice.draw(g,this, curOffsetX, curOffsetY);
	
    //Draw the enemies over the player
	for(int i = 0; i < level.getEnemies().size(); i++) {
		Enemy ai = level.getEnemies().get(i);
		ai.draw(g,this, curOffsetX, curOffsetY);
	}

    //Drawing GUI
    if (currentLevel > 1)
    {
    	g.setFont(textUIFont);
    	g.setColor(new Color(211,255,211,255));
    	
	    //Health
	    g.drawString(""+alice.getHealth(), guiIconOffset + guiTextOffsetX, guiTextOffsetY);
	    health.draw(g, this, 0, 0);
	    
	    if (currentLevel > 2)
	    {
		    //Grenades
		    grenades.draw(g, this, 0, 0);
		    g.drawString("[Q] "+alice.getGrenadeNum(), guiIconOffset + guiIconSpacing + guiTextOffsetX, guiTextOffsetY);

		    if (currentLevel > 3)
		    {
			    g.drawString("[E] "+alice.getShieldNum(), guiIconOffset + guiIconSpacing * 2 + guiTextOffsetX, guiTextOffsetY);
			    shields.draw(g, this, 0, 0);
		    }
	    }
    }
    
    //Drawing tooltip subtitle
    
    
    g.setColor(new Color(211,255,211,(int)(255 * tooltipOpacity)));
    
    if (level.getTooltip().length() > 0)
    {
	    String str = "Tip: " + level.getTooltip();
	    
	    g.setFont(textCaptionFont);
	    g.drawString(str, (int)(width/2.0 - g.getFontMetrics().stringWidth(str)/2.0), (int)(height - 50));
    }
    
    //Make it darker as we fall to our doom
    double curY = alice.getY() - (level.getDeathHeight() - 600);
    if (curY < 0)
    	curY = 0;
    
    double darkOpacity = curY/2;
    
    if (darkOpacity > 255)
    	darkOpacity = 255;
    
    g.setColor(new Color(10,20,20,(int)(darkOpacity)));
    g.fillRect(0, 0, width, height);
  }
  
  public ArrayList<Locomotion> getMovingObjects()
  {
	  return movingObjects;
  }

  //Handle keyboard inputs
  public void keyPressed(KeyEvent e) {
  	if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
		leftKeyPressed = true;
  	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
  		rightKeyPressed = true;
  	} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
  		upKeyPressed = true;
  	} else if (e.getKeyCode() == KeyEvent.VK_Z) {
  		currentLevel++;
  		respawn(false);
  	} else if (e.getKeyCode() == KeyEvent.VK_ENTER && currentLevel == 0) {
  		currentLevel++;
  		respawn(false);
  	}
  	else {
  		//If we got this far, we are dealing with projectiles
  		double projX = (alice.getBoundsX()[0] + alice.getBoundsX()[1])/2;
  		double projY = (3 * alice.getBoundsY()[0] + alice.getBoundsY()[1])/4;
  		
  		int sign  = (int)Math.signum(alice.getVelX());
  		if (sign == 0) sign = 1;
  		
  		if (e.getKeyCode() == KeyEvent.VK_Q && alice.getGrenadeNum() > 0)
  		{
  			alice.addGrenadeNum(-1);
	  		//Create an energy grenade
	  		Projectile energyGrenade = new Projectile("GrenadeStatic.png", projX,
	  				projY, sign * 10 + (int)alice.getVelX(), 10, 0.0, -0.5, 64, level.getProjs(), movingObjects, "Grenade", 200);
	  		
	  		movingObjects.add(energyGrenade); //Add it to the list of moving objects
	  		level.addProj(energyGrenade); //Add it to the list of objects to draw
	  		
	  		//System.out.println("nade");
  		}
  		
  		else if (e.getKeyCode() == KeyEvent.VK_E && alice.getShieldNum() > 0)
  		{
  			alice.addShieldNum(-1);
	  		//Create an energy shield
	  		Projectile energyShield = new Projectile("ShieldStatic.png", projX,
	  				projY, sign * 12 + (int)alice.getVelX(), 0, sign * -0.3, 0.0, 64, level.getProjs(), movingObjects, "Shield", 200);
	  		
	  		movingObjects.add(energyShield); //Add it to the list of moving objects
	  		level.addProj(energyShield); //Add it to the list of objects to draw
  		}
  	}
  }
    
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
		leftKeyPressed = false;
  	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
  		rightKeyPressed = false;
  	} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
  		upKeyPressed = false;
  	}
  }

  public void keyTyped(KeyEvent e)
  {
	  
  }
  
  
  public void run() {
	//Standard game tick
  	while(true) {
  		//increaseWeight = true;
  		if (leftKeyPressed) {
  			alice.walk(-1);
  			//increaseWeight = false;
  	  	} 
  		if (rightKeyPressed) {
  			alice.walk(1);
  			//increaseWeight = false;
  	  	}
  		if (upKeyPressed) {
  			alice.jump();
  			upKeyPressed = false;
  			//increaseWeight = false;
  	  	}
  		
  		tooltipTime -= 0.5;
  		
  		if (tooltipTime < 0)
  			tooltipTime = 0;
  		
  		//TEMP: This could work in the future. Stretch goal.
  		/*
  		if (increaseWeight)
  		{
	  		cameraOffsetWeight += 0.005;
	  		
	  		if(cameraOffsetWeight > 1.0)
	  			cameraOffsetWeight = 1.0;
  		}
  		else
  		{
	  		cameraOffsetWeight -= 0.005;
	  		
	  		if(cameraOffsetWeight < 0.0)
	  			cameraOffsetWeight = 0.0;  			
  		}
  		*/

  		//Go through all moving objects and change velocity/position
  		for(int i = 0; i < movingObjects.size(); i++) {
  			Locomotion loc = movingObjects.get(i);
  			loc.tick();
  		}

  		checkAlice();
  		cleanup();
  		checkCollision();
  		
  		repaint();
  		
  		// WAIT
  		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  	}
  }
	 
  //If she falls too far, respawn and restart the level.
  private void checkAlice() {    
  	if (alice.getY() > level.getDeathHeight())
  	{
  		if (currentLevel == 0)
  			currentLevel++;
  		respawn(true);
  	}
  }
  
  private void respawn(boolean useSpawnDelay)
  {
	  if (useSpawnDelay)
	  {		  
		// WAIT
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
		//level = new Level(currentLevel, this); //Reset the level
		initLevel();
		alice = new Player(playerSpawn[0], playerSpawn[1], level.getHealth(), level.getGrenades(), level.getShields());
		movingObjects.set(0, alice); //Add Alice back to the list of objects to track, or else the game wont update her position anymore
  }
  
  private void checkCollision()
  {
	//Go through all moving objects and change velocity/position
	for(int i = 0; i < movingObjects.size(); i++) {
		Locomotion loc = movingObjects.get(i);
		if(loc instanceof Collidable) //Check if this moving object is collidable
		{
			Collidable col = (Collidable)loc; //This must be an enemy, a player, or a projectile
			//Enemy, Player, and Projectile all have a check(Collidable c) method
			
		    //Check collision against the platforms
			for(int b = 0; b < level.getPlatforms().size(); b++) {
				Platform plat = level.getPlatforms().get(b);
				
				boolean hit = col.check(plat);
				
				if (hit)
				{
					
					if (col.getTag() == "Player" && plat.getTag() == "LevelToken")
					{
						//System.out.println("Old level: " + currentLevel);
				  		currentLevel++;
				  		//System.out.println("New level: " + currentLevel);
				  		respawn(false);
					}
					
					//Powerups
					if (col.getTag() == "Player" && plat.getTag() == "HealthPickup")
					{
				  		alice.damage(-1);
				  		
				  		plat.check(col);
					}
					
					if (col.getTag() == "Player" && plat.getTag() == "GrenadePickup")
					{
				  		alice.addGrenadeNum(1);
				  		plat.check(col);
					}
					
					if (col.getTag() == "Player" && plat.getTag() == "ShieldPickup")
					{
				  		alice.addShieldNum(1);
				  		plat.check(col);
					}
				}
			}
			
		    //Check collision against the projectiles
			for(int b = 0; b < level.getProjs().size(); b++) {
				Projectile proj = level.getProjs().get(b);
				
				if(col.getTag() == "Shield" && proj.getTag() == "Fireball")
				{
					///SHIELD DVA MECHANIC
					//proj.check(col); //We want the fireball to check so that it gets destroyed instead of the shield
				}
				
				if(col.getTag() == "Player" && proj.getTag() == "Fireball")
				{
					boolean hit = proj.check(col); //We want the fireball to check so that it gets destroyed instead of the shield
					
					if (hit)
					{
						alice.damage(1);
						
						if (alice.getHealth() <= 0)
							respawn(false);
					}
				}
				
				if(col.getTag() == "Player" && proj.getTag() == "Shield" && proj.getTimeAlive() > 15)
				{
					//System.out.println("SFASFASF");
					boolean hit = proj.check(col); //We want the shield to check so that it gets destroyed instead of the player  && proj.getTimeAlive() > 100
					if (hit) alice.addShieldNum(1);
				}
			} //projectile loop
			
			//Check collision against enemies
			for(int b = 0; b < level.getEnemies().size(); b++) {
				Enemy ai = level.getEnemies().get(b);
				
				
				if (col.getTag() != "Enemy" && col.getTag() != "Fireball") //Don't want enemies to self destruct!
				{
					boolean hit = col.check(ai);
					if (hit)
					{
						if (col.getTag() == "Grenade")
						{
							ai.delete();
							movingObjects.remove(ai);
						} else if (col.getTag() == "Shield") {
							ai.stun();
						} else if (col.getTag() == "Player") {
							if (!ai.isStunned())
							{
								alice.damage(1);
							}
							
							if (alice.getHealth() <= 0)
								respawn(false);
							
							//return;
						}
					} //hit
				}
			} //enemy loop

		}
	} //moving objects loop 
  }
  
  //Go through all moving objects and make sure they dont fall too far
  private void cleanup() {    
		//Projectiles
		for(int i = 0; i < level.getProjs().size(); i++) {
			Projectile proj = level.getProjs().get(i);
			if (proj.getY() > level.getDeathHeight())
			{
				proj.delete();
				//movingObjects.remove(proj);
			}
		}
  }
  
  //Animation
  private void cycleProps()
  {
	for(int i = 0; i < level.getProps().size(); i++) {
		Sprite prop = level.getProps().get(i);
		prop.cycleImage(0, 1);
	}
  }

  //Creates the window
  public static void main(String[] args)
  {
    JFrame w = new JFrame("The Timbermen");
    w.setBounds(DRAWING_WIDTH/4, DRAWING_HEIGHT/4, DRAWING_WIDTH, DRAWING_HEIGHT);
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Game panel = new Game();
    w.addKeyListener(panel);
    w.add(panel);
    w.setResizable(true);
    w.setVisible(true);
    
    panel.run();
  }
}

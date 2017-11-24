import java.util.ArrayList;

/**
TODO This stores all level-specific data.
TODO  The information in this class depends on an index, known here as levelIndex, or currentLevel in the Game class.
This index determines every aspect of the level, and how it does so is determined by functions in this class.

@author  TODO Thomas Z
@version TODO 5/16/16

Period - TODO 4
Assignment - TODO Final Project

Sources - TODO Thomas Z
*/

public class Level {
	private ArrayList<Platform> platforms; //Collection of platforms in the level (i.e. walls, floors, powerups, level tokens, collidable)
	private ArrayList<Sprite> props; //Collection of props in the level (i.e. flowers, decorations, non-collidable)
	private ArrayList<Enemy> enemies; //Collection of enemies in the level (i.e. bad guys)
	private ArrayList<Projectile> projs; //Collection of projectiles in the level (i.e. grenades, shields, fireballs)
	private int levelIndex = 0; //Used to initialize the level
	
	//Makes level design and switching out images easier
	private String platformBrickGrass = "Platform1.png";
	private String platformBrick = "Platform2.png";
	
	private String blockBrickGrass = "Block1.png";
	private String blockBrick = "Block2.png";
	
	private String blueberryBush = "FoliageProps2.png";
	private String grassTall = "FoliageProps1.png";
	private String grassShort = "FoliageProps3.png";
	private String tree = "Tree.png";
	
	private String enemyNormal = "TimbermanStanding1.png";
	private String enemyRanged = "TimbermanFireballStanding1.png";
	private String levelToken = "LevelToken.png";
	
	private String healthPickup = "PowerupHealth.png";
	private String grenadePickup = "PowerupGrenade.png";
	private String shieldPickup = "PowerupShield.png";
	
	private String jumpPad = "JumpPad.png";
	
	private String[] faceGroup;
	
	//A little odd storing the Game here, but we need it to create RangedEnemies
	private Game game;
	
	public Level(int i, Game g)
	{
		  platforms = new ArrayList<Platform>();
		  props = new ArrayList<Sprite>();
		  enemies = new ArrayList<Enemy>();
		  projs = new ArrayList<Projectile>();
		  levelIndex = i;
		  faceGroup = new String[]{"face0.png","face1.png"};
		  game = g;
		  initialize();
	}
	
	public ArrayList<Platform> getPlatforms()
	{
		return platforms;
	}
	
	public ArrayList<Sprite> getProps()
	{
		return props;
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}
	
	public ArrayList<Projectile> getProjs()
	{
		return projs;
	}
	
	public void addProj(Projectile proj)
	{
		projs.add(proj);
	}
	
	//Returns the story text at the beginning of every level
	public String getDesc()
	{
		switch (levelIndex)
		{
		case 0:
			return "";
		case 1:
			return "Alice found herself in\nan unfamiliar land, all\nalone. .  .   .     .";
		case 2:
			return "She wishes to not be\nalone; she should be\ncareful what she wishes for.";
		case 3:
			return "Tap into your inner self,\nthe mind is capable\nof incredible feats.";
		case 4:
			return "When there are many paths,\nthe one that you choose\nmay decide the next.";
		case 5:
			return "Life is eqaully cruel to those\nwho deserve it most and to\nthose who deserve it least.";
		case 6:
			return "I hope you enjoyed this adventure, I had... fun making it!";
		default:
			return "o_o";
		}		
	}
	
	//Returns the number of grenades you start with
	public int getGrenades()
	{
		switch (levelIndex)
		{
		case 3:
			return 3;
		case 6:
			return 100;
		default:
			return 0;
		}		
	}
	
	//Returns the number of shields you start with
	public int getShields()
	{
		switch (levelIndex)
		{
		case 4:
			return 2;
		case 6:
			return 100;
		default:
			return 0;
		}		
	}
	
	//Returns the number of HP you start with
	public int getHealth()
	{
		switch (levelIndex)
		{
		case 5:
			return 3;
		case 6:
			return 100;
		default:
			return 3;
		}		
	}
	
	//Returns the tooltip to display at the bottom of the screen
	public String getTooltip()
	{
		switch (levelIndex)
		{
		case 0:
			return "Press Enter to start the game!";
		case 1:
			return "Use WASD or arrows keys to move! You can also use Spacebar to jump!";
		case 2:
			return "Look out for Timbermen! These guys can take you out real quick!";
		case 3:
			return "Press Q to throw a fireball! Be careful: you can only carry so many!";
		case 4:
			return "Press E to throw a shield! It can absorb enemy fireballs!";
		case 5:
			return "From now on, you have to find your own grenades and shields! Have fun!";
		case 6:
			return "Feel proud of yourself!";
		default:
			return "o_o";
		}		
	}
	
	//The initial position of the player
	public int[] getPlayerSpawn()
	{
		switch (levelIndex)
		{
		case 0:
			return new int[] {64, 200-128};
		case 1:
			return new int[] {200,500-128};
		case 2:
			return new int[] {264, 200-128};
		case 3:
			return new int[] {264, 200-128};
		case 4:
			return new int[] {264, 100-128};
		case 5:
			return new int[] {32, 500-128};
		case 6:
			return new int[] {96+128, 500-128};
		default:
			return new int[] {264, 500-128};
		}		
	}
	
	//Maximum distance the player can fall before restarting level
	public int getDeathHeight()
	{
		switch (levelIndex)
		{
		case 0:
			return 800;
		case 2:
			return 1500;
		case 4:
			return 1500;
		case 5:
			return 1200;
		default:
			return 1000;
		}
	}
	
	//Define level layout here
	//WARNING: Most of these values were made by guessing and checking, so there are a lot of unsimplified values
	private void initialize()
	{
		switch (levelIndex)
		{
		case 0:
			platforms.add(new Platform(platformBrickGrass,0,200,256,64));
			break;
		case 1: //LEVEL 1
			platforms.add(new Platform(platformBrickGrass,200,500,256,64));
			props.add(new Sprite(blueberryBush,200,500-64,64,64));
			//props.add(new Sprite(faceGroup,200,500-64,64,64));
			props.add(new Sprite(grassTall,200+100,500-64,64,64));

			platforms.add(new Platform(platformBrickGrass,500,400,256,64));
			//platforms.add(new Platform(platformBrick,500+256,400+64,-256,64));
			props.add(new Sprite(grassTall,500+100,400-64,-64,64));
			
			platforms.add(new Platform(platformBrickGrass,1000+256,300,-256,64));
			platforms.add(new Platform(platformBrickGrass,1256,300-128,256,64));
			platforms.add(new Platform(platformBrickGrass,1512,300,256,64));
			//props.add(new Sprite(grassShortStraight,900+256-64,300-64,-64,64));
			props.add(new Sprite(grassShort,1000+256-64,300-64-64,-64,128));
			props.add(new Sprite(grassShort,1000+256-64,300-64,64,64));
			props.add(new Sprite(blueberryBush,1000+300+64,300-64-128,-64,64));
			props.add(new Sprite(grassTall,1000+600,300-64,64,64));
			
			platforms.add(new Platform(levelToken,1512+256-64,300-64,64,64,"LevelToken", platforms)); //Level taken
			break;
		case 2: //LEVEL 2
			platforms.add(new Platform(platformBrickGrass,200,200,256,64));
			
			platforms.add(new Platform(platformBrick,500,350,256,64));
			enemies.add(new Enemy(600, 350-256, enemies));
			
			platforms.add(new Platform(platformBrickGrass,800,200,256,64));

			
			platforms.add(new Platform(platformBrickGrass,1256,300,256,64));
			props.add(new Sprite(grassTall,1256+128,300-64,64,64));
			enemies.add(new Enemy(1456, 300-256, enemies));
			
			platforms.add(new Platform(platformBrickGrass,1256,500+64,256,64));
			props.add(new Sprite(blueberryBush,1256+128,500,-64,64));
			props.add(new Sprite(grassTall,1256+128+64,500,64,64));
			
			platforms.add(new Platform(platformBrickGrass,1512,500,256,64));
			props.add(new Sprite(grassTall,1512+64,500-64,64,64));
			props.add(new Sprite(grassTall,1512+128+128,500-128,-128,128));
			props.add(new Sprite(grassTall,1512+256,500-64,-64,64));
			
			platforms.add(new Platform(platformBrickGrass,1768,500,256,64));
			props.add(new Sprite(grassShort,1768+256,500-128,-64,128));
			
			platforms.add(new Platform(platformBrickGrass,2024+256,500,256,64));
			props.add(new Sprite(grassShort,2024+256+64,500-64,-64,64));
			
			platforms.add(new Platform(blockBrickGrass,2024+512,564,256,256));
			platforms.add(new Platform(blockBrickGrass,2024+512+256,564+64,256,256));
			platforms.add(new Platform(blockBrickGrass,2024+1024,564,256,256));
			
			platforms.add(new Platform(jumpPad,2024+1024+256,564,64,64, "JumpPad", platforms));
			
			platforms.add(new Platform(platformBrickGrass,2024+512,350,256,64));
			props.add(new Sprite(grassTall,2024+512+64,350-64,-64,64));
			enemies.add(new Enemy(2024+512+64, 350-256, enemies));
			
			platforms.add(new Platform(platformBrickGrass,2024+512+256,200,256,64));
			
			platforms.add(new Platform(levelToken,2024+512+256+128-32,200-64,64,64,"LevelToken", platforms)); //Level taken
			break;
		case 3: //LEVEL 3
			platforms.add(new Platform(platformBrickGrass,200,200,256,64));
			props.add(new Sprite(grassTall,320,200-64,-64,64));
			
			platforms.add(new Platform(platformBrick,500,150,256,64));
			enemies.add(new Enemy(500+20-64, 150-256, enemies));
			enemies.add(new Enemy(500-20+256-64, 150-256, enemies));
			
			platforms.add(new Platform(platformBrickGrass,800,200,256,64));
			props.add(new Sprite(grassShort,800+128,200-128,64,128));
			
			platforms.add(new Platform(platformBrickGrass,1256,50,256,64));
			enemies.add(new Enemy(1256+128, 50-256, enemies));
			//platforms.add(new Platform(levelToken,1256+64+128,200-64,64,64,"LevelToken")); //Level taken
			
			platforms.add(new Platform(platformBrickGrass,1056+256,350,-256,64));
			props.add(new Sprite(tree,1056,350-256+64,256,256));
			
			platforms.add(new Platform(platformBrickGrass,1056-256,500,256,64));
			platforms.add(new Platform(grenadePickup,1056-256+64,500-64+1 * 4,64,64,"GrenadePickup", platforms));
			
			platforms.add(new Platform(platformBrickGrass,1856,100,256,64));
			platforms.add(new Platform(levelToken,1256-64+128+700,100-64,64,64,"LevelToken", platforms)); //Level taken
			break;
		case 4: //LEVEL 4
			platforms.add(new Platform(platformBrickGrass,200,100,256,64));
			props.add(new Sprite(grassShort,200+256,100-64,-64,64));
			
			platforms.add(new Platform(platformBrickGrass,-56,100+64,256,64));
			props.add(new Sprite(tree,-56,100-256+128,256,256));
			
			
			platforms.add(new Platform(platformBrickGrass,500,0,256,64));
			
			platforms.add(new Platform(platformBrickGrass,800,100,256,64));
			
			platforms.add(new Platform(platformBrickGrass,1000,400-64,256,64));
			platforms.add(new Platform(jumpPad,1000-64,400-64,64,64, "JumpPad", platforms));
			
			//platforms.add(new Platform(platformBrickGrass,1000-256,400,256,64));
			
			platforms.add(new Platform(blockBrick,1256,0,256,256));
			enemies.add(new RangedEnemy(1256-20+256-64, 0-256, enemies, projs, game.getMovingObjects())); //Ranged enemy
			platforms.add(new Platform(platformBrick,1256,400,256,64));
			
			platforms.add(new Platform(blockBrick,1856,0,256,256));
			enemies.add(new RangedEnemy(1856-20+256-64, 0-256, enemies, projs, game.getMovingObjects())); //Ranged enemy
			platforms.add(new Platform(platformBrick,1856,400,256,64));
			platforms.add(new Platform(healthPickup,1856+128-32,400-64+1 * 4,64,64, "HealthPickup", platforms));
			
			platforms.add(new Platform(platformBrick,1856,700,256,64));
			enemies.add(new Enemy(1856+128, 700-256, enemies));
			
			platforms.add(new Platform(platformBrick,1856,850,256,64));
			platforms.add(new Platform(grenadePickup,1856+128-32-64,850-64+1 * 4,64,64, "GrenadePickup", platforms));
			platforms.add(new Platform(shieldPickup,1856+128-32+64,850-64+1 * 4,64,64, "ShieldPickup", platforms));
			
			//platforms.add(new Platform(platformBrick,1856+256,850+64,256,64));
			platforms.add(new Platform(jumpPad,1856+256+64,700+64,64,64, "JumpPad", platforms));
			platforms.add(new Platform(jumpPad,1856-128,700+64,64,64, "JumpPad", platforms));
			
			platforms.add(new Platform(platformBrickGrass,1856+256,-64,256,64));
			props.add(new Sprite(tree,1856+256,-256,256,256));
			platforms.add(new Platform(platformBrickGrass,1856+512,0,256,64));
			
			platforms.add(new Platform(platformBrickGrass,3000,100,256,64));
			enemies.add(new Enemy(3000+128,100-256, enemies));
			
			platforms.add(new Platform(platformBrickGrass,3532,100-64,256,64));
			platforms.add(new Platform(levelToken,3532+64+128,100-128,64,64,"LevelToken", platforms)); //Level taken
			
			//Jackpot!
			platforms.add(new Platform(platformBrickGrass,-800,400,256,64));
			platforms.add(new Platform(healthPickup,-800,400-64+1 * 4,64,64, "HealthPickup", platforms));
			platforms.add(new Platform(grenadePickup,-800+64,400-64+1 * 4,64,64, "GrenadePickup", platforms));
			platforms.add(new Platform(shieldPickup,-800+128,400-64+1 * 4,64,64, "ShieldPickup", platforms));
			platforms.add(new Platform(shieldPickup,-800+128+64,400-64+1 * 4,64,64, "ShieldPickup", platforms));
			platforms.add(new Platform(jumpPad,-800+256+128,400,64,64, "JumpPad", platforms));
			break;
		case 5:
			platforms.add(new Platform(platformBrickGrass,0,500+64,256,64));
			enemies.add(new Enemy(128+32,500-256+64, enemies));
			props.add(new Sprite(tree,-256,500-256,256,256));
			
			platforms.add(new Platform(platformBrickGrass,-256,500-64,256,64));
			platforms.add(new Platform(shieldPickup,-256,500-128+1 * 4,64,64, "ShieldPickup", platforms));
			
			platforms.add(new Platform(platformBrickGrass,256,500-64,256,64));
			
			platforms.add(new Platform(platformBrick,256+512,500-128,256,64));
			
			platforms.add(new Platform(platformBrick,256+1024,500-128-64,256,64));
			platforms.add(new Platform(healthPickup,256+1024+128-32,500-256+1 * 4,64,64, "HealthPickup", platforms));
			
			platforms.add(new Platform(platformBrick,256+1024+512,500-128-64,256,64));
			enemies.add(new RangedEnemy(256+1024+512+64, 500-256-128-64, enemies, projs, game.getMovingObjects())); //Ranged enemy
			
			platforms.add(new Platform(platformBrickGrass,512+1024+512,500-256,256,64));
			platforms.add(new Platform(grenadePickup,512+1024+512+128-32,500-256-64+1 * 4,64,64, "GrenadePickup", platforms));
			platforms.add(new Platform(blockBrickGrass,512+1024+512+256,500-256,256,256));
			platforms.add(new Platform(platformBrickGrass,512+2048,500-256,256,64));
			
			platforms.add(new Platform(blockBrick,1024+2048,500-256,256,256));
			enemies.add(new RangedEnemy(1024+2048+128, 500-512, enemies, projs, game.getMovingObjects())); //Ranged enemy
			
			platforms.add(new Platform(blockBrickGrass,1024+2048+512,500-256-128,256,256));
			platforms.add(new Platform(blockBrickGrass,1024+2048+512+256+64,500-256-128,256,256));
			
			//Elevator :D
			platforms.add(new Platform(jumpPad,1024+2048+512+256,500-256-128,64,64, "JumpPad", platforms));
			platforms.add(new Platform(jumpPad,1024+2048+512+256,500-512-256,64,64, "JumpPad", platforms));
			
			//Platforms under jump pad
			platforms.add(new Platform(platformBrickGrass,1024+2048+512+256-64-32-128,500-256-128+256-64,256,64));
			platforms.add(new Platform(shieldPickup,1024+2048+512+256-64-32-128,500-256-128+256-128+1 * 4,64,64, "ShieldPickup", platforms));
			
			platforms.add(new Platform(platformBrickGrass,1024+2048+512+256-64-32+128,500-256-128+256-64,256,64));
			
			platforms.add(new Platform(platformBrickGrass,4096+64,500-512+256-64,256,64));
			platforms.add(new Platform(healthPickup,4096+128+64,500-512+256-128+1 * 4,-64,64, "HealthPickup", platforms));
			platforms.add(new Platform(healthPickup,4096+256+64,500-512+256-128+1 * 4,-64,64, "HealthPickup", platforms));
			
			//platforms.add(new Platform(platformBrickGrass,1024+2048+512+256-64-32,500-512-1024,256,64));
			
			platforms.add(new Platform(platformBrickGrass,1024+2048+512-64-32,500-512-1024,256,64));
			
			enemies.add(new Enemy(1024+2048+512-64-32,500-512-1024-256, enemies));
			platforms.add(new Platform(grenadePickup,1024+2048+512-64-32+64+32,500-512-1024-64+1 * 4,64,64, "GrenadePickup", platforms));
			
			platforms.add(new Platform(platformBrickGrass,4000,500-512-1024,256,64));
			enemies.add(new Enemy(1024+2048+512+256+256-64-32,500-512-1024-256, enemies));
			platforms.add(new Platform(grenadePickup,4000+64+32,500-512-1024-64+1 * 4,64,64, "GrenadePickup", platforms));
			
			platforms.add(new Platform(platformBrickGrass,1024+2048+512+256-64-32,500-2048,256,64));
			platforms.add(new Platform(levelToken,1024+2048+512+256-32+128,500-2048-64,64,64,"LevelToken", platforms)); //Level taken

			platforms.add(new Platform(platformBrick,1024+2048-64-32,500-512-1024-64,256,64));
			platforms.add(new Platform(platformBrick,1024+2048-64-32-256,500-512-1024-64,256,64));
			
			platforms.add(new Platform(platformBrick,1024+2048-64-32-256-512,500-512-1024,256,64));
			enemies.add(new RangedEnemy(1024+2048-64-32-256-512+32,500-512-1024-256, enemies, projs, game.getMovingObjects())); //Ranged enemy
			
			//Final stretch
			platforms.add(new Platform(platformBrickGrass,1024+2048-64-32-256-1024,500-512-1024,256,64));
			platforms.add(new Platform(platformBrickGrass,1024+2048-64-32-256-1024-256,500-512-1024,256,64));
			platforms.add(new Platform(platformBrickGrass,1024+2048-64-32-256-1024-512,500-512-1024,256,64));
			platforms.add(new Platform(platformBrickGrass,1024+2048-64-32-256-1024-512-256,500-512-1024,256,64));
			platforms.add(new Platform(platformBrickGrass,1024+2048-64-32-256-2048,500-512-1024,256,64));
			
			props.add(new Sprite(tree,1024+2048-32-256-1024,500-512-1024-256+64,256,256));
			props.add(new Sprite(tree,1024+2048-32-512-1024,500-512-1024-256+64,256,256));
			props.add(new Sprite(tree,1024+2048-32-512-1024-256,500-512-1024-256+64,-256,256));
			
			props.add(new Sprite(grassTall,1024+2048+32-256-2048,500-512-1024-64,64,64));
			props.add(new Sprite(grassTall,1024+2048+256-256-2048,500-512-1024-64,-64,64));
			props.add(new Sprite(grassShort,1024+2048+512-256-2048,500-512-1024-64,-64,64));
			props.add(new Sprite(grassShort,1024+2048+512-256-2048+80,500-512-1024-128,64,128));
			props.add(new Sprite(blueberryBush,1024+2048+512-256-2048+200,500-512-1024-64,-64,64));
			
			platforms.add(new Platform(levelToken,1024+2048-64-32-256-2048,500-512-1024-64,64,64,"LevelToken", platforms)); //Level taken
			
			//platforms.add(new Platform(blockBrickGrass,1024+2048+512+256+64,500-256-128,256,256));
			break;
		case 6:
			platforms.add(new Platform(platformBrickGrass,0,500,256,64));
			platforms.add(new Platform(platformBrickGrass,256,500,256,64));
			//platforms.add(new Platform(platformBrickGrass,256,500,256,64));
			//platforms.add(new Platform(platformBrickGrass,512,500,256,64));
			
			props.add(new Sprite(grassTall,0,500-64,64,64));
			props.add(new Sprite(grassShort,64,500-64,64,64));
			props.add(new Sprite(blueberryBush,128,500-64,64,64));
			props.add(new Sprite(enemyNormal,128+64,500-128,64,128));
			props.add(new Sprite(enemyRanged,64+256,500-128,64,128));
			props.add(new Sprite(tree,256+128,500-64-32,128,128));
			break;
		default: //MENU
			break;
		}
	}
}

Program by: Thomas Zakharzhevskiy
The Timbermen

Introduction:
	This game is a platforming game, though it is not intended to be a Mario clone. It is a series of levels that involve jumping, running, and fighting enemies.
	This game solves the problem of a light, simple game that has a set of interesting mechanics but stays accessible.
	This game is for anyone who wants to just have a bit of fun and try to come up with new ways of getting through the levels. Anyone can play this game and have fun.
	
	This game is the story of a player, named Alice, who ends up in a strange dream world with sentient trees.
	The enemies in this game, the Timbermen, are capable of quickly killing the player, and can only be defeated with special abilities.
	These abilities consist of a grenade attack, where the player throws a green fireball that instantly destroys any enemy it hits, and a shield ability.
	This shield is launched straight forward, and it absorbs any incoming projectiles it touches, and boomerangs back to the player, unless it hits an enemy.
	Enemies hit by the shield are stunned temporarily. Stunned enemies do no damage, and cannot move, making them easy to destroy, or easy to ignore. You decide.
	Every time you use an ability, you use up "ammo" for that ability. If you catch your shield after throwing it, you get to use it again.
	Powerups are a prominent part of the game, and can restore ammo (you can store up to 3 grenades and 3 shields) and health (you have a max health of 3)
	Powerups include:
		Health Restore (strawberry) +1, +2, or +3 HP
		Energy Grenade (green block) +1, +2, or +3 Grenades
		Energy Shield (blue block) +1, +2, or +3 Shields

Instructions:
        W, Spacebar, or up arrow to jump,
        A or left arrow to go left,
        D or right arrow to go right,
        Q tosses an energy grenade, and
        E throws an energy shield.

Class List: 
        Player: This class is responsible for all player-specific behaviour. This includes horizontal movement, jumping, attacking, blocking, health, and picking up powerups.
        Menu: Has buttons that can start the game or quit the game.
        Enemy: This class handles enemy movement, AI, how much damage they do, and appearance.
        RangedEnemy: A variant of Enemy that can shoot projectiles.
        Projectile: Can move sideways and can be affected by gravity/acceleration.
        Level: Keeps track of the level, including a list of platforms, a list of enemies, and what number this level is.
        Game: Keeps track of what number level the player is at. Also tracks camera movement, renders the game, and updates moving objects.
        Sprite: Most basic graphical element. Can store one or more images, can move, change visibility, animate, and more.
	Platform: A variant of Sprite that has collision.
		
Interfaces:
	Locomotion: Indicates that this object can move. Must return velocities, and must have a tick() method that updates position and velocity.
	Collidable: Indicates that this class can collide. Must return specifc X&Y boundaries, which enable more precise collision than image bounds.

Responsibility List:
        Thomas: Everything
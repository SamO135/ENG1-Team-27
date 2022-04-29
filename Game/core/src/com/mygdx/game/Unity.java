package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Animations.Explosion;
import com.mygdx.game.CameraUtils.cam;
import com.mygdx.game.Collectables.Coin;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Enemies.EnemyShip;
import com.mygdx.game.Enemies.Hurricane;
import com.mygdx.game.utils.Projectile;
import com.mygdx.game.utils.TiledObjectUtil;
import com.mygdx.game.utils.gui;
import com.mygdx.game.Colliders.BaseCollider;

import java.util.ArrayList;
import java.util.Iterator;

import static com.mygdx.game.utils.Constants.PPM;
import static java.lang.Math.toRadians;

public class Unity extends ApplicationAdapter {

	public static int collagesNotBossCount;
	static float w;
	static float h;

	private SpriteBatch batch;
	private SpriteBatch HUDbatch;
	private Texture img;
	public static Texture blank;
	private Texture imgenemy;
	private Sprite sprite;
	private Sprite spriteEnemyAlcuin;
	private Sprite spriteEnemyGoodricke;
	private Sprite spriteEnemyJames;
	private Sprite spriteEnemyDerwent;
	private EnemyShip AlcuinShip;
	public EnemyShip GoodrickeShip;
	private EnemyShip JamesShip;
	private EnemyShip DerwentShip;
	private BitmapFont SmallFont;
	private BitmapFont MediumFont;
	private BitmapFont LargeFont;

	private final float scale = 2.0f;
	private OrthographicCamera camera;

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;
	public static int mapWidth = 0;
	public static int mapHeight = 0;

	private Box2DDebugRenderer b2dr;
	public static World world;
	public BaseCollider player;
	public College Goodricke;
	private College Alcuin;
	private College Derwent;
	private College James;
	private int spawnx;
	private int spawny;

	private static float health = 1f;
	private float playerRotation;
	private float playerBaseRotationSpeed = 0.8f;
	private static float playerRotationUpgrade;
	public static float playerDmgFromBullet;
	public static float damageUpgrade;
	private static float weatherResistanceUpgrade;
	public static float numOfHurricanes = 2;
	private int plunder = 0;
	private float score = 0;
	
	private int cannonCooldown = 0;
	private static int cannonCooldownSpeed = 1;

	private ArrayList<Coin> coins;
	private ArrayList<Projectile> cannonballs;
	private ArrayList<Projectile> enemyCannonballs;
	private ArrayList<College> Collages;
	private ArrayList<EnemyShip> enemyShips;
	private ArrayList<Explosion> explosions;
	private ArrayList<Hurricane> hurricanes;
	public enum Screen{
		NewOrResume, Help, DifficultySelection, Home, MAIN_GAME, Shop, End, GameOver
	}
	Screen currentScreen = Screen.NewOrResume;
	public enum Difficulty{
		Easy, Normal, Hard
	}
	Screen previousScreen = currentScreen;
	public static Difficulty difficulty = Difficulty.Hard;

	Preferences prefs;

	@Override
	public void create () {
		prefs = Gdx.app.getPreferences("York Pirates");


		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		spawnx = 700;
		spawny = 700;

		cannonballs = new ArrayList<Projectile>();
		enemyCannonballs = new ArrayList<Projectile>();
		Collages = new ArrayList<College>();
		explosions = new ArrayList<Explosion>();		// Instantiating all the arrays for the different game objects
		enemyShips = new ArrayList<EnemyShip>();
		hurricanes = new ArrayList<Hurricane>();
		coins = new ArrayList<Coin>();

		//batches
		batch = new SpriteBatch();
		HUDbatch = new SpriteBatch();
		
		img = new Texture("PirateShip3Mast.png");
		imgenemy = new Texture("PirateShipEnemy.png");
		blank = new Texture("Blank.png");
		
		//init collision system
		world = new World(new Vector2(0, 0f), false);
		b2dr = new Box2DDebugRenderer();

		//initialise colleges
		Goodricke = new College(new Vector2(3140, 3110), "TowerGoodricke.png", true, world, "Goodricke", prefs);
		Collages.add(Goodricke);
		GoodrickeShip = new EnemyShip(new Vector2(2900, 2600), Goodricke, world, spriteEnemyGoodricke, prefs);
		enemyShips.add(GoodrickeShip);

		Alcuin = new College(new Vector2(1500, 1300), "TowerAlcuin.png", false, world, "Alcuin", prefs);
		Collages.add(Alcuin);
		AlcuinShip = new EnemyShip(new Vector2(1500, 1000), Alcuin, world, spriteEnemyAlcuin, prefs);
		enemyShips.add(AlcuinShip);

		Derwent = new College(new Vector2(520, 2170), "TowerDerwent.png", false, world, "Derwent", prefs);
		Collages.add(Derwent);
		DerwentShip = new EnemyShip(new Vector2(520, 1900), Derwent, world, spriteEnemyDerwent, prefs);
		enemyShips.add(DerwentShip);

		James = new College(new Vector2(3080, 1080), "TowerJames.png", false, world, "James", prefs);
		Collages.add(James);
		JamesShip = new EnemyShip(new Vector2(2900, 800), James, world, spriteEnemyJames, prefs);
		enemyShips.add(JamesShip);

		collagesNotBossCount = Collages.size() - 1;
		
		//initialise fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Oswald-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1.4f;
		parameter.size = 20;
		SmallFont = generator.generateFont(parameter);
		parameter.size = 32;
		MediumFont = generator.generateFont(parameter);
		parameter.size = 64;
		LargeFont = generator.generateFont(parameter);

		//initialise sprites
		sprite = new Sprite(img);
		sprite.setSize(128,64);
		sprite.setOrigin(64, 32);
		sprite.setRotation(180f);
		spriteEnemyAlcuin = new Sprite(imgenemy);
		spriteEnemyDerwent = new Sprite(imgenemy);
		spriteEnemyGoodricke = new Sprite(imgenemy);
		spriteEnemyJames = new Sprite(imgenemy);

		//initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / scale, h / scale);

		//initialise player
		player = new BaseCollider(new Vector2(spawnx , spawny), 128, 64, false, world);

		//initialise coins
		coins.add(new Coin(new Vector2(1000, 500), 200, 1, prefs));
		coins.add(new Coin(new Vector2(2500, 1500), 200, 2, prefs));
		coins.add(new Coin(new Vector2(2900, 450), 200, 3, prefs));
		coins.add(new Coin(new Vector2(1000, 2500), 200, 4, prefs));

		//initialise hurricanes
		for (int i = 0; i < numOfHurricanes; i ++){
			hurricanes.add(new Hurricane(prefs, i));
		}


		//enemyShips.add(Alcuin.getColliderBody());
		//enemyShips.add(Derwent.getColliderBody());
		//enemyShips.add(Goodricke.getColliderBody());
		//enemyShips.add(James.getColliderBody());

		map = new TmxMapLoader().load("MapAssets/GameMap.tmx");
		MapProperties props = map.getProperties();
		mapWidth = props.get("width", Integer.class);
		mapHeight = props.get("height", Integer.class);
		tmr = new OrthogonalTiledMapRenderer(map);

		TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("Collision-layer").getObjects());
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());  // deltaTime is time between a frame refresh

		if (currentScreen == Screen.DifficultySelection){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();

			HUDbatch.begin();

			//draw menu
			gui.drawDifficultySelectionScreen(HUDbatch, SmallFont, LargeFont);

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
				difficulty = Difficulty.Easy;
				currentScreen = Screen.Home;
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
				difficulty = Difficulty.Normal;
				currentScreen = Screen.Home;
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
				difficulty = Difficulty.Hard;
				currentScreen = Screen.Home;
			}

			//Change values based on difficulty
			applyDifficulty(difficulty);

			HUDbatch.end();
		}

		//menu screen
		if(currentScreen == Screen.Home){

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();
			
			HUDbatch.begin();
			
			//draw menu
			gui.drawMenuScreen(HUDbatch, SmallFont, LargeFont, MediumFont, difficulty);


			HUDbatch.end();
		}
    
		if(currentScreen == Screen.MAIN_GAME){
			
			//reduce cannon cooldown
			if (cannonCooldown > 0){
				cannonCooldown -= cannonCooldownSpeed;
			}
			//reduce college shoot cooldown
			for (College college : Collages){
				if (college.shootCooldown > 0){
					college.shootCooldown -= 1;
				}
			}
			//reduce enemy ship shoot cooldown
			for (EnemyShip enemyShip : enemyShips){
				if (enemyShip.shootCooldown > 0){
					enemyShip.shootCooldown -= 1;
				}
			}
			
			ScreenUtils.clear(0, 0, 1, 1);

			b2dr.render(world, camera.combined.scl(PPM));

			//Draw the map
			tmr.render();

			//Update the position of the player's image
			//sprite.setPosition(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM);
			sprite.setPosition(player.getBody().getPosition().x, player.getBody().getPosition().y);
			player.update();
			//Update the position of the enemy ships' image
			spriteEnemyAlcuin.setPosition(AlcuinShip.getPosition().x, AlcuinShip.getPosition().y);
			spriteEnemyDerwent.setPosition(DerwentShip.getPosition().x, DerwentShip.getPosition().y);
			spriteEnemyGoodricke.setPosition(GoodrickeShip.getPosition().x, GoodrickeShip.getPosition().y);
			spriteEnemyJames.setPosition(JamesShip.getPosition().x, JamesShip.getPosition().y);

			batch.begin();

			//Draw player & enemies
			sprite.draw(batch);
			spriteEnemyAlcuin.draw(batch);
			spriteEnemyDerwent.draw(batch);
			spriteEnemyGoodricke.draw(batch);
			spriteEnemyJames.draw(batch);


			//Shooting code -- player shooting
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && cannonCooldown <= 0){
				cannonballs.add(new Projectile(new Vector2(player.getBody().getPosition().x, player.getBody().getPosition().y), mousePos(Gdx.input.getX(), Gdx.input.getY()).nor(), false));
				cannonCooldown = 40;
			}
			//Enemy college shooting
			Vector2 displacement;
			for (College college : Collages){
				displacement = new Vector2(player.getBody().getPosition().x + (sprite.getWidth()/2) - college.getLocation().x, player.getBody().getPosition().y - college.getLocation().y);
				if (!college.isCaptured() && displacement.len() <= 600 && college.shootCooldown <= 0){
					enemyCannonballs.add(new Projectile(new Vector2(college.getLocation()), displacement.nor(), true));
					college.shootCooldown = 120;
				}
			}
			//Enemy ship shooting
			for (EnemyShip enemyShip : enemyShips){
				displacement = new Vector2(player.getBody().getPosition().x + (sprite.getWidth()/2) - enemyShip.getPosition().x, player.getBody().getPosition().y - enemyShip.getPosition().y);
				if (!enemyShip.isCaptured() && displacement.len() <= 400 && enemyShip.shootCooldown <= 0){
					enemyCannonballs.add(new Projectile(new Vector2(enemyShip.getPosition()), displacement.nor(), true));
					enemyShip.shootCooldown = 180;
				}
			}

			/*if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
				AlcuinShip.position = new Vector2(1200, 1000);
				String message = "";
				Vector2 direction = new Vector2();
				for (College college : Collages){
					direction = new Vector2(player.getBody().getPosition().x + (sprite.getWidth()/2) - college.getLocation().x, player.getBody().getPosition().y - college.getLocation().y);
					message += college.getLocation() + ", ";
					//enemyCannonballs.add(new Projectile(new Vector2(college.getColliderBody().getPosition().x, college.getColliderBody().getPosition().y), direction));
					enemyCannonballs.add(new Projectile(new Vector2(college.getLocation()), direction));
				}
				//System.out.println("BaseCollider: " + player.getBody().getPosition());
				//System.out.println("Sprite: " + sprite.getX() + ", " + sprite.getY());
				//System.out.println("Projectile Collider: " + player.getProjectileCollider().position);
				System.out.println(direction.len());
				System.out.println("");
				//System.out.println("Colleges: " + message);
				//System.out.println("Direction: " + direction);
			}*/

			//Update projectiles -- player cannonballs
			ArrayList<Projectile> cannonballsToRemove = new ArrayList<Projectile>();
			for(Projectile cannonball : cannonballs){
				cannonball.update(Gdx.graphics.getDeltaTime());
				if(cannonball.remove){
					cannonballsToRemove.add(cannonball);
				}
				else{
					cannonball.render(batch);
				}
			}
			//update all enemy cannonnalls (both college and enemy ship)
			for(Projectile cannonball : enemyCannonballs){
				cannonball.update(Gdx.graphics.getDeltaTime());
				if(cannonball.remove){
					cannonballsToRemove.add(cannonball);
				}
				else{
					cannonball.render(batch);
				}
			}

			//Update explosions
			ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
			for(Explosion explosion : explosions){
				explosion.update(Gdx.graphics.getDeltaTime());
				if(explosion.remove){
					explosionsToRemove.add(explosion);
				}
			}
			explosions.removeAll(explosionsToRemove);



			//Health bar colour -- change colour depending on the amount of health
			if (health > 0.6f){
				batch.setColor(Color.GREEN);
			}else if(health > 0.2f){
				batch.setColor(Color.ORANGE);
			}else{
				batch.setColor(Color.RED);
			}

			//Health bar position of ships
			batch.draw(blank, player.getBody().getPosition().x + 30, player.getBody().getPosition().y - 10, 60 * health, 5);
			batch.setColor(Color.RED);
			for (EnemyShip enemyShip : enemyShips){
				//batch.draw(blank, enemyShip.getPosition().x + 30, enemyShip.getPosition().y, 60 * enemyShip.getHealth(), 5);
			}
			//batch.draw(blank, spriteEnemyGoodricke.getX() + 30, spriteEnemyGoodricke.getY(), 60 * health, 5);
			//batch.draw(blank, spriteEnemyDerwent.getX() + 30, spriteEnemyDerwent.getY(), 60 * health, 5);
			//batch.draw(blank, spriteEnemyAlcuin.getX() + 30, spriteEnemyAlcuin.getY(), 60 * health, 5);
			//batch.draw(blank, spriteEnemyJames.getX() + 30, spriteEnemyJames.getY(), 60 * health, 5);
			batch.setColor(Color.WHITE);



			//After all updates, checking for collisions
			ArrayList<EnemyShip> enemyShipsToRemove = new ArrayList<EnemyShip>();
			for (Projectile cannonball: cannonballs){
				for (College college: Collages){
					if(cannonball.getProjectileCollider().collidesWith(college.getProjectileCollider())){
						cannonballsToRemove.add(cannonball);
						explosions.add(new Explosion(cannonball.getPosition()));
						plunder = college.hit(plunder);
						if(!college.isCaptured() && college.bossReady){
							plunder += 50f;
						}else if(college.isCaptured()){
							//collagesNotBossCount -= 1;
						}

					}
				}
				for (EnemyShip enemyShip : enemyShips){
					if (cannonball.getProjectileCollider().collidesWith(enemyShip.getProjectileCollider())){
						cannonballsToRemove.add(cannonball);
						explosions.add(new Explosion((cannonball.getPosition())));
						enemyShip.hit();
						if (enemyShip.getHealth() == 0f){
							plunder += 50;
							enemyShipsToRemove.add(enemyShip);
						}
					}
				}
			}
			for (Projectile cannonball : enemyCannonballs){
				if (cannonball.getProjectileCollider().collidesWith(player.getProjectileCollider())){
					cannonballsToRemove.add(cannonball);
					explosions.add(new Explosion((cannonball.getPosition())));
					health = Math.round((health-playerDmgFromBullet)*100f)/100f;
					System.out.println(health);
				}
			}
			cannonballs.removeAll(cannonballsToRemove);
			enemyCannonballs.removeAll(cannonballsToRemove);
			//enemyShips.removeAll(enemyShipsToRemove);

			// check for collision with hurricane & render/update them
			for (Hurricane hurricane : hurricanes){
				if (hurricane.collidesWith(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight()) && Hurricane.canDamage){
					System.out.println("HIT " + hurricane.getPosition());
					health -= Hurricane.getDamage() * (1-weatherResistanceUpgrade);
					Hurricane.setDamageDelay();
				}
				hurricane.render(batch);
				hurricane.update();
			}


			//Check if the player has collided with a coin
			for (Coin coin : coins){
				if (coin.collidesWith(player.getBody().getPosition().x, player.getBody().getPosition().y, sprite.getWidth(), sprite.getHeight()) && !coin.collected){
					plunder += coin.getValue();
					coin.collected = true;
				}
			}


			// if health reaches 0, game over
			if (health <= 0f){
				currentScreen = Screen.GameOver;
			}

			for(College college: Collages){
				college.render(batch);
				score = college.captured(score, Gdx.graphics.getDeltaTime());
			}

			for(EnemyShip enemyShip: enemyShips){
				enemyShip.render(batch);
			}

			for(Explosion explosion: explosions){
				explosion.render(batch);
			}

			for (Coin coin: coins){
				if (!coin.collected){
					coin.render(batch);
				}

			}

			batch.end();
			
			gui.drawMainScreen(HUDbatch, MediumFont, SmallFont, plunder, score);

		}
		if(currentScreen == Screen.End){

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();

			HUDbatch.begin();

			//draw menu
			gui.drawEndScreen(HUDbatch, SmallFont, LargeFont);

			HUDbatch.end();
		}

		if (currentScreen == Screen.GameOver){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();

			HUDbatch.begin();

			//draw menu
			gui.drawGameOverScreen(HUDbatch, SmallFont, LargeFont);

			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				setDefaultPreferences(prefs, Collages, enemyShips, coins, spawnx, spawny);
				plunder = prefs.getInteger("plunder", 0);
				score = prefs.getFloat("score", 0);
				player.getBody().setTransform(prefs.getFloat("playerx", 700), prefs.getFloat("playery", 700), player.getBody().getAngle());
				health = prefs.getFloat("player_health", 1f);
				sprite.setRotation(prefs.getFloat("player_rotation", 180f));
				cannonCooldownSpeed = prefs.getInteger("cannon_cooldown_speed", 1);
				damageUpgrade = prefs.getFloat("damage_upgrade", 0f);
				weatherResistanceUpgrade = prefs.getFloat("weather_resistance_upgrade", 0f);
				playerRotationUpgrade = prefs.getFloat("rotation_upgrade", 0f);
				for (Coin coin : coins){
					coin.collected = prefs.getBoolean("coin" + coin.getId() + "collected");
					coin.setValue(prefs.getInteger("coin" + coin.getId() + "value"));
				}
				for(Hurricane hurricane : hurricanes)
					hurricane.resetHurricane();

				for (College college : Collages){
					college.updateCollege();
				}
				for (EnemyShip enemyShip : enemyShips){
					enemyShip.updateShip();
				}
				cannonballs.removeAll(cannonballs);
				enemyCannonballs.removeAll(enemyCannonballs);
				explosions.removeAll(explosions);

				currentScreen = Screen.DifficultySelection;
			}

			HUDbatch.end();
		}

		if (currentScreen == Screen.Shop){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();

			HUDbatch.begin();


			//draw Shop
			gui.drawShopScreen(HUDbatch, SmallFont, MediumFont, LargeFont, difficulty, plunder);

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
				if (cannonCooldownSpeed < 5 && plunder >= 200) {
					cannonCooldownSpeed += 1;
					plunder -= 200;
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
				if (plunder >= 200){
					if (getHealth() < 1 && getHealth() >= 0.7f){
						health = 1f;
						plunder -= 200;
					}
					else if (getHealth() > 0 && getHealth() < 0.7f){
						health += 0.3f;
						plunder -= 200;
					}
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
				if (damageUpgrade < 1f && plunder >= 200f){
					damageUpgrade += 0.1f;
					plunder -= 200f;
					applyDifficulty(difficulty);
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
				if (weatherResistanceUpgrade < 0.5f && plunder >= 200f){
					weatherResistanceUpgrade += 0.1f;
					plunder -= 200f;
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
				if (playerRotationUpgrade < 0.5f && plunder >= 100f){
					playerRotationUpgrade += 0.1f;
					plunder -= 100f;
				}
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.Q)){
				savePreferences(prefs, Collages, enemyShips, plunder, score, player, health, hurricanes, coins, damageUpgrade, weatherResistanceUpgrade, difficulty);
				Gdx.app.exit();
				System.exit(0);
			}


			HUDbatch.end();

		}

		if (currentScreen == Screen.NewOrResume){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();

			HUDbatch.begin();

			//draw menu
			gui.drawNewOrResumeGameScreen(HUDbatch, SmallFont, LargeFont);

			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
				currentScreen = Screen.DifficultySelection;
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
				plunder = prefs.getInteger("plunder", 0);
				score = prefs.getFloat("score", 0);
				player.getBody().setTransform(prefs.getFloat("playerx", 700), prefs.getFloat("playery", 700), player.getBody().getAngle());
				health = prefs.getFloat("player_health", 1f);
				sprite.setRotation(prefs.getFloat("player_rotation", 180f));
				cannonCooldownSpeed = prefs.getInteger("cannon_cooldown_speed", 1);
				damageUpgrade = prefs.getFloat("damage_upgrade", 0f);
				weatherResistanceUpgrade = prefs.getFloat("weather_resistance_upgrade", 0f);
				playerRotationUpgrade = prefs.getFloat("rotation_upgrade", 0f);
				for (int count = 0; count < hurricanes.size(); count++){
					hurricanes.get(count).setDestination(prefs.getFloat("hurricane" + hurricanes.get(count).getId() + "_destx", 700), prefs.getFloat("hurricane"  + hurricanes.get(count).getId() + "_desty", 700));
					hurricanes.get(count).setPosition(prefs.getFloat("hurricane" + hurricanes.get(count).getId() + "x"), prefs.getFloat("hurricane" + hurricanes.get(count).getId() + "y"));
				}
				// Check if any coins have already been collected when the user resumes a game, and remove them if so.
				for (Coin coin : coins){
					coin.collected = prefs.getBoolean("coin" + coin.getId() + "collected", true);
				}

				int diff = prefs.getInteger("difficulty", 2);
				if (diff == 1){
					difficulty = Difficulty.Easy;
				}
				else if (diff == 3){
					difficulty = Difficulty.Hard;
				}
				else{
					difficulty = Difficulty.Normal;
				}
				for (College college : Collages){
					college.updateCollege();
				}
				for (EnemyShip enemyShip : enemyShips){
					enemyShip.updateShip();
				}

				//Change values based on difficulty
				applyDifficulty(difficulty);

				currentScreen = Screen.Shop;
			}

			//else if (Gdx.input.isKeyJustPressed(Input.Keys.H)){
			//	previousScreen = currentScreen;
			//	currentScreen = Screen.Help;
			//}

			HUDbatch.end();
		}

		if (currentScreen == Screen.Help){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();

			HUDbatch.begin();

			//draw menu
			gui.drawHelpScreen(HUDbatch, SmallFont, LargeFont);

			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				currentScreen = previousScreen;
			}

			HUDbatch.end();
		}
	}

	@Override
	public void resize(int width, int height){
		camera.setToOrtho(false, width / scale, height / scale);
	}

	@Override
	public void dispose () {
		world.dispose();
		b2dr.dispose();
		batch.dispose();
		tmr.dispose();
		map.dispose();
		img.dispose();
		blank.dispose();
	}

	private void update (float delta){
		world.step(1/60f, 6, 2);

		inputUpdate(delta);

		cam.cameraUpdate(delta, camera, player.getBody().getPosition());
		cam.boundry(camera, mapWidth * 32, mapHeight * 32);
		tmr.setView(camera);
		batch.setProjectionMatrix(camera.combined);
	}

	private void inputUpdate(float delta){
		boolean changedScreen = false;
		int horizontalforce = 0;
		int verticalforce = 0;
		playerRotation = sprite.getRotation();
		
		if(currentScreen == Screen.Home && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
			currentScreen = Screen.MAIN_GAME;
		}
		
		if (currentScreen == Screen.MAIN_GAME) {
			
			if(Gdx.input.isKeyPressed(Input.Keys.A)){
				sprite.setRotation((float) (playerRotation + (playerBaseRotationSpeed * (1+playerRotationUpgrade)) * PPM));
				player.getBody().setTransform(player.getBody().getPosition().x, player.getBody().getPosition().y, (float) toRadians(playerRotation));
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)){
				sprite.setRotation((float) (playerRotation - (playerBaseRotationSpeed * (1+playerRotationUpgrade)) * PPM));
				player.getBody().setTransform(player.getBody().getPosition().x, player.getBody().getPosition().y, (float) toRadians(playerRotation));
			}
			if(Gdx.input.isKeyPressed(Input.Keys.W)){
				verticalforce += -Math.sin(toRadians(playerRotation)) * 10;
				horizontalforce += -Math.cos(toRadians(playerRotation)) * 10;
				score += 0.005;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)){
				verticalforce += Math.sin(toRadians(playerRotation)) * 10;
				horizontalforce += Math.cos(toRadians(playerRotation)) * 10;
				score += 0.005;
			}
			player.getBody().setLinearVelocity(horizontalforce * 32, verticalforce * 32);
			//player.getBody().setTransform(player.getBody().getPosition().x + horizontalforce, player.getBody().getPosition().y + verticalforce, (float) toRadians(playerRotation));
			//player.def.position.set((player.getBody().getPosition().x + horizontalforce) * PPM, (player.getBody().getPosition().y + verticalforce) * PPM);
		}
		if(currentScreen == Screen.MAIN_GAME && Goodricke.isCaptured()){
			currentScreen = Screen.End;
		}
		if(currentScreen == Screen.End && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			if (currentScreen == Screen.MAIN_GAME){
				currentScreen = Screen.Shop;
				changedScreen = true;
			}
			if (currentScreen == Screen.Shop && !changedScreen){
				currentScreen = Screen.MAIN_GAME;
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.H)){
			if (currentScreen == Screen.MAIN_GAME || currentScreen == Screen.NewOrResume || currentScreen == Screen.Shop) {
				previousScreen = currentScreen;
				currentScreen = Screen.Help;
			}
		}
	}

	private Vector2 mousePos(int screenX, int screenY) {

		//screenX, screenY - Mouse Coords

		Vector2 centerPosition = new Vector2(player.getBody().getPosition().x, player.getBody().getPosition().y);

		Vector3 worldCoordinates = new Vector3(screenX, screenY,0);
		camera.unproject(worldCoordinates);

		Vector2 mouseLoc = new Vector2(worldCoordinates.x, worldCoordinates.y);

		Vector2 direction = mouseLoc.sub(centerPosition);
		System.out.println(mouseLoc);
		return mouseLoc;
	}

	public static float getHeight(){
		return h;
	}

	public static float getWidth(){
		return w;
	}

	public static float getMapWidth(){
		return mapWidth;
	}

	public static float getMapHeight(){
		return mapHeight;
	}

	public static int getCannonCooldownSpeed(){
		return cannonCooldownSpeed;
	}

	public static float getHealth(){
		return health;
	}

	public static float getDamageUpgrade(){return damageUpgrade;}

	public static float getWeatherResistanceUpgrade(){return weatherResistanceUpgrade;}

	public static float getPlayerRotationUpgrade(){return playerRotationUpgrade;}

	public void savePreferences(Preferences prefs, ArrayList<College> Colleges, ArrayList<EnemyShip> enemyShips, int plunder, float score, BaseCollider player, float player_health, ArrayList<Hurricane> hurricanes, ArrayList<Coin> coins, float damageUpgrade, float weatherResistanceUpgrade, Difficulty difficulty){
		prefs.putInteger("plunder", plunder);
		prefs.putFloat("score", score);
		prefs.putFloat("player_health", player_health);
		prefs.putFloat("playerx", player.getBody().getPosition().x);
		prefs.putFloat("playery", player.getBody().getPosition().y);
		prefs.putFloat("player_rotation", playerRotation);
		prefs.putInteger("cannon_cooldown_speed", cannonCooldownSpeed);
		prefs.putFloat("damage_upgrade", damageUpgrade);
		prefs.putFloat("weather_resistance_upgrade", weatherResistanceUpgrade);
		prefs.putFloat("rotation_upgrade", playerRotationUpgrade);
		for (int count = 0; count < hurricanes.size(); count++){
			prefs.putFloat("hurricane" + hurricanes.get(count).getId() + "x", hurricanes.get(count).getPosition().x);
			prefs.putFloat("hurricane"  + hurricanes.get(count).getId() + "y", hurricanes.get(count).getPosition().y);
			prefs.putFloat("hurricane"  + hurricanes.get(count).getId() + "_destx", hurricanes.get(count).getDestination().x);
			prefs.putFloat("hurricane"  + hurricanes.get(count).getId() + "_desty", hurricanes.get(count).getDestination().y);
		}
		for (int count = 0; count < coins.size(); count++){
			System.out.println("coin: " + coins.get(count).getId());
			prefs.putFloat("coin" + coins.get(count).getId() + "x", coins.get(count).getPosition().x);
			prefs.putFloat("coin" + coins.get(count).getId() + "y", coins.get(count).getPosition().y);
			prefs.putInteger("coin" + coins.get(count).getId() + "value", coins.get(count).getValue());
			prefs.putBoolean(("coin" + coins.get(count).getId() + "collected"), coins.get(count).collected);
		}

		if (difficulty == Difficulty.Easy)
			prefs.putInteger("difficulty", 1);
		else if (difficulty == Difficulty.Normal)
			prefs.putInteger("difficulty", 2);
		else if (difficulty == Difficulty.Hard)
			prefs.putInteger("difficulty", 3);

		for (College college : Colleges){
			prefs.putFloat(college.getName() + "_health", college.getHealth());
			prefs.putBoolean(college.getName() + "_isCaptured", college.isCaptured());
		}

		for (EnemyShip enemyShip : enemyShips){
			prefs.putFloat(enemyShip.getCollege().getName() + "Ship_health", enemyShip.getHealth());
			prefs.putBoolean(enemyShip.getCollege().getName() + "Ship_isCaptured", enemyShip.isCaptured());
		}
		prefs.flush();
	}

	private Preferences setDefaultPreferences(Preferences prefs, ArrayList<College> Colleges, ArrayList<EnemyShip> enemyShips, ArrayList<Coin> coins, int spawnx, int spawny){
		prefs.putInteger("plunder", 0);
		prefs.putFloat("score", 0f);
		prefs.putFloat("player_health", 1f);
		prefs.putFloat("playerx", spawnx);
		prefs.putFloat("playery", spawny);
		prefs.putFloat("player_rotation", 180f);
		prefs.putInteger("cannon_cooldown_speed", 1);
		prefs.putFloat("damage_upgrade", 0f);
		prefs.putFloat("weather_resistance_upgrade", 0f);
		prefs.putFloat("rotation_upgrade", 0f);

		for (College college : Colleges){
			prefs.putFloat(college.getName() + "_health", 1f);
			prefs.putBoolean(college.getName() + "_isCaptured", false);
		}

		for (EnemyShip enemyShip : enemyShips){
			prefs.putFloat(enemyShip.getCollege().getName() + "Ship_health", 1f);
			prefs.putBoolean(enemyShip.getCollege().getName() + "Ship_isCaptured", false);
		}
		for (int count = 0; count < coins.size(); count++){
			System.out.println("coin: " + coins.get(count).getId());
			prefs.putFloat("coin" + coins.get(count).getId() + "x", coins.get(count).getPosition().x);
			prefs.putFloat("coin" + coins.get(count).getId() + "y", coins.get(count).getPosition().y);
			prefs.putInteger("coin" + coins.get(count).getId() + "value", 200);
			prefs.putBoolean(("coin" + coins.get(count).getId() + "collected"), false);
		}
		prefs.flush();

		return prefs;
	}

	public void applyDifficulty(Difficulty difficulty){
		float college_damage_taken;
		float enemyship_damage_taken;
		//Change values based on difficulty
		switch (difficulty) {
			case Easy:
				college_damage_taken = 0.3f;
				enemyship_damage_taken = 0.5f;
				college_damage_taken += college_damage_taken * damageUpgrade;
				enemyship_damage_taken += enemyship_damage_taken * damageUpgrade;
				College.setDmgTakenFromBullet(college_damage_taken);
				EnemyShip.setDmgTakenFromBullet(enemyship_damage_taken);
				playerDmgFromBullet = 0.1f;
				break;
			case Hard:
				college_damage_taken = 0.1f;
				enemyship_damage_taken = 0.25f;
				college_damage_taken += college_damage_taken * damageUpgrade;
				enemyship_damage_taken += enemyship_damage_taken * damageUpgrade;
				College.setDmgTakenFromBullet(college_damage_taken);
				EnemyShip.setDmgTakenFromBullet(enemyship_damage_taken);
				playerDmgFromBullet = 0.25f;
				break;
			default:
				college_damage_taken = 0.2f;
				enemyship_damage_taken = 0.34f;
				college_damage_taken += college_damage_taken * damageUpgrade;
				enemyship_damage_taken += enemyship_damage_taken * damageUpgrade;
				College.setDmgTakenFromBullet(college_damage_taken);
				EnemyShip.setDmgTakenFromBullet(enemyship_damage_taken);
				playerDmgFromBullet = 0.2f;
				break;
		}
	}
}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Enemies.EnemyShip;
import com.mygdx.game.utils.Projectile;
import com.mygdx.game.utils.TiledObjectUtil;
import com.mygdx.game.utils.gui;
import com.mygdx.game.Colliders.BaseCollider;
import jdk.internal.org.jline.utils.DiffHelper;

import java.security.Key;
import java.util.ArrayList;
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
	private Sprite spriteEnemyGoodrick;
	private Sprite spriteEnemyJames;
	private Sprite spriteEnemyDerwent;
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
	private static World world;
	private BaseCollider player;
	private College Goodricke;
	private College Alcuin;
	private College Derwent;
	private College James;
	private int spawnx;
	private int spawny;

	private static float health = 1f;
	private int plunder = 0;
	private float score = 0;
	
	private int cannonCooldown = 0;
	private static int cannonCooldownSpeed = 1;

	private ArrayList<Projectile> cannonballs;
	private ArrayList<College> Collages;
	private ArrayList<Body> enemyShips;
	private ArrayList<Explosion> explosions;
	public enum Screen{
		DifficultySelection, Home, MAIN_GAME, Shop, End
	}
	Screen currentScreen = Screen.DifficultySelection;
	public enum Difficulty{
		Easy, Normal, Hard
	}
	public static Difficulty difficulty = Difficulty.Hard;

	@Override
	public void create () {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		spawnx = 700;
		spawny = 700;

		cannonballs = new ArrayList<Projectile>();
		Collages = new ArrayList<College>();
		explosions = new ArrayList<Explosion>();
		enemyShips = new ArrayList<>();

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
		Goodricke = new College(new Vector2(3140, 3110), "TowerGoodricke.png", true, world);
		Collages.add(Goodricke);
		//enemyShips.add(new EnemyShip(2900, 2600, Goodricke, world));

		Alcuin = new College(new Vector2(1500, 1300), "TowerAlcuin.png", false, world);
		Collages.add(Alcuin);
		//enemyShips.add(new EnemyShip(1500, 1000, Alcuin, world));

		Derwent = new College(new Vector2(520, 2170), "TowerDerwent.png", false, world);
		Collages.add(Derwent);
		//enemyShips.add(new EnemyShip(520, 1900, Derwent, world));

		James = new College(new Vector2(3080, 1080), "TowerJames.png", false, world);
		Collages.add(James);
		//enemyShips.add(new EnemyShip(2900, 800, James, world));

		collagesNotBossCount = Collages.size() - 1;
		
		//initialise fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Oswald-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
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
		spriteEnemyGoodrick = new Sprite(imgenemy);
		spriteEnemyJames = new Sprite(imgenemy);

		//initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / scale, h / scale);

		player = new BaseCollider(new Vector2(spawnx , spawny), 128, 64, false, world);
		enemyShips.add(Alcuin.getColliderBody());
		enemyShips.add(Derwent.getColliderBody());
		enemyShips.add(Goodricke.getColliderBody());
		enemyShips.add(James.getColliderBody());

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

			switch (difficulty){
				case Easy:
					College.setDmgTakenFromBullet(0.3f);
					break;
				case Hard:
					College.setDmgTakenFromBullet(0.1f);
					break;
				default:
					College.setDmgTakenFromBullet(0.2f);
					break;
			}

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
			
			ScreenUtils.clear(0, 0, 1, 1);

			b2dr.render(world, camera.combined.scl(PPM));

			//Draw the map
			tmr.render();

			//Update the position of the player's image
			sprite.setPosition(player.getBody().getPosition().x * PPM - (img.getWidth()) / 3, player.getBody().getPosition().y * PPM  - (img.getHeight()) / 3);
			spriteEnemyAlcuin.setPosition(Alcuin.getColliderBody().getPosition().x - imgenemy.getWidth() / 2, Alcuin.getColliderBody().getPosition().y - imgenemy.getHeight() / 2);
			spriteEnemyDerwent.setPosition(Derwent.getColliderBody().getPosition().x - imgenemy.getWidth() / 2, Derwent.getColliderBody().getPosition().y - imgenemy.getHeight() / 2);
			spriteEnemyGoodrick.setPosition(Goodricke.getColliderBody().getPosition().x - imgenemy.getWidth() / 2, Goodricke.getColliderBody().getPosition().y - imgenemy.getHeight() / 2);
			spriteEnemyJames.setPosition(James.getColliderBody().getPosition().x - imgenemy.getWidth() / 2, James.getColliderBody().getPosition().y - imgenemy.getHeight() / 2);

			batch.begin();

			//Draw player & enemies
			sprite.draw(batch);
			spriteEnemyAlcuin.draw(batch);
			spriteEnemyDerwent.draw(batch);
			spriteEnemyGoodrick.draw(batch);
			spriteEnemyJames.draw(batch);


			//Shooting code
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && cannonCooldown <= 0){
				cannonballs.add(new Projectile(new Vector2(player.getBody().getPosition().x, player.getBody().getPosition().y), mousePos(Gdx.input.getX(), Gdx.input.getY())));
				cannonCooldown = 40;
			}

			//Update projectiles
			ArrayList<Projectile> cannonballsToRemove = new ArrayList<Projectile>();
			for(Projectile cannonball : cannonballs){
				cannonball.update(Gdx.graphics.getDeltaTime());
				if(cannonball.remove){
					cannonballsToRemove.add(cannonball);
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


			//Health bar colour
			if (health > 0.6f){
				batch.setColor(Color.GREEN);
			}else if(health > 0.2f){
				batch.setColor(Color.ORANGE);
			}else{
				batch.setColor(Color.RED);
			}

			//Health bar position
			batch.draw(blank, player.getBody().getPosition().x - 30, player.getBody().getPosition().y - 70, 60 * health, 5);
			batch.setColor(Color.RED);
			batch.draw(blank, James.getColliderBody().getPosition().x - 30, James.getColliderBody().getPosition().y - 70, 60 * health, 5);
			batch.draw(blank, Derwent.getColliderBody().getPosition().x - 30, Derwent.getColliderBody().getPosition().y - 70, 60 * health, 5);
			batch.draw(blank, Alcuin.getColliderBody().getPosition().x - 30, Alcuin.getColliderBody().getPosition().y - 70, 60 * health, 5);
			batch.draw(blank, Goodricke.getColliderBody().getPosition().x - 30, Goodricke.getColliderBody().getPosition().y - 70, 60 * health, 5);
			batch.setColor(Color.WHITE);

			for(Projectile cannonball : cannonballs){
				cannonball.render(batch);
			}

			//After all updates, checking for collisions
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
			}
			cannonballs.removeAll(cannonballsToRemove);


			for(College college: Collages){
				college.render(batch);
				score = college.captured(score, Gdx.graphics.getDeltaTime());
			}

			for(Explosion explosion: explosions){
				explosion.render(batch);
			}

			batch.end();
			
			gui.drawMainScreen(HUDbatch, SmallFont, plunder, score);

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

		if (currentScreen == Screen.Shop){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();

			HUDbatch.begin();


			//draw Shop
			gui.drawShopScreen(HUDbatch, SmallFont, LargeFont);

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
					}
					else if (getHealth() > 0 && getHealth() < 0.7f){
						health += 0.3f;
					}
				}
			}

			//if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
			//	currentScreen = Screen.MAIN_GAME;
			//}

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
		float currentRotation = sprite.getRotation();
		
		if(currentScreen == Screen.Home && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
			currentScreen = Screen.MAIN_GAME;
		}
		
		if (currentScreen == Screen.MAIN_GAME) {
			
			if(Gdx.input.isKeyPressed(Input.Keys.A)){
				sprite.setRotation((float) (currentRotation + 0.8));
				player.getBody().setTransform(player.getBody().getPosition().x, player.getBody().getPosition().y, (float) toRadians(currentRotation));
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)){
				sprite.setRotation((float) (currentRotation - 0.8));
				player.getBody().setTransform(player.getBody().getPosition().x, player.getBody().getPosition().y, (float) toRadians(currentRotation));
			}
			if(Gdx.input.isKeyPressed(Input.Keys.W)){
				verticalforce += -Math.sin(toRadians(currentRotation))*1000;
				horizontalforce += -Math.cos(toRadians(currentRotation))*1000;
				score += 0.005;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)){
				verticalforce += Math.sin(toRadians(currentRotation))*1000;
				horizontalforce += Math.cos(toRadians(currentRotation))*1000;
				score += 0.005;
			}
			player.getBody().setLinearVelocity(horizontalforce * 32, verticalforce * 32);
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
	}

	private Vector2 mousePos(int screenX, int screenY) {

		//screenX, screenY - Mouse Coords

		Vector2 centerPosition = new Vector2(player.getBody().getPosition().x, player.getBody().getPosition().y);

		Vector3 worldCoordinates = new Vector3(screenX, screenY,0);
		camera.unproject(worldCoordinates);

		Vector2 mouseLoc = new Vector2(worldCoordinates.x, worldCoordinates.y);

		Vector2 direction = mouseLoc.sub(centerPosition);
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
}

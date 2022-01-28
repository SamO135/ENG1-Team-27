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
import com.mygdx.game.utils.Projectile;
import com.mygdx.game.utils.TiledObjectUtil;
import com.mygdx.game.utils.gui;
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
	private Sprite sprite;
	private BitmapFont SmallFont;
	private BitmapFont LargeFont;

	private final float scale = 2.0f;
	private OrthographicCamera camera;

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;
	private int mapWidth = 0;
	private int mapHeight = 0;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player;
	private College Goodricke;
	private College Alcuin;
	private College Derwent;
	private College James;
	private int spawnx;
	private int spawny;

	private float health = 1f;
	private int plunder = 0;
	private float score = 0;
	
	private int cannonCooldown = 0;

	private ArrayList<Projectile> cannonballs;
	private ArrayList<College> Collages;
	private ArrayList<Explosion> explosions;
	public enum Screen{
		Home, MAIN_GAME, End;
	}

	Screen currentScreen = Screen.Home;

	@Override
	public void create () {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		spawnx = 700;
		spawny = 700;

		cannonballs = new ArrayList<Projectile>();
		Collages = new ArrayList<College>();
		explosions = new ArrayList<Explosion>();

		//batches
		batch = new SpriteBatch();
		HUDbatch = new SpriteBatch();
		
		img = new Texture("PirateShip3Mast.png");
		blank = new Texture("Blank.png");

		//initialise colleges
		Goodricke = new College(3140, 3110, "TowerGoodricke.png", true);
		Collages.add(Goodricke);

		Alcuin = new College(1500, 1300, "TowerAlcuin.png", false);
		Collages.add(Alcuin);

		Derwent = new College(520, 2170, "TowerDerwent.png", false);
		Collages.add(Derwent);

		James = new College(3080, 1080, "TowerJames.png", false);
		Collages.add(James);

		collagesNotBossCount = Collages.size() - 1;
		
		//initialise fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Oswald-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = 20;
		SmallFont = generator.generateFont(parameter);
		parameter.size = 64;
		LargeFont = generator.generateFont(parameter);

		//initialise sprites
		sprite = new Sprite(img);
		sprite.setSize(128,64);
		sprite.setOrigin(64, 32);
		sprite.setRotation(180f);

		//initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / scale, h / scale);

		world = new World(new Vector2(0, 0f), false);
		b2dr = new Box2DDebugRenderer();

		player = createBox(spawnx , spawny, 128, 64, false);

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
		
		//menu screen
		if(currentScreen == Screen.Home){

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//draw map
			tmr.render();
			
			HUDbatch.begin();
			
			//draw menu
			gui.drawMenuScreen(HUDbatch, SmallFont, LargeFont);

			HUDbatch.end();
		}
    
		if(currentScreen == Screen.MAIN_GAME){
			
			//reduce cannon cooldown
			if (cannonCooldown > 0){
				cannonCooldown -= 1;
			}
			
			ScreenUtils.clear(0, 0, 1, 1);

			b2dr.render(world, camera.combined.scl(PPM));

			tmr.render();

			sprite.setPosition(player.getPosition().x * PPM - (img.getWidth()) / 3, player.getPosition().y * PPM  - (img.getHeight()) / 3);
			batch.begin();
			sprite.draw(batch);

			//Shooting code
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && cannonCooldown <= 0){
				cannonballs.add(new Projectile(new Vector2(player.getPosition().x, player.getPosition().y), mousePos(Gdx.input.getX(), Gdx.input.getY())));
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
			batch.draw(blank, player.getPosition().x - 30, player.getPosition().y - 70, 60 * health, 5);
			batch.setColor(Color.WHITE);

			for(Projectile cannonball : cannonballs){
				cannonball.render(batch);
			}

			//After all updates, checking for collisions
			for (Projectile cannonball: cannonballs){
				for (College college: Collages){
					if(cannonball.getCollisionRect().collidesWith(college.getCollisionRect())){
						cannonballsToRemove.add(cannonball);
						explosions.add(new Explosion(cannonball.getPosition().x, cannonball.getPosition().y));
						plunder = college.hit(plunder);
						if(college.getHealth() != 0f && !college.isCaptured() && college.bossReady){
							plunder += 50f;
						}else if(college.isCaptured()){
							collagesNotBossCount -= 1;
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
			
			gui.updateMainScreen(HUDbatch, SmallFont, plunder, score);

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

	public void update (float delta){
		world.step(1/60f, 6, 2);

		inputUpdate(delta);

		cam.cameraUpdate(delta, camera, player.getPosition().x, player.getPosition().y);
		cam.boundry(camera, mapWidth * 32, mapHeight * 32);
		tmr.setView(camera);
		batch.setProjectionMatrix(camera.combined);
	}

	public void inputUpdate(float delta){
		int horizontalforce = 0;
		int verticalforce = 0;
		float currentRotation = sprite.getRotation();
		
		if(currentScreen == Screen.Home && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
			currentScreen = Screen.MAIN_GAME;
		}
		
		if (currentScreen == Screen.MAIN_GAME) {
			
			if(Gdx.input.isKeyPressed(Input.Keys.A)){
				sprite.setRotation((float) (currentRotation + 0.8));
				player.setTransform(player.getPosition().x, player.getPosition().y, (float) toRadians(currentRotation));
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)){
				sprite.setRotation((float) (currentRotation - 0.8));
				player.setTransform(player.getPosition().x, player.getPosition().y, (float) toRadians(currentRotation));
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
			player.setLinearVelocity(horizontalforce * 32, verticalforce * 32);	
		}
		if(currentScreen == Screen.MAIN_GAME && Goodricke.isCaptured()){
			currentScreen = Screen.End;
		}
		if(currentScreen == Screen.End && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}

	public void updateRotation(int i) { // 1 = left, 2 = right, 3 = up, 4 = down
		float currentRotation = sprite.getRotation();
		switch (i) {
			case 1:
				if (currentRotation > 180){
					sprite.setRotation((float) (currentRotation + (360 - currentRotation) * 0.1));
				}
				else {
					sprite.setRotation((float) (currentRotation + (0 - currentRotation) * 0.1));
				}
				player.setTransform(player.getPosition().x, player.getPosition().y, (float) toRadians(currentRotation));
				break;
			case 2:
				sprite.setRotation((float) (currentRotation + (180-currentRotation) * 0.1));
				player.setTransform(player.getPosition().x, player.getPosition().y, (float) toRadians(currentRotation));
				break;
			case 3:
				if (currentRotation < 90){
					sprite.setRotation((float) (currentRotation + (-90 - currentRotation) * 0.1));
				}
				else {
					sprite.setRotation((float) (currentRotation + (270 - currentRotation) * 0.1));
				}
				player.setTransform(player.getPosition().x, player.getPosition().y, (float) toRadians(currentRotation));
				break;
			case 4:
				if (currentRotation > 270){
					sprite.setRotation((float) (currentRotation + (450 - currentRotation) * 0.1));
				}
				else {
					sprite.setRotation((float) (currentRotation + (90 - currentRotation) * 0.1));
				}
				player.setTransform(player.getPosition().x, player.getPosition().y, (float) toRadians(currentRotation));
				break;
		}
	}


	public Body createBox(int x, int y, int width, int height, boolean isStatic) {
		Body pBody;
		BodyDef def = new BodyDef();

		if(isStatic){
			def.type = BodyDef.BodyType.StaticBody;
		}
		else{
			def.type = BodyDef.BodyType.DynamicBody;
		}
		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = true;
		pBody = world.createBody(def);


		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM,height / 2 / PPM);

		pBody.createFixture(shape, 1.0f);
		shape.dispose();

		return pBody;
	}

	public Vector2 mousePos(int screenX, int screenY) {

		//screenX, screenY - Mouse Coords

		Vector2 centerPosition = new Vector2(player.getPosition().x, player.getPosition().y);

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
}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Animations.Explosion;
import com.mygdx.game.CameraUtils.cam;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Colliders.CollisionRect;
import com.mygdx.game.utils.Projectile;
import com.mygdx.game.utils.TiledObjectUtil;
import com.mygdx.game.utils.gui;


import java.util.ArrayList;

import static com.mygdx.game.utils.Constants.PPM;
import static java.lang.Math.random;
import static java.lang.Math.toRadians;

public class Unity extends ApplicationAdapter {

	static float w;
	static float h;

	private SpriteBatch batch;
	private SpriteBatch HUDbatch;
	private Texture img;
	public static Texture blank;
	private Sprite sprite;
	private BitmapFont font;

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
	private float plunder = 0f;

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
		Goodricke = new College(3140, 3110, "TowerGoodricke.png");
		Collages.add(Goodricke);

		Alcuin = new College(1500, 1300, "TowerAlcuin.png");
		Collages.add(Alcuin);

		Derwent = new College(520, 2170, "TowerDerwent.png");
		Collages.add(Derwent);

		James = new College(3080, 1080, "TowerJames.png");
		Collages.add(James);
		
		//initialise fonts
		font = new BitmapFont();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.getData().setScale(2);


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

		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown (int keyCode) {

				if(currentScreen == Screen.Home && keyCode == Input.Keys.SPACE){
					currentScreen = Screen.MAIN_GAME;
				}
				return true;
			}
		});

	}

	@Override
	public void render () {
		if(currentScreen == Screen.Home){

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.begin();
			font.draw(batch, "Title Screen!", w *.25f, h * .75f);
			font.draw(batch, "Destory Goodricke College to win.", w *.25f, h * .5f);
			font.draw(batch, "Press space to play.", w *.25f, h * .25f);
			batch.end();
		}
		if(currentScreen == Screen.MAIN_GAME){
			update(Gdx.graphics.getDeltaTime());  // deltaTime is time between a frame refresh
			ScreenUtils.clear(0, 0, 1, 1);

			b2dr.render(world, camera.combined.scl(PPM));

			tmr.render();

			sprite.setPosition(player.getPosition().x * PPM - (img.getWidth()) / 3, player.getPosition().y * PPM  - (img.getHeight()) / 3);
			batch.begin();
			sprite.draw(batch);

			//Shooting code
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
				cannonballs.add(new Projectile(new Vector2(player.getPosition().x, player.getPosition().y), mousePos(Gdx.input.getX(), Gdx.input.getY())));
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
						college.hit();
						if(college.getHealth() != 0f){
							plunder += 50f;
						}

					}
				}
			}
			cannonballs.removeAll(cannonballsToRemove);


			for(College college: Collages){
				college.render(batch);
			}

			for(Explosion explosion: explosions){
				explosion.render(batch);
			}

			batch.end();
			
			gui.update(HUDbatch, font, plunder);
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

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			horizontalforce -= 1;
			updateRotation(1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			horizontalforce += 1;
			updateRotation(2);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			verticalforce += 1;
			updateRotation(3);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			verticalforce -= 1;
			updateRotation(4);
		}
		player.setLinearVelocity(horizontalforce * 1000 * 32, verticalforce * 1000 * 32);

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

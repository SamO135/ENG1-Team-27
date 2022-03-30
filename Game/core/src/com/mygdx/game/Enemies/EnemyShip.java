package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Colliders.BaseCollider;
import com.mygdx.game.Colliders.ProjectileCollider;
import com.mygdx.game.Unity;
import java.util.Random;

import static com.mygdx.game.utils.Constants.PPM;

public class EnemyShip {
	
    private Texture img;
    private Sprite sprite;
    Random rand = new Random();
    ProjectileCollider rect;
    BaseCollider collider;
    float maxHealth = 1f;
    float health = maxHealth;
    Texture blank = Unity.blank;
    Body body;
	private Vector2 position;
	private Vector2 collegePosition;
    static float dmgTakenFromBullet = 0.5f;
    boolean isCaptured = false;
    public float shootCooldown;

    public EnemyShip(Vector2 position, College college, World world, Sprite sprite){
        this.collegePosition = college.getLocation();
        this.position = position;
        this.sprite = sprite;
        this.collider = new BaseCollider(position, 128, 64, true, world);
        this.shootCooldown = 180;

        this.img = new Texture("PirateShipEnemy.png");
        //sprite = new Sprite(this.img);
        this.rect = new ProjectileCollider(position, img.getWidth(), img.getHeight());

        /*Body pBody;
        BodyDef def = new BodyDef();
        def.position.set(position.x / PPM, position.y / PPM);
        pBody = world.createBody(def);


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(img.getWidth() / 2 / PPM,img.getHeight() / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        this.body = pBody;
        shape.dispose();

        /*while(x < 0 || x > Unity.getMapWidth() * 32 || y < 0 || y > Unity.getMapHeight() * 32){
            randomLocation = getRandomLocation(collegePosition);
        }*/
    }

    public void hit(){
        takeDamage(dmgTakenFromBullet);
    }

    public void render(SpriteBatch batch){
        //sprite.setPosition(position.x, position.y);
        //sprite.draw(batch);
        if(!isCaptured){
            batch.setColor(Color.RED);
        }else{
            health = maxHealth;
            batch.setColor(Color.GREEN);
        }
        batch.draw(blank, position.x + (img.getWidth() / 2) - 30, position.y - 10, 60 * health, 5);
        batch.setColor(Color.WHITE);
    }

    public ProjectileCollider getProjectileCollider(){ return rect;}

    public float takeDamage(float dmg){
        if (!isCaptured){
            if (health > dmg){
                health -= dmg;
            }
            else{
                health = 0f;
                isCaptured = true;
            }
        }

        return health;
    }

    public boolean isCaptured(){
        return isCaptured;
    }

    public float getHealth(){
        return health;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public int getWidth(){
        return img.getWidth();
    }

    public int getHeight(){
        return img.getHeight();
    }

    public static void setDmgTakenFromBullet(float dmg){
        dmgTakenFromBullet = dmg;
    }

    private Vector2 getRandomLocation(){
        return new Vector2(
                collegePosition.x + (rand.nextInt(1500) - 500),
                collegePosition.y + (rand.nextInt(1500) - 500)
        );
    }
}

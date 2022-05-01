package com.mygdx.game.Enemies;

import com.badlogic.gdx.Preferences;
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
    public static float dmgTakenFromBullet = 0.5f;
    boolean isCaptured = false;
    public float shootCooldown;
    private College college;
    private Preferences prefs;

    /** Constructs a new enemy ship
     * @param position The position of the enemy ship
     * @param college The college that the ship belongs to
     * @param world The world to create the enemy ship in
     * @param sprite The sprite of the enemy ship
     * @param prefs The save file the enemy ship data if saved to*/
    public EnemyShip(Vector2 position, College college, World world, Sprite sprite, Preferences prefs){
        this.collegePosition = college.getLocation();
        this.position = position;
        this.sprite = sprite;
        this.collider = new BaseCollider(position, 128, 64, true, world);
        this.shootCooldown = 180;
        this.college = college;
        this.prefs = prefs;

        this.img = new Texture("PirateShipEnemy.png");
        //sprite = new Sprite(this.img);
        this.rect = new ProjectileCollider(position, img.getWidth(), img.getHeight());
    }

    /** Deals damage to the enemy ship*/
    public void hit(){
        takeDamage(dmgTakenFromBullet);
    }

    /** Renders the enemy ship at its x and y position
     * @param batch A SpriteBatch instance*/
    public void render(SpriteBatch batch){
        if(!isCaptured){
            batch.setColor(Color.RED);
        }else{
            health = maxHealth;
            batch.setColor(Color.GREEN);
        }
        batch.draw(blank, position.x + (img.getWidth() / 2) - 30, position.y - 10, 60 * health, 5);
        batch.setColor(Color.WHITE);
    }

    /** @return The ships rect*/
    public ProjectileCollider getProjectileCollider(){ return rect;}

    /** Deals damage to the ship
     * @param dmg The damage the ship will take
     * @return The health of the ship after it has been damaged*/
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

    /** @return The college the ship belongs to*/
    public College getCollege(){
        return this.college;
    }

    /** @return True if the ship is captured, false otherwise*/
    public boolean isCaptured(){
        return isCaptured;
    }

    /** @return The health of the ship*/
    public float getHealth(){
        return health;
    }

    /** @return the position of the ship*/
    public Vector2 getPosition(){
        return this.position;
    }

    /** @return The ship's sprite*/
    public Sprite getSprite(){
        return this.sprite;
    }

    /** @return The width of the ship's image*/
    public int getWidth(){
        return img.getWidth();
    }

    /** @return the height of the ship's image*/
    public int getHeight(){
        return img.getHeight();
    }

    /** Sets the damage the ship takes from the player's cannonballs
     * @param dmg The damage the ship will take
     * */
    public static void setDmgTakenFromBullet(float dmg){
        dmgTakenFromBullet = dmg;
    }

    /** Generates and returns a new random location
     * @return A Vector2 location
     */
    private Vector2 getRandomLocation(){
        return new Vector2(
                collegePosition.x + (rand.nextInt(1500) - 500),
                collegePosition.y + (rand.nextInt(1500) - 500)
        );
    }

    /** Updates the ships's capture state and health based on the values saved in the game's save file*/
    public void updateShip(){
        this.isCaptured = prefs.getBoolean(this.college.getName() + "Ship_isCaptured", false);
        this.health = prefs.getFloat(this.college.getName() + "Ship_health", 1f);
    }
}

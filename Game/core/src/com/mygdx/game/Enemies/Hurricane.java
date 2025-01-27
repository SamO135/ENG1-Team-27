package com.mygdx.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Unity;

public class Hurricane {
    private Rectangle rect;
    private Texture image;
    private Vector2 position;
    private static final int moveSpeed = 100;
    private TextureRegion textureRegion;
    private Vector2 direction;
    private Vector2 destination;
    private Vector2 velocity;
    private static float maxDamageDelay = 100f * Unity.numOfHurricanes;
    public static float damageDelay = 0f;
    public static boolean canDamage = true;
    private static float damage = 0.2f;
    private int id;

    /** Constructs a new hurricane
     * @param prefs The preferences file the hurricane gets saved to
     * @param id A unique integer value to identify the hurricane*/
    public Hurricane(Preferences prefs, int id) {
        this.image = new Texture("Hurricane_128x82.png");
        this.textureRegion = new TextureRegion(image);
        this.position = generateNewDestination();
        this.rect = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());
        this.direction = new Vector2(0, 0);
        this.destination = new Vector2(prefs.getFloat("hurricane" + id + "_destx", 1000), prefs.getFloat("hurricane" + id + "_desty", 1000));
        this.velocity = new Vector2(0, 0);
        this.id = id;
    }

    /** Renders the hurricane
     * @param batch A SpriteBatch instance*/
    public void render(SpriteBatch batch){
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2f, this.position.y - this.image.getHeight()/2f);
    }

    /** Updates The hurricane's position, direction and destination, and reduces the damage delay*/
    public void update(){
        reduceDamageDelay();
        if (reachedDestination()){
            this.destination = generateNewDestination();
        }
        direction.set(this.destination.x - this.position.x, this.destination.y - this.position.y);
        direction.scl(1/direction.len());
        this.velocity.set(direction.x * moveSpeed * Gdx.graphics.getDeltaTime(), direction.y * moveSpeed * Gdx.graphics.getDeltaTime());
        this.position.add(this.velocity);
        this.rect.setPosition(this.position);
    }

    /** @return True if the hurricane object overlaps with the provided coordinates and dimensions, False otherwise*/
    public boolean collidesWith(float x, float y, float width, float height){
        if (this.rect.x >= x && this.rect.x <= x + width && this.rect.y >= y && this.rect.y <= y + height){
            return true;
        }
        return false;
    }

    /** Sets the damage delay of all hurricanes*/
    public static void setDamageDelay() {
        damageDelay = maxDamageDelay;
        canDamage = false;
    }

    /** @return The damage the hurricane deals*/
    public static float getDamage(){return damage;}

    /** @return The Vector2 position of the hurricane object*/
    public Vector2 getPosition() {
        return position;
    }

    /** Sets the position of the hurricane object*/
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    /** @return The Vector2 destination of the hurricane*/
    public Vector2 getDestination() {
        return destination;
    }

    /** Sets the destination of the hurricane*/
    public void setDestination(float x, float y) {
        this.destination.set(destination);
    }

    /** @return The unique integer value of the hurricane*/
    public int getId(){return this.id;}

    /** Resets the position and destination of the hurricane*/
    public void resetHurricane(){
        this.position = generateNewDestination();
        this.destination = generateNewDestination();
    }

    /** @return True if the hurricane has reached its destination, False otherwise*/
    public boolean reachedDestination(){
        if ((this.position.x > this.destination.x - moveSpeed && this.position.x < this.destination.x + moveSpeed) && (this.position.y > this.destination.y - moveSpeed && this.position.y < this.destination.y + moveSpeed)){
            return true;
        }
        return false;
    }

    /** Generates a random new Vector2 position between the map boundaries*/
    private Vector2 generateNewDestination(){
        int x = MathUtils.random.nextInt(3420);
        int y = MathUtils.random.nextInt(3460);
        return new Vector2(x, y);
    }

    /** Reduces the remaining delay of the hurricane's damage*/
    public void reduceDamageDelay(){
        if (damageDelay > 0){
            damageDelay -= 1;
        }
        else if (damageDelay < 0){
            damageDelay = 0;
        }
        else{
            canDamage = true;
        }
    }
}

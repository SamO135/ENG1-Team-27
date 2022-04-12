package com.mygdx.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hurricane {
    private Rectangle rect;
    private Texture image;
    private Vector2 position;
    private static final int moveSpeed = 100;
    private TextureRegion textureRegion;
    private Vector2 direction;
    private Vector2 destination;
    private Vector2 velocity;
    private static float maxDamageDelay = 100f;
    public static float damageDelay = 0f;
    public static boolean canDamage = true;
    private static float damage = 0.2f;


    public Hurricane() {
        this.image = new Texture("Hurricane_128x82.png");
        this.textureRegion = new TextureRegion(image);
        this.position = generateNewDestination();
        this.rect = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());
        this.direction = new Vector2(0, 0);
        this.destination = generateNewDestination();
        this.velocity = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch){
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2f, this.position.y - this.image.getHeight()/2f);
    }

    public void update(){
        reduceDamageDelay();
        if (reachedDestination()){
            this.destination = generateNewDestination();
            System.out.println(this.destination);
        }
        direction.set(this.destination.x - this.position.x, this.destination.y - this.position.y);
        direction.scl(1/direction.len());
        this.velocity.set(direction.x * moveSpeed * Gdx.graphics.getDeltaTime(), direction.y * moveSpeed * Gdx.graphics.getDeltaTime());
        this.position.add(this.velocity);
        this.rect.setPosition(this.position);
    }

    public boolean collidesWith(float x, float y, float width, float height){
        if (this.rect.x > x && this.rect.x < x + width && this.rect.y > y && this.rect.y < y + height){
            return true;
        }
        return false;
    }

    public static void setDamageDelay() {
        damageDelay = maxDamageDelay;
        canDamage = false;
    }

    public static float getDamage(){return damage;}

    private boolean reachedDestination(){
        if ((this.position.x > this.destination.x - moveSpeed && this.position.x < this.destination.x + moveSpeed) && (this.position.y > this.destination.y - moveSpeed && this.position.y < this.destination.y + moveSpeed)){
            return true;
        }
        return false;
    }

    private Vector2 generateNewDestination(){
        int x = MathUtils.random.nextInt(3420);
        int y = MathUtils.random.nextInt(3460);
        return new Vector2(x, y);
    }

    private void reduceDamageDelay(){
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

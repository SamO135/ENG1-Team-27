package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Colliders.ProjectileCollider;

public class Projectile {
    public int SPEED = 300;
    private static Texture texture;
    float num = 0;
    int width = 8;
    int height = 8;

    ProjectileCollider rect;

    Vector2 position;
    Vector2 targetposition;

    public boolean remove = false;

    /** Constructs a new projectile
     * @param position A Vector2 position of the projectile
     * @param enemy True if the bullet belongs to an enemy, False otherwise*/
    public Projectile(Vector2 position, Vector2 target, boolean enemy){
        if (!enemy){
            SPEED = 1000;
        }
        this.position = position;
        this.targetposition = target;
        this.rect = new ProjectileCollider(position, width, height);

        if (texture == null){
            texture = new Texture("cannonball.png");
        }
    }

    /** Updates the projectile's position, removes object after 5 seconds*/
    public void update(float deltaTime){
        num += deltaTime;

        position.y += targetposition.y * SPEED * deltaTime;
        position.x += targetposition.x * SPEED * deltaTime;
        rect.move(position);
        if(num >= 5){
            remove = true;
        }
    }

    /** Renders the projectile object*/
    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    /** @return The position of the projectile*/
    public Vector2 getPosition(){
        return this.position;
    }

    /** Sets the position of the projectile*/
    public void setPosition(Vector2 Position){
        this.position = Position;
    }

    /** @return The projectile rect*/
    public ProjectileCollider getProjectileCollider(){ return rect;}

}

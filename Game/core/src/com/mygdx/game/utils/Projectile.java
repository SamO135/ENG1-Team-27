package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Unity;

public class Projectile {
    public static final int SPEED = 3;
    private static Texture texture;
    float num = 0;

    Vector2 position;
    Vector2 targetposition;

    public boolean remove = false;

    public Projectile(Vector2 position, Vector2 target){
        this.position = position;
        this.targetposition = target;

        if (texture == null){
            texture = new Texture("cannonball.png");
        }
    }

    public void update(float deltaTime){
        num += deltaTime;
        position.y += targetposition.y * SPEED * deltaTime;
        position.x += targetposition.x * SPEED * deltaTime;
        if(num >= 5){
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void setPosition(Vector2 Position){
        this.position = Position;
    }

}

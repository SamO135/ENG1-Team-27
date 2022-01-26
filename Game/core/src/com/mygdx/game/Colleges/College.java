package com.mygdx.game.Colleges;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Colliders.CollisionRect;
import com.mygdx.game.Unity;

public class College {
    private Texture img;
    private Sprite sprite;
    int x,y;
    CollisionRect rect;
    float health = 1f;
    Texture blank = Unity.blank;

    public College(int x, int y, String img){
        this.img = new Texture(img);
        sprite = new Sprite(this.img);
        this.x = x;
        this.y = y;
        this.rect = new CollisionRect(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
    }

    public CollisionRect getCollisionRect(){ return rect;}

    public void render(SpriteBatch batch){
        sprite.setPosition(x, y);
        sprite.draw(batch);
        batch.setColor(Color.RED);
        batch.draw(blank, x + (img.getWidth() / 2) - 60, y - 10, 120 * health, 5);
    }

    public void hit(){
        if(health > 0.2f){
            health -= 0.2f;
        }else{
            health = 0f;
        }
    }

    public float getHealth(){
        return health;
    }
}

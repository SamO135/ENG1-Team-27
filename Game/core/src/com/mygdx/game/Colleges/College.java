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
    public boolean isBoss;
    public boolean bossReady = false;
    boolean isCaptured = false;
    float num = 0;

    public College(int x, int y, String img, boolean boss){
        this.img = new Texture(img);
        sprite = new Sprite(this.img);
        this.x = x;
        this.y = y;
        isBoss = boss;
        if(isBoss == false){bossReady = true;}
        this.rect = new CollisionRect(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
    }

    public CollisionRect getCollisionRect(){ return rect;}

    public void render(SpriteBatch batch){
        sprite.setPosition(x, y);
        sprite.draw(batch);
        if(isCaptured  != true){
            batch.setColor(Color.RED);
        }else{
            health = 1;
            batch.setColor(Color.GREEN);
        }
        batch.draw(blank, x + (img.getWidth() / 2) - 60, y - 10, 120 * health, 5);
        batch.setColor(Color.WHITE);

    }

    public void hit(){
        if(health > 0.2f){
            if(isBoss == true){
                if(bossReady == true){
                    health -= 0.2f;
                }
            }else{
                health -= 0.2f;
            }
        }else{
            health = 0f;
            isCaptured = true;
        }
    }

    public float captured(float score, float delta){
        num += delta;
        if((int) num >= 1 && isCaptured == true){
            score += 5;
            num = 0;
        }
        return score;
    }

    public float getHealth(){
        return health;
    }

    public boolean isCaptured(){
        return isCaptured;
    }
}

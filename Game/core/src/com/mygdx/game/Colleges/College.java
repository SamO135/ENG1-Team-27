package com.mygdx.game.Colleges;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Colliders.BaseCollider;
import com.mygdx.game.Colliders.ProjectileCollider;
import com.mygdx.game.Unity;

public class College {
    private Texture img;
    private Sprite sprite;
    Vector2 position;
    ProjectileCollider rect;
    BaseCollider collider;
    float health = 1f;
    Texture blank = Unity.blank;
    public boolean isBoss;
    public boolean bossReady = false;
    boolean isCaptured = false;
    float num = 0;
    int collegesNotBoss;

    public College(Vector2 position, String img, boolean boss, World world){
        this.img = new Texture(img);
        sprite = new Sprite(this.img);
        this.position = position;
        isBoss = boss;
        if(!isBoss){bossReady = true;}
        this.rect = new ProjectileCollider(position, (int) sprite.getWidth(), (int) sprite.getHeight());
        this.collider = new BaseCollider(position, 128, 64, true, world);
    }

    public ProjectileCollider getProjectileCollider(){ return rect;}

    public void render(SpriteBatch batch){
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        if(!isCaptured){
            batch.setColor(Color.RED);
        }else{
            health = 1;
            batch.setColor(Color.GREEN);
        }
        batch.draw(blank, position.x + (img.getWidth() / 2) - 60, position.y - 10, 120 * health, 5);
        batch.setColor(Color.WHITE);

    }

    public int  hit(int plunder){
        collegesNotBoss = Unity.collagesNotBossCount;
        if(collegesNotBoss == 0){
            bossReady = true;
        }
        if(isBoss){
            if(bossReady){
                if(health > 0.1f){
                    health -= 0.1f;

                }else{
                    health = 0f;
                    isCaptured = true;
                }
            }
        }else{
            if(health > 0.2f){
                health -= 0.2f;

            }else{
                health = 0f;
                isCaptured = true;
                collegesNotBoss -= 1;
                plunder += 200;
            }
        }
        return plunder;
    }

    public float captured(float score, float delta){
        num += delta;
        if((int) num >= 1 && isCaptured){
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

    public Vector2 getLocation(){
        return position;
    }
    
    public Body getColliderBody() {
    	return collider.getBody();
    }
}

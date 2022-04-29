package com.mygdx.game.Colleges;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
    private String name;
    private Texture img;
    private Sprite sprite;
    Vector2 position;
    ProjectileCollider rect;
    BaseCollider collider;
    float maxHealth = 1f;
    float health = maxHealth;
    Texture blank = Unity.blank;
    public boolean isBoss;
    public boolean bossReady = false;
    boolean isCaptured = false;
    float num = 0;
    static int collegesNotBoss;
    public static float dmgTakenFromBullet = 0.2f;
    public float shootCooldown;
    private Preferences prefs;

    public College(Vector2 position, String img, boolean boss, World world, String name, Preferences prefs){
        this.img = new Texture(img);
        sprite = new Sprite(this.img);
        this.position = position;
        isBoss = boss;
        if(!isBoss){bossReady = true;}
        this.rect = new ProjectileCollider(position, (int) sprite.getWidth(), (int) sprite.getHeight());
        this.collider = new BaseCollider(position, 128, 64, true, world);
        this.shootCooldown = 40;
        this.isCaptured = false;
        this.prefs = prefs;
        this.name = name;
    }

    public ProjectileCollider getProjectileCollider(){ return rect;}

    public void render(SpriteBatch batch){
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        if(!isCaptured){
            batch.setColor(Color.RED);
        }else{
            health = maxHealth;
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
            if(bossReady) {
                if (takeDamage(dmgTakenFromBullet/2) == 0f){
                    isCaptured = true;
                }
            }
        }else{
            if (takeDamage(dmgTakenFromBullet) == 0f) {
                health = 0f;
                isCaptured = true;
                Unity.collagesNotBossCount -= 1;
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

    public void setHealth(float newHealth){ health = newHealth; }

    public float takeDamage(float dmg){
        if(health > dmg){
            health -= dmg;

        }else {
            health = 0f;
        }
        return health;
    }

    public static void setDmgTakenFromBullet(float dmg){
        dmgTakenFromBullet = dmg;
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

    public String getName(){
        return name;
    }

    public void updateCollege(){
        this.isCaptured = prefs.getBoolean(this.name + "_isCaptured", false);
        this.health = prefs.getFloat(this.name + "_health", 1f);
        if (this.isCaptured){
            Unity.collagesNotBossCount -= 1;
        }
    }
}

package com.mygdx.game.Collectables;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private Rectangle rect;
    private Texture image;
    private Vector2 position;
    private TextureRegion textureRegion;
    int value;
    int id;
    Preferences prefs;
    public boolean collected;

    public Coin(Vector2 pos, int plunder, int id, Preferences prefs){
        this.position = pos;
        this.image = new Texture("coin_22x22.png");
        this.textureRegion = new TextureRegion(image);
        this.rect = new Rectangle(this.position.x, this.position.y, this.image.getWidth(), this.image.getHeight());
        this.value = plunder;
        this.id = id;
        this.prefs = prefs;
        this.collected = false;
    }

    public void render(SpriteBatch batch){
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2f, this.position.y - this.image.getHeight()/2f);
    }


    public boolean collidesWith(float x, float y, float width, float height){
        if (this.rect.x >= x && this.rect.x <= x + width && this.rect.y >= y && this.rect.y <= y + height){
            return true;
        }
        return false;
    }

    public boolean alreadyCollected(){
        return prefs.getBoolean("coin" + this.id + "collected");
    }

    public int getId(){
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val){
        this.value = val;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void dispose(){
        this.image.dispose();
    }
}

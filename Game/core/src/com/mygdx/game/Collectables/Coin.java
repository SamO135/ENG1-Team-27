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

    /** Constructs a new coin
     * @param pos The position of the coin as a Vector2
     * @param plunder The amount of plunder the coin provides on pickup
     * @param id A unique value assigned to the coin object
     * @param prefs The preferences file the coin gets saved to
     */
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

    /** Renders the coin object
     * @param batch a SpriteBatch instance*/
    public void render(SpriteBatch batch){
        batch.draw(textureRegion, this.position.x - this.image.getWidth()/2f, this.position.y - this.image.getHeight()/2f);
    }

    /** @return True if the coin object overlaps with the provided coordinates and dimensions, False otherwise*/
    public boolean collidesWith(float x, float y, float width, float height){
        if (this.rect.x >= x && this.rect.x <= x + width && this.rect.y >= y && this.rect.y <= y + height){
            return true;
        }
        return false;
    }

    /** @return the unique id of the coin*/
    public int getId(){
        return id;
    }

    /** @return the amount of plunder the coin is worth*/
    public int getValue() {
        return value;
    }

    /** Sets the amount of plunder that the coin is worth*/
    public void setValue(int val){
        this.value = val;
    }

    /** @return the position of the coin*/
    public Vector2 getPosition(){
        return this.position;
    }

    /** Disposes the image of the coin*/
    public void dispose(){
        this.image.dispose();
    }
}

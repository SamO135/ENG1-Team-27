package com.mygdx.game.Animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Explosion {
    public static final float frame_length = 0.2f;
    public static final int offset = 4;
    public static final int size = 32;

    private static Animation<?> anim = null;
    float statetime;
    public boolean remove = false;
	private Vector2 position;

    /** Constructs a new explosion object
     * @param loc A Vector2 location of the explosion*/
	public Explosion(Vector2 loc){
        this.position = new Vector2(loc.x - offset, loc.y - offset);
        statetime = 0;


        if(anim == null){
            anim = new Animation(frame_length, TextureRegion.split(new Texture("Explosion.png"), size, size)[0]);
        }
    }

    /** Animates the explosion
     * @param deltatime The time in seconds since the last update*/
    public void update(float deltatime){
        statetime += deltatime;
        if(anim.isAnimationFinished(statetime)){
            remove = true;
        }
    }

    /** Renders the explosion
     * @param batch A SpriteBatch instance
     * */
    public void render(SpriteBatch batch){
        batch.draw((TextureRegion) anim.getKeyFrame(statetime), position.x, position.y);
    }

}

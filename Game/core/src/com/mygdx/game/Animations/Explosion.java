package com.mygdx.game.Animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Explosion {
    public static final float frame_length = 0.2f;
    public static final int offset = 4;
    public static final int size = 32;

    private static Animation anim = null;
    float x, y;
    float statetime;
    public boolean remove = false;

    public Explosion(float x, float y){
        this.x = x - offset;
        this.y = y - offset;
        statetime = 0;


        if(anim == null){
            anim = new Animation(frame_length, TextureRegion.split(new Texture("Explosion.png"), size, size)[0]);
        }
    }

    public void update(float deltatime){
        statetime += deltatime;
        if(anim.isAnimationFinished(statetime)){
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw((TextureRegion) anim.getKeyFrame(statetime), x, y);
    }

}

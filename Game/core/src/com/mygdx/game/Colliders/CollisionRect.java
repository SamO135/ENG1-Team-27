package com.mygdx.game.Colliders;

import com.badlogic.gdx.physics.box2d.Body;

public class CollisionRect {

    float x, y;
    int width, height;

    public CollisionRect(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height= height;
    }

    public void move(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith(CollisionRect rect, Body body, int width, int height, String type){
        if (type == "body"){
            return x < body.getPosition().x + width && y < body.getPosition().y + height && x + this.width > body.getPosition().x && y + this.height > height;
        }else{
            return x < rect.x + rect.width && y < rect.y + rect.height && x + this.width > rect.x && y + this.height > rect.y;
        }
    }
}

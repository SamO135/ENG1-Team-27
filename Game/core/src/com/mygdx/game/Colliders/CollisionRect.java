package com.mygdx.game.Colliders;

import com.badlogic.gdx.math.Vector2;

public class CollisionRect {

    Vector2 position;
    int width, height;

    public CollisionRect(Vector2 position, int width, int height){
        this.position = position;
        this.width = width;
        this.height= height;
    }

    public void move(Vector2 newPosition){
        this.position = newPosition;
    }

    public boolean collidesWith(CollisionRect rect){
            return position.x < rect.position.x + rect.width && position.y < rect.position.y + rect.height && position.x + this.width > rect.position.x && position.y + this.height > rect.position.y;

    }
}

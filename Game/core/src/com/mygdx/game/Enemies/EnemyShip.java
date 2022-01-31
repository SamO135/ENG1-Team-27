package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Colliders.CollisionRect;
import com.mygdx.game.Unity;
import java.util.Random;

import static com.mygdx.game.utils.Constants.PPM;

public class EnemyShip {
	
    private Texture img;
    private Sprite sprite;
    Random rand = new Random();
    CollisionRect rect;
    float health = 1f;
    Texture blank = Unity.blank;
    Body body;
	private Vector2 position;
	private Vector2 collegePosition;

    public EnemyShip(Vector2 position, College college, World world){ 
        this.collegePosition = college.getLocation();
        this.position = position;

       /*this.img = new Texture("PirateShipEnemy.png");
        sprite = new Sprite(this.img);
        rect = new CollisionRect(x, y, img.getWidth(), img.getHeight());

        Body pBody;
        BodyDef def = new BodyDef();
        def.position.set(x / PPM, y / PPM);
        pBody = world.createBody(def);


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(img.getWidth() / 2 / PPM,img.getHeight() / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        this.body = pBody;
        shape.dispose();*/

        /*while(x < 0 || x > Unity.getMapWidth() * 32 || y < 0 || y > Unity.getMapHeight() * 32){
            randomLocation = getRandomLocation(collegePosition);
        }*/
    }

    public void render(SpriteBatch batch){
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
        batch.draw(blank, position.x + (img.getWidth() / 2) - 60, position.y - 10, 60 * health, 5);
    }

    private Vector2 getRandomLocation(){
        return new Vector2(
                collegePosition.x + (rand.nextInt(1500) - 500),
                collegePosition.y + (rand.nextInt(1500) - 500)
        );
    }
}

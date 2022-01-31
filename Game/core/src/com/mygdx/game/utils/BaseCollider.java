package com.mygdx.game.utils;

import static com.mygdx.game.utils.Constants.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BaseCollider {
	
	Body body;
	
	public BaseCollider(Vector2 position, int width, int height, boolean isStatic, World world) {
		BodyDef def = new BodyDef();

		if(isStatic){
			def.type = BodyDef.BodyType.StaticBody;
		}
		else{
			def.type = BodyDef.BodyType.DynamicBody;
		}
		def.position.set(position.x / PPM, position.y / PPM);
		this.body = world.createBody(def);


		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM,height / 2 / PPM);

		body.createFixture(shape, 1.0f);
		shape.dispose();

	}
	
	public Body getBody() {
		return(body);
	}
}

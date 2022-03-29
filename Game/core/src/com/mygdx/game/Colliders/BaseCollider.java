package com.mygdx.game.Colliders;

import static com.mygdx.game.utils.Constants.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BaseCollider {
	
	Body body;
	ProjectileCollider rect;
	BodyDef def;

	
	public BaseCollider(Vector2 position, int width, int height, boolean isStatic, World world) {
		this.def = new BodyDef();

		if(isStatic){
			this.def.type = BodyDef.BodyType.StaticBody;
		}
		else{
			this.def.type = BodyDef.BodyType.DynamicBody;
		}
		this.def.position.set(position.x / PPM, position.y / PPM);
		this.body = world.createBody(def);

		this.rect = new ProjectileCollider(def.position, width, height);


		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM,height / 2 / PPM);

		body.createFixture(shape, 1.0f);
		shape.dispose();

	}
	
	public Body getBody() {
		return(body);
	}

	public ProjectileCollider getProjectileCollider(){
		return rect;
	}

	public void update(){
		//this.rect.position = new Vector2(this.body.getPosition().scl(PPM));
		this.rect.position = new Vector2(this.body.getPosition());
	}
}

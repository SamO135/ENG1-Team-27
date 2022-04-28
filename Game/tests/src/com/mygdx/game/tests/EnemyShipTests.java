package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Enemies.EnemyShip;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class EnemyShipTests {

    /*@Test
    public void testTakeDamage(){
        World world = new World(new Vector2(0, 0f), false);
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        College college = new College(new Vector2(3140, 3110), "TowerGoodricke.png", true, world, "college", prefs);
        Sprite spriteEnemyGoodricke = new Sprite(new Texture("PirateShipEnemy.png"));

        EnemyShip ship = new EnemyShip(new Vector2(2900, 2600), college, world, spriteEnemyGoodricke, prefs);
        float health = ship.getHealth();
        ship.hit();
        assertTrue(health - 0.5f == ship.getHealth());
    }*/

    @Test
    public void test(){

    }

}

package com.mygdx.game.tests;


import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enemies.Hurricane;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Unity;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class HurricaneTests {

    @Test
    public void testGetID(){
        int id = 1;
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, id);

        assertEquals(id, hurricane.getId(), 0.0);
    }

    @Test
    public void testSetDamageDelay(){
        Hurricane.setDamageDelay();
        assertEquals(100f * Unity.numOfHurricanes, Hurricane.damageDelay, 0.0);
    }


    @Test
    public void testGetDamage(){
        assertEquals(0.2f, Hurricane.getDamage(), 0.0);
    }

    @Test
    public void testGetPosition(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, 1);
        int x = 1000;
        int y = 1000;
        hurricane.setPosition(x, y);
        Vector2 pos = hurricane.getPosition();
        assertTrue(x == pos.x && y == pos.y);
    }

    @Test
    public void testGetDestination(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, 1);
        int x = 1000;
        int y = 1000;
        hurricane.setDestination(x, y);
        Vector2 destination = new Vector2(x, y);
        assertTrue(hurricane.getDestination().x == destination.x && hurricane.getDestination().y == destination.y);
    }

    @Test
    public void testResetHurricane(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, 1);
        Vector2 old_pos = hurricane.getPosition();
        Vector2 old_destination = hurricane.getDestination();
        hurricane.resetHurricane();
        Vector2 new_pos = hurricane.getPosition();
        Vector2 new_destination = hurricane.getDestination();
        assertTrue(old_pos.x != new_pos.x && old_pos.y != new_pos.y);
        assertTrue(old_destination.x != new_destination.x && old_destination.y != new_destination.y);
    }

    @Test
    public void testReachedDestination(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, 1);
        float x = hurricane.getDestination().x;
        float y = hurricane.getDestination().y;
        hurricane.setPosition(x, y);
        assertTrue(hurricane.reachedDestination());
        hurricane.setPosition(x-100, y-100);
        assertFalse(hurricane.reachedDestination());

    }

    @Test
    public void testReduceDamageDelay(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, 1);
        Hurricane.setDamageDelay();
        float delay = Hurricane.damageDelay;
        hurricane.reduceDamageDelay();
        assertEquals(delay - 1, Hurricane.damageDelay, 0.0);
    }

    @Test
    public void testReduceDamageDelayBelowZero(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, 1);
        Hurricane.damageDelay = -1;
        hurricane.reduceDamageDelay();
        assertEquals(0, Hurricane.damageDelay, 0.0);
    }

    //@Test
    //public void testUpdate(){
    //    Preferences prefs = Gdx.app.getPreferences("test prefs");
    //    Hurricane hurricane = new Hurricane(prefs, 1);
    //    hurricane.update();
    //}

    @Test
    public void testCollidesWith(){
        // Test when the objects do collide
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Hurricane hurricane = new Hurricane(prefs, 1);
        float x = hurricane.getPosition().x;
        float y = hurricane.getPosition().y;
        float width = 64f;
        float height = 64f;
        assertTrue(hurricane.collidesWith(x, y, width, height));

        // Test when the object do not collide
        x = hurricane.getPosition().x + (width*2);
        y = hurricane.getPosition().y + (width*2);
        assertFalse(hurricane.collidesWith(x, y, width, height));
    }

}

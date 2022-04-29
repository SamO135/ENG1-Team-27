package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Collectables.Coin;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CollectablesTests {

    @Test
    public void testCoinAlreadyCollected(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Coin coin = new Coin(new Vector2(1000, 1000), 200, 1, prefs);
        prefs.putBoolean("coin" + coin.getId() + "collected", false);
        assertFalse(coin.alreadyCollected());
        prefs.putBoolean("coin" + coin.getId() + "collected", true);
        assertTrue(coin.alreadyCollected());
    }

    @Test
    public void testGetId(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Coin coin = new Coin(new Vector2(1000, 1000), 200, 1, prefs);
        assertEquals(1, coin.getId(), 0.0);
    }

    @Test
    public void getValue(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Coin coin = new Coin(new Vector2(1000, 1000), 200, 1, prefs);
        assertEquals(200, coin.getValue(), 0.0);
    }


    @Test
    public void testGetPosition(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Coin coin = new Coin(new Vector2(1000, 2000), 200, 1, prefs);
        assertTrue(coin.getPosition().x == 1000 && coin.getPosition().y == 2000);
    }


    @Test
    public void testCollidesWith(){
        // Test when the objects do collide
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Coin coin = new Coin(new Vector2(1000, 2000), 200, 1, prefs);
        float x = coin.getPosition().x;
        float y = coin.getPosition().y;
        float width = 64f;
        float height = 64f;
        assertTrue(coin.collidesWith(x, y, width, height));

        // Test when the object do not collide
        x = coin.getPosition().x + (width*2);
        y = coin.getPosition().y + (width*2);
        assertFalse(coin.collidesWith(x, y, width, height));
    }

    @Test
    public void testUpdate(){
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        Coin coin = new Coin(new Vector2(1000, 2000), 200, 1, prefs);
        coin.collected = false;
        prefs.putBoolean("coin" + coin.getId() + "collected", true);
        coin.update();
        assertTrue(coin.collected);
    }
}

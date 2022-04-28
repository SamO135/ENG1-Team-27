package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Unity;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.jvm.hotspot.memory.Universe;

import static org.junit.Assert.assertTrue;

public class DifficultyTests {


    @Test
    public void testDefaultDifficulty() {
        Unity game = new Unity();
        assertTrue(game.difficulty == Unity.Difficulty.Hard);
    }

    @Test
    public void testEasyDifficulty() {
        Unity game = new Unity();
        Unity.Difficulty someDiff = Unity.Difficulty.Easy;
        game.applyDifficulty(someDiff);

        assertTrue(game.playerDmgFromBullet == 0.1f);
    }

    /*@Test
    public void testEasyDifficulty2() {
        Unity game = new Unity();
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        World world = new World(new Vector2(0, 0f), false);
        College testCollege = new College(new Vector2(3140, 3110), "TowerGoodricke.png", true, world, "Test", prefs);
        Unity.Difficulty someDiff = Unity.Difficulty.Easy;
        game.applyDifficulty(someDiff);

        assertTrue(game.playerDmgFromBullet == 0.1f);

        double damageTaken = game.damageUpgrade * 0.1;
        testCollege.setDmgTakenFromBullet(4);

    }*/

    @Test
    public void testNormalDifficulty() {
        Unity game = new Unity();
        Unity.Difficulty someDiff = Unity.Difficulty.Normal;
        game.applyDifficulty(someDiff);

        assertTrue(game.playerDmgFromBullet == 0.2f);
    }

    @Test
    public void testHardDifficulty() {
        Unity game = new Unity();
        Unity.Difficulty someDiff = Unity.Difficulty.Hard;
        game.applyDifficulty(someDiff);

        assertTrue(game.playerDmgFromBullet == 0.25f);
    }


}

package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Enemies.EnemyShip;
import com.mygdx.game.Unity;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.jvm.hotspot.memory.Universe;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class DifficultyTests {


    @Test
    public void testDefaultDifficulty() {
        Unity game = new Unity();
        assertTrue(game.difficulty == Unity.Difficulty.Hard);
    }


    @Test
    public void testEasyDifficulty() {
        Unity game = new Unity();

        // Set Difficulty to easy
        Unity.Difficulty someDiff = Unity.Difficulty.Easy;
        game.applyDifficulty(someDiff);

        // Player takes correct amount of damage from bullets
        assertTrue(game.playerDmgFromBullet == 0.1f);

        // College takes correct amount of damage from bullets
        float collegeDmgTakenDefault = 0.3f;
        assertTrue(College.dmgTakenFromBullet == (collegeDmgTakenDefault + (0.3 * Unity.damageUpgrade)));

        // Enemy Ship takes correct amount of damage from bullets
        float enemyShipDmgTakenDefault = 0.5f;
        assertTrue(EnemyShip.dmgTakenFromBullet == (enemyShipDmgTakenDefault + (0.3 * Unity.damageUpgrade)));


    }

    @Test
    public void testNormalDifficulty() {
        Unity game = new Unity();

        // Set Difficulty to easy
        Unity.Difficulty someDiff = Unity.Difficulty.Normal;
        game.applyDifficulty(someDiff);

        // Player takes correct amount of damage from bullets
        assertTrue(game.playerDmgFromBullet == 0.2f);

        // College takes correct amount of damage from bullets
        float collegeDmgTakenDefault = 0.2f;
        assertTrue(College.dmgTakenFromBullet == (collegeDmgTakenDefault + (0.3 * Unity.damageUpgrade)));

        // Enemy Ship takes correct amount of damage from bullets
        float enemyShipDmgTakenDefault = 0.34f;
        assertTrue(EnemyShip.dmgTakenFromBullet == (enemyShipDmgTakenDefault + (0.3 * Unity.damageUpgrade)));
    }

    @Test
    public void testHardDifficulty() {
        Unity game = new Unity();

        // Set Difficulty to easy
        Unity.Difficulty someDiff = Unity.Difficulty.Hard;
        game.applyDifficulty(someDiff);

        // Player takes correct amount of damage from bullets
        assertTrue(game.playerDmgFromBullet == 0.25f);

        // College takes correct amount of damage from bullets
        float collegeDmgTakenDefault = 0.1f;
        assertTrue(College.dmgTakenFromBullet == (collegeDmgTakenDefault + (0.3 * Unity.damageUpgrade)));

        // Enemy Ship takes correct amount of damage from bullets
        float enemyShipDmgTakenDefault = 0.25f;
        assertTrue(EnemyShip.dmgTakenFromBullet == (enemyShipDmgTakenDefault + (0.3 * Unity.damageUpgrade)));
    }


}

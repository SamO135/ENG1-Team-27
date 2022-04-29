package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Colleges.College;
import com.mygdx.game.Colliders.BaseCollider;
import com.mygdx.game.Enemies.EnemyShip;
import com.mygdx.game.Enemies.Hurricane;
import com.mygdx.game.Unity;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;

import static com.mygdx.game.Unity.damageUpgrade;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PreferencesTests {

    /*@Test
    public void testSavePreferences(){
        Unity game = new Unity();
        Preferences prefs = Gdx.app.getPreferences("test prefs");
        ArrayList<College> Colleges = new ArrayList<College>();
        ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
        ArrayList<Hurricane> hurricanes = new ArrayList<Hurricane>();
        //BaseCollider player = new BaseCollider(new Vector2(1000, 1000), 128, 64, false, game.world);
        float score = 15;
        int plunder = 100;
        float health = 0.2f;
        float weatherResistanceUpgrade = 0.3f;
        Unity.Difficulty difficulty = Unity.Difficulty.Easy;
        try{
            game.savePreferences(prefs, Colleges, enemyShips, plunder, score, game.player, health, hurricanes, damageUpgrade, weatherResistanceUpgrade, difficulty);
        }
        catch (Exception NullPointerException){
            assertTrue(false);
        }
    }*/

    @Test
    public void test(){

    }
}

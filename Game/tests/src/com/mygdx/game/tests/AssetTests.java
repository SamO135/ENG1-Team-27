package com.mygdx.game.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testCannonBallAssetExists(){
        assertTrue("This test will only pass if cannonball.png exists", Gdx.files.internal("cannonball.png").exists());
    }
}

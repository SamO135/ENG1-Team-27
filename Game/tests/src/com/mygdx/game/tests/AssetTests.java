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

    @Test
    public void testLogoAssetExists(){
        assertTrue("This test will only pass if Logo.png exists", Gdx.files.internal("Logo.png").exists());
    }

    @Test
    public void testHurricaneAssetExists(){
        assertTrue("This test will only pass if Hurricane.png exists", Gdx.files.internal("hurricane_128x82.png").exists());
    }

    @Test
    public void testExplosionAssetExists(){
        assertTrue("This test will only pass if Explosion.png exists", Gdx.files.internal("Explosion.png").exists());
    }

    @Test
    public void testPirateShipEnemyAssetExists(){
        assertTrue("This test will only pass if PirateShipEnemy.png exists", Gdx.files.internal("PirateShipEnemy.png").exists());
    }

    @Test
    public void testTowerDerwentAssetExists(){
        assertTrue("This test will only pass if TowerDerwent.png exists", Gdx.files.internal("TowerDerwent.png").exists());
    }

    @Test
    public void testTowerJamesAssetExists(){
        assertTrue("This test will only pass if TowerJames.png exists", Gdx.files.internal("TowerJames.png").exists());
    }

    @Test
    public void testTowerGoodrickeAssetExists(){
        assertTrue("This test will only pass if TowerGoodricke.png exists", Gdx.files.internal("TowerGoodricke.png").exists());
    }

    @Test
    public void testTowerAlcuinAssetExists(){
        assertTrue("This test will only pass if TowerAlcuin.png exists", Gdx.files.internal("TowerAlcuin.png").exists());
    }

    @Test
    public void testGameMapAssetExists(){
        assertTrue("This test will only pass if GameMap.tmx exists", Gdx.files.internal("MapAssets/GameMap.tmx").exists());
    }

    @Test
    public void testMainMapAssetExists(){
        assertTrue("This test will only pass if MainMap.tmx exists", Gdx.files.internal("MapAssets/MainMap.tmx").exists());
    }

    @Test
    public void testTilesetKennyAssetExists(){
        assertTrue("This test will only pass if TilesetKenny.tsx exists", Gdx.files.internal("MapAssets/TilesetKenny.tsx").exists());
    }

    @Test
    public void testTOswaldBoldAssetExists(){
        assertTrue("This test will only pass if Oswald-Bold.ttf exists", Gdx.files.internal("fonts/Oswald-Bold.ttf").exists());
    }

    @Test
    public void testTOswaldHeavyAssetExists(){
        assertTrue("This test will only pass if Oswald-Heavy.ttf exists", Gdx.files.internal("fonts/Oswald-Heavy.ttf").exists());
    }

    @Test
    public void testOceanTileAssetExists(){
        assertTrue("This test will only pass if Sea 1.png exists", Gdx.files.internal("MapAssets/Beach Tiles/Ocean/Sea 1.png").exists());
    }

    @Test
    public void testPierTileAssetExists(){
        assertTrue("This test will only pass if Pier corner B to L exists", Gdx.files.internal("MapAssets/Beach Tiles/Pier/Pier Corner Bottom to Left.png").exists());
    }

    @Test
    public void testSandTileAssetExists(){
        assertTrue("This test will only pass if Sand 1.png exists", Gdx.files.internal("MapAssets/Beach Tiles/Sand/Sand 1.png").exists());
    }

    @Test
    public void testShoreTileAssetExists(){
        assertTrue("This test will only pass if Beach Bottom 1.png exists", Gdx.files.internal("MapAssets/Beach Tiles/Shore/Beach Bottom 1.png").exists());
    }
}

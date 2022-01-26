package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Unity;

public class gui {

    private static float width = Unity.getWidth();
    private static float height = Unity.getHeight();

    public static void updateMainScreen(Batch batch, BitmapFont font, float count, int score) {
        drawPlunder(batch, font, count, score);
    }

    public static void drawPlunder(Batch batch, BitmapFont font, float count, int score){
        
        GlyphLayout PlunderInfoTextLayout = new GlyphLayout(font, "Plunder: " + count);
        GlyphLayout ScoreInfoTextLayout = new GlyphLayout(font, "Score: " + score);
        float PlunderInfoTextWidth = PlunderInfoTextLayout.width;
        float PlunderInfoTextHeight = PlunderInfoTextLayout.height;
        float ScoreInfoTextWidth = ScoreInfoTextLayout.width;
        float ScoreInfoTextHeight = ScoreInfoTextLayout.height;

        batch.begin();
        font.draw(batch, "Plunder: " + count, Math.round(width -(PlunderInfoTextWidth*1.2)),
                Math.round(height-(PlunderInfoTextHeight*1.2)));
        font.draw(batch, "Score: " + score, Math.round(width / 2 -(ScoreInfoTextWidth*1.2)),
                Math.round(height-(ScoreInfoTextHeight*1.2)));
        batch.end();
    }
    
    public static void drawMenuScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont){
    	//title
    	GlyphLayout TitleTextLayout = new GlyphLayout(LargeFont, "York Pirates");
        float TitleTextWidth = TitleTextLayout.width;
		LargeFont.draw(batch, "York Pirates", (width-TitleTextWidth)/2, height * .75f);
		
    	GlyphLayout MissionTextLayout = new GlyphLayout(SmallFont, "Defeat Goodricke college to win!");
    	GlyphLayout StartTextLayout = new GlyphLayout(SmallFont, "Press any key to start");
        float MissionTextWidth = MissionTextLayout.width;
        float StartTextWidth = StartTextLayout.width;
        SmallFont.draw(batch, "Defeat Goodricke college to win!", (width-MissionTextWidth)/2, height * .5f);
		SmallFont.draw(batch, "Press any key to start", (width-StartTextWidth)/2, height * .4f);
    }
}
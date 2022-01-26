package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Unity;

public class gui {

    private static float width = Unity.getWidth();
    private static float height = Unity.getHeight();

    public static void updateMainScreen(Batch batch, BitmapFont font) {
        drawPlunder(batch, font);
    }

    public static void drawPlunder(Batch batch, BitmapFont font){
        
        GlyphLayout PlunderInfoTextLayout = new GlyphLayout(font, "Plunder: ");
        float PlunderInfoTextWidth = PlunderInfoTextLayout.width;
        float PlunderInfoTextHeight = PlunderInfoTextLayout.height;

        batch.begin();
        font.draw(batch, "Plunder: ", Math.round(width -(PlunderInfoTextWidth*1.2)),
                Math.round(height-(PlunderInfoTextHeight*1.2)));
        batch.end();
    }
    
    public static void drawMenuScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont){
    	//title
    	GlyphLayout TitleTextLayout = new GlyphLayout(LargeFont, "York Pirates");
        float TitleTextWidth = TitleTextLayout.width;
		LargeFont.draw(batch, "York Pirates", (width-TitleTextWidth)/2, height * .75f);
		
    	GlyphLayout MissionTextLayout = new GlyphLayout(SmallFont, "[placeholder]");
    	GlyphLayout StartTextLayout = new GlyphLayout(SmallFont, "Press any key to start");
        float MissionTextWidth = MissionTextLayout.width;
        float StartTextWidth = StartTextLayout.width;
        SmallFont.draw(batch, "[placeholder]", (width-MissionTextWidth)/2, height * .5f);
		SmallFont.draw(batch, "Press any key to start", (width-StartTextWidth)/2, height * .4f);
    }
}
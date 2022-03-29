package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Unity;
import jdk.internal.org.jline.utils.DiffHelper;

public class gui {

    private static float width = Unity.getWidth();
    private static float height = Unity.getHeight();

    public static void drawMainScreen(Batch batch, BitmapFont font, int plunder, float score) {
    	
        GlyphLayout PlunderInfoTextLayout = new GlyphLayout(font, "Plunder: " + plunder);
        GlyphLayout ScoreInfoTextLayout = new GlyphLayout(font, "Score: " + Math.round(score));
        float PlunderInfoTextWidth = PlunderInfoTextLayout.width;
        float PlunderInfoTextHeight = PlunderInfoTextLayout.height;
        float ScoreInfoTextWidth = ScoreInfoTextLayout.width;
        float ScoreInfoTextHeight = ScoreInfoTextLayout.height;

        batch.begin();
        font.draw(batch, "Plunder: " + plunder, Math.round(width -(PlunderInfoTextWidth*1.2)),
                Math.round(height-(PlunderInfoTextHeight*1.2)));
        font.draw(batch, "Score: " + Math.round(score), Math.round(width / 2 -(ScoreInfoTextWidth*1.2)),
                Math.round(height-(ScoreInfoTextHeight*1.2)));
        batch.end();
    }
    
    public static void drawMenuScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont, BitmapFont MediumFont, Unity.Difficulty difficulty){
    	//title
    	GlyphLayout TitleTextLayout = new GlyphLayout(LargeFont, "York Pirates");
        GlyphLayout DifficultyTextLayout = new GlyphLayout(MediumFont, "(" + difficulty + " mode)");
        float TitleTextWidth = TitleTextLayout.width;
        float DifficultyTextWidth = DifficultyTextLayout.width;
		LargeFont.draw(batch, "York Pirates", (width-TitleTextWidth)/2, height * .75f);
        MediumFont.draw(batch, "(" + difficulty + " mode)", (width-DifficultyTextWidth)/2, height * .69f);
		
    	GlyphLayout MissionTextLayout = new GlyphLayout(SmallFont, "Defeat Goodricke college to win!");
    	GlyphLayout StartTextLayout = new GlyphLayout(SmallFont, "Press any key to start");
        float MissionTextWidth = MissionTextLayout.width;
        float StartTextWidth = StartTextLayout.width;
        SmallFont.draw(batch, "Defeat Goodricke college to win!", (width-MissionTextWidth)/2, height * .5f);
		SmallFont.draw(batch, "Press any key to start", (width-StartTextWidth)/2, height * .4f);
    }

    public static void drawDifficultySelectionScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont){
        //title
        GlyphLayout TitleTextLayout = new GlyphLayout(LargeFont, "York Pirates");
        float TitleTextWidth = TitleTextLayout.width;
        LargeFont.draw(batch, "York Pirates", (width-TitleTextWidth)/2, height * .75f);

        GlyphLayout DifficultySelectionTextLayout = new GlyphLayout(SmallFont, "Difficulty Selection: ");
        GlyphLayout EasyTextLayout = new GlyphLayout(SmallFont, "Press 1 for EASY");
        GlyphLayout NormalTextLayout = new GlyphLayout(SmallFont, "Press 2 for NORMAL");
        GlyphLayout HardTextLayout = new GlyphLayout(SmallFont, "Press 2 for HARD");
        float DifficultySelectionTextWidth = DifficultySelectionTextLayout.width;
        float EasyTextWidth = EasyTextLayout.width;
        float NormalTextWidth = NormalTextLayout.width;
        float HardTextWidth = HardTextLayout.width;
        SmallFont.draw(batch, "Difficulty Selection: ", (width-DifficultySelectionTextWidth)/2, height * .575f);
        SmallFont.draw(batch, "Press 1 for EASY", (width-EasyTextWidth)/2, height * .5f);
        SmallFont.draw(batch, "Press 2 for NORMAL", (width-NormalTextWidth)/2, height * .45f);
        SmallFont.draw(batch, "Press 3 for HARD", (width-HardTextWidth)/2, height * .4f);
    }

    public static void drawEndScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont){
        GlyphLayout TitleText = new GlyphLayout(LargeFont, "You Win!");
        float TitleTextWidth = TitleText.width;
        LargeFont.draw(batch, "You Win!", (width-TitleTextWidth)/2, height * .5f);

        GlyphLayout StartTextLayout = new GlyphLayout(SmallFont, "Press esc to exit");
        float StartTextWidth = StartTextLayout.width;
        SmallFont.draw(batch, "Press esc to exit", (width-StartTextWidth)/2, height * .4f);
    }


    public static void drawGameOverScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont) {
        GlyphLayout TitleText = new GlyphLayout(LargeFont, "Game Over!");
        float TitleTextWidth = TitleText.width;
        LargeFont.draw(batch, "Game Over!", (width - TitleTextWidth) / 2, height * .5f);

        GlyphLayout StartTextLayout = new GlyphLayout(SmallFont, "Press SPACE to try again");
        float StartTextWidth = StartTextLayout.width;
        SmallFont.draw(batch, "Press SPACE to try again", (width - StartTextWidth) / 2, height * .4f);
    }

    public static void drawShopScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont){
        GlyphLayout TitleText = new GlyphLayout(LargeFont, "Shop");
        float TitleTextWidth = TitleText.width;
        LargeFont.draw(batch, "Shop", (width-TitleTextWidth)/2, height * 0.92f);

        GlyphLayout StartTextLayout = new GlyphLayout(SmallFont, "Press esc to exit");
        float StartTextWidth = StartTextLayout.width;
        SmallFont.draw(batch, "Press esc to exit", (width-StartTextWidth)/2, height * 0.2f);

        GlyphLayout Upgrade1TextLayout = new GlyphLayout(SmallFont, "Press 1 to increased fire rate (200 plunder) : " + Unity.getCannonCooldownSpeed() + "/5");
        float Upgrade1TextWidth = Upgrade1TextLayout.width;
        SmallFont.draw(batch, "Press 1 to increased fire rate (200 plunder) : " + Unity.getCannonCooldownSpeed() + "/5", (width * 0.35f), height * 0.8f);

        GlyphLayout Upgrade2TextLayout = new GlyphLayout(SmallFont, "Press 2 to repair ship by 30%: ");
        float Upgrade2TextWidth = Upgrade2TextLayout.width;
        SmallFont.draw(batch, "Press 2 to repair ship by 30% (200 plunder) : " + "Current health = " + Unity.getHealth(), (width * 0.35f), height * 0.75f);
    }
}
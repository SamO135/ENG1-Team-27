package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Unity;
import jdk.internal.org.jline.utils.DiffHelper;

public class gui {

    private static float width = Unity.getWidth();
    private static float height = Unity.getHeight();

    public static void drawMainScreen(Batch batch, BitmapFont font, BitmapFont SmallFont, int plunder, float score) {
    	
        GlyphLayout PlunderInfoTextLayout = new GlyphLayout(font, "Plunder: " + plunder);
        GlyphLayout ScoreInfoTextLayout = new GlyphLayout(font, "Score: " + Math.round(score));
        GlyphLayout HelpTextLayout = new GlyphLayout(font, "Help (H)");
        float PlunderInfoTextWidth = PlunderInfoTextLayout.width;
        float PlunderInfoTextHeight = PlunderInfoTextLayout.height;
        float ScoreInfoTextWidth = ScoreInfoTextLayout.width;
        float ScoreInfoTextHeight = ScoreInfoTextLayout.height;
        float HelpTextWidth = HelpTextLayout.width;

        batch.begin();
        font.draw(batch, "Plunder: " + plunder, Math.round(width -(PlunderInfoTextWidth*1.2)),
                Math.round(height-(PlunderInfoTextHeight*1.2)));
        font.draw(batch, "Score: " + Math.round(score), (width-ScoreInfoTextWidth) / 2, Math.round(height-(ScoreInfoTextHeight*1.2)));

        font.draw(batch, "Shop (esc)", (width * 0.025f), (height * 0.1f));
        font.draw(batch, "Help (H)", (width*0.975f - HelpTextWidth), height * 0.1f);
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

    public static void drawNewOrResumeGameScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont) {
        GlyphLayout TitleTextLayout = new GlyphLayout(LargeFont, "York Pirates");
        float TitleTextWidth = TitleTextLayout.width;
        LargeFont.draw(batch, "York Pirates", (width-TitleTextWidth)/2, height * .75f);

        GlyphLayout NewGameTextLayout = new GlyphLayout(SmallFont, "Press 1 for New Game");
        GlyphLayout ResumeGameTextLayout = new GlyphLayout(SmallFont, "Press 2 to Resume Game");
        GlyphLayout HelpTextLayout = new GlyphLayout(SmallFont, "Press H for Help");
        float NewGameTextWidth = NewGameTextLayout.width;
        float ResumeGameTextWidth = ResumeGameTextLayout.width;
        float HelpTextWidth = HelpTextLayout.width;
        SmallFont.draw(batch, "Press 1 for New Game", (width-NewGameTextWidth)/2, height * .575f);
        SmallFont.draw(batch, "Press 2 to Resume Game", (width-ResumeGameTextWidth)/2, height * .5f);
        SmallFont.draw(batch, "Press H for Help", (width-HelpTextWidth)/2, height * .425f);
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

    public static void drawShopScreen(Batch batch, BitmapFont SmallFont, BitmapFont MediumFont, BitmapFont LargeFont, Unity.Difficulty difficulty, int plunder){
        GlyphLayout ShopTitleText = new GlyphLayout(LargeFont, "Shop");
        float ShopTitleTextWidth = ShopTitleText.width;
        LargeFont.draw(batch, "Shop", (width-ShopTitleTextWidth)/2, height * 0.92f);

        GlyphLayout ExitTextLayout = new GlyphLayout(SmallFont, "Press esc to exit");
        float ExitTextWidth = ExitTextLayout.width;
        SmallFont.draw(batch, "Press esc to exit shop", (width-ExitTextWidth)/2, height * 0.2f);

        GlyphLayout PlunderTextLayout = new GlyphLayout(SmallFont, "Plunder: " + plunder);
        float PlunderTextWidth = PlunderTextLayout.width;
        float PlunderTextHeight = PlunderTextLayout.height;
        MediumFont.draw(batch, "Plunder: " + plunder, Math.round(width -(PlunderTextWidth*1.76)), Math.round(height-(PlunderTextHeight*1.8)));

        SmallFont.draw(batch, "200 plunder - Press 1 to increased fire rate : " + Unity.getCannonCooldownSpeed() + "/5", (width * 0.35f), height * 0.8f);

        SmallFont.draw(batch, "200 plunder - Press 2 to repair ship by 0.3 : " + "Current health = " + Unity.getHealth() + "/1.0", (width * 0.35f), height * 0.75f);

        SmallFont.draw(batch, "200 plunder - Press 3 to increase player bullet damage : " + "+ " + Math.round(Unity.getDamageUpgrade()*100) + "% (Max 100%)", (width * 0.35f), height * 0.7f);

        SmallFont.draw(batch, "200 plunder - Press 4 to increase player weather resistance : " + "+ " + Math.round(Unity.getWeatherResistanceUpgrade()*100) + "% (Max 50%)", (width * 0.35f), height * 0.65f);

        SmallFont.draw(batch, "100 plunder - Press 5 to increase player rotation speed : " + "+ " + Math.round(Unity.getPlayerRotationUpgrade()*100) + "% (Max 50%)", (width * 0.35f), height * 0.6f);

        MediumFont.draw(batch, "Save & Quit (Q)", (width * 0.025f), (height * 0.1f));

        MediumFont.draw(batch, "Difficulty: " + difficulty, (width * 0.025f), (height * 0.95f));

        GlyphLayout HelpTextLayout = new GlyphLayout(MediumFont, "Help (H)");
        float HelpTextWidth = HelpTextLayout.width;
        MediumFont.draw(batch, "Help (H)", (width*0.975f - HelpTextWidth), height * 0.1f);
    }

    public static void drawHelpScreen(Batch batch, BitmapFont SmallFont, BitmapFont LargeFont) {
        GlyphLayout HelpTitleText = new GlyphLayout(LargeFont, "Help");
        float HelpTitleTextWidth = HelpTitleText.width;
        LargeFont.draw(batch, "Help", (width-HelpTitleTextWidth)/2, height * 0.92f);

        String text1 = "- Increasing the difficulty increases enemy health and enemy damage \n \n" +
                "- Only 1 game can be saved at a time, starting a new game will not overwrite a previous save, only \n" +
                "when you 'Save & Quit (Q)' does a previous save get overwritten. \n \n" +
                "- Entering the shop pauses the game. \n \n \n \n \n" +
                "CONTROLS: \n" +
                "- WASD = move \n" +
                "- Left mouse click = shoot";
        GlyphLayout HelpText = new GlyphLayout(SmallFont, text1);
        float HelpTextWidth = HelpText.width;
        SmallFont.draw(batch, text1, (width-HelpTextWidth*.75f)/2, height*.75f);


        GlyphLayout ExitText = new GlyphLayout(SmallFont, "Exit (esc)");
        float ExitTextWidth = HelpTitleText.width;
        SmallFont.draw(batch, "Exit (esc)", (width-ExitTextWidth)/2, height * 0.2f);
    }
}
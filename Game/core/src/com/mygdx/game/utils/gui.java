package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.Unity;

public class gui {

    private static float width = Unity.getWidth();
    private static float height = Unity.getHeight();
    static float PlunderInfoTextWidth;
    static float PlunderInfoTextHeight;

    public static void update(Batch batch, BitmapFont font) {
        drawPlunder(batch, font);
    }

    public static void drawPlunder(Batch batch, BitmapFont font){
        GlyphLayout PlunderInfoTextLayout = new GlyphLayout(font, "Plunder: ");
        PlunderInfoTextWidth = PlunderInfoTextLayout.width;
        PlunderInfoTextHeight = PlunderInfoTextLayout.height;
        batch.begin();
        font.draw(batch, "Plunder: ", Math.round(width -(PlunderInfoTextWidth*1.2)),
                Math.round(height-(PlunderInfoTextHeight*1.2)));
        batch.end();

    }
}
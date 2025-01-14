
package com.mygdx.game.CameraUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.mygdx.game.utils.Constants.PPM;

public class cam {

    /** Updates the camera position to keep it centred on the player
     * @param delta The time in seconds since the last update
     * @param camera The camera to update
     * @param position The position of the camera
     * */
    public static void cameraUpdate(float delta, OrthographicCamera camera, Vector2 position) {
        Vector3 cameraPosition = camera.position;

        cameraPosition.x = cameraPosition.x + (position.x - cameraPosition.x) * 0.1f * PPM;
        cameraPosition.y = cameraPosition.y + (position.y - cameraPosition.y) * 0.1f * PPM;
        camera.position.set(cameraPosition);

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            camera.zoom -= 0.03f;
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            camera.zoom += 0.03f;
        }
        if(camera.zoom > 3.55f){
            camera.zoom = 3.55f;
        }
        if(camera.zoom < 1f){
            camera.zoom = 1f;
        }
        camera.update();
    }

    public static void boundry(OrthographicCamera camera, float width, float height){
        Vector3 position = camera.position;

        Vector2 camMin = new Vector2(camera.viewportWidth, camera.viewportHeight);
        camMin.scl(camera.zoom/2); //bring to center and scale by the zoom level
        Vector2 camMax = new Vector2(width, height);
        camMax.sub(camMin); //bring to center

        //keep camera within borders
        position.x = Math.min(camMax.x, Math.max(position.x, camMin.x));
        position.y = Math.min(camMax.y, Math.max(position.y, camMin.y));

        camera.position.set(position.x, position.y, camera.position.z);
        camera.update();
    }
}

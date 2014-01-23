package java_source;

import clojure.lang.AFunction;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by jacob on 1/21/14.
 */
public class Game implements ApplicationListener
{
    public static int HEIGHT;
    public static int WIDTH;

    OrthographicCamera camera;

    AFunction render_fn;

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Game(AFunction render_fn)
    {
        this.render_fn = render_fn;
    }

    public void init(OrthographicCamera cam, InputProcessor inputProcessor)
    {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        this.camera = cam;
        Gdx.input.setInputProcessor(inputProcessor);
        cam.translate(WIDTH / 2, HEIGHT / 2);
        cam.update();
    }

    public void reLoad(AFunction render_fn)
    {
        this.render_fn = render_fn;
    }

    @Override
    public void create()
    {

    }

    @Override
    public void resize(int i, int i2) {

    }

    @Override
    public void render() {
        render_fn.call();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

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
    public int HEIGHT;
    public int WIDTH;

    OrthographicCamera camera;
    InputProcessor inputProcessor;
    AFunction render_fn;


    public Game(AFunction render_fn, InputProcessor inputProcessor)
    {
        this.render_fn = render_fn;
        camera = new OrthographicCamera();
        this.inputProcessor = inputProcessor;
    }

    //for use when debug mode is enabled
    public void reLoad(AFunction render_fn, OrthographicCamera camera, InputProcessor inputProcessor)
    {
        try
        {
            this.render_fn = render_fn;
            this.camera = camera;
            Gdx.input.setInputProcessor(inputProcessor);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void create()
    {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        camera.translate(WIDTH / 2, HEIGHT / 2);
        camera.update();

        Gdx.input.setInputProcessor(inputProcessor);
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

    //GETTERS
    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}

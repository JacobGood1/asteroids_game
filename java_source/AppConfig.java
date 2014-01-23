package java_source;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by jacob on 1/21/14.
 */
public class AppConfig extends LwjglApplicationConfiguration
{
    public AppConfig()
    {
        this.title = "floating space rocks";
        this.width = 500;
        this.height = 500;
        this.useGL20 = false;
        this.resizable = false;
    }
}

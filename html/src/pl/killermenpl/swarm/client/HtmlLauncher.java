package pl.killermenpl.swarm.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import pl.killermenpl.swarm.SwarmGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration config = new GwtApplicationConfiguration(800, 600);
                config.preferFlash = false;
                return config;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new SwarmGame();
        }
}
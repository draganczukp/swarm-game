package pl.killermenpl.swarm;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SwarmGame extends ApplicationAdapter {
	
	public static Array<Vector2> targets;
	public static float targetChance = 0.1f;
	
	public static Array<Particle> particles;
	public static float particleChance = 1;
	
	public static final Color background = new Color(.2f,.2f,.2f,1);
	public static Vector2 mouse;
	private ShapeRenderer sr;
	@Override
	public void create () {

		sr = new ShapeRenderer();
		
		targets = new Array<Vector2>();
		targets.add(new Vector2(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight())));
		
		particles = new Array<Particle>();
		particles.add(new Particle(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight()), targets.random()));
		
		mouse = new Vector2();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mouse.set(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
		
		if(MathUtils.randomBoolean(targetChance/100)){
			targets.add(new Vector2(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight())));
		}
		
		if(MathUtils.randomBoolean(particleChance/100)){
			if(targets.random()!=null)
				particles.add(new Particle(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight()), targets.random()));
		}
		
		for(Particle p : particles){
			p.update();
			if(p.ttl <= 0)
				particles.removeValue(p, true);
		}
		

		
		sr.setAutoShapeType(true);
		sr.begin();
		sr.set(ShapeType.Filled);

		sr.setColor(Color.GREEN);
		for(Vector2 t : targets){
			sr.circle(t.x, t.y, 4);
		}

		sr.setColor(0.9f, 0.9f, 0.9f, 1);
		for(Particle p : particles){
			p.draw(sr);
		}
		
		
		sr.setColor(Color.YELLOW);
		sr.circle(mouse.x, mouse.y, 5);
		sr.end();
	}
	
	@Override
	public void dispose () {
		
	}
}

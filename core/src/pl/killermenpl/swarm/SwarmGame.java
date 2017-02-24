package pl.killermenpl.swarm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import pl.killermenpl.swarm.lib.Libs;

public class SwarmGame extends ApplicationAdapter implements InputProcessor {

	public static Array<Vector2> targets;
	public static float targetChance;

	public static Array<Particle> particles;
	public static float particleChance;

	public static final Color background = new Color(.2f, .2f, .2f, 1);
	public static Vector2 mouse;
	private ShapeRenderer sr;

	public SpriteBatch batch;

	public static boolean hit;
	private BitmapFont fnt;

	@Override
	public void create() {
		targetChance = 0.3f;
		particleChance = 2;

		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();

		sr = new ShapeRenderer();

		targets = new Array<Vector2>();
		targets.add(new Vector2(MathUtils.random(Gdx.graphics.getWidth() - 200) + 100,
				MathUtils.random(Gdx.graphics.getHeight() - 100) + 50));

		particles = new Array<Particle>();
		spawnParticle();
		mouse = new Vector2();

		Score.init();

		fnt = new BitmapFont(Gdx.files.internal("Aileron-Regular.fnt"));

		hit = false;

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (hit) {
			batch.begin();
			GlyphLayout go = new GlyphLayout(fnt, "Game Over");

			fnt.draw(batch, go, Gdx.graphics.getWidth() / 2 - (go.width / 2),
					Gdx.graphics.getHeight() / 2 + (go.height));

			GlyphLayout f5 = new GlyphLayout(fnt, "Press any key to restart");
			fnt.draw(batch, f5, Gdx.graphics.getWidth() / 2 - (f5.width / 2),
					Gdx.graphics.getHeight() / 2 - (f5.height / 2));
			batch.end();

			return;
		}

		mouse.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

		if (MathUtils.randomBoolean(targetChance / 100)) {
			spawnTarget();
		}

		if (MathUtils.randomBoolean(particleChance / 100)) {
			spawnParticle();
		}

		for (Particle p : particles) {
			p.update();
			if (p.ttl <= 0)
				particles.removeValue(p, true);
		}

		sr.setAutoShapeType(true);
		sr.begin();
		sr.set(ShapeType.Filled);

		sr.setColor(Color.BLUE);
		for (Vector2 t : targets) {
			sr.circle(t.x, t.y, 4);
		}

		sr.setColor(0.9f, 0.9f, 0.9f, 1);
		for (Particle p : particles) {
			p.draw(sr);
		}

		// sr.setColor(Color.YELLOW);
		// sr.circle(mouse.x, mouse.y, 5);
		sr.end();

		Score.update();

		batch.begin();
		Score.draw(batch);
		batch.end();

		Particle.maxSpeed = Libs.map(Score.score, 0, 1000, 10, 2);
	}

	private void spawnParticle() {
		if (targets.random() == null)
			return;

		Particle p;
		float dist;
		do {
			p = new Particle(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight()),
					targets.random());
			dist = p.pos.dst(p.target);
		} while (dist < 100);
		particles.add(p);
	}

	private void spawnTarget() {
		if (targets.random() == null) {
			targets.add(
					new Vector2(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight())));
			return;
		}

		Vector2 target;
		float distance = Float.MAX_VALUE;
		do {
			target = new Vector2(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight()));

			for (Vector2 t : targets) {
				float d = target.dst(t);
				if (d < distance) {
					distance = d;
				}
			}

		} while (distance >= 100);

		targets.add(target);
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean keyDown(int keycode) {

		this.create();

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}

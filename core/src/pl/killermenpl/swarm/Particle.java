package pl.killermenpl.swarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import pl.killermenpl.swarm.lib.Libs;

public class Particle {
	public Vector2 pos, vel, acc;
	private Vector2 _vel;
	private Vector2 target;
	private static float r = 4f;
	private static float maxSpeed = 5;
	private static float maxForce = 1;
	public float ttl = 5;
	private Color c;
	
	
	public Particle(float x, float y, Vector2 target) {
		pos = new Vector2(x, y);
		vel = new Vector2();
		acc = new Vector2();
		_vel = new Vector2();
		this.target = target;
		c = new Color(Color.GREEN);
	}

	public void update() {
		applyForce(arrive());
		applyForce(flee(SwarmGame.mouse));

		vel.add(acc);
		acc.setZero();

		_vel.set(vel);
		// _vel.scl(Gdx.graphics.getDeltaTime());

		pos.add(vel);
		
		
		ttl -= Gdx.graphics.getDeltaTime();
		
		updateColor();
	}

	private void updateColor(){
		c.r = Libs.map(ttl, 5, 0, 0, 1);
		c.g = Libs.map(ttl, 5, 0, 1, 0);
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(c);
		sr.circle(pos.x, pos.y, r);
	}

	public void applyForce(Vector2 force) {
		this.acc.add(force);
	}

	public Vector2 arrive() {
		Vector2 desired = Vector2.X.set(target).sub(pos);
		float d = desired.len();

		float speed = maxSpeed;

		if (d < 500) {
			speed = Libs.map(d, 0, 500, 0, maxSpeed);
		}
		desired.setLength(speed);
		Vector2 steer = Vector2.Y.set(desired).sub(vel);
		steer.limit(maxForce);
		return steer;
	}

	public Vector2 flee(Vector2 target) {
		Vector2 desired = Vector2.X.set(target).sub(pos);
		float d = desired.len();

		float speed = maxSpeed;
//		System.out.println(d);
		if (d < 100) {
//			System.out.println(d);
			speed = Libs.map(d, 0, 100, 0, maxSpeed);
			desired.setLength(speed);
			desired.scl(-1);
			Vector2 steer = Vector2.Y.set(desired).sub(vel);
			steer.limit(maxForce*3);
			return steer;
		}
		return Vector2.Zero;
	}
}

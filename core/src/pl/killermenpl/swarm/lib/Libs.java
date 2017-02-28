package pl.killermenpl.swarm.lib;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Libs {
	public static float map(float value, float start1, float stop1, float start2, float stop2){
		return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
	}
	
	public static float avg(float... fs){
		float r = 0;
		
		for (float f : fs) {
			r += f;
		}
		
		r /= fs.length;
		
		return r;
	}

	public static Vector2 avg(Array<Vector2> items) {
		Vector2 out = new Vector2();
		
		for(Vector2 v : items){
			out.add(v);
		}
		
		out.x /= items.size;
		out.y /= items.size;
		return out;
	}
}

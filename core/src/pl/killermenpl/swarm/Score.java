package pl.killermenpl.swarm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
	public static BitmapFont font;
	public static float score = 0;
	
	
	public static void init(){
		font = new BitmapFont();
		score = 0;
	}
	
	public static void update(){
		score += Gdx.graphics.getDeltaTime();
	}
	
	public static void draw(SpriteBatch batch){
		font.draw(batch, "Score: "+Math.floor(score), 10, Gdx.graphics.getHeight() - 10);
	}
	
	
}

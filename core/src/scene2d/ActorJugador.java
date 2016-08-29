package scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorJugador extends Actor{
	
	private Texture jugadorTexture;
	private boolean alive;
	
	public ActorJugador(Texture jugador)
	{
		this.jugadorTexture = jugador;
		this.alive = true;
		setSize(jugador.getWidth(), jugador.getHeight());
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(jugadorTexture, getX(), getY());
	}
	

	public boolean isAlive() { return alive; }
	public void setAlive(boolean valor) { this.alive = valor;}
}

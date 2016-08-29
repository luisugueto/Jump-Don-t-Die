package scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gameprosalt.BaseScreen;
import com.gameprosalt.MyGame;

public class MyGameScreen extends BaseScreen{

	private Stage stage;
	private ActorJugador actorJugador;
	private ActorPinchos actorPinchos;
	private Texture jugadorTexture;
	private Texture pinchosTexture;
	private TextureRegion pinchosRegion;
	
	public MyGameScreen(MyGame game) {
		super(game);
		jugadorTexture = new Texture("player.png");
		pinchosTexture = new Texture("spike.png");
		pinchosRegion = new TextureRegion(pinchosTexture, 0,0,128,64);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		stage.setDebugAll(true);
		
		actorJugador = new ActorJugador(jugadorTexture);
		actorPinchos = new ActorPinchos(pinchosRegion);
		stage.addActor(actorJugador);
		stage.addActor(actorPinchos);
		actorJugador.setPosition(20, 100);
		actorPinchos.setPosition(500, 100);
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		stage.dispose();
		jugadorTexture.dispose();
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		comprobarColisiones(actorJugador, actorPinchos);
		stage.draw();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		jugadorTexture.dispose();
	}
	
	private void comprobarColisiones(Actor uno, Actor dos){
		if(actorJugador.isAlive() && (uno.getX() + uno.getWidth() > dos.getX()))
		{
			System.out.println("hay colision");
			actorJugador.setAlive(false);
		}
	}
	
}

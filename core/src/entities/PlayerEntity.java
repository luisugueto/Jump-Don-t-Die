package entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gameprosalt.Constants;

public class PlayerEntity extends Actor{

	private Texture texture;
	private World world;
	private Body body;
	private Fixture fixture;
	private boolean alive = true, jumping = false;
	private boolean mustJump = false;
	private boolean debeSaltar, saltando, jugadorVivo = true;
	
	
	public PlayerEntity(World world, Texture texture, Vector2 position) {
		this.world = world;
		this.texture = texture;
		
		BodyDef def = new BodyDef();
		def.position.set(position);
		def.type = BodyType.DynamicBody;
		body = world.createBody(def);
		
		PolygonShape box = new PolygonShape();
		//dimensiones en metros
		box.setAsBox(0.5f, 0.5f);
		fixture = body.createFixture(box, 1);
		fixture.setUserData("player");
		box.dispose();
		setSize(Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		setPosition((body.getPosition().x - 0.5f) * Constants.PIXELS_IN_METER, 
					(body.getPosition().y - 0.5f) * Constants.PIXELS_IN_METER);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if(Gdx.input.justTouched() || mustJump)
		{
			mustJump = false;
			jump();
		}
		if(alive){
			float speedY = body.getLinearVelocity().y;
			body.setLinearVelocity(Constants.PLAYER_SPPED, speedY);
		}
		if (jumping) {
			body.applyForceToCenter(0, -Constants.IMPULSE_JUMP * 1.25f, true);
		}
	}
	public void jump()
	{
		if(!jumping && alive)
		{
			
			jumping = true;
			Vector2 positionVector2 = body.getPosition();
			body.applyLinearImpulse(0, 12, positionVector2.x, positionVector2.y, true);
		}
	}
	
	public void detach()
	{
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}
	
	public void setAlive(Boolean valor) { this.alive = valor;}
	public Boolean isAlive() { return this.alive;}
	public void setMustJump(Boolean valor) { this.mustJump = valor;}
	public void setJumping(Boolean valor) { this.jumping = valor;}
}

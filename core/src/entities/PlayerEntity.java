package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
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
	private boolean alive = true, jumping = true;
	
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
	
	public void detach()
	{
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}
}
package com.gameprosalt;

import java.sql.Time;

import jdk.nashorn.internal.objects.Global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen{

	private World world;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private Body jugadorBody, sueloBody, pinchoBody, piedraBody;
	private Fixture jugadorFixture, sueloFixture, pinchoFixture, piedraFixture;
	private boolean debeSaltar, saltando, jugadorVivo = true;
	
	public Box2DScreen(MyGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		world = new World(new Vector2(0, -10), true);
		renderer = new Box2DDebugRenderer();
		//dimensiones en metro
		camera = new OrthographicCamera(16, 9);
		camera.translate(0,1);
		
		world.setContactListener(new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
				if (fixtureA == jugadorFixture && fixtureB == sueloFixture) {
					saltando = true;
				}
				if (fixtureA == sueloFixture && fixtureB == jugadorFixture) {
					saltando = true;
				}
			}
			
			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
				
				if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor")) 
						|| (fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player"))) {
					if (Gdx.input.isTouched()) {
						debeSaltar = true;
					}
					saltando = false;
				}
				
				if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("spike")) 
						|| (fixtureA.getUserData().equals("spike") && fixtureB.getUserData().equals("player"))) {
						jugadorVivo = false;
				}	
			}
		});
		
		jugadorBody = world.createBody(createJugadorDef());
		sueloBody = world.createBody(createSueloDef());
		pinchoBody = world.createBody(createPinchoDef(5f));
		piedraBody = world.createBody(createPiedraDef());
		
		PolygonShape jugadorPolygonShape = new PolygonShape();
		//dimensiones en metros
		jugadorPolygonShape.setAsBox(0.5f, 0.5f);
		jugadorFixture = jugadorBody.createFixture(jugadorPolygonShape, 1);
		jugadorFixture.setUserData("player");
		jugadorPolygonShape.dispose();
		
		PolygonShape sueloPolygonShape = new PolygonShape();
		//dimensiones en metros
		sueloPolygonShape.setAsBox(500, 1);
		sueloFixture = sueloBody.createFixture(sueloPolygonShape, 1);
		sueloFixture.setUserData("floor");
		sueloPolygonShape.dispose();
		
		CircleShape piedraCircleShape = new CircleShape();
		piedraCircleShape.setRadius(0.2f);
		//piedraFixture = piedraBody.createFixture(piedraCircleShape, 1);
		//piedraCircleShape.dispose();
		
		pinchoFixture = createPinchoFixture(pinchoBody);
		pinchoFixture.setUserData("spike");
		
	}
	
	private BodyDef createPiedraDef() {
		BodyDef def = new BodyDef();
		def.position.set(0, 10);
		def.type = BodyType.DynamicBody;
		return def;
	}
	
	private BodyDef createJugadorDef() {
		BodyDef def = new BodyDef();
		def.position.set(0,1);
		def.type = BodyType.DynamicBody;
		return def;
	}
	
	private BodyDef createSueloDef() {
		BodyDef def = new BodyDef();
		def.position.set(0,-1);
		return def;
	}
	
	private BodyDef createPinchoDef(float x) {
		BodyDef def = new BodyDef();
		def.position.set(x, 0.5f);
		return def;
	}
	
	private Fixture createPinchoFixture(Body pinchoBody) {
		Vector2[] vertices = new Vector2[3];
		vertices[0] = new Vector2(-0.5f, -0.5f);
		vertices[1] = new Vector2(0.5f, -0.5f);
		vertices[2] = new Vector2(0, 0.5f);
		
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		Fixture fix = pinchoBody.createFixture(shape, 1);
		shape.dispose();
		return fix;
	}
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		jugadorBody.destroyFixture(jugadorFixture);
		sueloBody.destroyFixture(sueloFixture);
		pinchoBody.destroyFixture(pinchoFixture);
		piedraBody.destroyFixture(piedraFixture);
		world.destroyBody(piedraBody);
		world.destroyBody(jugadorBody);
		world.destroyBody(sueloBody);
		world.destroyBody(pinchoBody);
		world.dispose();
		renderer.dispose();
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub		
		if (debeSaltar) {
			debeSaltar = false;
			saltar();
		}
		if(Gdx.input.justTouched() && !saltando)
		{
			debeSaltar = true;
		}
		if (jugadorVivo) {
			float velocityY = jugadorBody.getLinearVelocity().y;
			jugadorBody.setLinearVelocity(5, velocityY);
		}
		world.step(delta, 6, 2);
		camera.update();
		renderer.render(world, camera.combined);
	}
	
	private void saltar()
	{
		Vector2 position = jugadorBody.getPosition();
		jugadorBody.applyLinearImpulse(0, 6, position.x, position.y, true);
	}
}

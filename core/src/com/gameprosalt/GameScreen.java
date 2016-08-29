package com.gameprosalt;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entities.FloorEntity;
import entities.PlayerEntity;
import entities.SpikeEntity;

public class GameScreen extends BaseScreen{

	private Stage stage;
	private World world;
	private PlayerEntity playerEntity;
	private FloorEntity floorEntity;
	private ArrayList<FloorEntity> floorlist = new ArrayList<FloorEntity>();
	private ArrayList<SpikeEntity> spikelist = new ArrayList<SpikeEntity>();
	private Music music;	
	private Sound die, jump;
	
	public GameScreen(MyGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		stage = new Stage(new FitViewport(640, 340));
		world = new World(new Vector2(0, -10), true);
		music = game.getManager().get("song.mp3");
		die = game.getManager().get("die.ogg");
		jump = game.getManager().get("jump.ogg");
	
		world.setContactListener(new ContactListener() {
			
			private boolean areColliled(Contact contact, Object userA, Object userB)
			{
					return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
							(contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
			}
			
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
				
			}
			
			@Override
			public void beginContact(Contact contact) {
				if(areColliled(contact, "player", "floor"))
				{
					playerEntity.setJumping(false);
					if (Gdx.input.isTouched()) {
						jump.play();
						playerEntity.setMustJump(true);
					}
				}
				if(areColliled(contact, "player", "spike"))
				{
					if(playerEntity.isAlive()) {
						die.play();
						playerEntity.setAlive(false);
						System.out.println("GAME OVER");
					}
				}
				
			}
		});
		
	}
	
	@Override
	public void show() {
		game.getManager();
		Texture playerTexture = game.getManager().get("player.png");
		Texture floorTexture = game.getManager().get("floor.png");
		Texture overfloorTexture = game.getManager().get("overfloor.png");
		Texture spikeTexture = game.getManager().get("spike.png");
		
		floorlist.add(new FloorEntity(world, floorTexture, overfloorTexture, 0, 1000, 1));
	
		for(int i = 50; i < 1000; i+=30)
			spikelist.add(new SpikeEntity(world, spikeTexture, i, 1));
		
		
		playerEntity = new PlayerEntity(world, playerTexture, new Vector2(1.5f, 1.5f));
		stage.addActor(playerEntity);

		for(FloorEntity floor : floorlist)
		{
			stage.addActor(floor);
		}
		
		for(SpikeEntity spike : spikelist)
		{
			stage.addActor(spike);
		}
	}
	
	@Override
	public void hide() {
		music.stop();
		// TODO Auto-generated method stub
		playerEntity.detach();
		playerEntity.remove();
		for(FloorEntity floor : floorlist)
		{
			floor.detach();
			floor.remove();
		}
		
		for(SpikeEntity spike : spikelist)
		{
			spike.detach();
			spike.remove();
		}
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		world.dispose();
	}
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.justTouched() && playerEntity.isAlive()) {
			jump.play();
		}
		music.setVolume(0.75f);
		music.play();
		if (playerEntity.getX() > 100 && playerEntity.isAlive()) {
			stage.getCamera().translate(Constants.PLAYER_SPPED * delta * Constants.PIXELS_IN_METER, 0, 0);
		}
		stage.act();	
		world.step(delta, 6, 2);
		stage.draw();
	}
	
}

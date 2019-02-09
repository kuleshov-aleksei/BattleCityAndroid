package com.encamy.battlecity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

import javax.swing.text.View;

import sun.rmi.runtime.Log;

public class BattleCity extends ApplicationAdapter
{
	//SpriteBatch batch;
	private OrthographicCamera m_camera;
	private Viewport m_viewport;
	private TiledMap m_tileMap;
    private OrthogonalTiledMapRenderer m_renderer;

    private Player m_player;

	private static final float FRAME_DURATION = 0.20f;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final float SCALE = 10;//WORLD_TO_STAGE_SCALE

	@Override
	public void create ()
    {
		//batch = new SpriteBatch();

		m_camera = new OrthographicCamera();
		m_camera.setToOrtho(false, WIDTH, HEIGHT);

		m_tileMap = new TmxMapLoader().load("general_map.tmx");
		m_renderer = new OrthogonalTiledMapRenderer(m_tileMap);
        m_renderer.setView(m_camera);

		TextureAtlas atlas = load_atlas(m_tileMap);

		Animation left = new Animation(FRAME_DURATION, atlas.findRegions("yellow1_left"));
		Animation top = new Animation(FRAME_DURATION, atlas.findRegions("yellow1_top"));
		Animation right = new Animation(FRAME_DURATION, atlas.findRegions("yellow1_right"));
		Animation bottom = new Animation(FRAME_DURATION, atlas.findRegions("yellow1_bottom"));
		left.setPlayMode(Animation.PlayMode.LOOP);
		top.setPlayMode(Animation.PlayMode.LOOP);
		right.setPlayMode(Animation.PlayMode.LOOP);
		bottom.setPlayMode(Animation.PlayMode.LOOP);

		Vector2 spawnpoint = new Vector2();
		for (MapObject object : m_tileMap.getLayers().get("Objects").getObjects())
		{
			if (object instanceof RectangleMapObject &&
				object.getProperties().containsKey("type") &&
				object.getProperties().get("type", String.class).equals("spawn_player1"))
			{
				spawnpoint.x = ((RectangleMapObject)object).getRectangle().getX();
				spawnpoint.y = ((RectangleMapObject)object).getRectangle().getY();
			}
		}

		MapObjects walls  = m_tileMap.getLayers().get("Collisions").getObjects();
		
		m_player = new Player(left, top, right, bottom, walls);
		m_player.setPosition(spawnpoint.x, spawnpoint.y);

		Gdx.input.setInputProcessor(m_player);
	}

	@Override
	public void render ()
    {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		m_camera.update();

		m_renderer.getBatch().begin();
		m_player.draw(m_renderer.getBatch());
		m_renderer.getBatch().end();

        m_renderer.render();
	}
	
	@Override
	public void dispose ()
    {
		m_tileMap.dispose();
		m_renderer.dispose();
	}

	@Override
	public void pause()
    {
		super.pause();
	}

	@Override
	public void resume()
    {
		super.resume();
	}

	private TextureAtlas load_atlas(TiledMap tiledMap)
	{
		TextureAtlas atlas = new TextureAtlas();

		Iterator<TiledMapTile> tiles = tiledMap.getTileSets().getTileSet("entities").iterator();
		while (tiles.hasNext())
		{
			TiledMapTile tile = tiles.next();
			if (tile.getProperties().containsKey("tank_name") && tile.getProperties().containsKey("tank_type") && tile.getProperties().containsKey("direction"))
			{
					String tank_name = tile.getProperties().get("tank_name", String.class);
					String tank_type = tile.getProperties().get("tank_type", String.class);
					String direction = tile.getProperties().get("direction", String.class);

					atlas.addRegion(tank_name + tank_type + "_" + direction, tile.getTextureRegion());
			}
		}

		return atlas;
	}
}

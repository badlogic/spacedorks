package com.badlogicgames.spacedorks.utils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RoomGeneratorApp extends ApplicationAdapter {
	SpriteBatch batch;
	Skin skin;
	Stage ui;
	OrthographicCamera cam;
	TextureRegion[][] tiles;
	private CameraController camController;	

	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth() / 16f, Gdx.graphics.getHeight() / 16f);
		Texture tileTexture = new Texture("tiles.png");
		tiles = TextureRegion.split(tileTexture, 16, 16);		
		createUI();
		setupInput();
	}

	private void setupInput() {
		InputMultiplexer multiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(multiplexer);
		multiplexer.addProcessor(ui);
		camController = new CameraController(cam);
		multiplexer.addProcessor(new GestureDetector(camController));
		multiplexer.addProcessor(camController);
	}

	private void createUI() {
		skin = new Skin(Gdx.files.internal("ui/default/uiskin.json"));
		ui = new Stage(new ScreenViewport(), batch);
		Table table = new Table();
		table.setFillParent(true);
		table.top().left();
		ui.addActor(table);

		TextButton generate = new TextButton("Generate", skin);
		generate.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Generating new map...");
			}
		});
		table.add(generate);
	}

	@Override
	public void resize(int width, int height) {
		ui.getViewport().update(width, height, true);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ui.act();
		ui.draw();

		camController.update();
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		renderMap();
		batch.end();
	}

	private void renderMap() {
		batch.draw(tiles[0][0], 0, 0, 1, 1);
	}
}

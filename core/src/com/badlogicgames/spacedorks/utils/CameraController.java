package com.badlogicgames.spacedorks.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraController extends InputAdapter implements GestureListener {
	OrthographicCamera camera;
	float velX, velY;
	boolean flinging = false;
	float initialScale = 1;
	Vector3 tmp = new Vector3();
	Vector3 tmp2 = new Vector3();

	public CameraController(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	public boolean touchDown(float x, float y, int pointer, int button) {
		flinging = false;
		initialScale = camera.zoom;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {		
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {		
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {		
		flinging = true;
		tmp2.setZero();
		camera.unproject(tmp2);
		tmp.set(velocityX, velocityY, 0);
		camera.unproject(tmp);
		tmp.sub(tmp2);
		velX = velocityX * 0.005f;
		velY = velocityY * 0.005f;
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		tmp2.setZero();
		camera.unproject(tmp2);
		tmp.set(deltaX, deltaY, 0);
		camera.unproject(tmp);
		tmp.sub(tmp2);
		camera.position.add(-tmp.x, -tmp.y, 0);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {		
		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {
		float ratio = originalDistance / currentDistance;
		camera.zoom = initialScale * ratio;
		System.out.println(camera.zoom);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {
		return false;
	}

	public void update() {
		if (flinging) {
			velX *= 0.90f;
			velY *= 0.90f;
			camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY * Gdx.graphics.getDeltaTime(), 0);
			if (Math.abs(velX) < 0.01f)
				velX = 0;
			if (Math.abs(velY) < 0.01f)
				velY = 0;
		}
	}

	@Override
	public boolean scrolled(int amount) {
		camera.zoom += amount * 0.01f;
		return false;
	}
}
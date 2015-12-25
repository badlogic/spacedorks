package com.badlogicgames.spacedorks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogicgames.spacedorks.SpaceDorks;
import com.badlogicgames.spacedorks.utils.RoomGeneratorApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RoomGeneratorApp(), config);
	}
}

package com.github.burgerguy.hudtweaks.util.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.math.Matrix4f;

public class MatrixCache {
	private final Map<String, Matrix4f> matrixMap = new HashMap<>();
	
	public Matrix4f getMatrix(String identifier) {
		return matrixMap.get(identifier);
	}
	
	public void putMatrix(String identifier, Matrix4f matrix) {
		matrixMap.put(identifier, matrix);
	}
	
	/**
	 * Each HudElement can have multiple update events which determine if the matrix should be updated.
	 * TODO: figure out a way to make these not hardcoded, and make the checks not in InGameHudMixin.
	 */
	public enum UpdateEvent {
		ON_RENDER,
		ON_HEALTH_ROWS_CHANGE,
		ON_SCREEN_BOUNDS_CHANGE,
		ON_RIDING_HEALTH_ROWS_CHANGE,
		ON_OFFHAND_STATUS_CHANGE,
		ON_STATUS_EFFECTS_CHANGE,
		ON_HOTBAR_ATTACK_INDICATOR_CHANGE
		// TODO: add update events for exp/jump bar switch, food/ridable health switch, etc.
	}
}

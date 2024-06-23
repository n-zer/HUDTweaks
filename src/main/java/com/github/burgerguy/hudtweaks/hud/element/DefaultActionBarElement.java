package com.github.burgerguy.hudtweaks.hud.element;

import com.github.burgerguy.hudtweaks.hud.HTIdentifier;
import com.github.burgerguy.hudtweaks.mixin.InGameHudAccessor;
import com.github.burgerguy.hudtweaks.util.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Matrix4f;

public class DefaultActionBarElement extends HudElement {
	public static final HTIdentifier IDENTIFIER = new HTIdentifier(Util.MINECRAFT_MODID, new HTIdentifier.ElementId("actionbar", "hudtweaks.element.actionbar"));
	private static final float Y_OFFSET = -4.0F;

	public DefaultActionBarElement() {
		super(IDENTIFIER, "onActionBarChange");
	}
	
	@Override
	protected float calculateWidth(MinecraftClient client) {
		if (((InGameHudAccessor) client.inGameHud).getActionBarRemaining() - Util.getTrueTickDelta(client) > 160.0F / 255.0F) {
			Text actionBarText = ((InGameHudAccessor) client.inGameHud).getActionBarText();
			if (actionBarText != null) {
				return client.textRenderer.getWidth(actionBarText) - 1; // compensate for lack of shadow
			}
		}
		return 14; // same default size as tooltip
	}
	
	@Override
	protected float calculateHeight(MinecraftClient client) {
		return client.textRenderer.fontHeight - 1; // compensate for lack of shadow
	}
	
	@Override
	protected float calculateDefaultX(MinecraftClient client) {
		return (client.getWindow().getScaledWidth() - getWidth()) / 2.0F;
	}
	
	@Override
	protected float calculateDefaultY(MinecraftClient client) {
		return client.getWindow().getScaledHeight() - 68.0F + Y_OFFSET;
	}

	@Override
	// TODO: X scaling is weird here, scales from middle rather than left side
	protected void createMatrix() { // TODO: rotation broken on this, also has weird offset
		Quaternionf quaternion = new Quaternionf(new AxisAngle4f(rotationDegrees, 0, 0, 1));
		Matrix4f matrix = new Matrix4f().translate(getX(), getY(), 0);
		matrix.translate(getXRotationAnchor() * getWidth(), getYRotationAnchor() * getHeight(), 0);
		matrix.rotate(quaternion);
		matrix.translate(-getXRotationAnchor() * getWidth(), -getYRotationAnchor() * getHeight(), 0);
		matrix.translate(-getDefaultX(), -getDefaultY() + Y_OFFSET, 0);
		matrix.scale(xScale, yScale, 1);
		matrix.translate(0, -Y_OFFSET, 0);
		cachedMatrix = matrix;
	}
}

package com.github.burgerguy.hudtweaks.hud;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.burgerguy.hudtweaks.hud.element.DefaultAirEntry;
import com.github.burgerguy.hudtweaks.hud.element.DefaultArmorEntry;
import com.github.burgerguy.hudtweaks.hud.element.DefaultExperienceBarEntry;
import com.github.burgerguy.hudtweaks.hud.element.DefaultHealthEntry;
import com.github.burgerguy.hudtweaks.hud.element.DefaultHotbarEntry;
import com.github.burgerguy.hudtweaks.hud.element.HudElementEntry;
import com.github.burgerguy.hudtweaks.hud.element.HudElementType;
import com.github.burgerguy.hudtweaks.hud.tree.RelativeTreeRootScreen;
import com.github.burgerguy.hudtweaks.hud.element.DefaultHungerEntry;
import com.github.burgerguy.hudtweaks.hud.element.DefaultJumpBarEntry;
import com.github.burgerguy.hudtweaks.hud.element.DefaultMountHealthEntry;
import com.github.burgerguy.hudtweaks.hud.element.DefaultStatusEffectsEntry;
import com.github.burgerguy.hudtweaks.util.Util;
import com.google.gson.JsonElement;

/**
 * We maintain two maps in this class. The first one is a map of raw
 * identifiers to elements, and is used with matrix application for
 * speed. The second is a map from element types to groups of that
 * type of element. It is used for basically anything else and manages
 * element replacements by mods that implement the API.
 */
public class ElementRegistry {
	private final Map<HTIdentifier.ElementType, HudElementType> elementGroupMap = new HashMap<>();
	
	public void init() {
		addEntry(new DefaultHotbarEntry());
		addEntry(new DefaultExperienceBarEntry());
		addEntry(new DefaultJumpBarEntry());
		addEntry(new DefaultArmorEntry());
		addEntry(new DefaultHealthEntry());
		addEntry(new DefaultHungerEntry());
		addEntry(new DefaultMountHealthEntry());
		addEntry(new DefaultAirEntry());
		addEntry(new DefaultStatusEffectsEntry());
	}
	
	public HudElementType getElementType(HTIdentifier.ElementType elementIdentifier) {
		return elementGroupMap.get(elementIdentifier);
	}

	public HudElementEntry getActiveEntry(HTIdentifier.ElementType elementIdentifier) {
		return getElementType(elementIdentifier).getActiveEntry();
	}
	
	public Collection<HudElementType> getElementTypes() {
		return elementGroupMap.values();
	}
	
	public void addEntry(HudElementEntry entry) {
		if (!entry.getIdentifier().getElementType().equals(RelativeTreeRootScreen.IDENTIFIER.getElementType())) {
			if (elementGroupMap.containsKey(entry.getIdentifier().getElementType())) {
				getElementType(entry.getIdentifier().getElementType()).add(entry);
			} else {
				HTIdentifier.ElementType elementId = entry.getIdentifier().getElementType();
				HudElementType type = new HudElementType(elementId);
				type.add(entry);
				entry.setParentNode(type);
				entry.init();
				elementGroupMap.put(elementId, type);
			}
		} else {
			Util.LOGGER.error("Failed to add element: identifier \"screen\" is reserved");
		}
	}
	
	public void updateFromJson(JsonElement json) {
		for (Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
			try {
				// temporary element identifier to retrieve the group from a string representation
				getElementType(new HTIdentifier.ElementType(entry.getKey(), null)).updateFromJson(entry.getValue());
			} catch (NullPointerException e) {
				Util.LOGGER.error("HudElementType specified in config doesn't exist in element type map, skipping...", e);
				continue;
			}
		}
	}
}

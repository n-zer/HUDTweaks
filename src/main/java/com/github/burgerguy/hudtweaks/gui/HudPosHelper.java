package com.github.burgerguy.hudtweaks.gui;

public class HudPosHelper {
	/**
	 * Either width or height
	 */
	private transient final int elementDimension;
	
	/**
	 * The anchor point for calculation
	 */
	private Anchor anchor;
	
	/**
	 * The offset from the anchor point.
	 */
	private int offset;
	
	/**
	 * The relative position of the element from 0 to 1 on the screen,
	 * with 1 being the far side and 0 being the close side.
	 */
	private double relativePos;
	
	/**
	 * Set to true if any of the properties have been changed. This
	 * signals that the entire HudElement needs to be recalculated.
	 */
	private transient boolean requiresUpdate;
	
	public HudPosHelper(int elementDimension) {
		this.elementDimension = elementDimension;
	}
	
	public Anchor getAnchor() {
		return anchor;
	}

	public void setAnchor(Anchor type) {
		this.anchor = type;
		this.requiresUpdate = true;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
		this.requiresUpdate = true;
	}

	public double getRelativePos() {
		return relativePos;
	}

	public void setRelativePos(double relativePos) {
		this.relativePos = relativePos;
		this.requiresUpdate = true;
	}
	
	public boolean requiresUpdate() {
		return requiresUpdate;
	}
	
	/**
	 * Don't call this unless you know what you're doing.
	 */
	void setUpdated() {
		this.requiresUpdate = false;
	}
	
	public void reset() {
		this.anchor = null;
		this.offset = 0;
		this.relativePos = 0;
		this.requiresUpdate = true;
	}
	
	public int calculateScreenPos(int screenDimension) {
		if (anchor == null) {
			return Integer.MIN_VALUE;
		}
		
		int negativeAnchorPos = (int) (screenDimension * relativePos) + offset;
		
		switch(anchor) {
			case MINIMUM:
				return negativeAnchorPos;
			case CENTER:
				return negativeAnchorPos - (int) (elementDimension / 2F);
			case MAXIMUM:
				return negativeAnchorPos - elementDimension;
			default:
				throw new UnsupportedOperationException("how");
		}
	}
	
	public enum Anchor {
		MINIMUM,
		CENTER,
		MAXIMUM
	}
}

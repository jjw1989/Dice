package com.david.opengldemo;

import android.graphics.Bitmap;

public class Dice {
	private Face face;
	private Location location; // 位置
	private int screenW;
	private int screenH;
	private int pathX, pathY; // 步长
	private int orienX, orienY; // 方向
	private boolean state; // 是否运动
	public static int count = 0;

	Dice(int screenW, int screenH, Bitmap bmp) {
		face = new Face();
		location = new Location(screenW, screenH, bmp);
		this.screenW = screenW;
		this.screenH = screenH - 60;
		state = true;
		count++;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isValid(Dice dice) {
		return this.location.isValid(dice.location);
	}

	public int getLeft() {
		return this.location.getLeft();
	}

	public void setLeft(int left) {
		this.location.setLeft(left);
	}

	public int getTop() {
		return this.location.getTop();
	}

	public void setTop(int top) {
		this.location.setTop(top);
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getScreenW() {
		return screenW;
	}

	public void setScreenW(int screenW) {
		this.screenW = screenW;
	}

	public int getScreenH() {
		return screenH;
	}

	public void setScreenH(int screenH) {
		this.screenH = screenH;
	}

	public int getPicWidth() {
		return this.location.getPicWidth();
	}

	public void setPicWidth(int picWidth) {
		this.location.setPicWidth(picWidth);
	}

	public int getPicHeight() {
		return this.location.getPicHeight();
	}

	public void setPicHeight(int picHeight) {
		this.location.setPicHeight(picHeight);
	}

	public int getPathX() {
		return pathX;
	}

	public void setPathX(int pathX) {
		this.pathX = pathX;
	}

	public int getPathY() {
		return pathY;
	}

	public void setPathY(int pathY) {
		this.pathY = pathY;
	}

	public int getOrienX() {
		return orienX;
	}

	public void setOrienX(int orienX) {
		this.orienX = orienX;
	}

	public int getOrienY() {
		return orienY;
	}

	public void setOrienY(int orienY) {
		this.orienY = orienY;
	}

}

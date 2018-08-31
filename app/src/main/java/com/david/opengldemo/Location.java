
package com.david.opengldemo;

import java.util.Random;

import android.graphics.Bitmap;

public class Location {
	private int left;
	private int top;
	public int picWidth;
	public int picHeight;
	
	private static Random random=new Random();

	public Location(int screenW, int screenH, Bitmap bmp) {
		// TODO Auto-generated constructor stub
		this.picWidth = bmp.getWidth();
		this.picHeight = bmp.getHeight();
		//���ݱ߽�������ӵ����λ��
		this.left = random.nextInt(screenW - picWidth);
		this.top = random.nextInt(screenH - picHeight);	
	}
	
	
	public boolean isValid(Location location) {
		if(Math.abs(left-location.left)<picWidth && Math.abs(top-location.top)<picHeight){
			return false;
		}else{
			return true;
		}
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}


	public int getPicWidth() {
		return picWidth;
	}


	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}


	public int getPicHeight() {
		return picHeight;
	}


	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}
	
	
}

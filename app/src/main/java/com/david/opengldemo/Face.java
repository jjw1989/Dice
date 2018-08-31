package com.david.opengldemo;

import java.util.Random;

public class Face {
	private int faceId;
	private int faceValue;
	
	private Random random = new Random();
	
	public final static int[] face = { R.drawable.value1, R.drawable.value2,
			R.drawable.value3, R.drawable.value4, R.drawable.value5,
			R.drawable.value6 };
	
	public final static int[] rollFace = { R.drawable.roll1, R.drawable.roll2,
		R.drawable.roll3, R.drawable.roll4, R.drawable.roll5,
		R.drawable.roll6, R.drawable.roll7, R.drawable.roll8,
		R.drawable.roll9 };
	
	public Face() {
		// TODO Auto-generated constructor stub
		faceValue=random.nextInt(6);
		faceId=face[faceValue];
	}

	public int getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(int faceValue) {
		this.faceValue = faceValue;
	}

	public int getFaceId() {
		return faceId;
	}

	public void setFaceId(int faceId) {
		this.faceId = faceId;
	}
}

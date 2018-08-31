package com.david.opengldemo;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;

public class MainActivity extends Activity {
	Vibrator vibrator;
	Dices dices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dices = new Dices(this);
		setContentView(dices);
		
	}

	//��back��ť���˳�����
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		System.exit(0);
	}
}
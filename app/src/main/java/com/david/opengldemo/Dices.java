package com.david.opengldemo;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Dices extends SurfaceView implements Callback, Runnable {
	public static int NUM = 6;

	public static int screenW;
	public static int screenH;
	public static int picWidth = 115;
	public static int picHeight = 148;

	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private Move move;
	private Result result;

	private Context context;
	private ShakeListener mShakeListener;// 摇手机
	private MediaPlayer mp; // 音乐

	private Bitmap moon; // 月亮
	private Bitmap city; // 城市
	private int switchMode; // 切换模式

	private Dice[] dice = new Dice[Dices.NUM];
	private Random random = new Random();

	public Dices(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(40);
		paint.setTextAlign(Align.CENTER);
		setFocusable(true);
		Dice.count = 0;

		moon = BitmapFactory.decodeResource(getResources(), R.drawable.moon);
		city = BitmapFactory.decodeResource(getResources(), R.drawable.city);
		// 添加摇动监听器
		mShakeListener = new ShakeListener(context);
		mShakeListener.setOnShakeListener(new shakeLitener());
		// 添加触摸监听器
		setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				float touchX, touchY;
				touchX = arg1.getX();
				touchY = arg1.getY();
				if (touchX <= moon.getWidth() + 10
						&& touchY <= moon.getHeight() + 10) {
					switchMode++;
					if (switchMode == 2) {
						NUM = (NUM == 5 ? 6 : 5);
						switchMode = 0;
					}
				} else {
					switchMode = 0;
				}
				return false;
			}
		});
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		Dice.count = 0;
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		th = new Thread(this);
		th.start();
		if (mp != null) {
			mp.stop();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		th = Thread.currentThread(); // 获得当前线程
		Dice.count = 0;
		init();
		move = new Move(dice);
		mp = MediaPlayer.create(context, R.raw.sound_dice);

		while (flag) {
			long start = System.currentTimeMillis();
			myRolling();
			move.start();
			mp.start();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 20) {
					Thread.sleep(20 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			shake();
		}
	}

	// 摇动骰子
	public void shake() {
		// TODO Auto-generated method stub
		for (int i = 0; i < Dices.NUM; i++) {
			if (!dice[i].isState()) {
				continue;
			}
			return;
		}
		if (mp.isPlaying()) {
			mp.stop();
		} else if (move.isOk()) {
			while (!mp.isPlaying()) {
				mShakeListener.start();
			}
		}
	}

	// 摇骰子监听器
	private class shakeLitener implements ShakeListener.OnShakeListener {
		@Override
		public void onShake() {
			// TODO Auto-generated method stub
			switchMode = 0;
			move.init();
			mp = MediaPlayer.create(context, R.raw.sound_dice);
			move.start();
			mp.start();
		}
	}

	// 初始随机骰子位置
	private void init() {
		Dice.count = 0;
		for (int i = 0; i < Dices.NUM; i++) {
			dice[i] = new Dice(screenW, screenH, BitmapFactory.decodeResource(
					getResources(), Face.face[i]));
			for (int j = 0; j < Dice.count; j++) {
				if (dice[i].isValid(dice[j]) || i == j) {
					continue;
				}
				i--;
				Dice.count--;
				break;
			}
		}
	}

	// 绘制骰子
	private void myRolling() {
		// TODO Auto-generated method stub
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {

				canvas.drawColor(0xff942222);

				// 绘制月亮与城市
				canvas.drawBitmap(moon, 10, 10, paint);
				canvas.drawBitmap(city, 0, screenH - city.getHeight(), paint);
				for (int i = 0; i < Dices.NUM; i++) {
					if (dice[i].isState()) {
						rolling(canvas, i);
					} else {
						sitting(canvas, i);
					}
				}
				if (!mp.isPlaying() && NUM != 5) {
					// 绘制结果
					result = new Result(dice);
					canvas.drawText(result.getMyBobing().name(), screenW / 2,
							screenH - 40, paint);
				}
			}
		} catch (Exception e) {

		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	// 绘制转动时的骰子
	public void rolling(Canvas canvas, int i) {
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
				Face.rollFace[random.nextInt(9)]), dice[i].getLeft(), dice[i]
				.getTop(), paint);
	}

	// 绘制停止时的骰子
	public void sitting(Canvas canvas, int i) {
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
				Face.face[dice[i].getFace().getFaceValue()]),
				dice[i].getLeft(), dice[i].getTop(), paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
}

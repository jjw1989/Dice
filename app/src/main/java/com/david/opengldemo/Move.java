package com.david.opengldemo;

import java.util.Random;

/*	计算各个骰子运动轨迹，能更真实得模拟出现实世界
 *  1.保证骰子不越界，不重叠
 *  2.骰子的能量和方向随机
 *  3.随运动时间的增长，速度降低
 */
public class Move {
	Dice[] dice;
	private Random random = new Random();

	public Move(Dice[] dice) {
		// TODO Auto-generated constructor stub
		this.dice = dice;
		init();
	}
	//赋予骰子随机步长和随机方向
	public void init() {
		for (int i = 0; i < Dices.NUM; i++) {
			dice[i].setOrienX(random.nextInt(2) == 0 ? 1 : -1);
			dice[i].setOrienY(random.nextInt(2) == 0 ? 1 : -1);
			dice[i].setPathX(random.nextInt(30) + 10);
			dice[i].setPathY(random.nextInt(30) + 10);
			dice[i].getFace().setFaceValue(random.nextInt(6));
			dice[i].setState(true);
		}
	}
	//保证骰子不越界
	public void start() {
		for (int i = 0; i < Dices.NUM; i++) {
			// pathX<=0 && pathY<=0	为结束标志
			if (dice[i].getPathX() <= 0 && dice[i].getPathY() <= 0) {
				if (!isValid(i)) {
					if(dice[i].getPathX() == 0){
						dice[i].setPathX(1);
					}
					if(dice[i].getPathY() == 0){
						dice[i].setPathY(1);
					}
				} else {
					dice[i].setState(false);
				}
			}
			if (!dice[i].isState()) {
				continue;
			} else {
				moveX(i);
				// X轴越界，设置为矫正方向
				if (!insideX(i)) {
					if (dice[i].getLeft() <= 0) {
						dice[i].setOrienX(1);
					} else if (dice[i].getLeft() >= dice[i].getScreenW()
							- dice[i].getPicWidth()) {
						dice[i].setOrienX(-1);
					}
				} else if (indice(i) != -1) {
					// X轴不越界，与骰子相撞,设置弹开方向
					if (dice[i].getLeft() <= dice[indice(i)].getLeft()) {
						dice[i].setOrienX(-1);
					} else {
						dice[i].setOrienX(1);
					}
				}
				moveY(i);
				// Y轴越界，设置为矫正方向
				if (!insideY(i)) {
					if (dice[i].getTop() <= 0) {
						dice[i].setOrienY(1);
					} else if (dice[i].getTop() >= dice[i].getScreenH()
							- dice[i].getPicHeight()) {
						dice[i].setOrienY(-1);
					}
				} else if (indice(i) != -1) {
					// Y轴不越界，与骰子相撞,设置为弹开方向
					if (dice[i].getTop() <= dice[indice(i)].getTop()) {
						dice[i].setOrienY(-1);
					} else {
						dice[i].setOrienY(1);
					}
				}
			}
		}
	}
	//X轴的运动计算
	private void moveX(int i) {
		dice[i].setLeft(dice[i].getLeft() + dice[i].getOrienX()
				* dice[i].getPathX());
		//运动减速
		if(dice[i].getPathX()<1){
			dice[i].setPathX(0);
		}else{
			dice[i].setPathX(dice[i].getPathX()-1);
		}
	}
	//Y轴的运动计算
	private void moveY(int i) {
		dice[i].setTop(dice[i].getTop() + dice[i].getOrienY()
				* dice[i].getPathY());
		//运动减速
		if(dice[i].getPathY()<1){
			dice[i].setPathY(0);
		}else{
			dice[i].setPathY(dice[i].getPathY()-1);
		}
	}
	//X轴不越界
	public boolean insideX(int i) {
		if (dice[i].getLeft() <= dice[i].getScreenW() - dice[i].getPicWidth()
				&& dice[i].getLeft() >= 0) {
			return true;
		}
		return false;
	}
	//Y轴不越界
	public boolean insideY(int i) {
		if (dice[i].getTop() <= dice[i].getScreenH() - dice[i].getPicHeight()
				&& dice[i].getTop() >= 0) {
			return true;
		}
		return false;
	}
	// 与骰子重叠返回骰子j，不重叠返回-1
	public int indice(int i) {
		for (int j = 0; j < Dices.NUM; j++) {
			if (!dice[i].isValid(dice[j]) && i != j) {
				return j;
			}
		}
		return -1;
	}

	//判断骰子位置是否有效
	private boolean isValid(int i) {
		return (insideX(i) && insideY(i) && indice(i) == -1);
	}

	//判断所有骰子是否静止
	public boolean isOk(){
		for(int i=0;i<Dices.NUM;i++){
			if(insideX(i) && insideY(i) && indice(i) == -1){
				continue;
			}
			return false;
		}
		return true;
	}
}

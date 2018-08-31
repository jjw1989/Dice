package com.david.opengldemo;

import android.media.MediaPlayer;

public class Result {
	private int[] faceValue;
	private int[] count;
	private int maxCount;

	private MediaPlayer mp;

	public static enum bobing{
		状元插金花,满堂红,遍地锦,六勃黑,五王,五子,状元,对堂,三红,四进,二举,一秀,不中
	}
	private bobing myBobing;

	public Result(Dice[] dice) {
		// TODO Auto-generated constructor stub
		faceValue=new int[Dices.NUM];
		count=new int[Dices.NUM];
		int i=0;
		for(Dice d:dice ){
			count[d.getFace().getFaceValue()]++;
			faceValue[i++]=d.getFace().getFaceValue();
		}
		mp=null;

		sort();
		maxCount();
		compatable();
	}
	private void sort(){
		int temp;
		for(int i=0;i<Dices.NUM;i++){
			for(int j=i+1;j<Dices.NUM;j++){
				if(faceValue[i]>faceValue[j]){
					temp=faceValue[j];
					faceValue[j]=faceValue[i];
					faceValue[i]=temp;
				}
			}
		}
	}

	private void maxCount(){
		maxCount=0;
		for(int i=0;i<Dices.NUM;i++){
			if(count[i]>maxCount){
				maxCount=count[i];
			}
		}
	}
	//对照
	private void compatable(){
		//faceValue的值视为实际值-1
		if(maxCount==6){
			if(faceValue[0]==3){
				myBobing=bobing.满堂红;
			}else if(faceValue[0]==0){
				myBobing=bobing.遍地锦;
			}else{
				myBobing=bobing.六勃黑;
			}
		}else if(maxCount==5){
			if(faceValue[1]==3){
				myBobing=bobing.五王;
			}else{
				myBobing=bobing.五子;
			}
		}else if(maxCount==4){
			if(faceValue[2]==3){
				if(faceValue[0]==faceValue[1]&& faceValue[0]==0){
					myBobing=bobing.状元插金花;
				}else{
					myBobing=bobing.状元;
				}
			}else{
				myBobing=bobing.四进;
			}
		}else if(maxCount==1){
			myBobing=bobing.对堂;
		}else if(count[3]==3){
			myBobing=bobing.三红;
		}else if(count[3]==2){
			myBobing=bobing.二举;
		}else if(count[3]==1){
			myBobing=bobing.一秀;
		}else{
			myBobing=bobing.不中;
		}
	}
	public bobing getMyBobing() {
		return myBobing;
	}
	public void setMyBobing(bobing myBobing) {
		this.myBobing = myBobing;
	}
	public MediaPlayer getMp() {
		return mp;
	}
}

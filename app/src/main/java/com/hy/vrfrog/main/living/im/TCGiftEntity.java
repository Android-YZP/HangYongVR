package com.hy.vrfrog.main.living.im;

/**
 * 消息体类
 */
public class TCGiftEntity {


	private int  times;
	private String giftImg;
	private String Sender;


	public TCGiftEntity(int times, String giftImg, String sender) {
		this.times = times;
		this.giftImg = giftImg;
		Sender = sender;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getGiftImg() {
		return giftImg;
	}

	public void setGiftImg(String giftImg) {
		this.giftImg = giftImg;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}
}

package com.example.kushaldesai.testapplication;

import android.util.DisplayMetrics;

public class CustomSizes
{
	DisplayMetrics oDisplayMetrics;

	public CustomSizes(DisplayMetrics oDisplayMetrics)
	{
		this.oDisplayMetrics = oDisplayMetrics;
	}

	public float getFontSize(float fontSize)
	{
		return (float) ((fontSize*oDisplayMetrics.xdpi*oDisplayMetrics.widthPixels) / (oDisplayMetrics.xdpi*720*oDisplayMetrics.scaledDensity));
	}

	public int getRectViewWidthSize(int width)
	{
		return (int) ((width*oDisplayMetrics.xdpi*oDisplayMetrics.widthPixels) / (oDisplayMetrics.xdpi * 720));
	}

	public int getRectViewHeightSize(int height)
	{
		return (int) ((height*oDisplayMetrics.ydpi*oDisplayMetrics.heightPixels) / (oDisplayMetrics.ydpi * 1280));
	}

	public int getGifHeightSize(int height)
	{
		return (int) ((height*oDisplayMetrics.ydpi*oDisplayMetrics.heightPixels) / (oDisplayMetrics.ydpi * 1920));
	}


	public int getGifWidthSize(int height)
	{
		return (int) ((height*oDisplayMetrics.xdpi*oDisplayMetrics.widthPixels) / (oDisplayMetrics.xdpi * 1080));
	}
}
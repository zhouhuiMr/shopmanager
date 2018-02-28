package com.common.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class PicAuthCode {
	private final static String VERIFY_CODE = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static Random random = new Random();

	public static void outputImage(int width, int height, OutputStream os, String authcode) throws IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		
		//设置背景为白色
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		
		//设置文字
		char[] charcode = authcode.toCharArray();
		for(int i = 0 ;i<charcode.length;i++) {
			graphics.setColor(new Color(getRandomIntColor()));
	        Font font = new Font("Algerian", Font.ITALIC, 30);
	        graphics.setFont(font);
	        char[] c = new char[1];
	        c[0] = charcode[i];
	        graphics.drawChars(c, 0, c.length, (random.nextInt(10)+20)*i+10,30);
		}
        
		
		// 设置线条的颜色
        for (int i = 0; i < 10; i++) {  
        	graphics.setColor(new Color(getRandomIntColor())); 
            int x = random.nextInt(width - 1);  
            int y = random.nextInt(height - 1);  
            int xl = random.nextInt(width);  
            int yl = random.nextInt(height);  
            graphics.drawLine(x, y, xl, yl);  
        }  
		
		
		// 添加噪点  
        float yawpRate = 0.02f;// 噪声率  
        int area = (int) (yawpRate * width * height);  
        for (int i = 0; i < area; i++) {  
            int x = random.nextInt(width);  
            int y = random.nextInt(height);  
            int rgb = getRandomIntColor();  
            image.setRGB(x, y, rgb);  
        }
        
		
		graphics.dispose();
		ImageIO.write(image, "jpg", os);
	}
	
	/**
	 * 随机生成RGB,
	 * 例如255,255,255
	 * */
	private static int[] getRandomRGB() {
		int[] rgb = new int[3];
		for(int i = 0;i < 3;i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}
	
	/**
	 * 十六进制RGB
	 * */
	private static int getRandomIntColor() {  
        int[] rgb = getRandomRGB();  
        int color = 0;  
        for (int c : rgb) {  
            color = color << 8;  
            color = color | c;  
        }  
        return color;  
    } 
	
	/**
	 * 生成图片验证码的文字
	 * */
	public static String getAuthCode() {
		String authcode = "";
		for(int i = 0;i < 4;i++) {
			int beginIndex = random.nextInt(VERIFY_CODE.length());
			authcode += VERIFY_CODE.substring(beginIndex, beginIndex+1);
		}
		return authcode;
	}
	
}

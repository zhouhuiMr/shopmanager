package com.common.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Str {
	
	/**
	 * @see MessageDigest 
	 * */
	public static String encodeStrBySHA256(String str) {
		MessageDigest messageDigest = null;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes());
			encodeStr = byteToHex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
	
	private static String byteToHex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
		return stringBuffer.toString();
	}
}

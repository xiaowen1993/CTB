/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月3日
 * Created by ckm
 */
package com.ctb.framework.commons.utils;

import java.security.MessageDigest;

/**
 * @ClassName: com.ctb.framework.commons.utils.a
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年6月3日 下午5:19:47
 */
public class TokenEncryptUtils {
	
	// 编码密码,可自定义
	private static final String ENCODED_PASSWORD = "U2FsdGVkX19hf++ld87hozY8TDDInJD/gD/pFKJZJjU=";
	
	/**
	 * 编码
	 * @param str
	 * @return
	 */
	public static String encoded(String str) {
		return strToHex(encodedString(str, ENCODED_PASSWORD));
	}
	
	/**
	 * 转换
	 * @param str
	 * @param password
	 * @return
	 */
	private static String encodedString(String str, String password) {
		char[] pwd = password.toCharArray();
		int pwdLen = pwd.length;
		
        char[] strArray = str.toCharArray();
        for (int i=0; i<strArray.length; i++) {
        	strArray[i] = (char)(strArray[i] ^ pwd[i%pwdLen] ^ pwdLen);
        }
        return new String(strArray);
	}
	
	private static String strToHex(String s) {
		return bytesToHexStr(s.getBytes());
	}
	
	private static String bytesToHexStr(byte[] bytesArray) {
		StringBuilder builder = new StringBuilder();
		String hexStr;
		for (byte bt : bytesArray) {
			hexStr = Integer.toHexString(bt & 0xFF);
			if (hexStr.length() == 1) {
				builder.append("0");
				builder.append(hexStr);
			}else{
				builder.append(hexStr);
			}
		}
		return builder.toString();
	}
	
	/**
	 * 解码
	 * @param str
	 * @return
	 */
	public static String decoded(String str) {
		String hexStr = null;
		try {
			hexStr = hexStrToStr(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (hexStr != null) {
			hexStr = encodedString(hexStr, ENCODED_PASSWORD);
		}
		return hexStr;
	}
	
	private static String hexStrToStr(String hexStr) {
		return new String(hexStrToBytes(hexStr));
	}
	
	private static byte[] hexStrToBytes(String hexStr) {
		String hex;
		int val;
		byte[] btHexStr = new byte[hexStr.length()/2];
		for (int i=0; i<btHexStr.length; i++) {
			hex = hexStr.substring(2*i, 2*i+2);
			val = Integer.valueOf(hex, 16);
			btHexStr[i] = (byte) val;
		}
		return btHexStr;
	}
	
}
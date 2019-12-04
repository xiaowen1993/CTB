package com.ctb.framework.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/*** 获得16位的加密字符 **/
	public static String getMd5String16(String str) throws NoSuchAlgorithmException {
		String md5str = getMd5String32(str).substring(8);
		return md5str.substring(0, md5str.length() - 8);
	}

	/** 获得24位的MD5加密字符 **/
	public static String getMd5String24(String str) throws NoSuchAlgorithmException {

		String md5str = getMd5String32(str).substring(4);
		return md5str.substring(0, md5str.length() - 4);
	}

	/** 获得32位的MD5加密算法 **/
	public static String getMd5String32(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer();
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];

			if (i < 0)
				i += 256;

			if (i < 16)
				buf.append("0");

			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}
	
}

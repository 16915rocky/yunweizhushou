package com.chinamobile.yunweizhushou.utils;

/** 
 * @ClassName: EncryptUtils 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author GaoChunfa 
 * @date 2015年8月17日 下午3:00:18   
 */
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtils {

	/**
	 * 用MD5算法进行加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return MD5加密后的结果
	 */
	public static String encodeMD5String(String str) {
		return encode(str, "MD5");
	}

	/**
	 * 用SHA算法进行加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return SHA加密后的结果
	 */
	public static String encodeSHAString(String str) {
		return encode(str, "SHA");
	}

	/**
	 * 用base64算法进行加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return base64加密后的结果
	 */
	public static String encodeBase64String(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str.getBytes());
	}

	/**
	 * 用base64算法进行解密
	 * 
	 * @param str
	 *            需要解密的字符串
	 * @return base64解密后的结果
	 * @throws IOException
	 */
	public static String decodeBase64String(String str) throws IOException {
		BASE64Decoder encoder = new BASE64Decoder();
		return new String(encoder.decodeBuffer(str));
	}

	private static String encode(String str, String method) {
		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			dstr = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return dstr;
	}

	public static void main(String[] args) throws Exception {
//		 String user = 'admin';
//		 System.out.println('原始字符串 ' + user);
//		 System.out.println('MD5加密 ' + encodeMD5String(user));
//		 System.out.println('SHA加密 ' + encodeSHAString(user));
//		 String base64Str = encodeBase64String(user);
		System.out.println("Base64加密: " + encodeBase64String(""));
		System.out.println("Base64解密: " + decodeBase64String(""));
	}

	/**
	 * 
	 * @Title: md5UpperCase
	 * @Description: TODO(md5加密算法2)
	 */
	public static final String md5UpperCase(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("GBK");

			MessageDigest mdInst = MessageDigest.getInstance("MD5");

			mdInst.update(btInput);

			byte[] md = mdInst.digest();

			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 生成随即密码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	/**
	 * base64+随机 解密：解密IOS登录加密信息
	 * 
	 * @param
	 */
	public static String decodeBase64ForIOS(String str) throws Exception {
		int index = (str.length() - 1) * 2 / 3;
		StringBuffer result = new StringBuffer(str);
		result.deleteCharAt(index);
		return decodeBase64String(result.toString());
	}

	/**
	 * 随机数： A-Z,a-z,0-9
	 */
	public static String getRandomForSec() {
		String[] str = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "F", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9" };
		Random random = new Random();
		int i = Math.abs(random.nextInt(str.length));
		String result = str[i];
		return result;
	}

	/**
	 * base64+随机 加密：
	 * 
	 * @param
	 */
	public static String encodeBase64ForSec(String str) throws Exception {
		str = encodeBase64String(str);
		StringBuffer result = new StringBuffer(str);
		int index1 = result.length() * 2 / 7;
		result.insert(index1, getRandomForSec());
		int index2 = result.length() * 5 / 7;
		result.insert(index2, getRandomForSec());
		return result.toString();
	}

	/**
	 * base64+随机 解密：
	 * 
	 * @param
	 */
	public static String decodeBase64ForSec(String str) throws Exception {
		StringBuffer result = new StringBuffer(str);
		int index1 = (result.length() - 1) * 5 / 7;
		result.deleteCharAt(index1);
		int index2 = (result.length() - 1) * 2 / 7;
		result.deleteCharAt(index2);
		return decodeBase64String(result.toString());
	}

	public static  HashMap<String,String> encrypt(HashMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(String key:map.keySet()){
			i++;
			sb.append(key);
			sb.append("=");
			sb.append(map.get(key));
			if(!(map.size()==i)){
				sb.append("&");
			}
		//	System.out.print(sb.toString());
		}
		try {
			String s = EncryptUtils.encodeBase64ForSec(sb.toString());
			map.clear();
			map.put("token",s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static  String encryptToString(HashMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		int i=0;
		String s="";
		for(String key:map.keySet()){
			i++;
			sb.append(key);
			sb.append("=");
			sb.append(map.get(key));
			if(!(map.size()==i)){
				sb.append("&");
			}
			//	System.out.print(sb.toString());
		}
		try {
			 s = EncryptUtils.encodeBase64ForSec(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}


}

package me.hokas.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * PBE——Password-based
 * encryption（基于密码加密）。其特点在于口令由用户自己掌管，不借助任何物理媒体；采用随机数（这里我们叫做盐）
 * 杂凑多重加密等方法保证数据的安全性。是一种简便的加密方式。 本类运用了Java 6所支持的PBE对称加密算法到Android环境里,实现密码的安全存储.
 * 
 * @author Jason
 * 
 */
public class PasswordUtil {

	/**
	 * JAVA6支持以下任意一种算法 PBEWITHMD5ANDDES PBEWITHMD5ANDTRIPLEDES
	 * PBEWITHSHAANDDESEDE PBEWITHSHA1ANDRC2_40 PBKDF2WITHHMACSHA1
	 * */

	/**
	 * 定义使用的算法为:PBEWITHMD5andDES算法
	 */
	public static final String ALGORITHM = "PBEWithMD5AndDES";

	/**
	 * 定义迭代次数为1000次
	 */
	private static final int ITERATIONCOUNT = 1000;

	/**
	 * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节
	 * 
	 * @return byte[] 盐值
	 * */
	public static byte[] getSalt() throws Exception {
		// 实例化安全随机数
		SecureRandom random = new SecureRandom();
		// 产出盐
		return random.generateSeed(8);
	}

	/**
	 * 根据PBE密码生成一把密钥
	 * 
	 * @param password
	 *            生成密钥时所使用的密码
	 * @return Key PBE算法密钥
	 * */
	private static Key getPBEKey(String password) throws Exception {
		// 实例化使用的算法
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		// 设置PBE密钥参数
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		// 生成密钥
		SecretKey secretKey = keyFactory.generateSecret(keySpec);

		return secretKey;
	}

	/**
	 * 加密明文字符串
	 * 
	 * @param plaintext
	 *            待加密的明文字符串
	 * @param password
	 *            生成密钥时所使用的密码
	 * @param salt
	 *            盐值
	 * @return 加密后的密文字符串
	 * @throws Exception
	 */
	public static String encrypt(String plaintext, String password, byte[] salt) throws Exception {

		Key key = getPBEKey(password);

		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);

		Cipher cipher = Cipher.getInstance(ALGORITHM);

		cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

		byte encipheredData[] = cipher.doFinal(plaintext.getBytes());

		return bytesToHexString(encipheredData);
	}

	/**
	 * 解密密文字符串
	 * 
	 * @param ciphertext
	 *            待解密的密文字符串
	 * @param password
	 *            生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
	 * @param salt
	 *            盐值(如需解密,该参数需要与加密时使用的一致)
	 * @return 解密后的明文字符串
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext, String password, byte[] salt) throws Exception {

		Key key = getPBEKey(password);

		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);

		Cipher cipher = Cipher.getInstance(ALGORITHM);

		cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

		byte[] passDec = cipher.doFinal(hexStringToBytes(ciphertext));

		return new String(passDec);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param src
	 *            字节数组
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将十六进制字符串转换为字节数组
	 * 
	 * @param hexString
	 *            十六进制字符串
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase(Locale.ENGLISH);
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 操作示例
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "PBE";
		String password = "123";


		try {
			byte[] salt = PasswordUtil.getSalt();
			String ciphertext = PasswordUtil.encrypt(str, password, salt);
			PasswordUtil.decrypt(ciphertext, password, salt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
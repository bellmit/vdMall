package com.vedeng.mjx.common.util;

import com.vedeng.goods.api.utils.IdEncrypt;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hank
 * ID加解密
 */
public class CookieEncode {
	private static String ALGORITHM = "PBEWithMD5AndDES";
	private static StandardPBEStringEncryptor encryptor = null;
	private static String password;
	static Logger logger= LoggerFactory.getLogger(CookieEncode.class);


	public static void main(String[] args) {
		CookieEncode encode=new CookieEncode();
		System.out.println(encode.encodeIdTime(1l,2l));
	}
	public  void init(String password){
		if(encryptor==null){
			encryptor = new StandardPBEStringEncryptor();
			encryptor.setAlgorithm(ALGORITHM);
			encryptor.setPassword(password);
		}
	}

	/**
	 * PBEWithMD5AndDES 加密
	 */
	public static String encodeIdTime(long id,long time){
		String idEncode= IdEncrypt.encode(id);
		String timeEncode=IdEncrypt.encode(time);
		// 加密
		String ciphertext = encryptor.encrypt(idEncode+"@@"+timeEncode);
		return ciphertext;
	}

	public  static String decodeWithExpire(String value,long expire) {
		if(StringUtils.isBlank(value)){
			return StringUtils.EMPTY;
		}
		String idTime=encryptor.decrypt(value);
		String[] idTimeArray=StringUtils.split(idTime,"@@");
		if(ArrayUtils.isEmpty(idTimeArray)||idTimeArray.length!=2){
			logger.warn("error cookie::"+value);
			return StringUtils.EMPTY;
		}
		String id=IdEncrypt.decode( idTimeArray[0]);
		String time=IdEncrypt.decode(idTimeArray[1]);

		if(System.currentTimeMillis()-NumberUtils.toLong(time)>expire){
			//过期了
			logger.warn("expire cookie::"+value+"\t"+time+"\t"+expire);
			return StringUtils.EMPTY;
		}
		if(StringUtils.equals("0",id)){
return StringUtils.EMPTY;
		}
		return id;
	}


	/**
	 * PBEWithMD5AndDES 加密
	 */
	public static  String encode(String key) {
		// 加密
		String ciphertext = encryptor.encrypt(key);
		return ciphertext;
	}

	public  static String decode(String value) {
		return encryptor.decrypt(value);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

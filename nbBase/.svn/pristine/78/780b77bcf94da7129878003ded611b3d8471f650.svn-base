package nbBase.helper.common;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;


public class nbStringUtil {

	
	/**
	 * MD5加密
	 *  
	 * @param data
	 * @return
	 * @throws Exception
	 */

	public static byte[] encryptMD5(byte[] data) throws Exception {
	
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		return messageDigest.digest();
//		return DigestUtils.md5Digest(data);
		
	}
	
	/**
	 * MD5加密
	 *  
	 * @param data
	 * @return
	 * @throws Exception
	 */

	public static String encryptMD5(String text) throws Exception {
		
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(text.getBytes());
		return getFormattedText(messageDigest.digest());
		
//		byte[] data = text.getBytes();
//		String cryptedData = DigestUtils.md5DigestAsHex(data);
//		String crypted = Base64.encodeBase64String(cryptedData.getBytes());
//		
//		return crypted;
	}
	
	/**
	 * MD5加密
	 *  
	 * @param data
	 * @return
	 * @throws Exception
	 */

	public static String encryptMD5WithoutBase64(String text) throws Exception {
		
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(text.getBytes());
		return getFormattedText(messageDigest.digest());

	}
	
	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) { 			
			buf.append(HEX_DIGITS[(bytes[j] >> 4)&0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	
	
	/**
	* BASE64解密
	* 
	* @param key
	* @return
	* @throws Exception
	*/
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decodeBase64(key);
	}

	/**
	* BASE64 加密
	* 
	* @param key
	* @return
	* @throws Exception
	*/
	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encodeBase64String(key);
	}
	
	public static String DateTime2String(Date theDate){
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.format(theDate);
	}
	
	public static Date String2DateTime(String dateString) {
		if( dateString == null)
			return null;
		try{
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.parse(dateString);
		} catch(Exception e){
			return null;
		}
	}
	
	public static Date String2DateTimeF02(String dateString) {
		if( dateString == null)
			return null;
		try{
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf2.parse(dateString);
		} catch(Exception e){
			return null;
		}
	}
	
	
	
	public static String DateTime2StrinF01(Date theDate){
		if( theDate == null)
			return "";
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy年MM月dd日");
		return sf2.format(theDate);
	}
	
	public static String DateTime2StrinF02(Date theDate){
		if( theDate == null)
			return "";
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
		return sf2.format(theDate);
	}
	
	public static String DateTime2StrinF03(Date theDate){
		if( theDate == null)
			return "";
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		return sf2.format(theDate);
	}
	
	public static String DateTime2StrinF04(Date theDate){
		if( theDate == null)
			return "";
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sf2.format(theDate);
	}
	
	public static String convertLeftTimeToString(long leftTimeInSec){
		
		if(leftTimeInSec <= 0){
			return "已经截止";
		}
		
		if( ( leftTimeInSec / 60 ) == 0 ){
			return leftTimeInSec + " 秒";
		}
		
		if( ( leftTimeInSec / 3600 ) == 0 ){
			return ((int)leftTimeInSec/60) + " 分钟";
		}
		
		if( ( leftTimeInSec / (3600*24) ) == 0 ){
			return ((int)leftTimeInSec/3600) + " 小时";
		}
		
			return ((int)leftTimeInSec/(3600*24)) + " 天";
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNullEmpty(String string) {
		if(string == null)
			return true;
		if(string.length() == 0 )
			return true;
		
		return false;
	}

	public static String cutString(String target, int leftNumber) {
		if( target.length() <= leftNumber )
			return target;
		return target.substring(0, leftNumber) + "...";
	}
	
}

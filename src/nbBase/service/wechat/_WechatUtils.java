package nbBase.service.wechat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class _WechatUtils {
	
	/**
	 * 用时间生成一个唯一字符串
	 * @return
	 */
	public static String createUniqueString(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String text = sf.format(cal.getTime());
		return text + String.valueOf((int)(1+Math.random()*(100-1+1)));
	}
	
	/**
	 * 时间字符串
	 * @param offset 当前时间的offset（秒）
	 * @return
	 */
	public static String getTimeString(Long offset){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Long newTime = cal.getTimeInMillis() + offset*1000;
		
		cal.setTimeInMillis(newTime);
		String text = sf.format(cal.getTime());
		return text;
	}
	
	/**
	 * 时间字符串
	 * @param offset 当前时间的offset（秒）
	 * @return
	 */
	public static String getTimeString(Date date){
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	
		String text = sf.format(date);
		return text;
	}
	
	/**
	 * 
	 * @param ts
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToTime(String ts) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.parse(ts);
	}
	
	/**
	 * 按照微信要求生成订单号，也是基于时间的，并且拼接了商户号，填满字数
	 * @return
	 */
	public static String createBillNumber(String mchId){
		StringBuilder sb = new StringBuilder();
		
		sb.append(mchId);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		sb.append(sf.format(cal.getTime()));
		
		sf = new SimpleDateFormat("HHmmssSSS");
		sb.append(sf.format(cal.getTime()));
		
		sb.append(String.valueOf((int)(1+Math.random()*(9-1+1))));
		
		return sb.toString();
	}
	
	/**
	 * 创建签名
	 * @param valueSet
	 * @param payKey
	 * @return
	 * @throws Exception
	 */
	public static String createSinature(TreeMap<String, Object> valueSet, String payKey) throws Exception{
		
		StringBuilder valueStringBuilder = new StringBuilder();
		
		//对key进行排序
		Set<String> sortedKeys = new TreeSet<String>();
		Set<String> orgKeys = valueSet.keySet();
		for(String key : orgKeys){
			sortedKeys.add(key);
			System.out.println(key);
		}
		//排序结束
		
		//把参数排列成 key1=value1&key2=value2 的格式
		for(String key : sortedKeys){
			Object valueObject = valueSet.get(key);
			if( valueObject != null ){
				String valueString = valueObject.toString();
				if( valueString.length() > 0){
					if(valueStringBuilder.length() != 0){
						valueStringBuilder.append("&");
					}
					if( key == "_package" )
						key = "package";
					valueStringBuilder.append(key+"="+valueString);
				}
			}
		}
		//排列结束
		
		//append payKey秘钥
		valueStringBuilder.append("&key="+payKey);
		System.out.println(valueStringBuilder.toString());
		
		//
		//String encryptTarget = new String(valueStringBuilder.toString().getBytes(), "ISO8859-1");
		
		//进行MD5签名
		return encryptMD5(valueStringBuilder.toString()).toUpperCase();
		
	}
	
	
	
	/**
	 * MD5加密
	 *  
	 * @param data
	 * @return
	 * @throws Exception
	 */

	private static String encryptMD5(String text) throws Exception {
		
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
	private  static String getFormattedText(byte[] bytes) {
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
	 * 把一个map编写成一个xml字符串
	 * @param data
	 * @return
	 */
	public static String Map2XMLString(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<xml>");
		
		enumTheMapForAssemble(data, sb);
		
		sb.append("</xml>");
		return sb.toString();
		//return new String(sb.toString().getBytes(), "ISO8859-1");
		
	}
	
	@SuppressWarnings("unchecked")
	private static void enumTheMapForAssemble(Map<String, Object> data, StringBuilder sb){
		if( data instanceof Map){
			
			Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator();
			  while (it.hasNext()) {
			   Map.Entry<String, Object> entry = it.next();
			   
			   	if( entry.getValue() instanceof Map){
			   		enumTheMapForAssemble( (Map<String, Object>)entry.getValue(), sb);
			   	}
			   	else if( entry.getValue() instanceof List){
			   		
			   		sb.append("<"+entry.getKey()+">");
			   		
			   		for(Object item : (List<Object>)entry.getValue()){
				   		sb.append("<item>"
				   				+ item.toString()
				   				+ "</item>\r\n");
			   		}
			   		
			   		sb.append("</"+entry.getKey()+">");
			   	}
			   	else{
			   		sb.append("<"+entry.getKey()+"><![CDATA["
			   				+ entry.getValue().toString()
			   				+ "]]></"+entry.getKey()+">\r\n");
			   	}
			  }
		}
	}
	
	/**
	 * 一个个的参数来填写
	 * @param key
	 * @param value
	 * @param isRemove
	 */
	public static void prepareParameter(TreeMap<String, Object> sortMap, String key, Object value, boolean isRemove){
		if( !isRemove ){
			sortMap.put(key, value);
		}
		else{
			sortMap.remove(key);
		}
	}
	
	
	/**
	 * 
	 * @param xmlDataString
	 * @param apiURL
	 * @param certBin
	 * @param mchId
	 * @return
	 * @throws Exception
	 */
	public static String httpsPostSend(String xmlDataString, String apiURL, byte[] certBin, String mchId) throws Exception{
		System.out.println("使用key文件binary["+certBin.length+"]");
		ByteArrayInputStream instream = new ByteArrayInputStream(certBin);
		return httpsPostSend( xmlDataString,  apiURL,  instream,  mchId);
	}
	
	/**
	 * 使用httpClient的httpPost通过https协议向服务器发送数据，并获取返回数据
	 * @param xmlDataString
	 * @param apiURL
	 * @param certFileP12
	 * @param mchId
	 * @return
	 * @throws Exception
	 */
	public static String httpsPostSend(String xmlDataString, String apiURL, String certFileP12, String mchId) throws Exception{
		System.out.println("使用key文件名"); 
		FileInputStream instream = new FileInputStream(new File(certFileP12));
		return httpsPostSend( xmlDataString,  apiURL,  instream,  mchId);
	}
	
	
	/**
	 * 
	 * @param xmlDataString
	 * @param apiURL
	 * @param instream
	 * @param mchId
	 * @return
	 * @throws Exception
	 */
	private static String httpsPostSend(String xmlDataString, String apiURL, InputStream instream, String mchId) throws Exception{
		
		StringBuilder retSB = new StringBuilder();
		
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		
        try {
            keyStore.load(instream, mchId.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(	sslcontext, new String[] { "TLSv1" }, null,
        																	SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        
        try {

            HttpPost httpPost = new HttpPost(apiURL);
            
            StringEntity dataEntity = new StringEntity(xmlDataString, "utf-8");
            dataEntity.setContentEncoding("utf-8");
            dataEntity.setContentType("text/xml");
            dataEntity.setChunked(true);
            httpPost.setEntity(dataEntity);
            

            System.out.println("executing request https:" + httpPost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                    	 retSB.append(text);
                        System.out.println(text);
                    }
                   
                   
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return retSB.toString();
	}
	
	/**
	 * 不用证书的 httppost
	 * @param xmlDataString
	 * @param apiURL
	 * @return
	 * @throws Exception
	 */
	public static String httpPostSend(String dataString, String apiURL) throws Exception{
		
		StringBuilder retSB = new StringBuilder();
		
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {

            HttpPost httpPost = new HttpPost(apiURL);
            
            StringEntity dataEntity = new StringEntity(dataString, "utf-8");
            dataEntity.setContentEncoding("utf-8");
            
            if( dataString.trim().toLowerCase().startsWith("<")){
            	dataEntity.setContentType("text/xml");
            	dataEntity.setChunked(true);
            }
            if( dataString.trim().toLowerCase().startsWith("{"))
            	dataEntity.setContentType("application/json");
            
            httpPost.setEntity(dataEntity);
            

            System.out.println("executing request" + httpPost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                    	 retSB.append(text);
                        System.out.println(text);
                    }
                   
                   
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return retSB.toString();
	}
	
	/**
	 * 解析xml String，变成Map<String, Object>
	 * @param xmlString
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseXML(String xmlString, Class<?> cls) throws Exception{
			if( cls == null ) cls = new String().getClass();
			
	        Map<String, Object> maps = new HashMap<String, Object>();
	        List<Object> lists = new ArrayList<Object>();
	        SAXReader saxReader = new SAXReader();
	        InputStream   in_withcode   =   new   ByteArrayInputStream(xmlString.getBytes("UTF-8"));  
	        Document doc = saxReader.read(in_withcode);
	        Element et = doc.getRootElement();
	        
	        // 标记List是否为空
	        // boolean bool = true ;
	        // 根节点名字
	        List<Element> rList = et.elements();
	        for (Element element : rList) {
	            List<Element> rLists = element.elements();
	            if (!rLists.isEmpty() && rLists.size() > 0) {
	                //bool = false;
	                // 判断二级节点
	                for (Element e : rLists) {
	                    List<Element> li = e.elements();
	                    Class<?> cl = (Class<?>) Class.forName(cls.getName());
	                    Object ob = cl.newInstance();
	                    for (Element element2 : li) {
	                        String name = element2.getName();
	                        String value = element2.getText();
	                        Field field = ob.getClass().getDeclaredField(name);
	                        field.setAccessible(true);
	                        convertValue(field, ob, value);
	                    }
	                    lists.add(ob);
	                }
	            } else {
	                maps.put(element.getName(), element.getText());
	            }
	            maps.put(cls.getSimpleName() + "_List", lists);
	        }
	        return maps;
	    }
	
	/**
	 * 
	 * @param field
	 * @param obj
	 * @param value
	 * @throws Exception
	 */
	private static void convertValue(Field field, Object obj, String value) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch(field.getGenericType().toString()){
	        case "class java.lang.Integer":
	        case "class java.lang.Long":
	        case "int":
	        case "long":
	        	 field.set(obj, Long.parseLong(value));
	        	break;
	        	
	        case "class java.lang.Boolean":
	        case "boolean":
	        	field.set(obj, Boolean.parseBoolean(value));
	        	break;
	        	
	        case "class java.lang.Date":
	        	field.set(obj, sim.parse(value));
	        	break;
	        	
	 
	        default:
	        	 field.set(obj, value);
	        	break;
	        
	    }
	}
	
	/**
	 * 使用普通GET方法
	 * @param apiURL
	 * @return
	 * @throws Exception 
	 * @throws  
	 * @throws Exception
	 */
	public static String httpGet(String apiURL) throws Exception {
		
		Boolean outputLogger = true;
		StackTraceElement stack[] = Thread.currentThread().getStackTrace(); 
		String caller = stack[2].getMethodName();
		if(caller.equals("doCurl"))
			outputLogger = false;
		
		
		StringBuilder sb = new StringBuilder();
		
	
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try{
			HttpGet httpGet = new HttpGet(apiURL);
	
			// Get the response
			System.out.println("executing request" + httpGet.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpGet);
			
	        try {
	            HttpEntity entity = response.getEntity();
	
	            System.out.println("----------------------------------------");
	            System.out.println(response.getStatusLine());
	            if (entity != null) {
	                System.out.println("Response content length: " + entity.getContentLength());
	                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
	                String text;
	                while ((text = bufferedReader.readLine()) != null) {
	                	 sb.append(text);
	                	if( outputLogger)
	                		System.out.println(text);
	                }
	               
	            }
	            EntityUtils.consume(entity);
	        } finally {
	            response.close();
	        }
	    } finally {
	        httpclient.close();
	    }
    return sb.toString();
	}
	
	
	/**
	 * 
	 * @param apiURL
	 * @return "contentType" & "content"
	 * @throws Exception
	 */
	public static Map<String, Object> httpGetBytes(String apiURL) throws Exception {

		Boolean outputLogger = true;
		StackTraceElement stack[] = Thread.currentThread().getStackTrace(); 
		String caller = stack[2].getMethodName();
		if(caller.equals("doGetProtectedPicStream"))
			outputLogger = false;
		
		Map<String, Object> retValue = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try{
			HttpGet httpGet = new HttpGet(apiURL);
	
			// Get the response
			System.out.println("executing request" + httpGet.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpGet);
			
	        try {
	        	HttpEntity entity = response.getEntity();
	
	            System.out.println("----------------------------------------");
	            System.out.println(response.getStatusLine());
	            if (entity != null) {
	            	retValue = new HashMap<String, Object>();
	            	retValue.put("contentType", entity.getContentType().toString().split(":")[1].trim());
	            	
	                System.out.println("Response content length: " + entity.getContentLength());
	                
	                InputStream isr = entity.getContent();
	                
	                //FileOutputStream fos = new FileOutputStream(file);  
	                byte[] buf = new byte[(int) entity.getContentLength()];  
	                int len = 0;
	                int totalLen = 0;
	                while (len != -1) {
	                	
	                	if( outputLogger )
	                		System.out.println("read len: " +len);
	                	
	                	len = isr.read(buf, totalLen, buf.length+1);
	                	totalLen += len;
	                    //fos.write(buf, 0, len);
	                }  
//	                fos.flush();  
//	                fos.close();  
	                retValue.put("content", buf);
	                
	            }
	            EntityUtils.consume(entity);
	           
	        } finally {
	            response.close();
	        }
	    } finally {
	        httpclient.close();
	    }
    return retValue;
	}
	
	
	/**
	 * 用sha1进行签名，key会自动变成小写
	 * @param valueSet
	 * @return
	 * @throws Exception
	 */
	public static String sha1Sign(TreeMap<String, Object> valueSet) throws Exception {
		
		String signature = null;
		StringBuilder valueStringBuilder = new StringBuilder();
		
		//对key进行排序
		Set<String> sortedKeys = new TreeSet<String>();
		Set<String> orgKeys = valueSet.keySet();
		for(String key : orgKeys){
			sortedKeys.add(key);
			//System.out.println(key);
		}
		//排序结束
		
		//把参数排列成 key1=value1&key2=value2 的格式
		for(String key : sortedKeys){
			Object valueObject = valueSet.get(key);
			if( valueObject != null ){
				String valueString = valueObject.toString();
				if( valueString.length() > 0){
					if(valueStringBuilder.length() != 0){
						valueStringBuilder.append("&");
					}
					valueStringBuilder.append(key.toLowerCase()+"="+valueString);
				}
			}
		}
		//排列结束
		

        //注意这里参数名必须全部小写，且必须有序
      
        System.out.println(valueStringBuilder.toString());


            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(valueStringBuilder.toString().getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());

        return signature;
    }

	/**
	 * 
	 * @param hash
	 * @return
	 */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}

package nbBase.helper.common;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nbBase.database.common.nbBaseModel;

public class HttpWebIOHelper {
	
	//private CommonHelper commonHelper = new CommonHelper();

	public static void _printWebJson(Object theData, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(CommonHelper.getStringOfObj(theData));
		out.flush();
		out.close();
	}
	
	public static void printReturnXML(String xmlString, HttpServletResponse response) throws IOException{
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(xmlString);
		out.flush();
		out.close();
	}
	
	public static void printReturnText(String text, HttpServletResponse response) throws IOException{
		response.setContentType("text;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(text);
		out.flush();
		out.close();
	}
	
	public static void printReturnJson(nbReturn ret, HttpServletResponse response){
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("response.getWriter() ERROR!!!!!");
			return;
		}
		if( out == null ){
			System.out.println("printReturnJson() ERROR!!!!!");
			return;
		}
		out.print(CommonHelper.getStringOfObj(prepareRetAndData(ret)));
		out.flush();
		out.close();
	}
	
	public static void printStringOut(String theData, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(theData);
		out.flush();
		out.close();
	}
	
	public static String servletInputStream2String(HttpServletRequest request) throws IOException{
		request.setCharacterEncoding("utf-8");
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while( (line = reader.readLine()) != null){
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}
	
	public static Map<String, Object> servletInputStream2JsonMap(HttpServletRequest request){
		String jsonString = null;
		try {
			jsonString = CommonHelper.getObjectString( servletInputStream2String(request) );
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return (Map<String, Object>)JSONObject.parseObject(jsonString);
	}
	
	public static Class<?> servletInputStream2Object(HttpServletRequest request, Class<?> clazz) throws IOException{
		String jsonString = servletInputStream2String(request);
		return JSON.parseObject(jsonString, clazz);
	}
	
	public static Map<String, Object> prepareRetAndData(nbReturn ret){
		Object obj = ret.getObject();
		return prepareRetAndData(ret, obj);
	}
	
	public static Map<String, Object> prepareRetAndData(nbReturn ret, Object obj){
		Map<String, Object> theData = new HashMap<String, Object>();
		
		theData.put("retCode", ret.getRetCode());
		theData.put("retMessage", ret.getRetString());
		if( obj == null ){
			theData.put("retData", null);
			return theData;
		}
		if( obj instanceof nbBaseModel ){
			theData.put("retData", CommonHelper.getMapData(obj, null, null, null ) );
		}else if (obj instanceof List || obj instanceof Map ){
			theData.put("retData", obj);
		}else{
			theData.put("retData", obj);
		}
		
		return theData;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parseXMLParam(HttpServletRequest request) throws Exception{
		return parseXML(servletInputStream2String(request), null);
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
	
	public static List<FileItem> getFileItemsFromSevletRequest(HttpServletRequest request){
		try {
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			
			return items;
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static Integer getFormDataIntegerParameter(List<FileItem> items, String paramName) {
		for(FileItem item : items){
			if( paramName.equals(item.getFieldName()) ){
				String tmp = item.getString();
				if( tmp == null ) 
					return null;
				else
					return Integer.valueOf(tmp);
			}
		}
		
		return null;
		
	}
	
	private static Object retriveParamFromRequest(Map<String, Object> jsonMap, String key){
		if( jsonMap == null )
			return null;
		
		return jsonMap.get(key);
	}

	public static Boolean getJSONBoolean(Map<String, Object> jsonMap, String key, Boolean b) {
		Object ret = retriveParamFromRequest(jsonMap, key);
		if( ret == null )
			return b;
		else
			return Boolean.valueOf(ret.toString());

	}

	public static List<?> getJSONList(Map<String, Object> jsonMap, String key, List<?> defaultList) {
		Object ret = retriveParamFromRequest(jsonMap, key);
		if( ret == null )
			return defaultList;
		else
			return (List<?>) ret;
	}

	public static Integer getJSONInteger(Map<String, Object> jsonMap, String key, Integer defaultInteger) {
		Object ret = retriveParamFromRequest(jsonMap, key);
		if( ret == null )
			return defaultInteger;
		else
			return Integer.valueOf(ret.toString());
	
	}

	public static String getJSONString(Map<String, Object> jsonMap, String key, String defaultString) {
		Object ret = retriveParamFromRequest(jsonMap, key);
		if( ret == null )
			return defaultString;
		else
			return ret.toString();
	}
}

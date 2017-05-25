package nbBase.helper.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import nbBase.data.vo.VerifyCodePictureVo;
import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaUser;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.service.wechat._WechatKeyDefine;


public class CommonHelper {

	/**
	 * obj里边包含jpa对象会报错，要过滤一次。
	 * 
	 * @return
	 */
	public static String getStringOfObj(Object obj) {
//		SimplePropertyPreFilter spf = new SimplePropertyPreFilter();
//		//暂时不考虑持JPA对象报错的问题。以后可能需要加上
		return JSON.toJSONString(obj);
		
//		SerializeWriter sw = new SerializeWriter();
//		JSONSerializer serializer = new JSONSerializer(sw);
//		serializer.getPropertyFilters().add((PropertyFilter) spf);
//		serializer.write(obj);
//		return sw.toString();
	}
	
	/**
	 * 
	 * @param requestTemplate
	 * @param replacementList
	 * @return
	 */
	public static String replaceTheKeyWordForSMSSend(String requestTemplate, Map<String, String> replacementList) {
		
		Set<String> keys = replacementList.keySet();
		String toRet = requestTemplate;
		
		for( String key: keys){
			toRet = toRet.replace("{"+key+"}", replacementList.get(key));
		}
		
		return toRet;
	}
	
	public static String getFullPathOfCurrentRequest(HttpServletRequest request){
		String scheme =  request.getScheme();
		
		String server = request.getServerName();
		int port = request.getServerPort();
		String context = request.getContextPath();
		
		return scheme+"://"+server+(port==80?"":":"+port)+context;
	}
	
	public static String getFullPathWithParameters(HttpServletRequest request){
		
		String scheme =  request.getScheme();
		
		String server = request.getServerName();
		int port = request.getServerPort();
		String context = request.getContextPath();
		
		String parameter = request.getQueryString();
		if( !( parameter == null || parameter.length() == 0) )
			parameter = "?"+ parameter;
		return scheme+"://"+server+(port==80?"":":"+port)+context+request.getRequestURI() + parameter;
	}


	public static long getDaysDelta(Date afterTime, Date beforeTime) {
		
		return ((afterTime.getTime() - beforeTime.getTime())/1000/3600/24);
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	public static String getParamString(String[] target){
		if( target == null )
			return "";
		
		return target[0];
	}
	
	public static String getIpAddress(HttpServletRequest request) { 
		String ip = request.getHeader("x-forwarded-for"); 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("HTTP_CLIENT_IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getRemoteAddr(); 
		} 
		return ip; 
	} 
	
	
	/**
	 * 
	 * @param file
	 * @param contents
	 * @param barcodeFormat
	 * @param width
	 * @param height
	 * @param hints
	 * @throws Exception
	 */
	 public static void codeEncoder(
			 OutputStream outputStream,
			 String contents, 
			 BarcodeFormat barcodeFormat, 
			 int width,
			 int height, 
			 Map<EncodeHintType, Object> hints) throws Exception {
		 //System.out.println("0");
		// set default values
		if (barcodeFormat == null)
			barcodeFormat = BarcodeFormat.UPC_A;
		
		if (width <= 9)
			width = 300;
		if (height <= 9)
			height = 300;
		System.out.println("contents="+contents);
		
		// check the required parameters
//		if (file == null || file.getName().trim().isEmpty())
//			throw new IllegalArgumentException("File not found, or invalid file name.");
		if (contents == null || contents.trim().isEmpty())
			throw new IllegalArgumentException("Can't encode null or empty contents.");
		
		BitMatrix matrix;
		try {
			MultiFormatWriter barcodeWriter = new MultiFormatWriter();
			
			if ( hints != null && !hints.isEmpty() )
					matrix = barcodeWriter.encode(contents, barcodeFormat, width, height, hints);
			else
					matrix = barcodeWriter.encode(contents, barcodeFormat, width, height);
			System.out.println("encode 编码完成");
			MatrixToImageWriter.writeToStream(matrix, "jpeg", outputStream);
		
		} catch (Exception e) {
			System.out.println("抓到一个问题");
			throw new Exception(e.getMessage());
		}
	 }
	
	
	/**
	 * 
	 * @param file
	 * @param contents
	 * @param barcodeFormat
	 * @param width
	 * @param height
	 * @param hints
	 * @throws Exception
	 */
	 public static void codeEncoder(
			 String fileName, 
			 String contents, 
			 BarcodeFormat barcodeFormat, 
			 int width,
			 int height, 
			 Map<EncodeHintType, Object> hints) throws Exception {
		 //System.out.println("0");
		// set default values
		if (barcodeFormat == null)
			barcodeFormat = BarcodeFormat.UPC_A;
		
		if (width <= 9)
			width = 300;
		if (height <= 9)
			height = 300;
		//System.out.println("1");
		File file =  new File(fileName);
		//System.out.println("2");
		if( !file.exists() ){
			System.out.println(file.getAbsolutePath()+"没有找到，要创建");
			file.getParentFile().mkdirs();
			System.out.println(file.getParentFile()+"目录创建成功");
			file.createNewFile();
		}
		System.out.println("文件创建成功");
		System.out.println("contents="+contents);
		
		// check the required parameters
//		if (file == null || file.getName().trim().isEmpty())
//			throw new IllegalArgumentException("File not found, or invalid file name.");
		if (contents == null || contents.trim().isEmpty())
			throw new IllegalArgumentException("Can't encode null or empty contents.");
		
		BitMatrix matrix;
		try {
			MultiFormatWriter barcodeWriter = new MultiFormatWriter();
			
			if ( hints != null && !hints.isEmpty() )
					matrix = barcodeWriter.encode(contents, barcodeFormat, width, height, hints);
			else
					matrix = barcodeWriter.encode(contents, barcodeFormat, width, height);
			System.out.println("encode 编码完成");
			MatrixToImageWriter.writeToPath(matrix, "jpeg", file.toPath());
		
		} catch (Exception e) {
			System.out.println("抓到一个问题");
			throw new Exception(e.getMessage());
		}
	 }

	public static boolean saveImageFileFromBytes(String fileNameNoSuffix, String contentType, byte[] content) {
		String orgFilePath = fileNameNoSuffix + "." + contentType;
		String thumFilePath = fileNameNoSuffix + "_thum." + contentType;
		
		File orgFile = new File(orgFilePath);
		File thumFile = new File(thumFilePath);
		try {
			
			if( !orgFile.exists() ){
				orgFile.getParentFile().mkdirs();
				orgFile.createNewFile();
			}
			
			if( !thumFile.exists() ){
				thumFile.getParentFile().mkdirs();
				thumFile.createNewFile();
			}
			System.out.println("文件创建了："+orgFile.getAbsolutePath());
			 FileOutputStream outStream = new FileOutputStream(orgFile);
			 outStream.write(content);
			 outStream.close();
			 
			 ByteArrayInputStream in = new ByteArrayInputStream(content);    //将b作为输入流；
			 BufferedImage image = ImageIO.read(in);     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
			 float height = image.getHeight();
			 float width = image.getWidth();
			 
			 float ratio = width < height ? 100/height : 100/width ;
			 
			 int newHeight = (int)(height * ratio);
			 int newWidth = (int)(width * ratio);
			 
			 BufferedImage thum = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			 thum.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
			 
			 FileOutputStream fos = new FileOutputStream(thumFile);
		     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
		     encoder.encode(thum);
		     thum.flush();
		     fos.flush();
		     fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 把object中的成员放到map中
	 * @param theTarget 对象Object
	 * @param memberList 需要放进去的成员的名字列表 null = 全部放进去
	 * @param notList 不要放进去的成员的名字列表 null = 没有需要排除的成员
	 * @param isOnlyAllowPublic 是否只放public的成员进去， null = false
	 * @return
	 */
	public static Map<String, Object> getMapData(Object theTarget, String[] memberList, String[] notList, Boolean isOnlyAllowPublic) {

		Map<String, Object> theMap = new HashMap<String, Object>();
		
		Field[] fields = theTarget.getClass().getDeclaredFields();
		try{
			for( Field field : fields){
				
				boolean isAdd = false;
				
				if( memberList == null){
					isAdd = true; // 如果memberList 为空，那么所有的都要convert成map
				}else{
					for( String memberName : memberList){
						if( field.getName().equals(memberName)){
							isAdd = true; // 如果memberList不为空，那么只有在memberList中的变量才需要加入map中
						}
					}
				}
				
				if( notList != null){ //如果notList为空，那么没有不允许加入map中的变量
					for(String memberName : notList){
						if( field.getName().equals(memberName) ){
							isAdd = false;  //如果notList不为空，那么notList中的变量不能加入到map中
						}
					}
				}
				
				if( !isAdd )
					continue;
				
				
				boolean access = field.isAccessible();
				
				if( !access && isOnlyAllowPublic != null && isOnlyAllowPublic )
					continue; //如果只允许public的变量被放到map，且当前的变量并非public的话，那么就跳过 
				
				field.setAccessible(true);
				//从obj中获取field变量
				Object o = field.get(theTarget);
				theMap.put(field.getName(), o);
			    if(!access) field.setAccessible(false);
			    
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return theMap;
	}
	
	public static String getOpenIdFromSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> sessionTokenUserInfo = (Map<String, Object>) (session.getAttribute("currentWXUserinfo"));
		if( sessionTokenUserInfo == null )
			return null;
		if( sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid) == null )
			return null;
		
		return sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid).toString();
	}
	
	public static Integer getEventIdFromSession(HttpServletRequest request){
		
		Map<String, Object> jsonMap;
		try {
			jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);

			 if( jsonMap == null )
				 return null;
			 
			 if( jsonMap.get("eventId") == null ){
				 return null;
			 }
			 
			 Integer eventId = Integer.valueOf( jsonMap.get("eventId").toString() );
			 
			return eventId;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return null;
	}
	
	public static int generateRandom(int max, int min, int avoid){
		int ret = (int) Math.round(Math.random() * (max - min) + min);
		while( ret == avoid ){
			//如果混淆用的单词正好和对象单词一样，或和之前的对象单词一样，就重现找混淆用的单词
			ret = (int) Math.round(Math.random() * (max - min) + min);
		}
		return ret;
	}
	
	
	public static String getObjectString(Object obj){
		return obj == null ? "" : obj.toString();
	}
	
	
	public static Integer getObjectInteger(Object obj){
		return obj == null ? 0 : Integer.valueOf(obj.toString());
	}
	
	
	public static Boolean getObjectBoolean(Object obj, boolean nullValue){
		return obj == null ? nullValue : Boolean.valueOf(obj.toString());
	}
	
	
	public static Long getObjectLong(Object obj){
		return obj == null ? 0l : Long.valueOf(obj.toString());
	}
	
	
	public static List<Class<?>> getObjectList(List<Class<?>> obj){
		return obj == null ? new ArrayList<Class<?>>() : obj;
	}

	
	@SuppressWarnings("unchecked")
	public static JSONObject getObjectJSONObject(Object obj) {
		if( obj == null )
			return new JSONObject();
		
		return (JSONObject) obj;
	}

	public static ZaAdminUser getAdminUserFromSession(HttpServletRequest httpServletRequest) {
		
		HttpSession session = httpServletRequest.getSession();
		ZaAdminUser user = (ZaAdminUser) session.getAttribute("currentUserInfo"); 
		return user;
	}

	public static String getUsePerpose(String servletPath) {
		String[] dirs = servletPath.split("/");
		for(String dir : dirs){
			if( dir.length() > 0 ){
				return dir;
			}
		}
		return null;
	}

	public static String loadAppSpecifiedConfig(String key) {
		Properties props = new Properties();
		
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app_config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return props.getProperty(key, null);
	}
	
	public static boolean isListNullOrEmpty(List<?> target){
		if( target == null || target.size() == 0)
			return true;
		return false;
	}
	
	public String getRemortIP(HttpServletRequest request) {  
		if (request.getHeader("x-forwarded-for") == null) {  
			return request.getRemoteAddr();  
		}  
		
		return request.getHeader("x-forwarded-for");  
	}  
	
	
	 public static File getClassFile(Class clazz){
	        URL path = clazz.getResource(clazz.getName().substring(
	                clazz.getName().lastIndexOf(".")+1)+".classs");
	        if(path == null){
	            String name = clazz.getName().replaceAll("[.]", "/");
	            path = clazz.getResource("/"+name+".class");
	        }
	        return new File(path.getFile());
	    }
	 
	
	public static String getPackageRootName(Class clazz){
	    try{
            String ret =  java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(),"UTF-8");
            String[] rets = ret.split("/");
            for( int i = 0 ; i < rets.length; i++){
            	if( "WEB-INF".equals(rets[i].toUpperCase()) ){
            		ret = rets[i+2];
            		break;
            	}
            }
            System.out.println(ret);
            return ret;
        }catch (Exception e) {        	
            e.printStackTrace();
            return "";
        }
	}
	
	public static void filePathCheckAndCreate(String filePath) {
		File file = new File(filePath);
		if( !file.getParentFile().exists() ){
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static boolean renameFile(String source, String target) {
		File srcFile = new File(source);
		File tarFile = new File(target);
		
		if( !srcFile.exists() )
			return false;
		if( tarFile.exists() )
			return false;
		
		return srcFile.renameTo(tarFile);
	}

	public static byte[] loadBytesFromFile(String filePath) {
		Path path = Paths.get(filePath);
		try {
			byte[] data = Files.readAllBytes(path);
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean resizeImageFromBytes(String targetFilePath, String targetEncodeType, byte[] imgFileBytes, int twidth, int theight) {
		
		BufferedImage srcImg = null;
		if( imgFileBytes == null )
			return false;
		
		try {
			srcImg = ImageIO.read(new ByteArrayInputStream(imgFileBytes));
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		BufferedImage desImg = null;
		
		int srcWidth = srcImg.getWidth();
		int srcHeight = srcImg.getHeight();
		
		double widthRasio = ((double)srcWidth)/((double)twidth);
		double heightRasio = ((double)srcHeight)/((double)theight);
		double rasio = widthRasio > heightRasio ? widthRasio : heightRasio;
		twidth = (int) ((double)srcWidth/rasio);
		theight = (int) ((double)srcHeight/rasio);
		
		desImg = new BufferedImage(twidth,theight,BufferedImage.TYPE_INT_RGB);
		desImg.getGraphics().drawImage(srcImg, 0, 0, twidth, theight, null);
		try {
			CommonHelper.filePathCheckAndCreate(targetFilePath);
			ImageIO.write(desImg, targetEncodeType, new File(targetFilePath));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @param pictureVerifyCode
	 * @param session
	 * @param isOKToClear
	 * @return
	 */
	static public nbReturn checkVerifyCodePicture(String pictureVerifyCode, HttpSession session, boolean isOKToClear){
		
		nbReturn nbRet = new nbReturn();
		
		pictureVerifyCode = pictureVerifyCode.toUpperCase();
		     
        String verifyCode = (String) session.getAttribute("verifyCode");
        Date expireTime = (Date) session.getAttribute("verifyCodeExpireTime");
        Calendar cal = Calendar.getInstance();
        Date currentTime = cal.getTime();
        
        if( expireTime== null || expireTime.before(currentTime)){
        	nbRet.setError(ReturnCode.VERIFYCODE_EXPIRED);
        	return nbRet;
        }
        
        if( !verifyCode.equals(pictureVerifyCode)){
        	nbRet.setError(ReturnCode.VERIFYCODE_WRONG);
        	return nbRet;
        }
        
        if( isOKToClear ){
        	clearVerifyCodePictureInSession(session);
        }
        
        return nbRet;
		
	}
	
	/**
	 * 
	 * @param session
	 */
	static public void clearVerifyCodePictureInSession(HttpSession session){
		 session.removeAttribute("verifyCode");
	     session.removeAttribute("verifyCodeExpireTime");
	}

	
	/**
	 * 
	 * @param session
	 * @return
	 */
	static public ZaUser getCurrentUserInfoFromSession(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		
		ZaUser user =(ZaUser) session.getAttribute("CurrentUserInfoVo");
		
		return user;
	}
	
	/**
	 * 验证密码格式是否符合要求
	 * @param password
	 * @param confirmPassword
	 * @return
	 */
	
	public static nbReturn verifyPasswordFormat(String password, String confirmPassword){
		nbReturn nbRet = new nbReturn();
		if(password.length() < 6){
			nbRet.setError(ReturnCode.PASSWORD_LENGTH_NOT_ENOUGH);
			return nbRet;
		}
		
		if(confirmPassword!=null && !confirmPassword.equals(password)){
			nbRet.setError(ReturnCode.CONFIRMPASSWORD_NOT_SAME_WITH_PASSWORD);
			return nbRet;
		}
		
		return nbRet;
	}

	public static nbReturn checkVerifyCodePicture(String verifyCode, HttpServletRequest request, boolean isOKToClear) {
		HttpSession session = request.getSession();
		return CommonHelper.checkVerifyCodePicture(verifyCode, session, isOKToClear);
	}

	public static void setCurrentUserInfoFromSession(HttpServletRequest request, ZaUser user) {
		HttpSession session = request.getSession();
		session.setAttribute("CurrentUserInfoVo", user);
		
	}
	
	/**
	 * 转换get的Stream为Map格式的键值对参数表 
	 * @param queryString
	 * @return
	 */
	
	public static nbReturn pharseGetParameters(String queryString){
		nbReturn nbRet = new nbReturn();
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String[] parameters = queryString.split("&");
		
		for( int i = 0 ; i < parameters.length; i++){
			String[] keyvalue = parameters[i].split("=");
			if( keyvalue.length == 2){
				retMap.put(keyvalue[0], keyvalue[1]);
			}
		}
		nbRet.setObject(retMap);
		return nbRet;
	}

	
	/**
	 * 生成图片验证码
	 * @param verifyCodeWidth
	 * @param verifyCodeHeight
	 * @return
	 */
	public static nbReturn createVerifyCodePicture(Long verifyCodeWidth, Long verifyCodeHeight){
		
		int codeCount = 4;
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 

		
		nbReturn nbRet = new nbReturn();
		
		 // 定义图像buffer 
        BufferedImage buffImg = new BufferedImage(verifyCodeWidth.intValue(), verifyCodeHeight.intValue(), BufferedImage.TYPE_INT_RGB);         
        Graphics2D g = buffImg.createGraphics();         
        // 创建一个随机数生成器类         
        Random random = new Random();         
        // 将图像填充为白色         
        g.setColor(Color.WHITE);         
        g.fillRect(0, 0, verifyCodeWidth.intValue(), verifyCodeHeight.intValue());         
        // 创建字体，字体的大小应该根据图片的高度来定。         
        Font font = new Font("Fixedsys", Font.PLAIN, verifyCodeHeight.intValue()-2);         
        // 设置字体。         
        g.setFont(font);   
        // 画边框。         
        g.setColor(Color.BLACK); 
        g.drawRect(0, 0, verifyCodeWidth.intValue() - 1, verifyCodeHeight.intValue() - 1);
        
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。         
        g.setColor(Color.BLACK);         
        for (int i = 0; i < 10; i++) {         
            int x = random.nextInt(verifyCodeWidth.intValue());         
            int y = random.nextInt(verifyCodeHeight.intValue());         
            int xl = random.nextInt(12);         
            int yl = random.nextInt(12);         
            g.drawLine(x, y, x + xl, y + yl);         
        }         
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。         
        StringBuffer randomCode = new StringBuffer();         
        int red = 0, green = 0, blue = 0;         
        // 随机产生codeCount数字的验证码。         
        for (int i = 0; i < codeCount; i++) {         
            // 得到随机产生的验证码数字。         
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);         
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。         
            red = random.nextInt(255);         
            green = random.nextInt(255);         
            blue = random.nextInt(255);         
            // 用随机产生的颜色将验证码绘制到图像中。         
            g.setColor(new Color(red, green, blue));         
            g.drawString(strRand, i * ((verifyCodeWidth.intValue()-4) / (codeCount))+2, verifyCodeHeight.intValue()-4);         
            // 将产生的四个随机数组合在一起。         
            randomCode.append(strRand);         
        }         
        
        VerifyCodePictureVo verifyCodePictureVo = new VerifyCodePictureVo();
        verifyCodePictureVo.setBuffImg(buffImg);
        verifyCodePictureVo.setVerifyCode(randomCode.toString());
        nbRet.setObject(verifyCodePictureVo);
        
        return nbRet;
	}
	
	/**
	 * 生成一个length长度的随机数
	 * @param length 最长8位，最短4位
	 * @return 生成的随机数
	 */
	public static String generateRandomDigit(int length){
		char[] charset = {'0','1','2','3','4','5','6','7','8','9'};
		int charsetLength = charset.length;
		
		Random random = new Random();
		char[] rand = new char[length];
		for(int i = 0 ; i < length ; i++){
			Random randoom = new Random(random.nextInt());
			rand[i] = charset[Math.abs(randoom.nextInt()) % charsetLength];//(char)(randoom.nextInt() % 9 + '0');
		}
		return String.valueOf(rand);
	}
	
	/**
	 * 发送一条短信出去
	 * @param phoneNumber
	 * @param theCodeToBeSend
	 * @return
	 */
	public static nbReturn sendSMSNotification(String phoneNumber,String theText) {
		
		nbReturn nbRet = new nbReturn();
		System.out.println("Send SMS: " + phoneNumber + " with test" + theText);
		
		//TODO:这里发短信出去
//		SMS189Utils sms189Utils = new SMS189Utils();
//		nbRet = sms189Utils.sendTheSMS(phoneNumber, theCodeToBeSend);
		
		return nbRet;
	}

}

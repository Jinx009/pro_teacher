package other.helper.other;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import nbBase.helper.common.nbReturn;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatPageSDK;
import nbBase.service.wechat._WechatUtils;

public class casioEventHelper {
	
	private static BufferedImage clipImg(BufferedImage bi, int rasioX, int rasioY){
		
		int width = bi.getWidth();
		int height = bi.getHeight();
		int tWidth = 0;
		int tHeight = 0;
		
		boolean isLay = (width > height );
		
		if( isLay ){
			tHeight = height;
			tWidth = tHeight*rasioX/rasioY;
		}else{
			tWidth = width;
			tHeight = tWidth*rasioY/rasioX;
		}
		
		BufferedImage ImageNew = null; 
		ImageNew = new BufferedImage(tWidth,tHeight,BufferedImage.TYPE_INT_RGB);
		ImageNew.getGraphics().drawImage(bi, (tWidth-width)/2, (tHeight-height)/2, width, height, null);
		System.out.println("tWidth="+tWidth+";tHeight="+tHeight+";width="+width+";height="+height+";");
		
		return ImageNew;
		
	}
	
	public static nbReturn mergeImgFiles(BufferedImage bi01, BufferedImage bi02, String targetFilename) throws IOException{
		
		nbReturn nbRet = new nbReturn();
		
		bi01 = clipImg(bi01, 3, 4);
		bi02 = clipImg(bi02, 3, 4);
		
		
		int width01 = bi01.getWidth();
		int width02 = bi02.getWidth();
		int height01 = bi01.getHeight();
		int height02 = bi02.getHeight();
		
		
		BufferedImage ImageNew = null; 
		BufferedImage ImageARGBNew = null;
//		if(isLay01 == isLay02){ //同横、同竖
//			if( isLay01 ){ //都是横的
//				int targetWidth = width01 > width02 ? width02 : width01;
//				int targetHeight01 = targetWidth *  height01 / width01;
//				int targetHeight02 = targetWidth *  height02 / width02;
//				
//				ImageNew = new BufferedImage(targetWidth,targetHeight01+targetHeight02,BufferedImage.TYPE_INT_RGB);
//				ImageNew.getGraphics().drawImage(bi01, 0, 0, targetWidth, targetHeight01, null);
//				ImageNew.getGraphics().drawImage(bi02, 0, targetHeight01, targetWidth, targetHeight02, null);
//				
//			}else{//都是竖的
				
				int targetHeight = height01 > height02 ? height02 : height01;
				int targetWidth01 = targetHeight *  width01 / height01;
				int targetWidth02 = targetHeight *  width02 / height02;
				
				ImageARGBNew = new BufferedImage(targetWidth01+targetWidth02,targetHeight,BufferedImage.TYPE_INT_ARGB); 
				ImageARGBNew.getGraphics().drawImage(bi01, 0, 0, targetWidth01, targetHeight, null);
				ImageARGBNew.getGraphics().drawImage(bi02, targetWidth01, 0, targetWidth02, targetHeight, null);
				
				InputStream kvStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("resource/other/kv_template3.png");
				BufferedImage kvImage = ImageIO.read(kvStream);
				ImageARGBNew.getGraphics().drawImage(kvImage, 0, 0, targetWidth01+targetWidth02, targetHeight, null);
				
				ImageNew = new BufferedImage(targetWidth01+targetWidth02,targetHeight,BufferedImage.TYPE_INT_RGB);
				ColorConvertOp xformOp = new ColorConvertOp(null);
				xformOp.filter(ImageARGBNew,ImageNew);
//				
//			}
//		}else{
//			//一横一竖
//			//TODO:
//		}
		
		if( ImageNew != null ){
			ImageIO.write(ImageNew, "jpeg", new File(targetFilename));
		}
		
		
		return nbRet;
	}
	
	/**
	 * 把两个本地文件合并成一个文件
	 * @param filename1
	 * @param filename2
	 * @param targetFilename
	 * @return
	 * @throws IOException
	 */
	public static nbReturn mergeImgFiles(String filename1, String filename2, String targetFilename) throws IOException{
		
		BufferedImage bi01 = ImageIO.read(new File(filename1));
		BufferedImage bi02 = ImageIO.read(new File(filename2));
		
		return mergeImgFiles(bi01, bi02, targetFilename);
	}
	
	/**
	 * 把两个Img的byte[]合并成一个文件
	 * @param bytes01
	 * @param bytes02
	 * @param targetFilename
	 * @return
	 * @throws IOException
	 */
	public static nbReturn mergeImgFiles(byte[] bytes01, byte[] bytes02, String targetFilename) throws IOException{
		
		BufferedImage bi01 = ImageIO.read(new ByteArrayInputStream(bytes01));
		BufferedImage bi02 = ImageIO.read(new ByteArrayInputStream(bytes02));
		
		return mergeImgFiles(bi01, bi02, targetFilename);
		
	}
	
	public static byte[] readFileImage(String filename) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(filename));
		int len = bufferedInputStream.available();
		byte[] bytes = new byte[len];
		int r = bufferedInputStream.read(bytes);
		if (len != r) {
			bytes = null;
			throw new IOException("读取文件["+filename+"]不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}
	
	public static byte[] getFileFromWxServer(String serverId, WechatConfigure wxCon){
		
		WechatPageSDK wxPage = new WechatPageSDK(wxCon);
		byte[] content = null;
		
		try {
			
			if( ! wxPage.getPageAccessToken() )
				return null;
			
			String pageToken = wxPage.getPage_access_token();
			
			String url = wxCon.getFetchWxImageUrl(pageToken, serverId);
			
			Map<String, Object> ret = _WechatUtils.httpGetBytes(url);
			
			if( ret == null || ret.get("contentType") == null || ret.get("content") == null){
				return null;
			}
			
			String contentType = (String) ret.get("contentType");
			String[] tmp = contentType.split("/");
			contentType = tmp[tmp.length-1].toLowerCase();
			content = (byte[]) ret.get("content");
		
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return content;
		
	}

	public static WechatConfigure prepareWxConfig() {
		
		WechatConfigure wxcon = new WechatConfigure(null);
		
		wxcon.payKey = "";
		wxcon.mchId = "";
		wxcon.wxappid = "wx9b3d1cb48fb48ff4";
		wxcon.certFile_p12 = "";
		wxcon.server_ip = "127.0.0.1";
		wxcon.appSecret = "188be723d1bab5a44a7fdeeff06cc0f3";
		wxcon.orderDefaultExpireTime = 7200l;
		wxcon.wxPayNotifyUrl = "";
		wxcon.encodingAESKey = "";
		wxcon.idInDB = 0;
		wxcon.serverName = "http://pc.0angle.com";
		wxcon.tplMsgPaySuccess = "";
		wxcon.tplMsgMatchResultConfirm = "";
		wxcon.resourcePath = "/usr/local/www/pc-res.0angle.com";
		wxcon.resourceBrowsPath = "http://pc-res.0angle.com";
		wxcon.certFileBin = null;
		wxcon.companyLogoUrl = "";
		wxcon.configName = "casio";
		return wxcon;
	}

}

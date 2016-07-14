package nbBase.service.common;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nbBase.dao.ZaUserWxDao;
import nbBase.dao.ZaWxPayUnionOrderDao;
import nbBase.dao.common._SendAwardLogDao;
import nbBase.dao.common._WxConfigDao;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.database.models.ZaSendAwardLog;
import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import nbBase.helper.common.nbStringUtil;
import nbBase.service.wechat.WechatConfigure;
import nbBase.service.wechat.WechatMchPay;
import nbBase.service.wechat.WechatPageSDK;
import nbBase.service.wechat.WechatTemplateMsgValueSet;
import nbBase.service.wechat._WechatKeyDefine;
import nbBase.service.wechat._WechatUtils;
import nbBase.service.wechat._WxIF_Configure;


@Service("wxCommonService")
public class WxCommonServiceImpl implements WxCommonService, If_ErrorCheckService, _WxIF_Configure {

	
	@Autowired
	ZaUserWxDao userWxDao;
	
	@Autowired
	ZaWxPayUnionOrderDao wxPayUnionOrderDao;
	
	@Autowired
	_WxConfigDao wxConfigDao;
	
	@Autowired
	_SendAwardLogDao sendAwardLogDao;
	
	private Integer lastErrCode;
	private String lastErrMsg;
	
	@Override
	public Integer getLastErrCode() {
		return lastErrCode;
	}

	@Override
	public String getLastErrMsg() {
		return lastErrMsg;
	}

	@Override
	public void SetLastError(Integer code, String msg) {
		this.lastErrCode = code;
		this.lastErrMsg = msg;
	}


	private WechatConfigure loadWxConfigure(Integer configureId) {
		if( configureId == null )
			configureId = 1;
		
		ZaFrontWxConfig wxc = wxConfigDao.find(configureId);

		return DataWrapUtil.wrapWxConfigMap(wxc, this);
		
	}
	
	/**
	 * 获取当前的wx配置信息
	 * @param request
	 * @param configId null：获取默认的，0 仅获取session中的，不去读数据库
	 * @return
	 */
	@Deprecated
	@Override
	public WechatConfigure loadWxConfigure(HttpServletRequest request, Integer configId){
		
		HttpSession session = request.getSession();
		WechatConfigure wxCon = (WechatConfigure) session.getAttribute("currentWXConfig");
		
		if( configId != null && configId == 0 ){
			return wxCon;
		}
		
		if( configId == null && wxCon != null){
			return wxCon;
		}
		
			wxCon = this.loadWxConfigure(configId);
			if( wxCon != null ){
				session.setAttribute("currentWXConfig", wxCon);
				return wxCon;
			}
		
		return null;
	}
	
	@Override
	public nbReturn sendAward(
			String sendName,
			String actName,
			String wishing,
			String clientIp,
			String remark,
			String reOpenid,
			Integer totalAmount,
			Integer totalNum,
			Map<String, Object> sessionTokenUserInfo, 
			String additionalRemark,
			WechatConfigure wxCon) throws Exception {
		
		WechatMchPay wechatSendAward = new WechatMchPay(wxCon);
		
		nbReturn nbRet = new nbReturn();
		
		if( sessionTokenUserInfo != null && reOpenid == null){
			reOpenid = sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid) == null ? null :(String)sessionTokenUserInfo.get(_WechatKeyDefine.wxUserInfo.openid);
			if( reOpenid == null ){
				nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
				return nbRet;
			}
		}
		
		ZaSendAwardLog sal = new ZaSendAwardLog();
		sal.setActName(actName);
		sal.setAdditionalRemark(additionalRemark);
		sal.setClientIp(clientIp == null ? wxCon.server_ip : clientIp);
		sal.setMchBillno(_WechatUtils.createBillNumber(wxCon.mchId));
		sal.setMchId(wxCon.mchId);
		sal.setNonceStr(_WechatUtils.createUniqueString() );
		sal.setRemark(remark);
		sal.setReOpenid(reOpenid);
		sal.setSendName(sendName);
//		sal.setSendResult(sendResult);
//		sal.setSendResultMsg(sendResultMsg);
		Calendar cal = Calendar.getInstance();
		sal.setSendTime(cal.getTime());
		sal.setTotalAmount(totalAmount);
		sal.setTotalNum(totalNum);
		sal.setWishing(wishing);
		sal.setWxappid(wxCon.wxappid);
		
		sal = this.sendAwardLogDao.save(sal);
		
		Map<String, Object> retMessage = wechatSendAward.sendAward(sal);
		
		this.lastErrCode = ((String) retMessage.get("return_code")).toLowerCase().equals("success") ? 0 : 16;
		if( lastErrCode == 0)
			this.lastErrCode = ((String) retMessage.get("result_code")).toLowerCase().equals("fail") ? 16 : 0;
	
		this.lastErrMsg = (String) retMessage.get("return_msg");
		
		nbRet.setError(Long.valueOf(lastErrCode), lastErrCode);
		
		if( lastErrCode != 0){
			switch(((String) retMessage.get("err_code")).toUpperCase()){
			case "NOTENOUGH":
				nbRet.setError(ReturnCode.NOT_ENOUGH);
				break;
			case "SENDNUM_LIMIT":
				nbRet.setError(ReturnCode.SENDNUM_LIMIT);
				break;
			}
		}
		
		sal.setSendResult(this.lastErrCode);
		sal.setSendResultMsg(this.lastErrMsg);
		
		this.sendAwardLogDao.update(sal);
		
		
		nbRet.setObject(retMessage);
		
		return nbRet;
		
	}

	
	@Override
	public nbReturn sendTplMessage(
			String template_id, 
			String url, 
			Boolean needJumpAuth, 
			String first_data,
			String[] keywords, 
			String remark_data,
			String openid,
			WechatConfigure wxCon) throws Exception {
		
		nbReturn nbRet = new nbReturn();
		
		WechatTemplateMsgValueSet  wtmvs = new WechatTemplateMsgValueSet(wxCon);
		wtmvs.setTemplateId(template_id);
		wtmvs.setUrl(url, needJumpAuth);
		wtmvs.setFirstData(first_data, null);
		wtmvs.setKeywords(keywords, null);
		wtmvs.setRemarkData(remark_data, null);
		wtmvs.put(_WechatKeyDefine.wxTplMsg.touser, openid);
		
		WechatPageSDK jsSDK = new WechatPageSDK(wxCon);
		
		if(	!jsSDK.postTemplateMsg(	wtmvs) )
		{
			nbRet.setError(Long.valueOf(jsSDK.getLastErrorcode()), jsSDK.getLastErrormsg());
		}
		
		return nbRet;
	}
	
	@Override
	public WechatConfigure loadWxConfigureByAppId(String appId) {
		
		ZaFrontWxConfig wxc = wxConfigDao.findByAppId(appId);
		
		
		return DataWrapUtil.wrapWxConfigMap(wxc, this);
	}

	@Override
	public void insertCertFileToVarbinary(int wxConfigId) throws Exception {
		if( wxConfigId <= 0 )
			return;
		
		ZaFrontWxConfig wxConfig = this.wxConfigDao.find(wxConfigId);
		if( wxConfig == null )
			return;
		
		File certFile = new File(wxConfig.getCertFile_p12());
		
		@SuppressWarnings("resource")
		FileInputStream fis = new FileInputStream(certFile);
		int got = 0;
		byte[] data = new byte[(int)certFile.length()];
		int startIndex = 0;
		while( got != -1){
			got = fis.read(data, startIndex, (int)certFile.length()-startIndex);
			if( got != -1){
				startIndex += got;
				if( got == (int)certFile.length() ){
					break;
				}
			}
		}
		fis.close();
		
		 wxConfig.setCertP12Bin(data);
		 
		 this.wxConfigDao.update(wxConfig);
	}

	@Override
	public String[] saveWxImgToLocalServer(String serverId, WechatConfigure wxCon) throws Exception {
		String[] retUrl = new String[2];
		
		if( wxCon == null ){
			return null;
		}
		
		if( nbStringUtil.isNullEmpty(serverId) )
			return null;
		
		WechatPageSDK wxPage = new WechatPageSDK(wxCon);
		
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
		byte[] content = (byte[]) ret.get("content");
		
		if( !CommonHelper.saveImageFileFromBytes(wxCon.resourcePath+"/wxImg/"+serverId, contentType, content) )
			return null;
		
		retUrl[0] = wxCon.resourceBrowsPath + "/wxImg/" + serverId + "_thum." + contentType;
		retUrl[1] = wxCon.resourceBrowsPath + "/wxImg/" + serverId + "." + contentType;
		
		return retUrl;
	}
	
	@Override
	public nbReturn loadDefaultConfig() {
		nbReturn nbRet = new nbReturn();
		ZaFrontWxConfig wxCon = null;

			//从app_config.properties文件里获取配置好的默认微信公众号ID
			String defaultWxAppId = CommonHelper.loadAppSpecifiedConfig("wxAppId");
			if( nbStringUtil.isNullEmpty(defaultWxAppId) ){
				wxCon = this.wxConfigDao.findByAppId(defaultWxAppId);
			}
			//如果从app_config.properties获取公众号失败
			if( wxCon == null ){
				wxCon = this.wxConfigDao.findByDefault();
			}
			nbRet.setObject(DataWrapUtil.wrapWxConfigMap(wxCon, this));
		
		return nbRet;
		
	}

	

	@Override
	public boolean updatePageToken(int id, String pageToken, long pageTokenExpire) {
		
		ZaFrontWxConfig wxc = this.wxConfigDao.find(id);
		
		if( wxc == null){
			return false;
		}
		
		wxc.setCurrentPageToken(pageToken);
		wxc.setCurrentPageTokenExpire(pageTokenExpire);
		
		wxc = this.wxConfigDao.update(wxc);
		
		return true;

	}

	@Override
	public nbReturn transferMoney(String reason, String openId, int amountInCent, WechatConfigure wxCon, String remark) throws Exception {
		nbReturn nbRet = new nbReturn();
		
		if(openId == null || wxCon == null || amountInCent == 0){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		int toPay = amountInCent;
		
		WechatMchPay wechatMchPay = new WechatMchPay(wxCon);
		
		
		ZaSendAwardLog sal = new ZaSendAwardLog();
		sal.setAdditionalRemark(remark);
		sal.setActName("转账");
		sal.setClientIp(wxCon.server_ip);
		sal.setMchBillno(_WechatUtils.createBillNumber(wxCon.mchId));
		sal.setMchId(wxCon.mchId);
		sal.setNonceStr(_WechatUtils.createUniqueString() );
		sal.setRemark(reason);
		sal.setReOpenid(openId);
		sal.setSendName("转账");
//		sal.setSendResult(sendResult);
//		sal.setSendResultMsg(sendResultMsg);
		Calendar cal = Calendar.getInstance();
		sal.setSendTime(cal.getTime());
		sal.setTotalAmount(toPay);
		sal.setTotalNum(1);
		sal.setWishing("");
		sal.setWxappid(wxCon.wxappid);
		
		sal = this.sendAwardLogDao.save(sal);
		
		Map<String, Object> retMessage = wechatMchPay.transferMoney(openId, sal.getTotalAmount(), reason, sal.getClientIp());
		
		this.lastErrCode = ((String) retMessage.get("return_code")).toLowerCase().equals("success") ? 0 : 16;
		if( lastErrCode == 0)
			this.lastErrCode = ((String) retMessage.get("result_code")).toLowerCase().equals("fail") ? 16 : 0;
	
		this.lastErrMsg = (String) retMessage.get("return_msg");
		
		nbRet.setError(Long.valueOf(lastErrCode), lastErrCode);
		
		if( lastErrCode != 0){
			switch(((String) retMessage.get("err_code")).toUpperCase()){
			case "NOTENOUGH":
				nbRet.setError(ReturnCode.NOT_ENOUGH);
				break;
			case "SENDNUM_LIMIT":
				nbRet.setError(ReturnCode.SENDNUM_LIMIT);
				break;
			}
		}
		
		sal.setSendResult(this.lastErrCode);
		sal.setSendResultMsg(this.lastErrMsg);
		
		this.sendAwardLogDao.update(sal);
		
		
		return nbRet;
	}

	@Override
	public ZaFrontWxConfig loadZaWxConfigure(Integer wxConfigureId) {
		if( wxConfigureId == null || 0 == wxConfigureId){
			return null;
		}
		return this.wxConfigDao.find(wxConfigureId);
	}
	
	@Override
	public nbReturn uploadFile(List<FileItem> items, String targetAFileName, String storagePath, String browseUrlPath) throws Exception {
		
		nbReturn nbRet = new nbReturn();
		
		List<String> succeedFileList = new ArrayList<String>();
		
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = iter.next();
		    if( item.getName() == null || "".equals(item.getName())){
		    	continue;
		    }
		    if (item.isFormField()) {
		    	System.out.println("收到终端要上传FormField类型内容:");
		    		InputStream in =  item.getInputStream();
		    		byte[] bt = new byte[in.available()];
		    		in.read(bt);
		    		in.close();
		    		//获取文本域值
		    		String desc1 = new String(bt,"utf-8");
		    	System.out.println(item.getFieldName() + " = " +desc1);
		    	System.out.println("---------不做处理-----------");
		    	
		    } else {
		    	//打印保存上传文件路径
		        //System.out.println(request.getServletContext().getRealPath("upload"));
		        if(item.getName()!=null&&!"".equals(item.getName())){
		         String newName = item.getName();
		         //客户端文件
		         //File remoteFile = new File(item.getName());
		         //定义服务器下文件
		         String aFileName = (targetAFileName == null ? (storagePath==null? null : storagePath+"/"+newName):targetAFileName);
		         if( aFileName == null ){
		        	 nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
		        	 return nbRet;
		         }
		         File servFile = new File(aFileName);
		         //创建文件夹
		         servFile.getParentFile().mkdirs();
		         //检查文件是否已经存在，然后不断自增直到文件不存在为止
		         int index = 1;
		         while( servFile.exists() && targetAFileName == null){
		        	 String[] names = item.getName().split("\\.");
		        	 newName = names[names.length>1?names.length-2:0];
		        	 newName += "("+index+++")";
		        	 names[names.length>1?names.length-2:0] = newName;
		        	 newName = "";
		        	 for(String name : names){
		        		 if(newName.length() > 0 )
		        			 newName += ".";
		        		 newName += name;
		        	 }
		        	 aFileName = storagePath+"/"+newName;
		        	 servFile = new File(aFileName);
		         }
		         System.out.println("文件【"+item.getName()+"】将保存到:"+aFileName);
		         //读取客户端文件输入流
		         InputStream in =  item.getInputStream();
		         //定义写入服务器文件流
		         OutputStream os = new FileOutputStream(servFile);
		         //定义字节数组
		         byte[] bt = new byte[in.available()];
		         //对流进行读和写
		         for (;;) {
		        	 int len = in.read(bt);
		        	 if(len==-1){
		        		 break;
		        	 }
		        	 os.write(bt, 0, len);
		         }
		         //关闭输入输出流
		         if(os!=null){
		          os.flush();
		          os.close();
		         }
		         if(in!=null){
		          in.close();
		         }
		         succeedFileList.add(browseUrlPath == null ? aFileName : browseUrlPath + "/" + newName );
		       }
		}
	}
		
		nbRet.setObject(succeedFileList);
	
		
		return nbRet;
	}

	@Override
	public boolean updateJSTicket(int id, String jsTicket, long jsTicketExpire) {
		
		ZaFrontWxConfig wxc = this.wxConfigDao.find(id);
		
		if( wxc == null){
			return false;
		}
		
		wxc.setCurrentJsTicket(jsTicket);
		wxc.setCurrentJsTicketExpire(jsTicketExpire);
		
		wxc = this.wxConfigDao.update(wxc);
		
		return true;
	}

	@Override
	public ZaFrontUserWx loadWxUserByOpenId(String openId) {
		return this.userWxDao.findByOpenId(openId);
	}

	@Override
	public List<Map<String, Object>> saveWxImgToLocalServer(List<String> picList, WechatConfigure wxCon) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for(String wxServerId : picList){
			Map<String, Object> one = new HashMap<String, Object>();
			String[] newRet = null;
			try {
				newRet = this.saveWxImgToLocalServer(wxServerId, wxCon);
				one.put("imgUrl", newRet[1]);
				one.put("thumUrl", newRet[0]);
				ret.add(one);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public ZaFrontUserWx fakeAWxUser(ZaFrontUserWx wxUser) {
		if( wxUser != null )
			return wxUser;
		else{
			if( Boolean.valueOf(CommonHelper.loadAppSpecifiedConfig("isFakingWxUser")) )
				return this.loadWxUserByOpenId(CommonHelper.loadAppSpecifiedConfig("fakeWxUserOpenId"));
			else
				return null;
		}
	}

	@Override
	public boolean doUpdatePageToken(int id, String pageToken, long pageTokenExpire) {
		return this.updatePageToken(id, pageToken, pageTokenExpire);
	}

	@Override
	public boolean doUpdateJSTicket(int id, String jsTicket, long jsTicketExpire) {
		return this.updateJSTicket(id, jsTicket, jsTicketExpire);
	}

}

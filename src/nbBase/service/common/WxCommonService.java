package nbBase.service.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.helper.common.nbReturn;
import nbBase.service.wechat.WechatConfigure;

public interface WxCommonService {
	
	//WechatConfigure loadWxConfigure( Integer configureId);
	
	@Deprecated
	public 
	WechatConfigure loadWxConfigure(
			HttpServletRequest request, 
			Integer configId);
	
	public
	nbReturn sendAward(
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
			WechatConfigure wxCon) throws Exception;

	public
	nbReturn sendTplMessage(
			String template_id, 
			String url, 
			Boolean needJumpAuth, 
			String first_data,
			String[] keywords, 
			String remark_data,
			String openid,
			WechatConfigure wxCon) throws Exception;

	public WechatConfigure loadWxConfigureByAppId(String appId);

	public void insertCertFileToVarbinary(
			int wxConfigId) throws Exception;
	
	public String[] saveWxImgToLocalServer(
			String serverId, WechatConfigure wxCon
			) throws Exception;

	public nbReturn transferMoney(String reason, String openId, int amountInCent, WechatConfigure wxCon, String remark)
			throws Exception;

	public ZaFrontWxConfig loadZaWxConfigure(Integer wxConfigureId);

	/**
	 * 保存上传的文件到指定的文件或指定目录下
	 * @param items List<FileItem>
	 * @param targetAFileName 如果确定只是保存一个文件的话，可以指定要保存的对象文件路径和名称 可以为NULL
	 * @param storagePath 如果有多个文件的话，可以指定保存路径 可以为NULL
	 * @return
	 * @throws Exception
	 */
	public nbReturn uploadFile(List<FileItem> items, String targetAFileName, String storagePath, String browseUrlPath)
			throws Exception ;

	public ZaFrontUserWx loadWxUserByOpenId(String openId);

	public List<Map<String, Object>> saveWxImgToLocalServer(List<String> picList, WechatConfigure wxCon);

	public ZaFrontUserWx fakeAWxUser(ZaFrontUserWx wxUser);
	
	public nbReturn loadDefaultConfig();
	
	public boolean doUpdatePageToken(int id, String pageToken, long pageTokenExpire);
	
	public boolean doUpdateJSTicket(int id, String jsTicket, long jsTicketExpire);
}

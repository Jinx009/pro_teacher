package nbBase.service.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class WechatTemplateMsgValueSet extends HashMap<String, Object> {

	
	private WechatConfigure wxConfig  =null;
	
	Map<String, Object> data = new HashMap<String, Object>();
	
	public WechatTemplateMsgValueSet(WechatConfigure wxc){
		this.wxConfig = wxc;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -8003572696141373454L;
	
	private String lastErrorMsg = null;
	private Integer lastErrorCode = null;
	
	private String jsonString = null;
	
	
	public void setJsonString(String json){
		this.clear();
		this.putAll(JSONObject.parseObject(json));
		this.jsonString = json;
		
	}
	public String getJsonSTring(){
		this.jsonString = this.formJSONString();
		return this.jsonString;
	}

	public boolean checkParamter() throws Exception {
		lastErrorCode = -1;
		
		this.put(_WechatKeyDefine.wxTplMsg.data, data);
		
		if( super.get(_WechatKeyDefine.wxTplMsg.touser) == null ){
			lastErrorMsg = "touser openid lost!";
			return false;
		}
		if( super.get(_WechatKeyDefine.wxTplMsg.url) == null ){
			lastErrorMsg = "url lost!";
			return false;
		}
		if( super.get(_WechatKeyDefine.wxTplMsg.template_id) == null ){
			lastErrorMsg = "template_id lost!";
			return false;
		}
		
		/**
		 * 强制包装成auth的授权地址格式
		 */
		Boolean needJumpAuth = (Boolean) super.get(_WechatKeyDefine.wxTplMsg.needJumpAuth);
		String jumpToUrl = ((String)super.get(_WechatKeyDefine.wxTplMsg.url));
		
		if( needJumpAuth != null && needJumpAuth && !jumpToUrl.toLowerCase().startsWith("https://open.weixin.qq.com/connect/oauth") ){
			super.put(_WechatKeyDefine.wxTplMsg.url,
					  "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wxConfig.wxappid
					  +"&redirect_uri="+URLEncoder.encode(jumpToUrl,"utf-8")+"&response_type=code&scope=snsapi_base&state=autoAuth#wechat_redirect");
		}
		/*强制包装结束*/
		
		lastErrorCode = 0 ;
		return true;
	}
	
	private String wrapUrlForWxAuth(String jumpToUrl) throws UnsupportedEncodingException{
		
		/**
		 * 强制包装成auth的授权地址格式
		 */
		
		if(  !jumpToUrl.toLowerCase().startsWith("https://open.weixin.qq.com/connect/oauth") ){
			return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wxConfig.wxappid
					  +"&redirect_uri="+URLEncoder.encode(jumpToUrl,"utf-8")+"&response_type=code&scope=snsapi_base&state=autoAuth#wechat_redirect";
		}
		
		return jumpToUrl;
		/*强制包装结束*/
		
	}

	public Integer getLastErrorCode() {
		return lastErrorCode;
	}

	public String getLastErrorMsg() {
		return lastErrorMsg;
	}

	public String formJSONString() {
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		//serializer.getPropertyFilters().add(spf);
		serializer.write(this);
		return sw.toString();	
	}
	
	public void setTemplateId(String template_id) {
		this.put(_WechatKeyDefine.wxTplMsg.template_id, template_id);
	}
	
	public void setUrl(String url, Boolean needJumpAuth) throws UnsupportedEncodingException {
		if( needJumpAuth ){
			url = wrapUrlForWxAuth(url);
		}
		this.put(_WechatKeyDefine.wxTplMsg.url, url);
		
	}
	public void setFirstData(String first_data, String color) {
		Map<String, String> firstContent = new HashMap<String, String>();
		if( color == null ) color = "#173177";
		
		firstContent.put(_WechatKeyDefine.wxTplMsg.value, first_data);
		firstContent.put(_WechatKeyDefine.wxTplMsg.color, color);
		
		data.put(_WechatKeyDefine.wxTplMsg.first, firstContent);
		
	}
	public void setKeywords(String[] keywords, String color) {
		// TODO Auto-generated method stub
		int size = keywords.length;
		
		if( color == null ) color = "#173177";
		
		for(int i = 0 ; i < size; i++){
			
			Map<String, String> content = new HashMap<String, String>();
			content.put(_WechatKeyDefine.wxTplMsg.value, keywords[i]);
			content.put(_WechatKeyDefine.wxTplMsg.color, color);
			data.put(_WechatKeyDefine.wxTplMsg.keyword+String.valueOf(i+1), content);
		}
	}
	
	public void setRemarkData(String remark_data, String color) {
		
		Map<String, String> content = new HashMap<String, String>();
		if( color == null ) color = "#173177";
		
		content.put(_WechatKeyDefine.wxTplMsg.value, remark_data);
		content.put(_WechatKeyDefine.wxTplMsg.color, color);
		
		data.put(_WechatKeyDefine.wxTplMsg.remark, content);
		
	}

}

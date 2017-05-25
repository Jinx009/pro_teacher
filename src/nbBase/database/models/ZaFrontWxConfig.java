package nbBase.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



/**
 * The persistent class for the za_front_wx_config database table.
 * 
 */
@Entity
@Table(name="za_front_wx_config")
@NamedQuery(name="ZaFrontWxConfig.findAll", query="SELECT z FROM ZaFrontWxConfig z")
public class ZaFrontWxConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="app_secret")
	private String appSecret;

	@Lob
	private String certFile_p12;
	
	@Lob
	@Column(name="cert_p12_bin")
	private byte[] certP12Bin;

	@Column(name="config_name")
	private String configName;

	@Column(name="encoding_aes_key")
	private String encodingAesKey;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(name="is_default")
	private Boolean isDefault;
	
	@Column(name="tplmsg_match_result_confirm")
	private String tplmsgMatchResultConfirm;
	

	private String mchId;

	@Column(name="order_default_expire_time")
	private Long orderDefaultExpireTime;

	private String payKey;

	@Column(name="server_default_ip")
	private String serverDefaultIp;
	
	@Column(name="server_name")
	private String serverName;
	

	@Column(name="resource_path")
	private String resourcePath;
	
	@Column(name="resource_brows_path")
	private String resourceBrowsPath;
	
	@Column(name="tplmsg_pay_success")
	private String tplmsgPaySuccess;
	
	@Column(name="tplmsg_cf_result")
	private String tplmsgCfResult;

	@Lob
	@Column(name="wx_pay_notify_url")
	private String wxPayNotifyUrl;

	private String wxappid;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="company_logo_url")
	private String companyLogoUrl;
	
	@Column(name="current_page_token")
	private String currentPageToken;
	
	@Column(name="current_page_token_expire")
	private Long currentPageTokenExpire;
	
	@Column(name="current_js_ticket")
	private String currentJsTicket;
	
	@Column(name="current_js_ticket_expire")
	private Long currentJsTicketExpire;
	
//	//bi-directional many-to-one association to ZaCoreEvent
//	@OneToMany(mappedBy="zaFrontWxConfig",cascade = CascadeType.ALL)
//	private List<ZaCoreEvent> zaCoreEvents;

	public ZaFrontWxConfig() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCertFile_p12() {
		return this.certFile_p12;
	}

	public void setCertFile_p12(String certFile_p12) {
		this.certFile_p12 = certFile_p12;
	}

	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getEncodingAesKey() {
		return this.encodingAesKey;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getMchId() {
		return this.mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public Long getOrderDefaultExpireTime() {
		return this.orderDefaultExpireTime;
	}

	public void setOrderDefaultExpireTime(Long orderDefaultExpireTime) {
		this.orderDefaultExpireTime = orderDefaultExpireTime;
	}

	public String getPayKey() {
		return this.payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getServerDefaultIp() {
		return this.serverDefaultIp;
	}

	public void setServerDefaultIp(String serverDefaultIp) {
		this.serverDefaultIp = serverDefaultIp;
	}

	public String getWxPayNotifyUrl() {
		return this.wxPayNotifyUrl;
	}

	public void setWxPayNotifyUrl(String wxPayNotifyUrl) {
		this.wxPayNotifyUrl = wxPayNotifyUrl;
	}

	public String getWxappid() {
		return this.wxappid;
	}

	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getTplmsgPaySuccess() {
		return tplmsgPaySuccess;
	}

	public void setTplmsgPaySuccess(String tplmsgPaySuccess) {
		this.tplmsgPaySuccess = tplmsgPaySuccess;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getResourceBrowsPath() {
		return resourceBrowsPath;
	}

	public void setResourceBrowsPath(String resourceBrowsPath) {
		this.resourceBrowsPath = resourceBrowsPath;
	}

	public byte[] getCertP12Bin() {
		return certP12Bin;
	}

	public void setCertP12Bin(byte[] certP12Bin) {
		this.certP12Bin = certP12Bin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}

	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
	}

	public String getTplmsgCfResult() {
		return tplmsgCfResult;
	}

	public void setTplmsgCfResult(String tplmsgCfResult) {
		this.tplmsgCfResult = tplmsgCfResult;
	}

//	public List<ZaCoreEvent> getZaCoreEvents() {
//		return zaCoreEvents;
//	}
//
//	public void setZaCoreEvents(List<ZaCoreEvent> zaCoreEvents) {
//		this.zaCoreEvents = zaCoreEvents;
//	}

	public String getCurrentPageToken() {
		return currentPageToken;
	}

	public void setCurrentPageToken(String currentPageToken) {
		this.currentPageToken = currentPageToken;
	}

	public Long getCurrentPageTokenExpire() {
		return currentPageTokenExpire;
	}

	public void setCurrentPageTokenExpire(Long currentPageTokenExpire) {
		this.currentPageTokenExpire = currentPageTokenExpire;
	}

	public String getTplmsgMatchResultConfirm() {
		return tplmsgMatchResultConfirm;
	}

	public void setTplmsgMatchResultConfirm(String tplmsgMatchResultConfirm) {
		this.tplmsgMatchResultConfirm = tplmsgMatchResultConfirm;
	}

	public String getCurrentJsTicket() {
		return currentJsTicket;
	}

	public void setCurrentJsTicket(String currentJsTicket) {
		this.currentJsTicket = currentJsTicket;
	}

	public Long getCurrentJsTicketExpire() {
		return currentJsTicketExpire;
	}

	public void setCurrentJsTicketExpire(Long currentJsTicketExpire) {
		this.currentJsTicketExpire = currentJsTicketExpire;
	}

}
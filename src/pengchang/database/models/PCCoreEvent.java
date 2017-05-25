package pengchang.database.models;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;


/**
 * The persistent class for the za_core_events database table.
 * 
 */
@Entity
@Table(name="pc_core_events")
@NamedQuery(name="PCCoreEvent.findAll", query="SELECT z FROM PCCoreEvent z")
public class PCCoreEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_create_date")
	private Date eventCreateDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_deadline_date")
	private Date eventDeadlineDate;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="event_time")
	private Date eventTime;
	

	@Column(name="event_status")
	private Integer eventStatus;
	
	@Column(name="cf_event_type")
	private Integer cfEventType;

	@Lob
	@Column(name="event_detail_url")
	private String eventDetailUrl;
	
	@Lob
	@Column(name="event_address")
	private String eventAddress;

	@Lob
	@Column(name="event_head_img")
	private String eventHeadImg;

	@Column(name="event_short_desc")
	private String eventShortDesc;

	@Column(name="event_title")
	private String eventTitle;
	
	@Column(name="wx_config_id")
	private Integer wxConfigId;
	
	@Column(name="event_type")
	private Integer eventType;
	
	@Column(name="event_sub_type")
	private Integer eventSubType;

	@Column(name="is_event_sample")
	private Boolean isEventSample;

	@Column(name="is_event_succeed")
	private Boolean isEventSucceed;

	@Column(name="target_amount")
	private int targetAmount;

	@Column(name="target_member")
	private int targetMember;
	
	@Column(name="is_event_active")
	private Boolean isEventActive;
	
	@Column(name="is_event_passed_review")
	private Boolean isEventPassedReview;
	
	

	@Column(name="wx_card_desc")
	private String wxCardDesc;

	@Lob
	@Column(name="wx_card_img_url")
	private String wxCardImgUrl;

	@Column(name="wx_card_title")
	private String wxCardTitle;
	
	//bi-directional many-to-one association to ZaCoreEventMsg
	@OneToMany(mappedBy="zaCoreEvent",cascade = CascadeType.ALL)//, fetch=FetchType.EAGER)
	private List<PCCoreEventMsg> zaCoreEventMsgs;
	
	//bi-directional many-to-one association to ZaFrontUserWx
	@ManyToOne
	@JoinColumn(name="event_creater_wx_id")
	private ZaFrontUserWx wxCreater;
	
	//bi-directional many-to-one association to ZaCoreEventRule
	@OneToMany(mappedBy="zaCoreEvent", cascade = CascadeType.ALL)
	private List<PCCoreEventRule> zaCoreEventRules;
	
	//bi-directional many-to-one association to ZaFrontWxConfig
	@ManyToOne
	@JoinColumn(name="wx_config_id", referencedColumnName="id",insertable=false, updatable= false)
	private ZaFrontWxConfig zaFrontWxConfig;

	public PCCoreEvent() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEventCreateDate() {
		return this.eventCreateDate;
	}

	public void setEventCreateDate(Date eventCreateDate) {
		this.eventCreateDate = eventCreateDate;
	}

	public Date getEventDeadlineDate() {
		return this.eventDeadlineDate;
	}

	public void setEventDeadlineDate(Date eventDeadlineDate) {
		this.eventDeadlineDate = eventDeadlineDate;
	}

	public String getEventDetailUrl() {
		return this.eventDetailUrl;
	}

	public void setEventDetailUrl(String eventDetailUrl) {
		this.eventDetailUrl = eventDetailUrl;
	}

	public String getEventHeadImg() {
		return this.eventHeadImg;
	}

	public void setEventHeadImg(String eventHeadImg) {
		this.eventHeadImg = eventHeadImg;
	}

	public String getEventShortDesc() {
		return this.eventShortDesc;
	}

	public void setEventShortDesc(String eventShortDesc) {
		this.eventShortDesc = eventShortDesc;
	}

	public String getEventTitle() {
		return this.eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public Boolean getIsEventSample() {
		return this.isEventSample;
	}

	public void setIsEventSample(Boolean isEventSample) {
		this.isEventSample = isEventSample;
	}

	public Boolean getIsEventSucceed() {
		return this.isEventSucceed;
	}

	public void setIsEventSucceed(Boolean isEventSucceed) {
		this.isEventSucceed = isEventSucceed;
	}

	public int getTargetAmount() {
		return this.targetAmount;
	}

	public void setTargetAmount(int targetAmount) {
		this.targetAmount = targetAmount;
	}

	public int getTargetMember() {
		return this.targetMember;
	}

	public void setTargetMember(int targetMember) {
		this.targetMember = targetMember;
	}

	public String getWxCardDesc() {
		try {
			return URLDecoder.decode(this.wxCardDesc, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.wxCardDesc;
		
	}

	public void setWxCardDesc(String wxCardDesc) {
		try {
			this.wxCardDesc = URLEncoder.encode(wxCardDesc,"utf-8");
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		this.wxCardDesc = wxCardDesc;
	}

	public String getWxCardImgUrl() {
		return this.wxCardImgUrl;
	}

	public void setWxCardImgUrl(String wxCardImgUrl) {
		this.wxCardImgUrl = wxCardImgUrl;
	}

	public String getWxCardTitle() {
		return this.wxCardTitle;
	}

	public void setWxCardTitle(String wxCardTitle) {
		this.wxCardTitle = wxCardTitle;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Integer getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(Integer eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public Integer getWxConfigId() {
		return wxConfigId;
	}

	public void setWxConfigId(Integer wxConfigId) {
		this.wxConfigId = wxConfigId;
	}

	public List<PCCoreEventMsg> getZaCoreEventMsgs() {
		return zaCoreEventMsgs;
	}

	public void setZaCoreEventMsgs(List<PCCoreEventMsg> zaCoreEventMsgs) {
		this.zaCoreEventMsgs = zaCoreEventMsgs;
	}

	public ZaFrontUserWx getWxCreater() {
		return wxCreater;
	}

	public void setWxCreater(ZaFrontUserWx wxCreater) {
		this.wxCreater = wxCreater;
	}

	public Boolean getIsEventActive() {
		return isEventActive;
	}

	public void setIsEventActive(Boolean isEventActive) {
		this.isEventActive = isEventActive;
	}

	public Integer getCfEventType() {
		return cfEventType;
	}

	public void setCfEventType(Integer cfEventType) {
		this.cfEventType = cfEventType;
	}

	public List<PCCoreEventRule> getZaCoreEventRules() {
		return zaCoreEventRules;
	}

	public void setZaCoreEventRules(List<PCCoreEventRule> zaCoreEventRules) {
		this.zaCoreEventRules = zaCoreEventRules;
	}

	public ZaFrontWxConfig getZaFrontWxConfig() {
		return zaFrontWxConfig;
	}

	public void setZaFrontWxConfig(ZaFrontWxConfig zaFrontWxConfig) {
		this.zaFrontWxConfig = zaFrontWxConfig;
	}

	public Boolean getIsEventPassedReview() {
		return isEventPassedReview;
	}

	public void setIsEventPassedReview(Boolean isEventPassedReview) {
		this.isEventPassedReview = isEventPassedReview;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Integer getEventSubType() {
		return eventSubType;
	}

	public void setEventSubType(Integer eventSubType) {
		this.eventSubType = eventSubType;
	}

}
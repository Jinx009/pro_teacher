package pengchang.database.models;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nbBase.database.models.ZaFrontUserWx;
import nbBase.database.models.ZaFrontWxConfig;
import nbBase.database.models.ZaSendAwardLog;


/**
 * The persistent class for the za_core_event_msg database table.
 * 
 */
@Entity
@Table(name="pc_core_event_msg")
@NamedQuery(name="PCCoreEventMsg.findAll", query="SELECT z FROM PCCoreEventMsg z")
public class PCCoreEventMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="message_date")
	private Date messageDate;

	@Column(name="owner_award")
	private Integer ownerAward;

	@Column(name="owner_replier")
	private String ownerReplier;

	@Lob
	@Column(name="owner_reply")
	private String ownerReply;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="owner_reply_date")
	private Date ownerReplyDate;

	@Column(name="user_id")
	private Integer userId;

	//bi-directional many-to-one association to ZaCoreEventMsgImg
	@OneToMany(mappedBy="zaCoreEventMsg",cascade = CascadeType.ALL)
	private List<PCCoreEventMsgImg> zaCoreEventMsgImgs;

	//bi-directional many-to-one association to ZaCoreEvent
	@ManyToOne
	@JoinColumn(name="event_id")
	private PCCoreEvent zaCoreEvent;

	//bi-directional many-to-one association to ZaFrontWxConfig
	@ManyToOne
	@JoinColumn(name="wx_config_id")
	private ZaFrontWxConfig zaFrontWxConfig;

	//bi-directional one-to-one association to ZaSendAwardLog
	@OneToOne
	@JoinColumn(name="wx_send_award_log_id")
	private ZaSendAwardLog zaSendAwardLog;

	//bi-directional many-to-one association to ZaFrontUserWx
	@ManyToOne
	@JoinColumn(name="wx_user_id")
	private ZaFrontUserWx zaFrontUserWx;

	public PCCoreEventMsg() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageDate() {
		return this.messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public Integer getOwnerAward() {
		return this.ownerAward;
	}

	public void setOwnerAward(Integer ownerAward) {
		this.ownerAward = ownerAward;
	}

	public String getOwnerReplier() {
		return this.ownerReplier;
	}

	public void setOwnerReplier(String ownerReplier) {
		this.ownerReplier = ownerReplier;
	}

	public String getOwnerReply() {
		return this.ownerReply;
	}

	public void setOwnerReply(String ownerReply) {
		this.ownerReply = ownerReply;
	}

	public Date getOwnerReplyDate() {
		return this.ownerReplyDate;
	}

	public void setOwnerReplyDate(Date ownerReplyDate) {
		this.ownerReplyDate = ownerReplyDate;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<PCCoreEventMsgImg> getZaCoreEventMsgImgs() {
		return this.zaCoreEventMsgImgs;
	}

	public void setZaCoreEventMsgImgs(List<PCCoreEventMsgImg> zaCoreEventMsgImgs) {
		this.zaCoreEventMsgImgs = zaCoreEventMsgImgs;
	}

	public PCCoreEventMsgImg addZaCoreEventMsgImg(PCCoreEventMsgImg zaCoreEventMsgImg) {
		getZaCoreEventMsgImgs().add(zaCoreEventMsgImg);
		zaCoreEventMsgImg.setZaCoreEventMsg(this);

		return zaCoreEventMsgImg;
	}

	public PCCoreEventMsgImg removeZaCoreEventMsgImg(PCCoreEventMsgImg zaCoreEventMsgImg) {
		getZaCoreEventMsgImgs().remove(zaCoreEventMsgImg);
		zaCoreEventMsgImg.setZaCoreEventMsg(null);

		return zaCoreEventMsgImg;
	}

	public PCCoreEvent getZaCoreEvent() {
		return this.zaCoreEvent;
	}

	public void setZaCoreEvent(PCCoreEvent zaCoreEvent) {
		this.zaCoreEvent = zaCoreEvent;
	}

	public ZaFrontWxConfig getZaFrontWxConfig() {
		return this.zaFrontWxConfig;
	}

	public void setZaFrontWxConfig(ZaFrontWxConfig zaFrontWxConfig) {
		this.zaFrontWxConfig = zaFrontWxConfig;
	}

	public ZaSendAwardLog getZaSendAwardLog() {
		return this.zaSendAwardLog;
	}

	public void setZaSendAwardLog(ZaSendAwardLog zaSendAwardLog) {
		this.zaSendAwardLog = zaSendAwardLog;
	}

	public ZaFrontUserWx getZaFrontUserWx() {
		return this.zaFrontUserWx;
	}

	public void setZaFrontUserWx(ZaFrontUserWx zaFrontUserWx) {
		this.zaFrontUserWx = zaFrontUserWx;
	}

}
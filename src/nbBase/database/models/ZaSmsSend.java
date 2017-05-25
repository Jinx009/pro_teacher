package nbBase.database.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nbBase.database.common.nbBaseModel;


/**
 * The persistent class for the nb_sms_send database table.
 * 
 */
@Entity
@Table(name="za_sms_send")
@NamedQuery(name="ZaSmsSend.findAll", query="SELECT n FROM ZaSmsSend n")
public class ZaSmsSend implements Serializable, nbBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String applicationid;

	@Column(name="continouse_try_cycle")
	private int continouseTryCycle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="latest_event_time")
	private Date latestEventTime;

	private int lifecycle;

	@Column(name="sms_text")
	private String smsText;
	
	@Column(name="phone_code")
	private String phoneCode;

	@Lob
	@Column(name="request_reason_comment")
	private String requestReasonComment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="requested_time")
	private Date requestedTime;

	@Column(name="send_status")
	private int sendStatus;

	@Column(name="target_phone_number")
	private String targetPhoneNumber;

	//bi-directional many-to-one association to ZaSmsTemplate
	@ManyToOne
	@JoinColumn(name="request_reason_id")
	private ZaSmsTemplate nbSmsTemplate;

	public ZaSmsSend() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicationid() {
		return this.applicationid;
	}

	public void setApplicationid(String applicationid) {
		this.applicationid = applicationid;
	}

	public int getContinouseTryCycle() {
		return this.continouseTryCycle;
	}

	public void setContinouseTryCycle(int continouseTryCycle) {
		this.continouseTryCycle = continouseTryCycle;
	}

	public Date getLatestEventTime() {
		return this.latestEventTime;
	}

	public void setLatestEventTime(Date latestEventTime) {
		this.latestEventTime = latestEventTime;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public int getLifecycle() {
		return this.lifecycle;
	}

	public void setLifecycle(int lifecycle) {
		this.lifecycle = lifecycle;
	}

	public String getRequestReasonComment() {
		return this.requestReasonComment;
	}

	public void setRequestReasonComment(String requestReasonComment) {
		this.requestReasonComment = requestReasonComment;
	}

	public Date getRequestedTime() {
		return this.requestedTime;
	}

	public void setRequestedTime(Date requestedTime) {
		this.requestedTime = requestedTime;
	}

	public int getSendStatus() {
		return this.sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getTargetPhoneNumber() {
		return this.targetPhoneNumber;
	}

	public void setTargetPhoneNumber(String targetPhoneNumber) {
		this.targetPhoneNumber = targetPhoneNumber;
	}

	public ZaSmsTemplate getNbSmsTemplate() {
		return this.nbSmsTemplate;
	}

	public void setNbSmsTemplate(ZaSmsTemplate nbSmsTemplate) {
		this.nbSmsTemplate = nbSmsTemplate;
	}

}
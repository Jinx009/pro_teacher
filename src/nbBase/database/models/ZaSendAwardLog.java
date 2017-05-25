package nbBase.database.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the za_core_send_award_log database table.
 * 
 */
@Entity
@Table(name="za_core_send_award_log")
@NamedQuery(name="ZaSendAwardLog.findAll", query="SELECT z FROM ZaSendAwardLog z")
public class ZaSendAwardLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="act_name")
	private String actName;

	@Column(name="client_ip")
	private String clientIp;

	@Column(name="mch_billno")
	private String mchBillno;

	@Column(name="mch_id")
	private String mchId;

	@Column(name="nonce_str")
	private String nonceStr;
	
	@Column(name="additional_remark")
	private String additionalRemark;

	@Column(name="re_openid")
	private String reOpenid;

	@Lob
	private String remark;

	@Column(name="send_name")
	private String sendName;

	@Column(name="send_result")
	private int sendResult;

	@Column(name="send_result_msg")
	private String sendResultMsg;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_time")
	private Date sendTime;

	@Column(name="total_amount")
	private int totalAmount;

	@Column(name="total_num")
	private int totalNum;

	private String wishing;

	private String wxappid;

	public ZaSendAwardLog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActName() {
		return this.actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getMchBillno() {
		return this.mchBillno;
	}

	public void setMchBillno(String mchBillno) {
		this.mchBillno = mchBillno;
	}

	public String getMchId() {
		return this.mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNonceStr() {
		return this.nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getReOpenid() {
		return this.reOpenid;
	}

	public void setReOpenid(String reOpenid) {
		this.reOpenid = reOpenid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSendName() {
		return this.sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public int getSendResult() {
		return this.sendResult;
	}

	public void setSendResult(int sendResult) {
		this.sendResult = sendResult;
	}

	public String getSendResultMsg() {
		return this.sendResultMsg;
	}

	public void setSendResultMsg(String sendResultMsg) {
		this.sendResultMsg = sendResultMsg;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public String getWishing() {
		return this.wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getWxappid() {
		return this.wxappid;
	}

	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}

	public String getAdditionalRemark() {
		return additionalRemark;
	}

	public void setAdditionalRemark(String additionalRemark) {
		this.additionalRemark = additionalRemark;
	}

}
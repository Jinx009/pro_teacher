package pengchang.database.models;

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

import nbBase.database.models.ZaFrontUserWx;


/**
 * The persistent class for the za_core_orders database table.
 * 
 */
@Entity
@Table(name="pc_core_orders")
@NamedQuery(name="PCCoreOrder.findAll", query="SELECT z FROM PCCoreOrder z")
public class PCCoreOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	@Column(name="bar_code_url")
	private String barCodeUrl;

	@Column(name="front_user_id")
	private Integer frontUserId;
	
	@Column(name="wx_user_id")
	private Integer wxUserId;

	@Column(name="is_pay_succeed")
	private Boolean isPaySucceed;
	
	@Column(name="is_front_succeed")
	private Boolean isFrontSucceed;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="front_succeed_time")
	private Date frontSucceedTime;

	@Column(name="mch_order_code")
	private String mchOrderCode;
	
	@Column(name="order_deliver_status")
	private Integer orderDeliverStatus;
	
	@Column(name="earn_play_times")
	private Integer earnPlayTimes;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_date")
	private Date orderDate;

	@Column(name="ordered_copies")
	private Integer orderedCopies;

	@Column(name="pay_method")
	private Integer payMethod;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_succeed_time")
	private Date paySucceedTime;

	@Column(name="pay_wx_union_order_id")
	private Integer payWxUnionOrderId;

	@Lob
	@Column(name="qr_code_url")
	private String qrCodeUrl;

	@Column(name="total_fee")
	private Integer totalFee;

	@Lob
	@Column(name="user_address")
	private String userAddress;

	@Column(name="user_phone")
	private String userPhone;

	@Column(name="user_realname")
	private String userRealname;
	
	@Column(name="event_id")
	private Integer eventId;
	
	@Column(name="event_rule_id")
	private Integer eventRuleId;
	
	@Column(name="wx_pay_callback_id")
	private Integer wxPayCallbackId;
	
	@ManyToOne
	@JoinColumn(name="wx_user_id", insertable=false,  updatable=false)
	private ZaFrontUserWx wxSupporter;
	
	public ZaFrontUserWx getWxSupporter() {
		return wxSupporter;
	}

	public void setWxSupporter(ZaFrontUserWx wxSupporter) {
		this.wxSupporter = wxSupporter;
	}

	public PCCoreOrder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarCodeUrl() {
		return this.barCodeUrl;
	}

	public void setBarCodeUrl(String barCodeUrl) {
		this.barCodeUrl = barCodeUrl;
	}

	public Integer getFrontUserId() {
		return this.frontUserId;
	}

	public void setFrontUserId(Integer frontUserId) {
		this.frontUserId = frontUserId;
	}

	public Boolean getIsPaySucceed() {
		return this.isPaySucceed;
	}

	public void setIsPaySucceed(Boolean isPaySucceed) {
		this.isPaySucceed = isPaySucceed;
	}

	public String getMchOrderCode() {
		return this.mchOrderCode;
	}

	public void setMchOrderCode(String mchOrderCode) {
		this.mchOrderCode = mchOrderCode;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getOrderedCopies() {
		return this.orderedCopies;
	}

	public void setOrderedCopies(Integer orderedCopies) {
		this.orderedCopies = orderedCopies;
	}

	public Integer getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public Date getPaySucceedTime() {
		return this.paySucceedTime;
	}

	public void setPaySucceedTime(Date paySucceedTime) {
		this.paySucceedTime = paySucceedTime;
	}

	public Integer getPayWxUnionOrderId() {
		return this.payWxUnionOrderId;
	}

	public void setPayWxUnionOrderId(Integer payWxUnionOrderId) {
		this.payWxUnionOrderId = payWxUnionOrderId;
	}

	public String getQrCodeUrl() {
		return this.qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public Integer getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getUserAddress() {
		return this.userAddress;
	}

	public Integer getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(Integer wxUserId) {
		this.wxUserId = wxUserId;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserRealname() {
		return this.userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getEventRuleId() {
		return eventRuleId;
	}

	public void setEventRuleId(Integer eventRuleId) {
		this.eventRuleId = eventRuleId;
	}

	public Boolean getIsFrontSucceed() {
		return isFrontSucceed;
	}

	public void setIsFrontSucceed(Boolean isFrontSucceed) {
		this.isFrontSucceed = isFrontSucceed;
	}

	public Date getFrontSucceedTime() {
		return frontSucceedTime;
	}

	public void setFrontSucceedTime(Date frontSucceedTime) {
		this.frontSucceedTime = frontSucceedTime;
	}

	public Integer getWxPayCallbackId() {
		return wxPayCallbackId;
	}

	public void setWxPayCallbackId(Integer wxPayCallbackId) {
		this.wxPayCallbackId = wxPayCallbackId;
	}

	public Integer getOrderDeliverStatus() {
		return orderDeliverStatus;
	}

	public void setOrderDeliverStatus(Integer orderDeliverStatus) {
		this.orderDeliverStatus = orderDeliverStatus;
	}

	public Integer getEarnPlayTimes() {
		return earnPlayTimes;
	}

	public void setEarnPlayTimes(Integer earnPlayTimes) {
		this.earnPlayTimes = earnPlayTimes;
	}

}
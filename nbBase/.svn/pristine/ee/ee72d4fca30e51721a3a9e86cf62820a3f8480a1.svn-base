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
 * The persistent class for the za_pay_wx_union_id database table.
 * 
 */
@Entity
@Table(name="za_pay_wx_union_id")
@NamedQuery(name="ZaPayWxUnionId.findAll", query="SELECT z FROM ZaPayWxUnionOrder z")
public class ZaPayWxUnionOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="wx_configure_id")
	private int wxConfigureId;

	@Column(name="wx_device_info")
	private String wxDeviceInfo;

	@Column(name="wx_fee_type")
	private String wxFeeType;

	@Column(name="wx_goods_tag")
	private String wxGoodsTag;

	@Column(name="wx_nonce_str")
	private String wxNonceStr;

	@Lob
	@Column(name="wx_order_attach")
	private String wxOrderAttach;

	@Lob
	@Column(name="wx_order_body")
	private String wxOrderBody;

	@Lob
	@Column(name="wx_order_detail")
	private String wxOrderDetail;

	@Column(name="wx_out_trade_no")
	private String wxOutTradeNo;

	@Column(name="wx_prepay_id")
	private String wxPrepayId;

	@Column(name="wx_product_id")
	private int wxProductId;

	@Column(name="wx_spbill_create_ip")
	private String wxSpbillCreateIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="wx_time_expire")
	private Date wxTimeExpire;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="wx_time_start")
	private Date wxTimeStart;

	@Column(name="wx_total_fee")
	private int wxTotalFee;

	@Column(name="wx_trade_type")
	private String wxTradeType;

	public ZaPayWxUnionOrder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWxConfigureId() {
		return this.wxConfigureId;
	}

	public void setWxConfigureId(int wxConfigureId) {
		this.wxConfigureId = wxConfigureId;
	}

	public String getWxDeviceInfo() {
		return this.wxDeviceInfo;
	}

	public void setWxDeviceInfo(String wxDeviceInfo) {
		this.wxDeviceInfo = wxDeviceInfo;
	}

	public String getWxFeeType() {
		return this.wxFeeType;
	}

	public void setWxFeeType(String wxFeeType) {
		this.wxFeeType = wxFeeType;
	}

	public String getWxGoodsTag() {
		return this.wxGoodsTag;
	}

	public void setWxGoodsTag(String wxGoodsTag) {
		this.wxGoodsTag = wxGoodsTag;
	}

	public String getWxNonceStr() {
		return this.wxNonceStr;
	}

	public void setWxNonceStr(String wxNonceStr) {
		this.wxNonceStr = wxNonceStr;
	}

	public String getWxOrderAttach() {
		return this.wxOrderAttach;
	}

	public void setWxOrderAttach(String wxOrderAttach) {
		this.wxOrderAttach = wxOrderAttach;
	}

	public String getWxOrderBody() {
		return this.wxOrderBody;
	}

	public void setWxOrderBody(String wxOrderBody) {
		this.wxOrderBody = wxOrderBody;
	}

	public String getWxOrderDetail() {
		return this.wxOrderDetail;
	}

	public void setWxOrderDetail(String wxOrderDetail) {
		this.wxOrderDetail = wxOrderDetail;
	}

	public String getWxOutTradeNo() {
		return this.wxOutTradeNo;
	}

	public void setWxOutTradeNo(String wxOutTradeNo) {
		this.wxOutTradeNo = wxOutTradeNo;
	}

	public String getWxPrepayId() {
		return this.wxPrepayId;
	}

	public void setWxPrepayId(String wxPrepayId) {
		this.wxPrepayId = wxPrepayId;
	}

	public int getWxProductId() {
		return this.wxProductId;
	}

	public void setWxProductId(int wxProductId) {
		this.wxProductId = wxProductId;
	}

	public String getWxSpbillCreateIp() {
		return this.wxSpbillCreateIp;
	}

	public void setWxSpbillCreateIp(String wxSpbillCreateIp) {
		this.wxSpbillCreateIp = wxSpbillCreateIp;
	}

	public Date getWxTimeExpire() {
		return this.wxTimeExpire;
	}

	public void setWxTimeExpire(Date wxTimeExpire) {
		this.wxTimeExpire = wxTimeExpire;
	}

	public Date getWxTimeStart() {
		return this.wxTimeStart;
	}

	public void setWxTimeStart(Date wxTimeStart) {
		this.wxTimeStart = wxTimeStart;
	}

	public int getWxTotalFee() {
		return this.wxTotalFee;
	}

	public void setWxTotalFee(int wxTotalFee) {
		this.wxTotalFee = wxTotalFee;
	}

	public String getWxTradeType() {
		return this.wxTradeType;
	}

	public void setWxTradeType(String wxTradeType) {
		this.wxTradeType = wxTradeType;
	}

}
package other.database.other;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the other_event_casio_001 database table.
 * 
 */
@Entity
@Table(name="other_event_casio_001")
@NamedQuery(name="OtherEventCasio001.findAll", query="SELECT o FROM OtherEventCasio001 o")
public class OtherEventCasio001 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private Integer client;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name="pic_url")
	private String picUrl;

	@Column(name="weibo_shared")
	private Boolean weiboShared;

	@Column(name="weixin_shared")
	private Boolean weixinShared;
	
	@Column(name="weibo_shared_result")
	private Boolean weiboSharedResult;
	
	@Column(name="weixin_shared_result")
	private Boolean weixinSharedResult;

	public OtherEventCasio001() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getClient() {
		return this.client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Boolean getWeiboShared() {
		return this.weiboShared;
	}

	public void setWeiboShared(Boolean weiboShared) {
		this.weiboShared = weiboShared;
	}

	public Boolean getWeixinShared() {
		return this.weixinShared;
	}

	public void setWeixinShared(Boolean weixinShared) {
		this.weixinShared = weixinShared;
	}

	public Boolean getWeiboSharedResult() {
		return weiboSharedResult;
	}

	public void setWeiboSharedResult(Boolean weiboSharedResult) {
		this.weiboSharedResult = weiboSharedResult;
	}

	public Boolean getWeixinSharedResult() {
		return weixinSharedResult;
	}

	public void setWeixinSharedResult(Boolean weixinSharedResult) {
		this.weixinSharedResult = weixinSharedResult;
	}

}
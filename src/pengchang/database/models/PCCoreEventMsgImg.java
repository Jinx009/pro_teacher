package pengchang.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the za_core_event_msg_img database table.
 * 
 */
@Entity
@Table(name="pc_core_event_msg_img")
@NamedQuery(name="PCCoreEventMsgImg.findAll", query="SELECT z FROM PCCoreEventMsgImg z")
public class PCCoreEventMsgImg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="img_url")
	private String imgUrl;
	
	@Column(name="thum_url")
	private String thumUrl;

	//bi-directional many-to-one association to ZaCoreEventMsg
	@ManyToOne()
	@JoinColumn(name="event_msg_id")
	private PCCoreEventMsg zaCoreEventMsg;

	public PCCoreEventMsgImg() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public PCCoreEventMsg getZaCoreEventMsg() {
		return this.zaCoreEventMsg;
	}

	public void setZaCoreEventMsg(PCCoreEventMsg zaCoreEventMsg) {
		this.zaCoreEventMsg = zaCoreEventMsg;
	}

	public String getThumUrl() {
		return thumUrl;
	}

	public void setThumUrl(String thumUrl) {
		this.thumUrl = thumUrl;
	}

}
package nbBase.database.models;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



/**
 * The persistent class for the za_front_user_wx database table.
 * 
 */
@Entity
@Table(name="za_front_user_wx")
@NamedQuery(name="ZaFrontUserWx.findAll", query="SELECT z FROM ZaFrontUserWx z")
public class ZaFrontUserWx implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	@Column(name="head_img_url")
	private String headImgUrl;

	@Column(name="user_info_id")
	private int userInfoId;

	@Column(name="wx_app_id")
	private String wxAppId;

	@Column(name="wx_nickname")
	private String wxNickname;

	@Column(name="wx_open_id")
	private String wxOpenId;

	@Column(name="wx_sex")
	private int wxSex;

	@Column(name="wx_union_id")
	private String wxUnionId;
	
	@Column(name="attr_is_expert")
	private Boolean attrIsExpert;
	
//	//bi-directional many-to-one association to ZaCoreEventMsg
//	@OneToMany(mappedBy="zaFrontUserWx",cascade = CascadeType.ALL)
//	private List<ZaCoreEventMsg> zaCoreEventMsgs;
//	
//	//bi-directional many-to-one association to ZaCoreEvent
//	@OneToMany(mappedBy="wxCreater",cascade = CascadeType.ALL)
//	private List<ZaCoreEvent> createdEvents;

	public ZaFrontUserWx() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeadImgUrl() {
		return this.headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public int getUserInfoId() {
		return this.userInfoId;
	}

	public void setUserInfoId(int userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getWxAppId() {
		return this.wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getWxNickname() {
		try {
			return URLDecoder.decode(this.wxNickname, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.wxNickname;
//		try {
//			byte[] tmp = Base64.decode(this.wxNickname);
//			String ret;
//			ret = new String(tmp, "utf-8");
//			return ret;
//		
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "n/a";

	}

	public void setWxNickname(String wxNickname) {
		try {
			this.wxNickname = URLEncoder.encode(wxNickname, "utf-8");
			return;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.wxNickname = wxNickname;
		
//		try {
//			this.wxNickname = Base64.encode(wxNickname.getBytes("utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public String getWxOpenId() {
		return this.wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public int getWxSex() {
		return this.wxSex;
	}

	public void setWxSex(int wxSex) {
		this.wxSex = wxSex;
	}

	public String getWxUnionId() {
		return this.wxUnionId;
	}

	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}

	public Boolean getAttrIsExpert() {
		return attrIsExpert;
	}

	public void setAttrIsExpert(Boolean attrIsExpert) {
		this.attrIsExpert = attrIsExpert;
	}

//	public List<ZaCoreEventMsg> getZaCoreEventMsgs() {
//		return zaCoreEventMsgs;
//	}
//
//	public void setZaCoreEventMsgs(List<ZaCoreEventMsg> zaCoreEventMsgs) {
//		this.zaCoreEventMsgs = zaCoreEventMsgs;
//	}

}
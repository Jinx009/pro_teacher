package nbBase.database.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the nb_user database table.
 * 
 */
@Entity
@Table(name="za_user")
@NamedQuery(name="ZaUser.findAll", query="SELECT n FROM ZaUser n")
public class ZaUser implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String applicationId;

	private String descAsLeader;

	private String email;

	private Boolean emailVerified;

	private Long externalUserCallId;

	@Lob
	private String headPic;

	private String mobilePhone;

	private Boolean mobilePhoneVerified;

	private String password;

	private String realname;

	private String username;

	private String userOpenCode;

	
	public ZaUser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getDescAsLeader() {
		return this.descAsLeader;
	}

	public void setDescAsLeader(String descAsLeader) {
		this.descAsLeader = descAsLeader;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return this.emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Long getExternalUserCallId() {
		return this.externalUserCallId;
	}

	public void setExternalUserCallId(Long externalUserCallId) {
		this.externalUserCallId = externalUserCallId;
	}

	public String getHeadPic() {
		return this.headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Boolean getMobilePhoneVerified() {
		return this.mobilePhoneVerified;
	}

	public void setMobilePhoneVerified(Boolean mobilePhoneVerified) {
		this.mobilePhoneVerified = mobilePhoneVerified;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserOpenCode() {
		return this.userOpenCode;
	}

	public void setUserOpenCode(String userOpenCode) {
		this.userOpenCode = userOpenCode;
	}
	
	public String getDisplayName(){
		if( realname == null || realname.length() < 1){
			return realname;
		}else{
			return username;
		}
	}

}
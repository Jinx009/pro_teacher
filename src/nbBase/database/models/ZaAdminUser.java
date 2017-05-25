package nbBase.database.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the za_admin_user database table.
 * 
 */
@Entity
@Table(name="za_admin_user")
@NamedQuery(name="ZaAdminUser.findAll", query="SELECT z FROM ZaAdminUser z")
public class ZaAdminUser implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private byte isAccountManager;

	private byte isDeveloper;

	private byte isZAOwner;

	private String password;

	private String username;
	
	private String realname;

	public ZaAdminUser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getIsAccountManager() {
		return this.isAccountManager;
	}

	public void setIsAccountManager(byte isAccountManager) {
		this.isAccountManager = isAccountManager;
	}

	public byte getIsDeveloper() {
		return this.isDeveloper;
	}

	public void setIsDeveloper(byte isDeveloper) {
		this.isDeveloper = isDeveloper;
	}

	public byte getIsZAOwner() {
		return this.isZAOwner;
	}

	public void setIsZAOwner(byte isZAOwner) {
		this.isZAOwner = isZAOwner;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDisplayName() {
		return this.getRealname() == null ? this.getUsername() : this.getRealname() ;
	}

//	@Override
//	public Map<String, Object> modelToMap() {
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("id",this.getId());
//		data.put("isAccountManager",this.getIsAccountManager());
//		data.put("isPaied",this.getIsDeveloper());
//		data.put("paiedDate",this.getIsZAOwner());
//		data.put("realname",this.getRealname());
//		data.put("usageDesc",this.getUsername());
//		return data;
//	}

}
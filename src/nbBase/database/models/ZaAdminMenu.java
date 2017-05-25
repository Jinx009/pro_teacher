package nbBase.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the za_admin_menu database table.
 * 
 */
@Entity
@Table(name="za_admin_menu")
@NamedQuery(name="ZaAdminMenu.findAll", query="SELECT z FROM ZaAdminMenu z")
public class ZaAdminMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="menu_icon")
	private String menuIcon;

	@Column(name="menu_name")
	private String menuName;

	@Column(name="menu_parent")
	private int menuParent;

	@Column(name="menu_url")
	private String menuUrl;
	
	@Column(name="menu_code", unique=true)
	private String menuCode;

	public ZaAdminMenu() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuParent() {
		return this.menuParent;
	}

	public void setMenuParent(int menuParent) {
		this.menuParent = menuParent;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

}
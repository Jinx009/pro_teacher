package yuexiang.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the yuexiang_tags database table.
 * 
 */
@Entity
@Table(name="yuexiang_tags")
@NamedQuery(name="YuexiangTag.findAll", query="SELECT y FROM YuexiangTag y")
public class YuexiangTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String catalog;

	@Column(name="is_admin_only")
	private Boolean isAdminOnly;

	@Column(name="is_muilty_select")
	private Boolean isMuiltySelect;

	private String tag;

	@Column(name="tag_max")
	private String tagMax;

	public YuexiangTag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Boolean getIsAdminOnly() {
		return this.isAdminOnly;
	}

	public void setIsAdminOnly(Boolean isAdminOnly) {
		this.isAdminOnly = isAdminOnly;
	}

	public Boolean getIsMuiltySelect() {
		return this.isMuiltySelect;
	}

	public void setIsMuiltySelect(Boolean isMuiltySelect) {
		this.isMuiltySelect = isMuiltySelect;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagMax() {
		return this.tagMax;
	}

	public void setTagMax(String tagMax) {
		this.tagMax = tagMax;
	}
	


}
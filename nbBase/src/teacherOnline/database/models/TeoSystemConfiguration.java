package teacherOnline.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the nb_system_configuration database table.
 * 
 */
@Entity
@Table(name="teo_system_configuration")
@NamedQuery(name="TeoSystemConfiguration.findAll", query="SELECT n FROM TeoSystemConfiguration n")
public class TeoSystemConfiguration implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="config_code", unique = true)
	private int configCode;

	@Lob
	@Column(name="config_value")
	private String configValue;

	@Lob
	private String description;

	public TeoSystemConfiguration() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConfigCode() {
		return this.configCode;
	}

	public void setConfigCode(int configCode) {
		this.configCode = configCode;
	}

	public String getConfigValue() {
		return this.configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
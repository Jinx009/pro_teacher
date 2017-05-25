package nbBase.database.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nbBase.database.common.nbBaseModel;


/**
 * The persistent class for the nb_sms_template database table.
 * 
 */
@Entity
@Table(name="za_sms_template")
@NamedQuery(name="ZaSmsTemplate.findAll", query="SELECT n FROM ZaSmsTemplate n")
public class ZaSmsTemplate implements Serializable, nbBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="comments", nullable=true)
	private String comments;

	@Lob
	@Column(name="request_template")
	private String requestTemplate;

	//bi-directional many-to-one association to ZaSmsSend
	@OneToMany(mappedBy="nbSmsTemplate")
	private List<ZaSmsSend> nbSmsSends;

	public ZaSmsTemplate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestReasonCode() {
		return this.comments;
	}

	public void setRequestReasonCode(String comments) {
		this.comments = comments;
	}

	public String getRequestTemplate() {
		return this.requestTemplate;
	}

	public void setRequestTemplate(String requestTemplate) {
		this.requestTemplate = requestTemplate;
	}

	public List<ZaSmsSend> getNbSmsSends() {
		return this.nbSmsSends;
	}

	public void setNbSmsSends(List<ZaSmsSend> nbSmsSends) {
		this.nbSmsSends = nbSmsSends;
	}

	public ZaSmsSend addNbSmsSend(ZaSmsSend nbSmsSend) {
		getNbSmsSends().add(nbSmsSend);
		nbSmsSend.setNbSmsTemplate(this);

		return nbSmsSend;
	}

	public ZaSmsSend removeNbSmsSend(ZaSmsSend nbSmsSend) {
		getNbSmsSends().remove(nbSmsSend);
		nbSmsSend.setNbSmsTemplate(null);

		return nbSmsSend;
	}

}
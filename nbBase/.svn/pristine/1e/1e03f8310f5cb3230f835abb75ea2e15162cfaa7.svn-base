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
 * The persistent class for the yuexiang_verbal_article database table.
 * 
 */
@Entity
@Table(name="yuexiang_verbal_article")
@NamedQuery(name="YuexiangVerbalArticle.findAll", query="SELECT y FROM YuexiangVerbalArticle y")
public class YuexiangVerbalArticle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="img_url")
	private String imgUrl;

	private String introduce;

	private String title;

	@Column(name="verbal_url")
	private String verbalUrl;

	public YuexiangVerbalArticle() {
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

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVerbalUrl() {
		return this.verbalUrl;
	}

	public void setVerbalUrl(String verbalUrl) {
		this.verbalUrl = verbalUrl;
	}

}
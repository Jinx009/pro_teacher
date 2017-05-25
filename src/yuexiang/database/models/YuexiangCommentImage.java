package yuexiang.database.models;

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
 * The persistent class for the yuexiang_comment_images database table.
 * 
 */
@Entity
@Table(name="yuexiang_comment_images")
@NamedQuery(name="YuexiangCommentImage.findAll", query="SELECT y FROM YuexiangCommentImage y")
public class YuexiangCommentImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="img_url")
	private String imgUrl;
	
	@Column(name="thum_url")
	private String thumUrl;

	
	//bi-directional many-to-one association to YuexiangComment
	@ManyToOne
	@JoinColumn(name="comment_id")
	private YuexiangComment parentComment;

	public YuexiangCommentImage() {
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

	public YuexiangComment getParentComment() {
		return this.parentComment;
	}

	public void setParentComment(YuexiangComment parentComment) {
		this.parentComment = parentComment;
	}

	public String getThumUrl() {
		return thumUrl;
	}

	public void setThumUrl(String thumUrl) {
		this.thumUrl = thumUrl;
	}

}
package yuexiang.database.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontUserWx;


/**
 * The persistent class for the yuexiang_comments database table.
 * 
 */
@Entity
@Table(name="yuexiang_comments")
@NamedQuery(name="YuexiangComment.findAll", query="SELECT y FROM YuexiangComment y")
public class YuexiangComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="comment_date")
	private Date commentDate;

	@Column(name="is_deleted")
	private Boolean isDeleted;

	//bi-directional many-to-one association to YuexiangLike
	@OneToMany(mappedBy="yuexiangComment")
	private List<YuexiangLike> yuexiangLikes;

	//bi-directional many-to-one association to YuexiangCommnetImage
	@OneToMany(mappedBy="parentComment")
	private List<YuexiangCommentImage> imageList;

	//bi-directional many-to-one association to YuexiangBooksWarehouse
	@ManyToOne
	@JoinColumn(name="book_id")
	private YuexiangBook book;

	//bi-directional many-to-one association to YuexiangComment
	@ManyToOne
	@JoinColumn(name="parent_comment_id", referencedColumnName="id")
	private YuexiangComment parent;

	//bi-directional many-to-one association to YuexiangComment
	@OneToMany(mappedBy="parent")
	@Where(clause="is_deleted=0")
	@OrderBy("id DESC")
	private List<YuexiangComment> childList;

	//uni-directional one-to-one association to YuexiangComment
	@OneToOne
	@JoinColumn(name="recommend_comment_id")
	private YuexiangComment bestComment;

	//uni-directional many-to-one association to ZaAdminUser
	@ManyToOne
	@JoinColumn(name="commenter_operator_id")
	private ZaAdminUser createrOperator;

	//uni-directional many-to-one association to ZaFrontUserWx
	@ManyToOne
	@JoinColumn(name="commenter_wx_id")
	private ZaFrontUserWx createrUserWx;
	
	//bi-directional many-to-one association to YuexiangTopic
	@ManyToOne
	@JoinColumn(name="topic_id")
	private YuexiangTopic topic;

	public YuexiangComment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentDate() {
		return this.commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<YuexiangCommentImage> getImageList() {
		return this.imageList;
	}

	public void setImageList(List<YuexiangCommentImage> imageList) {
		this.imageList = imageList;
	}

	public YuexiangCommentImage addImageList(YuexiangCommentImage imageList) {
		getImageList().add(imageList);
		imageList.setParentComment(this);

		return imageList;
	}

	public YuexiangCommentImage removeImageList(YuexiangCommentImage imageList) {
		getImageList().remove(imageList);
		imageList.setParentComment(null);

		return imageList;
	}

	public YuexiangBook getBook() {
		return this.book;
	}

	public void setBook(YuexiangBook book) {
		this.book = book;
	}

	public YuexiangComment getParent() {
		return this.parent;
	}

	public void setParent(YuexiangComment parent) {
		this.parent = parent;
	}

	public List<YuexiangComment> getChildList() {
		
		return this.childList == null ? new ArrayList<YuexiangComment>() : this.childList;
	}

	public void setChildList(List<YuexiangComment> childList) {
		this.childList = childList;
	}

	public YuexiangComment addChildList(YuexiangComment child) {
		getChildList().add(child);
		child.setParent(this);

		return child;
	}

	public YuexiangComment removeChildList(YuexiangComment child) {
		getChildList().remove(child);
		child.setParent(null);
		
		return child;
	}

	public YuexiangComment getBestComment() {
		return this.bestComment;
	}

	public void setBestComment(YuexiangComment bestComment) {
		this.bestComment = bestComment;
	}

	public ZaAdminUser getCreaterOperator() {
		return this.createrOperator;
	}

	public void setCreaterOperator(ZaAdminUser createrOperator) {
		this.createrOperator = createrOperator;
	}

	public ZaFrontUserWx getCreaterUserWx() {
		return this.createrUserWx;
	}

	public void setCreaterUserWx(ZaFrontUserWx createrUserWx) {
		this.createrUserWx = createrUserWx;
	}
	
	public List<YuexiangLike> getYuexiangLikes() {
		return this.yuexiangLikes == null ? new ArrayList<YuexiangLike>() : this.yuexiangLikes;
	}

	public void setYuexiangLikes(List<YuexiangLike> yuexiangLikes) {
		this.yuexiangLikes = yuexiangLikes;
	}

	public YuexiangLike addYuexiangLike(YuexiangLike yuexiangLike) {
		getYuexiangLikes().add(yuexiangLike);
		yuexiangLike.setYuexiangComment(this);

		return yuexiangLike;
	}

	public YuexiangLike removeYuexiangLike(YuexiangLike yuexiangLike) {
		getYuexiangLikes().remove(yuexiangLike);
		yuexiangLike.setYuexiangComment(null);

		return yuexiangLike;
	}
	
	public YuexiangTopic getTopic() {
		return this.topic;
	}

	public void setTopic(YuexiangTopic topic) {
		this.topic = topic;
	}

}
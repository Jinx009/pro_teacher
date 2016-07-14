package yuexiang.database.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
 * The persistent class for the yuexiang_books database table.
 * 
 */
@Entity
@Table(name="yuexiang_books")
@NamedQuery(name="YuexiangBook.findAll", query="SELECT y FROM YuexiangBook y")
public class YuexiangBook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private Boolean approval01;

	private Boolean approval02;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="approve01_time")
	private Date approve01Time;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="approve02_time")
	private Date approve02Time;

	@Column(name="book_code")
	private String bookCode;
	
	@Column(name="is_in_warehouse")
	private boolean isWarehouse;
	
	@Column(name="is_verbal_only")
	private boolean isVerbalOnly;

	@Column(name="book_cover_img")
	private String bookCoverImg;

	@Column(name="book_cover_thum_img")
	private String bookCoverThumImg;

	@Lob
	@Column(name="book_description")
	private String bookDescription;

	@Column(name="book_name")
	private String bookName;

	@Column(name="book_publisher")
	private String bookPublisher;

	@Column(name="book_writer")
	private String bookWriter;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="is_deleted")
	private boolean isDeleted;

	@Column(name="verbal_url")
	private String verbalUrl;

	//uni-directional many-to-one association to ZaAdminUser
	@ManyToOne
	@JoinColumn(name="creater_operator_id", referencedColumnName="id")
	private ZaAdminUser adminUser;

	//uni-directional many-to-one association to ZaFrontUserWx
	@ManyToOne
	@JoinColumn(name="creater_wx_id", referencedColumnName="id")
	private ZaFrontUserWx createrWxUser;

	//bi-directional one-to-one association to YuexiangComment
	@OneToOne
	@JoinColumn(name="recommend_comment_id", referencedColumnName="id")
	private YuexiangComment recomComment;

	//uni-directional many-to-many association to YuexiangTag
	@ManyToMany
	@JoinTable(
			name="yuexiang_books_tags"
			, joinColumns={
				@JoinColumn(name="book_id")
				}
			, inverseJoinColumns={
				@JoinColumn(name="tag_id")
				}
			)
	private List<YuexiangTag> tags;
	

	//uni-directional many-to-one association to YuexiangBook
	@ManyToOne
	@JoinColumn(name="related_master_book_id")
	private YuexiangBook masterBook;

	//uni-directional many-to-one association to ZaAdminUser
	@ManyToOne
	@JoinColumn(name="approver01_id", referencedColumnName="id")
	private ZaAdminUser approver01;

	//uni-directional many-to-one association to ZaAdminUser
	@ManyToOne
	@JoinColumn(name="approver02_id", referencedColumnName="id")
	private ZaAdminUser approver02;
	
	//bi-directional many-to-one association to YuexiangComment
	@OneToMany(mappedBy="book")
	@Where(clause="is_deleted=0")
	@OrderBy("id DESC")
	private List<YuexiangComment> comments;

	public YuexiangBook() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getApproval01() {
		return this.approval01;
	}

	public void setApproval01(Boolean approval01) {
		this.approval01 = approval01;
	}

	public Boolean getApproval02() {
		return this.approval02;
	}

	public void setApproval02(Boolean approval02) {
		this.approval02 = approval02;
	}

	public Date getApprove01Time() {
		return this.approve01Time;
	}

	public void setApprove01Time(Date approve01Time) {
		this.approve01Time = approve01Time;
	}

	public Date getApprove02Time() {
		return this.approve02Time;
	}

	public void setApprove02Time(Date approve02Time) {
		this.approve02Time = approve02Time;
	}

	public String getBookCode() {
		return this.bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getBookCoverImg() {
		return this.bookCoverImg;
	}

	public void setBookCoverImg(String bookCoverImg) {
		this.bookCoverImg = bookCoverImg;
	}

	public String getBookCoverThumImg() {
		return this.bookCoverThumImg;
	}

	public void setBookCoverThumImg(String bookCoverThumImg) {
		this.bookCoverThumImg = bookCoverThumImg;
	}

	public String getBookDescription() {
		return this.bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookPublisher() {
		return this.bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public String getBookWriter() {
		return this.bookWriter;
	}

	public void setBookWriter(String bookWriter) {
		this.bookWriter = bookWriter;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getVerbalUrl() {
		return this.verbalUrl;
	}

	public void setVerbalUrl(String verbalUrl) {
		this.verbalUrl = verbalUrl;
	}

	public ZaAdminUser getAdminUser() {
		return this.adminUser;
	}

	public void setAdminUser(ZaAdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public ZaFrontUserWx getCreaterWxUser() {
		return this.createrWxUser;
	}

	public void setCreaterWxUser(ZaFrontUserWx createrWxUser) {
		this.createrWxUser = createrWxUser;
	}

	public YuexiangComment getRecomComment() {
		return this.recomComment;
	}

	public void setRecomComment(YuexiangComment recomComment) {
		this.recomComment = recomComment;
	}

	public List<YuexiangTag> getTags() {
		return this.tags;
	}

	public void setTags(List<YuexiangTag> tags) {
		this.tags = tags;
	}
	
	public void addTag(YuexiangTag tag) {
		if( this.tags == null ) this.tags = new ArrayList<YuexiangTag>();
		for( YuexiangTag thisTag : this.tags){
			if( thisTag.getId() == tag.getId()){
				return;
			}
		}
		this.tags.add(tag);
	}
	
	public void removeTag(YuexiangTag tag) {
		if( this.tags == null ) this.tags = new ArrayList<YuexiangTag>();
		for (Iterator<YuexiangTag> iter = this.tags.iterator(); iter.hasNext();) {
			YuexiangTag element = iter.next();
			if( element.getId() == tag.getId() ){
				iter.remove();
			}
		}
	}

	public YuexiangBook getMasterBook() {
		return this.masterBook;
	}

	public void setMasterBook(YuexiangBook masterBook) {
		this.masterBook = masterBook;
	}

	public ZaAdminUser getApprover01() {
		return this.approver01;
	}

	public void setApprover01(ZaAdminUser approver01) {
		this.approver01 = approver01;
	}

	public ZaAdminUser getApprover02() {
		return this.approver02;
	}

	public void setApprover02(ZaAdminUser approver02) {
		this.approver02 = approver02;
	}

	public List<YuexiangComment> getComments() {
		return comments;
	}

	public void setComments(List<YuexiangComment> comments) {
		this.comments = comments;
	}
	
	public YuexiangComment addComment(YuexiangComment comment) {
		if(this.comments == null) this.comments = new ArrayList<YuexiangComment>();
		getComments().add(comment);
		comment.setBook(this);

		return comment;
	}
	
	public List<YuexiangComment> addComment(List<YuexiangComment> comments) {
		if(this.comments == null) this.comments = new ArrayList<YuexiangComment>();
		getComments().addAll(comments);
		for( YuexiangComment comment : comments)
			comment.setBook(this);

		return comments;
	}

	public YuexiangComment removeComment(YuexiangComment comment) {
		if(this.comments == null) this.comments = new ArrayList<YuexiangComment>();
		getComments().remove(comment);
		comment.setBook(null);

		return comment;
	}

	public boolean getIsWarehouse() {
		return isWarehouse;
	}

	public void setIsWarehouse(boolean isWarehouse) {
		this.isWarehouse = isWarehouse;
	}

	public  List<YuexiangComment> getComments(boolean isExcludRecomComment) {
		if( !isExcludRecomComment || this.recomComment == null )
			return getComments();
		else{
			List<YuexiangComment> ret = getComments();
			for( YuexiangComment comment : ret){
				if( comment.getId() == this.recomComment.getId() ){
					ret.remove(comment);
				}
			}
			return ret;
		}
	}

	public boolean isVerbalOnly() {
		return isVerbalOnly;
	}

	public void setVerbalOnly(boolean isVerbalOnly) {
		this.isVerbalOnly = isVerbalOnly;
	}

}
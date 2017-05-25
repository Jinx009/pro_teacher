package yuexiang.database.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import nbBase.database.models.ZaFrontUserWx;


/**
 * The persistent class for the yuexiang_likes database table.
 * 
 */
@Entity
@Table(name="yuexiang_likes")
@NamedQuery(name="YuexiangLike.findAll", query="SELECT y FROM YuexiangLike y")
public class YuexiangLike implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	//bi-directional many-to-one association to YuexiangComment
	@ManyToOne
	@JoinColumn(name="comment_id")
	private YuexiangComment yuexiangComment;

	//uni-directional many-to-one association to ZaFrontUserWx
	@ManyToOne
	@JoinColumn(name="user_wx_id")
	private ZaFrontUserWx wxUser;

	public YuexiangLike() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public YuexiangComment getYuexiangComment() {
		return this.yuexiangComment;
	}

	public void setYuexiangComment(YuexiangComment yuexiangComment) {
		this.yuexiangComment = yuexiangComment;
	}

	public ZaFrontUserWx getWxUser() {
		return this.wxUser;
	}

	public void setWxUser(ZaFrontUserWx wxUser) {
		this.wxUser = wxUser;
	}

}
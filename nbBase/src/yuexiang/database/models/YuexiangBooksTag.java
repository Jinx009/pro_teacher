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
 * The persistent class for the yuexiang_books_tags database table.
 * 
 */
@Entity
@Table(name="yuexiang_books_tags")
@NamedQuery(name="YuexiangBooksTag.findAll", query="SELECT y FROM YuexiangBooksTag y")
public class YuexiangBooksTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="book_id")
	private int bookId;

	@Column(name="tag_id")
	private int tagId;

	public YuexiangBooksTag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

}
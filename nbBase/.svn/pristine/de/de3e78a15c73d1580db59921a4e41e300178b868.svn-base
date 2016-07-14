package yuexiang.database.models;

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


/**
 * The persistent class for the yuexiang_topic database table.
 * 
 */
@Entity
@Table(name="yuexiang_topic")
@NamedQuery(name="YuexiangTopic.findAll", query="SELECT y FROM YuexiangTopic y")
public class YuexiangTopic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	@Column(name="topic_name")
	private String topicName;

	//bi-directional many-to-one association to YuexiangComment
	@OneToMany(mappedBy="topic")
	private List<YuexiangComment> comments;

	public YuexiangTopic() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopicName() {
		return this.topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public List<YuexiangComment> getComments() {
		return this.comments;
	}

	public void setComments(List<YuexiangComment> comments) {
		this.comments = comments;
	}

	public YuexiangComment addComment(YuexiangComment comment) {
		getComments().add(comment);
		comment.setTopic(this);

		return comment;
	}

	public YuexiangComment removeComment(YuexiangComment comment) {
		getComments().remove(comment);
		comment.setTopic(null);

		return comment;
	}

}
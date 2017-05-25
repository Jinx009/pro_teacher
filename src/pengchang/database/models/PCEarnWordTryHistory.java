package pengchang.database.models;

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
 * The persistent class for the za_earn_word_try_history database table.
 * 
 */
@Entity
@Table(name="za_earn_word_try_history")
@NamedQuery(name="PCEarnWordTryHistory.findAll", query="SELECT z FROM PCEarnWordTryHistory z")
public class PCEarnWordTryHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(name="is_awarded")
	private Boolean isAwarded;

	@Column(name="try_timestamp")
	private Long tryTimestamp;

	@Column(name="user_answer")
	private String userAnswer;

	//bi-directional many-to-one association to ZaCoreOrder
	@ManyToOne
	@JoinColumn(name="order_id")
	private PCCoreOrder order;

	//bi-directional many-to-one association to ZaEarnWord
	@ManyToOne
	@JoinColumn(name="word_id")
	private PCEarnWord zaEarnWord;

	public PCEarnWordTryHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsAwarded() {
		return this.isAwarded;
	}

	public void setIsAwarded(Boolean isAwarded) {
		this.isAwarded = isAwarded;
	}

	public Long getTryTimestamp() {
		return this.tryTimestamp;
	}

	public void setTryTimestamp(Long tryTimestamp) {
		this.tryTimestamp = tryTimestamp;
	}

	public String getUserAnswer() {
		return this.userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public PCCoreOrder getZaCoreOrder() {
		return this.order;
	}

	public void setZaCoreOrder(PCCoreOrder zaCoreOrder) {
		this.order = zaCoreOrder;
	}

	public PCEarnWord getZaEarnWord() {
		return this.zaEarnWord;
	}

	public void setZaEarnWord(PCEarnWord zaEarnWord) {
		this.zaEarnWord = zaEarnWord;
	}

}
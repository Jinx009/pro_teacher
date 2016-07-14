package pengchang.database.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the za_earn_words database table.
 * 
 */
@Entity
@Table(name="pc_earn_words")
@NamedQuery(name="PCEarnWord.findAll", query="SELECT z FROM PCEarnWord z")
public class PCEarnWord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String chinese;

	@Column(name="correct_times")
	private Integer correctTimes;

	private String english;

	@Column(name="load_times")
	private Integer loadTimes;

	//bi-directional many-to-one association to ZaEarnWordTryHistory
	@OneToMany(mappedBy="zaEarnWord")
	private List<PCEarnWordTryHistory> zaEarnWordTryHistories;

	public PCEarnWord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChinese() {
		return this.chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public Integer getCorrectTimes() {
		return this.correctTimes;
	}

	public void setCorrectTimes(Integer correctTimes) {
		this.correctTimes = correctTimes;
	}

	public String getEnglish() {
		return this.english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public Integer getLoadTimes() {
		return this.loadTimes;
	}

	public void setLoadTimes(Integer loadTimes) {
		this.loadTimes = loadTimes;
	}

	public List<PCEarnWordTryHistory> getZaEarnWordTryHistories() {
		return this.zaEarnWordTryHistories;
	}

	public void setZaEarnWordTryHistories(List<PCEarnWordTryHistory> zaEarnWordTryHistories) {
		this.zaEarnWordTryHistories = zaEarnWordTryHistories;
	}

	public PCEarnWordTryHistory addZaEarnWordTryHistory(PCEarnWordTryHistory zaEarnWordTryHistory) {
		getZaEarnWordTryHistories().add(zaEarnWordTryHistory);
		zaEarnWordTryHistory.setZaEarnWord(this);

		return zaEarnWordTryHistory;
	}

	public PCEarnWordTryHistory removeZaEarnWordTryHistory(PCEarnWordTryHistory zaEarnWordTryHistory) {
		getZaEarnWordTryHistories().remove(zaEarnWordTryHistory);
		zaEarnWordTryHistory.setZaEarnWord(null);

		return zaEarnWordTryHistory;
	}

}
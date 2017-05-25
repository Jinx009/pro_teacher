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

import nbBase.database.models.ZaFrontUserWx;


/**
 * The persistent class for the za_match_event_result database table.
 * 
 */
@Entity
@Table(name="pc_match_event_result")
@NamedQuery(name="PCMatchEventResult.findAll", query="SELECT z FROM PCMatchEventResult z")
public class PCMatchEventResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="play_result")
	private Integer playResult;
	
	@Column(name="player_b_confirmed")
	private Boolean playBConfirmed;

	//uni-directional many-to-one association to ZaCoreEvent
	@ManyToOne
	@JoinColumn(name="event_id")
	private PCCoreEvent event;

	//uni-directional many-to-one association to ZaFrontUserWx
	@ManyToOne
	@JoinColumn(name="wx_user_id_b")
	private ZaFrontUserWx playerB;

	//uni-directional many-to-one association to ZaFrontUserWx
	@ManyToOne
	@JoinColumn(name="wx_user_id_a")
	private ZaFrontUserWx playerA;

	public PCMatchEventResult() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPlayResult() {
		return this.playResult;
	}

	public void setPlayResult(Integer playResult) {
		this.playResult = playResult;
	}

	public PCCoreEvent getEvent() {
		return event;
	}

	public void setEvent(PCCoreEvent event) {
		this.event = event;
	}

	public ZaFrontUserWx getPlayerB() {
		return playerB;
	}

	public void setPlayerB(ZaFrontUserWx playerB) {
		this.playerB = playerB;
	}

	public ZaFrontUserWx getPlayerA() {
		return playerA;
	}

	public void setPlayerA(ZaFrontUserWx playerA) {
		this.playerA = playerA;
	}

	public Boolean getPlayBConfirmed() {
		return playBConfirmed;
	}

	public void setPlayBConfirmed(Boolean playBConfirmed) {
		this.playBConfirmed = playBConfirmed;
	}



}
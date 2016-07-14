package pengchang.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the za_core_event_rule database table.
 * 
 */
@Entity
@Table(name="pc_core_event_rule")
@NamedQuery(name="PCCoreEventRule.findAll", query="SELECT z FROM PCCoreEventRule z")
public class PCCoreEventRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="event_id")
	private Integer eventId;

	@Lob
	@Column(name="rule_award_long_desc")
	private String ruleAwardLongDesc;

	@Column(name="rule_is_need_address")
	private Boolean ruleIsNeedAddress;
	
	@Column(name="rule_is_active")
	private Boolean ruleIsActive;
	
	@Column(name="rule_is_deleted")
	private Boolean ruleIsDeleted;

	@Column(name="rule_is_need_barcode")
	private Boolean ruleIsNeedBarcode;
	
	@Column(name="is_acceptable_after_deadline")
	private Boolean isAcceptableAfterDeadline;

	@Column(name="rule_max_amount")
	private Integer ruleMaxAmount;
	
	@Column(name="is_count_in_member")
	private Boolean isCountInMember;
	
	@Column(name="is_count_in_amount")
	private Boolean isCountInAmount;

	@Column(name="rule_min_amount")
	private Integer ruleMinAmount;
	
	@Column(name="rule_max_member")
	private Integer ruleMaxMember;

	@Column(name="rule_min_member")
	private Integer ruleMinMember;

	@Column(name="rule_short_desc")
	private String ruleShortDesc;

	@Column(name="rule_unit_fee")
	private Integer ruleUnitFee;
	
	@Column(name="rule_is_can_many_copy")
	private Boolean ruleIsCanManyCopy;
	
	@ManyToOne
	@JoinColumn(name="event_id", updatable=false, insertable=false)
	private PCCoreEvent zaCoreEvent;
	

	public PCCoreEventRule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getRuleAwardLongDesc() {
		return this.ruleAwardLongDesc;
	}

	public void setRuleAwardLongDesc(String ruleAwardLongDesc) {
		this.ruleAwardLongDesc = ruleAwardLongDesc;
	}

	public Boolean getRuleIsNeedAddress() {
		return this.ruleIsNeedAddress;
	}

	public void setRuleIsNeedAddress(Boolean ruleIsNeedAddress) {
		this.ruleIsNeedAddress = ruleIsNeedAddress;
	}

	public Boolean getRuleIsNeedBarcode() {
		return this.ruleIsNeedBarcode;
	}

	public void setRuleIsNeedBarcode(Boolean ruleIsNeedBarcode) {
		this.ruleIsNeedBarcode = ruleIsNeedBarcode;
	}

	public Integer getRuleMaxAmount() {
		return this.ruleMaxAmount;
	}

	public void setRuleMaxAmount(Integer ruleMaxAmount) {
		this.ruleMaxAmount = ruleMaxAmount;
	}

	public Integer getRuleMinAmount() {
		return this.ruleMinAmount;
	}

	public void setRuleMinAmount(Integer ruleMinAmount) {
		this.ruleMinAmount = ruleMinAmount;
	}

	public String getRuleShortDesc() {
		return this.ruleShortDesc;
	}

	public void setRuleShortDesc(String ruleShortDesc) {
		this.ruleShortDesc = ruleShortDesc;
	}

	public Integer getRuleUnitFee() {
		return this.ruleUnitFee;
	}

	public void setRuleUnitFee(Integer ruleUnitFee) {
		this.ruleUnitFee = ruleUnitFee;
	}

	public Integer getRuleMaxMember() {
		return ruleMaxMember;
	}

	public void setRuleMaxMember(Integer ruleMaxMember) {
		this.ruleMaxMember = ruleMaxMember;
	}

	public Integer getRuleMinMember() {
		return ruleMinMember;
	}

	public void setRuleMinMember(Integer ruleMinMember) {
		this.ruleMinMember = ruleMinMember;
	}

	public Boolean getRuleIsCanManyCopy() {
		return ruleIsCanManyCopy;
	}

	public void setRuleIsCanManyCopy(Boolean ruleIsCanManyCopy) {
		this.ruleIsCanManyCopy = ruleIsCanManyCopy;
	}

	public Boolean getRuleIsActive() {
		return ruleIsActive;
	}

	public void setRuleIsActive(Boolean ruleIsActive) {
		this.ruleIsActive = ruleIsActive;
	}

	public Boolean getRuleIsDeleted() {
		return ruleIsDeleted;
	}

	public void setRuleIsDeleted(Boolean ruleIsDeleted) {
		this.ruleIsDeleted = ruleIsDeleted;
	}

	public Boolean getIsCountInMember() {
		return isCountInMember;
	}

	public void setIsCountInMember(Boolean isCountInMember) {
		this.isCountInMember = isCountInMember;
	}

	public Boolean getIsCountInAmount() {
		return isCountInAmount;
	}

	public void setIsCountInAmount(Boolean isCountInAmount) {
		this.isCountInAmount = isCountInAmount;
	}

	public PCCoreEvent getZaCoreEvent() {
		return zaCoreEvent;
	}

	public void setZaCoreEvent(PCCoreEvent zaCoreEvent) {
		this.zaCoreEvent = zaCoreEvent;
	}

	public Boolean getIsAcceptableAfterDeadline() {
		return isAcceptableAfterDeadline;
	}

	public void setIsAcceptableAfterDeadline(Boolean isAcceptableAfterDeadline) {
		this.isAcceptableAfterDeadline = isAcceptableAfterDeadline;
	}

}
package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：合伙人职级 实体
 * @date 2015-10-26
 */
public class MyRankDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -1222998228213730006L;

	private String partnerLevel; //合伙人等级
	private String partnerAllowance; //合伙人津贴(元)
	private String completion;//完成情况(true完成,false未完成)
	private String distPartnerLevel;//目标合伙人级别(完成最高级为“”)
	private String distPartnerAllowance;//目标合伙人津贴(元)
	private String upgrade;//升级日期
	private List<String> partnerTarget;//季度考核指标
	private List<String> distPartnerTarget;//目标合伙人考核指标
	private List<String> requirements;//离目标的差距

	public String getPartnerLevel() {
		return partnerLevel;
	}

	public void setPartnerLevel(String partnerLevel) {
		this.partnerLevel = partnerLevel;
	}

	public String getPartnerAllowance() {
		return partnerAllowance;
	}

	public void setPartnerAllowance(String partnerAllowance) {
		this.partnerAllowance = partnerAllowance;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public String getDistPartnerLevel() {
		return distPartnerLevel;
	}

	public void setDistPartnerLevel(String distPartnerLevel) {
		this.distPartnerLevel = distPartnerLevel;
	}

	public String getDistPartnerAllowance() {
		return distPartnerAllowance;
	}

	public void setDistPartnerAllowance(String distPartnerAllowance) {
		this.distPartnerAllowance = distPartnerAllowance;
	}

	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	public List<String> getPartnerTarget() {
		return partnerTarget;
	}

	public void setPartnerTarget(List<String> partnerTarget) {
		this.partnerTarget = partnerTarget;
	}

	public List<String> getDistPartnerTarget() {
		return distPartnerTarget;
	}

	public void setDistPartnerTarget(List<String> distPartnerTarget) {
		this.distPartnerTarget = distPartnerTarget;
	}

	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

}
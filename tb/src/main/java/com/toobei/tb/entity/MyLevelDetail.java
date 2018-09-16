package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：理财师等级 实体
 * @date 2015-10-26
 */
public class MyLevelDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8892446581789339961L;

	private String cfgLevel;//理财师等级
	private String cfgAllowance;//理财师津贴(元)
	private String cfgTarget; //季度考核指标
	private String completion;//完成情况(true完成,false未完成)
	private String distCfgLevel; //目标理财师级别(完成最高级为“”)
	private String distCfgAllowance; //目标理财师津贴(元)
	private String distCfgTarget; //目标理财师季度考核指标
	private String upgrade;//升级日期
	private String requirements;//距离升级还需

	public String getCfgLevel() {
		return cfgLevel;
	}

	public void setCfgLevel(String cfgLevel) {
		this.cfgLevel = cfgLevel;
	}

	public String getCfgAllowance() {
		return cfgAllowance;
	}

	public void setCfgAllowance(String cfgAllowance) {
		this.cfgAllowance = cfgAllowance;
	}

	public String getCfgTarget() {
		return cfgTarget;
	}

	public void setCfgTarget(String cfgTarget) {
		this.cfgTarget = cfgTarget;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public String getDistCfgLevel() {
		return distCfgLevel;
	}

	public void setDistCfgLevel(String distCfgLevel) {
		this.distCfgLevel = distCfgLevel;
	}

	public String getDistCfgAllowance() {
		return distCfgAllowance;
	}

	public void setDistCfgAllowance(String distCfgAllowance) {
		this.distCfgAllowance = distCfgAllowance;
	}

	public String getDistCfgTarget() {
		return distCfgTarget;
	}

	public void setDistCfgTarget(String distCfgTarget) {
		this.distCfgTarget = distCfgTarget;
	}

	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

}
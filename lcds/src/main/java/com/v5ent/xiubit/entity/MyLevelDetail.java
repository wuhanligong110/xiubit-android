package com.v5ent.xiubit.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：理财师等级 实体
 * @date 2016-2-15
 */
public class MyLevelDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8892446581789339961L;

	private String completion;//保级任务完成情况(true完成,false未完成)
	private String levelCode;//理财师等级编码
	private String nextLevelProcess;//离下级理财师进度(0-99)
	private String currAllowance;//当前津贴(元)
	private String currGrantInfo;//当前津贴发放描述
	private String nextAllowance;//下个季度津贴(元)
	private String nextGrantInfo;//下季度津贴发放描述
	private String upgrade;//升级日期
	private List<String> requirements;//距离保级(升级)还需
	private List<String> welfare;//享有权益

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getNextLevelProcess() {
		return nextLevelProcess;
	}

	public void setNextLevelProcess(String nextLevelProcess) {
		this.nextLevelProcess = nextLevelProcess;
	}

	public String getCurrAllowance() {
		return currAllowance;
	}

	public void setCurrAllowance(String currAllowance) {
		this.currAllowance = currAllowance;
	}

	public String getCurrGrantInfo() {
		return currGrantInfo;
	}

	public void setCurrGrantInfo(String currGrantInfo) {
		this.currGrantInfo = currGrantInfo;
	}

	public String getNextAllowance() {
		return nextAllowance;
	}

	public void setNextAllowance(String nextAllowance) {
		this.nextAllowance = nextAllowance;
	}

	public String getNextGrantInfo() {
		return nextGrantInfo;
	}

	public void setNextGrantInfo(String nextGrantInfo) {
		this.nextGrantInfo = nextGrantInfo;
	}

	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

	public List<String> getWelfare() {
		return welfare;
	}

	public void setWelfare(List<String> welfare) {
		this.welfare = welfare;
	}

}
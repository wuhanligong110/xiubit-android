package com.v5ent.xiubit.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：理财师佣金计算
 * @date 2016-2-15
 */
public class ProfitCalculate extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -7527982129462789442L;
	private String fee;//预期佣金
	private String otherProfit;//其他收益
	private String reward;//其他收益
	private String sum;//合计
	private List<String> profitType;//其他收益类型名称
	private String profitTypeString;//其他收益类型名称

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getOtherProfit() {
		return otherProfit;
	}

	public void setOtherProfit(String otherProfit) {
		this.otherProfit = otherProfit;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public List<String> getProfitType() {
		return profitType;
	}

	public void setProfitType(List<String> profitType) {
		this.profitType = profitType;
	}

	public String getProfitTypeStr() {
		if (profitType == null || profitType.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < profitType.size(); i++) {
			sb.append(profitType.get(i));
			if (i < profitType.size() - 1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	public String getProfitTypeString() {
		return profitTypeString;
	}

	public void setProfitTypeString(String profitTypeString) {
		this.profitTypeString = profitTypeString;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
}
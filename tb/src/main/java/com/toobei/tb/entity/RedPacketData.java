package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class RedPacketData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8370039548993217785L;

	private List<RedPacketModel> availableRedPapers;//可用红包
	private List<RedPacketModel> usedRedPapers;//已使用红包
	private List<RedPacketModel> expireRedPapers;//过期红包

	public List<RedPacketModel> getAvailableRedPapers() {
		return availableRedPapers;
	}

	public void setAvailableRedPapers(List<RedPacketModel> availableRedPapers) {
		this.availableRedPapers = availableRedPapers;
	}

	public List<RedPacketModel> getUsedRedPapers() {
		return usedRedPapers;
	}

	public void setUsedRedPapers(List<RedPacketModel> usedRedPapers) {
		this.usedRedPapers = usedRedPapers;
	}

	public List<RedPacketModel> getExpireRedPapers() {
		return expireRedPapers;
	}

	public void setExpireRedPapers(List<RedPacketModel> expireRedPapers) {
		this.expireRedPapers = expireRedPapers;
	}
}
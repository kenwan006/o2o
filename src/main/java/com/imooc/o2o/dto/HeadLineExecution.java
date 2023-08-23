package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.enums.HeadLineStateEnum;

public class HeadLineExecution {
	// result state
	private int state;

	// stateInfo
	private String stateInfo;

	// shop count
	private int count;

	// award
	private HeadLine headLine;

	// headline list
	private List<HeadLine> headLineList;

	public HeadLineExecution() {
	}

	// unsuccessful constructor
	public HeadLineExecution(HeadLineStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// successful constructor
	public HeadLineExecution(HeadLineStateEnum stateEnum, HeadLine headLine) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.headLine = headLine;
	}

	// successful constructor
	public HeadLineExecution(HeadLineStateEnum stateEnum, List<HeadLine> headLineList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.headLineList = headLineList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public HeadLine getHeadLine() {
		return headLine;
	}

	public void setHeadLine(HeadLine headLine) {
		this.headLine = headLine;
	}

	public List<HeadLine> getHeadLineList() {
		return headLineList;
	}

	public void setHeadLineList(List<HeadLine> headLineList) {
		this.headLineList = headLineList;
	}

}

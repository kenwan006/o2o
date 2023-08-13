package com.imooc.o2o.enums;

public enum ProductStateEnum {
	OFFLINE(-1, "Invalid Product"), DOWN(0, "Off Shelf"), SUCCESS(1, "Success"), INNER_ERROR(-1001, "Fail"), EMPTY(-1002, "Product Null");

	private int state;

	private String stateInfo;

	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static ProductStateEnum stateOf(int index) {
		for (ProductStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}

package com.imooc.o2o.enums;

public enum ShopStateEnum {
	CHECK(0, "under review"), OFFLINE(-1, "invalid shop"), SUCCESS(1, "success"), PASS(2, "authenticated"), INNER_ERROR(-1001,
			"internal error"), NULL_SHOPID(-1002, "ShopId is null"),NULL_SHOP(-1003, "shop info is null");
	private int state;
	private String stateInfo;

	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/**
	 * return the enum based on the input state
	 */
	public static ShopStateEnum stateOf(int state) {
		for (ShopStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
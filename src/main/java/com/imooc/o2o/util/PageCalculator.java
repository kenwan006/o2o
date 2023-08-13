package com.imooc.o2o.util;

public class PageCalculator {
	public static int calculateRowIndex(int pageIndex, int pageSize) { //get the starting row index
		return (pageIndex > 0)? (pageIndex - 1) * pageSize : 0;
	}
}

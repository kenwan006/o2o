package com.imooc.o2o.service;

import java.io.IOException;
import java.util.List;

//import com.imooc.o2o.dto.HeadLineExecution;
//import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.entity.HeadLine;

public interface HeadLineService {
	public static final String HLLISTKEY = "headlinelist";

	/**
	 * return headline under certain condition
	 * 
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

	/**
	 * add headline and save headline image
	 * 
	 * @param headLine
	 * @param thumbnail
	 * @return
	 *
	HeadLineExecution addHeadLine(HeadLine headLine, ImageHolder thumbnail);

	/**
	 * modify headline
	 * 
	 * @param headLine
	 * @param thumbnail
	 * @return
	 *
	HeadLineExecution modifyHeadLine(HeadLine headLine, ImageHolder thumbnail);

	/**
	 * delete single headline
	 * 
	 * @param headLineId
	 * @return
	 *
	HeadLineExecution removeHeadLine(long headLineId);

	/**
	 * delete headlines in batch
	 * 
	 * @param headLineIdList
	 * @return
	 *
	HeadLineExecution removeHeadLineList(List<Long> headLineIdList);
**/
}

package com.seda.dailyReport.service.dailyQuery;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.seda.dailyReport.model.vo.DailyPlanVo;

/**
 * 个人日志查询service
 * @author 郭腾飞 20180627
 *
 */
public interface DailyQueryService {

	/**
	 * 个人工作计划查询
	 * @param planVo
	 * @param request
	 * @return
	 */
	PageInfo<DailyPlanVo> queryPlanList(DailyPlanVo planVo, HttpServletRequest request);

}

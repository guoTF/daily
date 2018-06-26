package com.seda.dailyReport.control.dailyPlan;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seda.dailyReport.model.DailyPlan;
import com.seda.dailyReport.model.dto.OperationDto;
import com.seda.dailyReport.service.dailyPlan.DailyPlanService;

/**
 * 日工作计划Controller
 * @author 郭腾飞 20180626
 *
 */
@Controller
@RequestMapping("/dailyPlan")
public class DailyPlanController {

	@Resource
	private DailyPlanService dailyPlanService;
	
	/**
	 * 列表显示所选计划日的计划
	 * @param day
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPlan")
	@ResponseBody
	public List<DailyPlan> getPlan(String day, HttpServletRequest request){
		return this.dailyPlanService.getPlan(day, request);
	}
	
	/**
	 * 保存日工作计划
	 * @param planList
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/savePlan")
	@ResponseBody
	public OperationDto savePlan(List<DailyPlan> planList, HttpServletRequest request){
		return this.dailyPlanService.savePlan(planList, request);
	}
}

package com.seda.dailyReport.control.performance;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seda.dailyReport.model.dto.OperationDto;
import com.seda.dailyReport.model.vo.PerformanceVo;
import com.seda.dailyReport.service.performance.PerformanceService;

/**
 * 绩效考核controller类
 * @author 郭腾飞 20180629
 *
 */
@Controller
@RequestMapping("/performance")
public class PerformanceController {

	@Resource
	private PerformanceService performanceService;
	
	/**
	 * 跳转到绩效考核页面/update
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/newPerformancePage")
	@ResponseBody
	public PerformanceVo newPerformancePage(HttpServletRequest request){
		return this.performanceService.newPerformancePage(request);
	}
	
	/**
	 * 新增绩效考核内容
	 * @param pv
	 * @param request
	 * @return
	 */
	public OperationDto addPerformance(PerformanceVo pv, HttpServletRequest request){
		return this.performanceService.addPerformance(pv, request);
	}
	
}

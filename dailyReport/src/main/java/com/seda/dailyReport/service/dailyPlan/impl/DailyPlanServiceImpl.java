package com.seda.dailyReport.service.dailyPlan.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seda.dailyReport.dao.DailyPlanMapper;
import com.seda.dailyReport.model.DailyPlan;
import com.seda.dailyReport.model.DailyPlanExample;
import com.seda.dailyReport.model.dto.OperationDto;
import com.seda.dailyReport.service.dailyPlan.DailyPlanService;
import com.seda.dailyReport.util.CreatePrimaryKeyUtils;
import com.seda.dailyReport.util.DateUtils;

/**
 * 日工作计划service实现类
 * @author 郭腾飞 20180626
 *
 */
@Service
public class DailyPlanServiceImpl implements DailyPlanService {
	
	@Resource
	private DailyPlanMapper dailyPlanMapper;

	/**
	 * 保存日计划
	 */
	@Override
	@Transactional
	public OperationDto savePlan(List<DailyPlan> planList, HttpServletRequest request) {
		OperationDto dto = new OperationDto();
		String userID = (String) request.getSession().getAttribute("userID");
		String planDay = planList.get(0).getPlanDay();
		if (StringUtils.isBlank(planDay)) {
			return dto.fail("0", "计划日期未选择");
		}
		List<DailyPlan> oldPlanList = this.getPlan(planDay, request);
		//删除该计划日已经做好的计划
		if (CollectionUtils.isNotEmpty(oldPlanList)) {
			DailyPlanExample planExample = new DailyPlanExample();
			planExample.createCriteria().andUserIdEqualTo(userID).andPlanDayEqualTo(planDay);
			this.dailyPlanMapper.deleteByExample(planExample);
		}
		if (CollectionUtils.isNotEmpty(planList)) {
			int num = 0;
			for (DailyPlan dailyPlan : planList) {
				String planProjectName = dailyPlan.getPlanProjectName();
				String planContent = dailyPlan.getPlanContent();
				String planGoal = dailyPlan.getPlanGoal();
				Integer planNum = dailyPlan.getPlanNum();
				Double planTime = dailyPlan.getPlanTime();
				if (StringUtils.isBlank(planProjectName)) {
					return dto.fail("0", "项目名称为空");
				}
				if (StringUtils.isBlank(planContent)) {
					return dto.fail("0", "工作内容为空");
				}
				if (StringUtils.isBlank(planGoal)) {
					return dto.fail("0", "计划目标为空");
				}
				if (planNum == null) {
					return dto.fail("0", "序号为空");
				}
				if (planTime == null || planTime == 0.00) {
					return dto.fail("0", "工作量为空");
				}
				dailyPlan.setId(CreatePrimaryKeyUtils.createPrimaryKey());
				dailyPlan.setUserId(userID);
				dailyPlan.setCreateDate(new Date());
				int i = this.dailyPlanMapper.insert(dailyPlan);
				if (i == 1) {
					num = num + 1;
				}
			}
			if (num == planList.size()) {
				return dto.success("保存成功");
			}
		}
		return dto.fail("0", "保存失败");
	}

	/**
	 * 获取日计划
	 */
	@Override
	public List<DailyPlan> getPlan(String day, HttpServletRequest request) {
		String userID = (String) request.getSession().getAttribute("userID");
		DailyPlanExample planExample = new DailyPlanExample();
		planExample.createCriteria().andUserIdEqualTo(userID).andPlanDayEqualTo(day);
		List<DailyPlan> list = this.dailyPlanMapper.selectByExample(planExample);
		return list;
	}

	/**
	 * 删除一条记录
	 */
	@Override
	public OperationDto deleteOnePlan(String planId) {
		OperationDto dto = new OperationDto();
		if (StringUtils.isBlank(planId)) {
			return dto.fail("0", "未获取到删除信息！");
		}
		int i = this.dailyPlanMapper.deleteByPrimaryKey(planId);
		if (i == 1) {
			return dto.success("删除成功");
		}
		return dto.fail("0", "删除失败");
	}

}

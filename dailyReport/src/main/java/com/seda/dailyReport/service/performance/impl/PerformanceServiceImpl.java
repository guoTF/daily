package com.seda.dailyReport.service.performance.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.seda.dailyReport.dao.LoginUserMapper;
import com.seda.dailyReport.dao.PerformanceAppraisalMapper;
import com.seda.dailyReport.model.LoginUser;
import com.seda.dailyReport.model.PerformanceAppraisal;
import com.seda.dailyReport.model.PerformanceAppraisalExample;
import com.seda.dailyReport.model.dto.OperationDto;
import com.seda.dailyReport.model.vo.PerformanceVo;
import com.seda.dailyReport.service.performance.PerformanceService;
import com.seda.dailyReport.util.CreatePrimaryKeyUtils;
import com.seda.dailyReport.util.DateUtils;

/**
 * 绩效考核service实现类
 * @author 郭腾飞  20180629
 *
 */
@Service
public class PerformanceServiceImpl implements PerformanceService {
	
	@Resource
	private LoginUserMapper loginUserMapper;
	
	@Resource
	private PerformanceAppraisalMapper performanceAppraisalMapper;

	/**
	 * 跳转到新增绩效考核页面
	 */
	@Override
	public PerformanceVo newPerformancePage(HttpServletRequest request) {
		PerformanceVo pv = new PerformanceVo();
		String userId = (String) request.getSession().getAttribute("userID");
		LoginUser loginUser = this.loginUserMapper.selectByPrimaryKey(userId);
		pv.setLoginUser(loginUser);
		String format = DateUtils.format(new Date(), DateUtils.LONG_PURE_DIGITAL_PATTERN);
		String appraisalMonth = format.substring(0, 6);
		pv.setAppraisalMonth(appraisalMonth);
		//如果存在该工作周期的内容时，为更新内容。
		PerformanceAppraisalExample example = new PerformanceAppraisalExample();
		example.createCriteria().andAppraisalMonthEqualTo(appraisalMonth).andUseridEqualTo(userId);
		List<PerformanceAppraisal> paList = this.performanceAppraisalMapper.selectByExample(example);
		pv.setPaList(paList);
		return pv;
	}
	
	/**
	 * 新增绩效考核内容
	 */
	@Override
	public OperationDto addPerformance(PerformanceVo pv, HttpServletRequest request) {
		OperationDto dto = new OperationDto();
		LoginUser loginUser = pv.getLoginUser();
		String userId = loginUser.getId();
		String appraisalMonth = pv.getAppraisalMonth();
		List<PerformanceAppraisal> paList = pv.getPaList();
		if (CollectionUtils.isEmpty(paList)) {
			return dto.fail("0", "绩效考核内容不能为空");
		}
		PerformanceAppraisal pa = paList.get(0);
		Integer status = pa.getStatus();
		if (status == 1) {
			return dto.fail("0", "邮件已发送给上级主管");
		}
		//根据考核周期和用户id查询，是否之前已经保存过绩效考核内容
		PerformanceAppraisalExample example = new PerformanceAppraisalExample();
		example.createCriteria().andUseridEqualTo(userId).andAppraisalMonthEqualTo(appraisalMonth);
		List<PerformanceAppraisal> list = this.performanceAppraisalMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			int i = this.performanceAppraisalMapper.deleteByExample(example);
			if (i != list.size()) {
				return dto.fail("0", "保存失败1");
			}
		}
		int count = 0;
		for (int i = 0; i < paList.size(); i++) {
			PerformanceAppraisal appraisal = paList.get(i);
			String concreteFunction = appraisal.getConcreteFunction();	//具体功能
			String manHour = appraisal.getManHour();		//耗用工时
			String projectName = appraisal.getProjectName();	//项目名称
			String performance = appraisal.getPerformance();	//完成情况（%）
			if (StringUtils.isBlank(concreteFunction)) {
				return dto.fail("0", "具体功能为空");
			}
			if (StringUtils.isBlank(manHour)) {
				return dto.fail("0", "耗用工时为空");
			}
			if (StringUtils.isBlank(projectName)) {
				return dto.fail("0", "项目名称为空");
			}
			if (StringUtils.isBlank(performance)) {
				return dto.fail("0", "完成情况为空");
			}
			appraisal.setId(CreatePrimaryKeyUtils.createPrimaryKey());
			appraisal.setAppraisalMonth(appraisalMonth);
			appraisal.setCreateBy(userId);
			String format = DateUtils.format(new Date(), DateUtils.LONG_PURE_DIGITAL_PATTERN);
			appraisal.setCreateDate(format);
			appraisal.setUpdateBy(userId);
			appraisal.setUpdateDate(format);
			appraisal.setStatus(0);//状态(0：未发邮件，1：已发邮件)
			int j = this.performanceAppraisalMapper.insertSelective(appraisal);
			if (j == 1) {
				count ++;
			}
		}
		if (count == paList.size()) {
			return dto.success("保存成功");
		}
		return dto.fail("0", "保存失败");
	}


}

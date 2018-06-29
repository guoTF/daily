package com.seda.dailyReport.model.vo;

import java.util.List;

import com.seda.dailyReport.model.LoginUser;
import com.seda.dailyReport.model.PerformanceAppraisal;

/**
 * 绩效考核VO类
 * @author 郭腾飞 20180629
 *
 */
public class PerformanceVo {

	private LoginUser loginUser;
	
	private List<PerformanceAppraisal> paList;
	
	//任务名称
	private String taskName;
	
	//考核周期
	private String appraisalMonth;
	
	//员工姓名
	private String userName;

	public String getAppraisalMonth() {
		return appraisalMonth;
	}

	public void setAppraisalMonth(String appraisalMonth) {
		this.appraisalMonth = appraisalMonth;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	public List<PerformanceAppraisal> getPaList() {
		return paList;
	}

	public void setPaList(List<PerformanceAppraisal> paList) {
		this.paList = paList;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}

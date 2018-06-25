package com.seda.dailyReport.service.login;

import javax.servlet.http.HttpServletRequest;

import com.seda.dailyReport.model.LoginUser;
import com.seda.dailyReport.model.dto.OperationDto;

/**
 * 登录service
 * @author admin
 *
 */
public interface LoginService {

	/**
	 * 注册
	 * @param loginUser
	 * @param request
	 * @return
	 */
	OperationDto register(LoginUser loginUser, String identifyingCode, HttpServletRequest request);

}

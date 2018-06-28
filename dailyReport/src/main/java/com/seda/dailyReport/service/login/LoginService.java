package com.seda.dailyReport.service.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param identifyingCode
	 * @param request
	 * @return
	 */
	OperationDto login(String userName, String password, String identifyingCode, HttpServletRequest request);

	/**
	 * 获取手机验证码
	 * @param phone
	 * @param session
	 * @return
	 */
	OperationDto sendMobileCode(String phone, HttpSession session);

}

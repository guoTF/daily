package com.seda.dailyReport.control.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seda.dailyReport.model.LoginUser;
import com.seda.dailyReport.model.dto.OperationDto;
import com.seda.dailyReport.service.login.LoginService;

/**
 * 登录controller类
 * @author 郭腾飞 20180625
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Resource
	private LoginService loginService;
	
	/**
	 * 注册
	 * @param loginUser
	 * @param identifyingCode 验证码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/register")
	public OperationDto register(LoginUser loginUser, String identifyingCode, HttpServletRequest request){
		return this.loginService.register(loginUser, identifyingCode, request);
	}
	
	/**
	 * 登录
	 * @return
	 */
	public OperationDto login(){
		return null;
	}
	
	
}

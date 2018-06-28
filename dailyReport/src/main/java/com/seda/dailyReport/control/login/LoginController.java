package com.seda.dailyReport.control.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public OperationDto register(LoginUser loginUser, String identifyingCode, String mobileCode, HttpServletRequest request){
		return this.loginService.register(loginUser, identifyingCode, mobileCode, request);
	}
	
	/**
	 * 手机获取验证码
	 * @param phone 手机号
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sendMobileCode")
	@ResponseBody
	public OperationDto sendMobileCode(String phone, HttpSession session){
		return this.loginService.sendMobileCode(phone, session);
	}
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param identifyingCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public OperationDto login(String userName, String password, String identifyingCode, HttpServletRequest request){
		return this.loginService.login(userName, password, identifyingCode,request);
	}
	
	
}

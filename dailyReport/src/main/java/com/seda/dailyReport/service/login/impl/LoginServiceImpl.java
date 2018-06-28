package com.seda.dailyReport.service.login.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.seda.dailyReport.dao.LoginUserMapper;
import com.seda.dailyReport.model.LoginUser;
import com.seda.dailyReport.model.LoginUserExample;
import com.seda.dailyReport.model.dto.OperationDto;
import com.seda.dailyReport.service.login.LoginService;
import com.seda.dailyReport.util.CreatePrimaryKeyUtils;
import com.seda.dailyReport.util.SmsUtils;

/**
 * 登录service实现类
 * 
 * @author admin
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private LoginUserMapper loginUserMapper;

	/**
	 * 注册
	 */
	@Override
	public OperationDto register(LoginUser loginUser, String identifyingCode, String mobileCode, HttpServletRequest request) {
		OperationDto dto = new OperationDto();
		String userName = loginUser.getUserName();
		String password = loginUser.getPassword();
		String phone = loginUser.getPhone();
		String mail = loginUser.getMail();
		String imgCode = (String) request.getSession().getAttribute("imgCode");
		if (StringUtils.isBlank(userName)) {
			return dto.fail("0", "用户名为空");
		}
		if (StringUtils.isBlank(password)) {
			return dto.fail("0", "密码为空");
		}
		if (StringUtils.isBlank(phone)) {
			return dto.fail("0", "电话为空");
		}
		if (StringUtils.isBlank(mail)) {
			return dto.fail("0", "邮箱为空");
		}
		if (StringUtils.isBlank(identifyingCode) || !identifyingCode.equals(imgCode)) {
			return dto.fail("0", "验证码错误");
		}
		LoginUserExample phoneExample = new LoginUserExample();
		phoneExample.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(1);
		List<LoginUser> phoneList = this.loginUserMapper.selectByExample(phoneExample);
		if (CollectionUtils.isNotEmpty(phoneList)) {
			return dto.fail("0", "该手机号已被使用");
		}
		LoginUserExample mailExample = new LoginUserExample();
		mailExample.createCriteria().andMailEqualTo(mail).andStatusEqualTo(1);
		List<LoginUser> mailList = this.loginUserMapper.selectByExample(mailExample);
		if (CollectionUtils.isNotEmpty(mailList)) {
			return dto.fail("0", "该邮箱已被使用");
		}
		String code = (String) request.getSession().getAttribute("mobileCode");
		if (StringUtils.isBlank(mobileCode) || !code.equals(mobileCode)) {
			return dto.fail("0", "验证码错误");
		}
		loginUser.setId(CreatePrimaryKeyUtils.createPrimaryKey());
		loginUser.setStatus(1);
		int i = this.loginUserMapper.insertSelective(loginUser);
		if (i == 1) {
			return dto.success("注册成功");
		}
		return dto.fail("0", "注册失败");
	}

	/**
	 * 登录
	 */
	@Override
	public OperationDto login(String userName, String password, String identifyingCode, HttpServletRequest request) {
		OperationDto dto = new OperationDto();
		if (StringUtils.isEmpty(userName)) {
			return dto.fail("0", "用户名为空");
		}
		if (StringUtils.isEmpty(password)) {
			return dto.fail("0", "密码为空");
		}
		String imgCode = (String) request.getSession().getAttribute("imgCode");
		if (StringUtils.isEmpty(identifyingCode) || !identifyingCode.equals(imgCode)) {
			return dto.fail("0", "验证码错误");
		}
		LoginUserExample userExample = new LoginUserExample();
		userExample.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(password).andStatusEqualTo(1);
		List<LoginUser> userList = this.loginUserMapper.selectByExample(userExample);
		if (CollectionUtils.isNotEmpty(userList) && userList.size() == 1) {
			return dto.success("登录成功");
		}
		return dto.fail("0", "登录失败");
	}

	/**
	 * 获取手机验证码
	 */
	@Override
	public OperationDto sendMobileCode(String phone, HttpSession session) {
		OperationDto dto = new OperationDto();
		// 生成手机验证码
		int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
		String content = String.format("尊敬的用户，您正在注册日志管理，本次动态验证码：%s，有效时间：2分钟，请勿泄露。",
				mobileCode);
		String sendSms = SmsUtils.sendSms(phone, content, "注册验证码");
		String code = sendSms.substring(0, sendSms.indexOf(":"));
		if ("2".equals(code)) {
			session.setAttribute("mobileCode", mobileCode);
			return dto.success("短信验证码发送成功");
		}
		return dto.fail("0", "短信验证码发送失败");
	}

}

package com.seda.dailyReport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.seda.dailyReport.dao.UserMapper;
import com.seda.dailyReport.model.vo.UserExcelVo;
import com.seda.dailyReport.service.UtilService;

/**
 * 公用service实现类
 * @author admin
 *
 */
public class UtilServiceImpl implements UtilService {
	
	@Resource
	private UserMapper userMapper;

	/**
	 * 查询需要导出的user对象
	 */
	@Override
	public List<UserExcelVo> getUserExcelVo(UserExcelVo userExcelVo) {
		List<UserExcelVo> uevList = this.userMapper.queryUserExcelVo(userExcelVo);
		return uevList;
	}

}

package com.seda.dailyReport.service;

import java.util.List;

import com.seda.dailyReport.model.vo.UserExcelVo;

/**
 * 公用service类
 * @author admin
 *
 */
public interface UtilService {

	/**
	 * 查询需要导出的user对象
	 * @param userExcelVo
	 * @return
	 */
	List<UserExcelVo> getUserExcelVo(UserExcelVo userExcelVo);

}

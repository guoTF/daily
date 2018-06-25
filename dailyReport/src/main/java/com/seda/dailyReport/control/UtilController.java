package com.seda.dailyReport.control;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seda.dailyReport.model.vo.UserExcelVo;
import com.seda.dailyReport.service.UtilService;
import com.seda.dailyReport.util.ExportExcel;

/**
 * 公用controller类
 * @author 郭腾飞 20180625
 *
 */
@Controller
@RequestMapping("/util")
public class UtilController {

	@Resource
	private UtilService utilService;
	
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 * @param accountTradeFlowVO
	 * @throws Exception
	 */
	@RequestMapping(value="/export")
	public void export(HttpServletRequest request, HttpServletResponse response, UserExcelVo userExcelVo) throws Exception {  
		List<UserExcelVo> excelVoList = this.utilService.getUserExcelVo(userExcelVo);
        ExportExcel<UserExcelVo> ee= new ExportExcel<UserExcelVo>();  
        String[] headers = { "序号", "姓名", "年龄", "电话"};  
        String fileName = "用户表";  
        ee.exportExcel(headers,excelVoList,fileName,response);  
    }
}

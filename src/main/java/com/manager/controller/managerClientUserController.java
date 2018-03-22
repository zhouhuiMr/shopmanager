package com.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.config.initConfig;
import com.manager.model.managerClientUserModel;
import com.object.resultEnum;
import com.object.resultObject;
import com.project.object.clientUser;

@Controller
@RequestMapping("/action/manager")
public class managerClientUserController {
	
	@Autowired
	private managerClientUserModel clientuserModel;
	
	@RequestMapping(path="/clientuser/list",method=RequestMethod.POST)
	@ResponseBody
	public resultObject getClientUserList(
			@RequestParam("column") int column,
			@RequestParam("curpage") int curpage,
			@RequestParam("colname") String colname,
			@RequestParam("condition") String condition) {
		resultObject result = new resultObject();
		if(column < 0 || column > 40) {
			result.setResult(resultEnum.PARAMETERERROR);
			result.setData("");
		}else if(curpage <= 0) {
			result.setResult(resultEnum.PARAMETERERROR);
			result.setData("");
		}else if(!checkColumn(colname)) {
			result.setResult(resultEnum.PARAMETERERROR);
			result.setData("");
		}else {
			result = clientuserModel.getClientUserList(column, curpage, colname, condition);
		}
		return result;
	}
	
	@RequestMapping(path="/clientuser/change",method=RequestMethod.POST)
	@ResponseBody
	public resultObject changeClientUserStatus(
			@RequestParam("id") int id,
			@RequestParam("status") int status
			) {
		resultObject result = new resultObject();
		if(status == 0 || status ==1) {
			clientUser user = new clientUser();
			user.setId(id);
			user.setStatus(status);
			result = clientuserModel.changeClientUserStatus(user);
		}else {
			result.setResult(resultEnum.PARAMETERERROR);
			result.setData("");
		}
		return result;
	}
	
	
	private boolean checkColumn(String column) {
		boolean isContain = false;
		if(column.equals("")) {
			isContain = true;
		}else {
			for(String str :initConfig.CLIENTUSERCOLUMN) {
				if(column.equals(str)) {
					isContain = true;
					break;
				}
			}
		}
		return isContain;
	}
}

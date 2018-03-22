package com.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.manager.client.service.manageClientUser;
import com.object.resultEnum;
import com.object.resultObject;
import com.project.object.clientUser;

@Service
public class managerClientUserModel {
	
	@Reference(version="1.0.0",interfaceName="manageClientUser",timeout=5000)
	private manageClientUser manageClientUser;
	

	public resultObject getClientUserList(int column, int curpage, String colname, String condition) {
		resultObject result = new resultObject();
		int pageCount = getClientUserCount(condition);
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(3);
		map.put("column",column);
		map.put("curpage", curpage);
		map.put("count", pageCount);
		map.put("pagecount", (int)Math.ceil((double)pageCount/(double)column));
		map.put("clientuserlist", "");
		result.setResult(resultEnum.SUCCESS);
		result.setData(map);
		if(pageCount > 0) {
			ArrayList<com.project.object.clientUser> clientUserList = manageClientUser.getClientUserList(column, curpage, colname, condition);
			if(clientUserList == null) {
				result.setResult(resultEnum.ERROR);
				result.setData("");
			}else {
				for(com.project.object.clientUser user : clientUserList) {
					changeUserAttr(user);
				}
				map.put("clientuserlist", clientUserList);
			}
		}
		return result;
	}
	
	/**
	 * @param int 行数
	 * @param String 查询条件
	 * @return int 按条件查询到的所有数据
	 * 
	 * 获取当前条件下所有的数据个数
	 * 
	 * */
	public int getClientUserCount(String condition) {
		int pagecount = manageClientUser.getClientUserCount(condition);
		return pagecount;
	}
	
	public resultObject changeClientUserStatus(clientUser user) {
		resultObject result = new resultObject();
		int row = manageClientUser.chageClientUserStatus(user);
		if(row == 1) {
			result.setResult(resultEnum.SUCCESS);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			result.setData(map);
		}else {
			result.setResult(resultEnum.FAILED);
			result.setData("");
		}
		return result;
	}
	
	 
	/**
	 * @param clientUser
	 * 
	 * 将对象中属性为''改成'无'
	 * 
	 * */
	private void changeUserAttr(clientUser clientUser) {
		if(clientUser.getUsername().equals("")) {
			clientUser.setUsername("\u65e0");
		}
		if(clientUser.getProvince().equals("")) {
			clientUser.setProvince("\u65e0");
		}
		if(clientUser.getCity().equals("")) {
			clientUser.setCity("\u65e0");
		}
		if(clientUser.getArea().equals("")) {
			clientUser.setArea("\u65e0");
		}
		if(clientUser.getAddress().equals("")) {
			clientUser.setAddress("\u65e0");
		}
	}
}

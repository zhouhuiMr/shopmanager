package com.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.mapper.clientuserMapper;
import com.object.clientUser;
import com.object.resultEnum;
import com.object.resultObject;

@Service
public class managerClientUserModel {
	@Autowired
	private clientuserMapper clientuserDao;

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
			int startcolumn = column*(curpage-1);
			ArrayList<clientUser> clientUserList = clientuserDao.getUserByCondition(startcolumn, column, colname, condition);
			if(clientUserList == null) {
				result.setResult(resultEnum.ERROR);
				result.setData("");
			}else {
				for(clientUser user : clientUserList) {
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
		int pagecount = clientuserDao.getPageCount(condition);
		return pagecount;
	}
	
	public resultObject changeClientUserStatus(clientUser user) {
		resultObject result = new resultObject();
		int row = clientuserDao.changeClientStatus(user.getId(),user.getStatus());
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

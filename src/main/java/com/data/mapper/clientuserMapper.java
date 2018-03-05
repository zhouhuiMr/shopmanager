package com.data.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.object.clientUser;

/**
 * 用户列表功能：
 * 1、分页每页数量；2、当前的页数；3、按照某一列进行排序；
 * 4、根据手机号或用户名进行查询；5、修改用户的状态
 * 
 * */
public interface clientuserMapper {
	public ArrayList<clientUser> getUserByCondition(
			@Param("startcolumn") int startcolumn,
			@Param("column") int column,
			@Param("colname") String colname,
			@Param("condition") String condition);
	
	public int getPageCount(@Param("condition") String condition);
}

package com.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.object.managers;

public interface managersDao {
	
	@Select("SELECT * FROM managers WHERE username = #{username} AND password = #{password}")
	public managers managerLogin(@Param("username") String username,@Param("password") String password);
}

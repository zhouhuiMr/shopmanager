package com.data.mapper;

import org.apache.ibatis.annotations.Param;

import com.object.managers;

public interface managersMapper {
	public managers managersLogin(@Param("username") String username,@Param("password") String password);
}

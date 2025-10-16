package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
*@author Shimmer
*@date 2025/10/16
*/
public interface AdminMapper {

    int insert(Admin admin);

    void deleteById(Integer id);

    void updateById(Admin admin);

    @Select("select * from `admin` where id = #{id}")
    Admin selectById(Integer id);

    @Select("select * from `admin` where username = #{username}")
    Admin selectByUsername(String username);

    List<Admin> selectAll(Admin admin);

}
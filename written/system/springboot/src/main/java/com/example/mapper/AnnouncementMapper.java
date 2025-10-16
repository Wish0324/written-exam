package com.example.mapper;

import com.example.entity.Announcement;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
*@author Shimmer
*@date 2025/10/16
*/
public interface AnnouncementMapper {

    int insert(Announcement announcement);

    void deleteById(Integer id);

    void updateById(Announcement announcement);

    @Select("select * from `announcement` where id = #{id}")
    Announcement selectById(Integer id);

    List<Announcement> selectAll(Announcement announcement);

}
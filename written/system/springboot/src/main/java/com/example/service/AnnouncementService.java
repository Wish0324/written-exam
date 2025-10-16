package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Announcement;
import com.example.mapper.AnnouncementMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shimmer
 * @date 2025/10/16
 */
@Service
public class AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    public void add(Announcement announcement) {
        announcement.setTime(DateUtil.now());
        announcementMapper.insert(announcement);
    }

    public void deleteById(Integer id) {
        announcementMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            announcementMapper.deleteById(id);
        }
    }

    public void updateById(Announcement announcement) {
        announcementMapper.updateById(announcement);
    }

    public Announcement selectById(Integer id) {
        return announcementMapper.selectById(id);
    }

    public List<Announcement> selectAll(Announcement announcement) {
        return announcementMapper.selectAll(announcement);
    }

    public PageInfo<Announcement> selectPage(Announcement announcement, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Announcement> list = announcementMapper.selectAll(announcement);
        return PageInfo.of(list);
    }

}
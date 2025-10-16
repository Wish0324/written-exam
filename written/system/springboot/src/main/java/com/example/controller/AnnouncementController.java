package com.example.controller;

import com.example.common.Result;
import com.example.entity.Announcement;
import com.example.service.AnnouncementService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Shimmer
 * @date 2025/10/16
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    @PostMapping("/add")
    public Result add(@RequestBody Announcement announcement) {
        announcementService.add(announcement);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        announcementService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        announcementService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Announcement announcement) {
        announcementService.updateById(announcement);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Announcement announcement = announcementService.selectById(id);
        return Result.success(announcement);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Announcement announcement) {
        List<Announcement> list = announcementService.selectAll(announcement);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(
        Announcement announcement,
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Announcement> pageInfo = announcementService.selectPage(announcement, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
package com.lantu.modulName.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.modulName.entity.Competition;
import com.lantu.modulName.service.impl.CompetitionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/com")
public class CompetitionController {
    @Autowired
    CompetitionServiceImpl competitionService;


    @GetMapping("/list")
    public Result<?> getList(@RequestParam(value = "pageNo") Long pageNo,
                             @RequestParam(value = "pageSize") Long pageSize){
        List<Competition> competitions= competitionService.list();
        LambdaQueryWrapper<Competition> wrapper=new LambdaQueryWrapper<>();
        Page<Competition> page=new Page<>(pageNo,pageSize);
        competitionService.page(page,wrapper);
        return Result.success(page,"查询成功");
    }

    @GetMapping("/search")
    public Result<?> searchByName(@RequestParam(value = "pageNo") Long pageNo,
                                  @RequestParam(value = "pageSize") Long pageSize,
                                @RequestParam(value = "competitionName") String competitionName){
        Page<Competition> page = new Page<>(pageNo,pageSize);
        LambdaQueryWrapper<Competition> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(competitionName!=null,Competition::getCompetitionName,competitionName);
        competitionService.page(page,wrapper);
        return Result.success(page,"查询成功");
                                }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody Competition competition){

        if (competition!=null){
            competitionService.save(competition);
        }

        return Result.success("新增成功");
    }
    @GetMapping("/{id}")
    public Result<String> delete(@PathVariable("id") Integer id){
        competitionService.removeById(id);
        return Result.success("删除成功");
    }
}

package com.lantu.modulName.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.modulName.entity.Teacher;
import com.lantu.modulName.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tea")


public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    public Result<List<Teacher>> getList(){
        List<Teacher> teacherList= teacherService.list();
        return Result.success(teacherList,"查询成功");
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return Result.success("修改成功");
    }

    @PostMapping("/insert")
    public Result<?> insert(@RequestBody Teacher teacher){
        teacherService.save(teacher);
        return Result.success("保存成功");

    }

    @GetMapping("/search")
    public Result<?> searchByName(@RequestParam (value = "pageNo") Long pageNo,
                                  @RequestParam(value = "pageSize") Long pageSize,
                                  @RequestParam(value = "teacherName") String teacherName){
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(teacherName!=null,Teacher::getTeacherName,teacherName);
        Page<Teacher> page=new Page(pageNo,pageSize);
        teacherService.page(page,wrapper);
        return Result.success(page,"查询成功");
    }

    @GetMapping("/{id}")
    public Result<String> delete(@PathVariable("id") Integer id){
        teacherService.removeById(id);
        return Result.success("删除成功");
    }
}

package com.lantu.modulName.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lantu.modulName.entity.Teacher;
import com.lantu.modulName.mapper.TeacherMapper;
import com.lantu.modulName.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>implements TeacherService {

}

package com.lantu.modulName.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lantu.modulName.entity.Competition;
import com.lantu.modulName.mapper.CompetitionMapper;
import com.lantu.modulName.service.CompetitionService;
import org.springframework.stereotype.Service;

@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
}

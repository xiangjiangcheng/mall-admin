package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.system.mapper.LogMapper;
import com.river.malladmin.system.model.entity.Log;
import com.river.malladmin.system.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @author xiang
 * @description 针对表【sys_log(系统日志表)】的数据库操作Service实现
 * @createDate 2025-03-24 22:23:11
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}





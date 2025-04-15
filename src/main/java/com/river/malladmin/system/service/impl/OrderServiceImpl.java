package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.system.model.entity.Order;
import com.river.malladmin.system.service.OrderService;
import com.river.malladmin.system.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
 * @author xiang
 * @description 针对表【biz_order】的数据库操作Service实现
 * @createDate 2025-04-15 21:53:08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}





package com.river.malladmin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.river.malladmin.system.model.entity.Product;
import com.river.malladmin.system.service.ProductService;
import com.river.malladmin.system.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
 * @author xiang
 * @description 针对表【biz_product】的数据库操作Service实现
 * @createDate 2025-04-15 21:53:14
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}





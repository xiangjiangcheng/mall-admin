package com.river.malladmin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.system.model.entity.DictData;
import com.river.malladmin.system.model.query.DictDataPageQuery;
import com.river.malladmin.system.model.vo.DictDataPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典数据映射层
 *
 * @author Ray Hao
 * @since 2.9.0
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

    /**
     * 字典数据分页列表
     */
    Page<DictDataPageVO> getDictDataPage(Page<DictDataPageVO> page, DictDataPageQuery queryParams);
}





package com.river.malladmin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.system.model.entity.Dict;
import com.river.malladmin.system.model.query.DictPageQuery;
import com.river.malladmin.system.model.vo.DictPageVO;
import com.river.malladmin.system.model.vo.DictVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典 访问层
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 字典分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 字典分页列表
     */
    Page<DictPageVO> getDictPage(Page<DictPageVO> page, DictPageQuery queryParams);

    /**
     * 获取字典列表（包含字典数据）
     *
     * @return 字典列表
     */
    List<DictVO> getAllDictWithData();

}





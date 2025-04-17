package com.river.malladmin.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.river.malladmin.system.model.entity.Dict;
import com.river.malladmin.system.model.form.DictForm;
import com.river.malladmin.system.model.vo.DictPageVO;
import org.mapstruct.Mapper;

/**
 * 字典 对象转换器
 *
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Page<DictPageVO> toPageVo(Page<Dict> page);

    DictForm toForm(Dict entity);

    Dict toEntity(DictForm entity);
}

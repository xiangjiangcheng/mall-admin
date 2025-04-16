package com.river.malladmin.system.model.query;

import com.river.malladmin.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "分类分页查询对象")
@EqualsAndHashCode(callSuper = true)
public class CategoryPageQuery extends BasePageQuery {

    @Schema(description = "关键字(分类名称)")
    private String keywords;

}

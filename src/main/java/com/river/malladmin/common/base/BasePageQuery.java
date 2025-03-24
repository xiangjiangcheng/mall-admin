package com.river.malladmin.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author JiangCheng Xiang
 */
@Data
@Schema(description = "基础分页查询对象")
public class BasePageQuery {

    @Schema(description = "页码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private int pageNum = 1;

    @Schema(description = "每页记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private int pageSize = 10;

    public static <T> Page<T> page(BasePageQuery query) {
        return new Page<>(query.getPageNum(), query.getPageSize());
    }

}

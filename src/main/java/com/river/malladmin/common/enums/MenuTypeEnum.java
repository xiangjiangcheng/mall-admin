package com.river.malladmin.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author JiangCheng Xiang
 */
@Getter
public enum MenuTypeEnum {

    MENU(1, "菜单"),
    CATALOG(2, "目录"),
    EXT_LINK(3, "外链"),
    BUTTON(4, "按钮");

    @EnumValue
    private final Integer value;
    private final String label;

    MenuTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}

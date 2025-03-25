package com.river.malladmin.common.contant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum Environ {
    LOCAL,
    DEV,
    TEST,
    QA,
    PROD;
    private static final Map<String, Environ> DATA_HOLDER = Arrays.stream(Environ.values())
            .collect(Collectors.toMap(Environ::name, m -> m));

    public static Environ parse(String name) {
        return DATA_HOLDER.get(name.toUpperCase());
    }
}

package com.mwh.config;

import com.mwh.Enum.UserEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @Author admin
 * @Description 枚举转换器
 * @Date 2026/05/19/12:38
 * @Version 1.0
 */

@Component
public class StringToUserEnumConverter implements Converter<String, UserEnum> {
    @Override
    public UserEnum convert(String source) {
        try {
            Integer code = Integer.valueOf(source);
            return UserEnum.fromCode(code);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

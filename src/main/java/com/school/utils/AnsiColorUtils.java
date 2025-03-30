package com.school.utils;

import com.school.common.content.AnsiColorContent;
import org.springframework.stereotype.Component;

@Component
public class AnsiColorUtils {

    /**
     * 为文本添加颜色
     * @param color ANSI颜色代码
     * @param text 要着色的文本
     * @return 带颜色的文本
     */
    public String withColor(String color, String text) {
        return color + text + AnsiColorContent.ANSI_RESET;
    }
}
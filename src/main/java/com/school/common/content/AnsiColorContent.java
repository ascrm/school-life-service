package com.school.common.content;

/**
 * @Author: ascrm
 * @Date: 2025/3/26
 * ANSI转义序列颜色代码
 */
public class AnsiColorContent {

    //文本颜色
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";    // 青色
    public static final String ANSI_PURPLE = "\u001B[35m";   // 品红/紫色

    // 高亮色（加粗版本）
    public static final String ANSI_BRIGHT_BLUE = "\u001B[94m";
    public static final String ANSI_BRIGHT_RED = "\u001B[91m";
    public static final String ANSI_BRIGHT_GREEN = "\u001B[92m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    public static final String ANSI_BRIGHT_CYAN = "\u001B[96m";
    public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    public static final String ANSI_BRIGHT_WHITE = "\u001B[97m";

    // 背景色（底色）
    public static final String ANSI_BG_BLACK = "\u001B[40m";
    public static final String ANSI_BG_RED = "\u001B[41m";
    public static final String ANSI_BG_GREEN = "\u001B[42m";
    public static final String ANSI_BG_YELLOW = "\u001B[43m";
    public static final String ANSI_BG_BLUE = "\u001B[44m";
    public static final String ANSI_BG_PURPLE = "\u001B[45m";
    public static final String ANSI_BG_CYAN = "\u001B[46m";
    public static final String ANSI_BG_WHITE = "\u001B[47m";

    // 文字样式
    public static final String ANSI_BOLD = "\u001B[1m";       // 加粗
    public static final String ANSI_UNDERLINE = "\u001B[4m";   // 下划线
    public static final String ANSI_BLINK = "\u001B[5m";       // 闪烁（部分终端不支持）
    public static final String ANSI_REVERSE = "\u001B[7m";    // 反色（前景背景反转）
} 
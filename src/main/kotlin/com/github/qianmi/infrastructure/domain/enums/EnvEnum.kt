package com.github.qianmi.infrastructure.domain.enums

/**
 * 环境
 */
enum class EnvEnum(
    val envName: String,
    val envCode: String,
) {
    LAKALA("Lakala", "68"),
    TEST0("Test0", "17"),
    TEST1("Test1", "14"),
    TEST2("Test2", "18"),
    TEST4("Test4", "24"),
    TEST5("Test5", "37"),
    TEST6("Test6", "69"),
    TEST7("Test7", "70"),
    DEV("Dev", "12"),
    PROD("Prod", "11"),

}
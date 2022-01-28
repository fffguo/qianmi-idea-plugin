package com.github.qianmi.infrastructure.domain.enums

/**
 * 环境
 */
enum class BugattiTaskTaskEnum(
    val taskId: String,
    val taskName: String,

    ) {
    LAKALA("deploy", "安装应用"),
    TEST0("1202613", "17"),
    TEST1("1202614", "安装Ansible依赖"),
    TEST2("1202615", "18"),
    TEST4("Test4", "24"),
    TEST5("Test5", "37"),
    TEST6("Test6", "69"),
    TEST7("Test7", "70"),
    DEV("Dev", "12"),
    PROD("Prod", "11"),

}
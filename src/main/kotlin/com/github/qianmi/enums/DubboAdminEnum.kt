package com.github.qianmi.enums

/**
 * dubbo admin环境
 * 账号 root
 * 密码 master123
 */
enum class DubboAdminEnum(val env: EnvEnum, val domain: String) {
    TEST0(EnvEnum.TEST0, "http://172.19.67.104:8080"),
    TEST1(EnvEnum.TEST1, "http://172.19.66.25:8080"),
    TEST2(EnvEnum.TEST2, "http://172.19.67.127:8080"),
    TEST4(EnvEnum.TEST4, "http://172.19.68.8:8080"),
    TEST5(EnvEnum.TEST5, "http://172.21.33.162:8080"),
    DEV(EnvEnum.DEV, "http://172.19.65.13:8080"),
    PROD(EnvEnum.PROD, "http://dubbomanager.dev.qianmi.com")
}